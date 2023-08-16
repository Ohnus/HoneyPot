package com.example.demo.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;

@EnableScheduling
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	private ImportAccessToken accessTokenAPI;

	@Autowired
	private ImportBillingKey billingKeyAPI;

	@Autowired
	private ImportPayments paymentsAPI;

//	@Autowired
//	private WithdrawlNh withdrawlAPI;

	@Autowired
	private PaymentService pservice;

	@Autowired
	private MemberService mservice;

	// 결제수단 등록 or 변경
	@PostMapping("/registerCards")
	public Map saveBillingKey(String cardNum, String cardExpiry, String birth, String cardPwd, String billingKey,
			String userNum) {
		// 클라이언트 사이드에서 카드정보 + 유저빌링키 서버사이드로 전송
		System.out.println("카드번호: " + cardNum);
		System.out.println("유효기간: " + cardExpiry);
		System.out.println("생년월일: " + birth);
		System.out.println("비밀번호: " + cardPwd);
		System.out.println("유저빌링키: " + billingKey);
		System.out.println("회원번호: " + userNum);
		String withdrawl = "";
		Map map = new HashMap<>();

		try {
			// api key, secret key로 메서드에서 인증토큰 발급..
			String accessToken = accessTokenAPI.getAccessToken();

			// 카드정보 + 유저빌링키 + 인증토큰으로 pg사 빌링키 발급..
			String customerUid = billingKey; // 유저빌링키

			// pg사 빌링키.. 사실 내가 쓸 일은 없지만 값 잘 받아오나 궁금해서 메서드 호출함
			String pgBillingKey = billingKeyAPI.getBillingKey(accessToken, customerUid, cardNum, cardExpiry, birth,
					cardPwd);
			System.out.println(pgBillingKey);
			// 출금 테스트
//			withdrawl = withdrawlAPI.withdrawl();
//			System.out.println("출금: " + withdrawl);

			// 유저빌링키 유저정보에 저장.. 유경누나랑 얘기해보고 서비스 불러와야지..
			MemberDto mdto = mservice.getByUserNum(userNum);
			System.out.println("유저빌링키 저장 또는 갱신 전: " + mdto.getBillingKey());
			mdto.setBillingKey(billingKey);
			mdto = mservice.save(mdto);
			System.out.println("유저빌링키 저장 또는 갱신 후: " + mdto.getBillingKey());

			map.put("msg", pgBillingKey);
			map.put("accessToken", accessToken);
//			map.put("withdrawl", withdrawl);
		} catch (Exception e) {
			map.put("msg", "실패" + e.getMessage());
		}

		return map;
	}

	// 결제수단 삭제: PG사 DB에서 빌링키 삭제 + 회원정보에서 빌링키 삭제
	@PostMapping("/deleteCards")
	public Map delBillingKey(String userNum) {
		Map map = new HashMap<>();

		try {
			MemberDto mdto = mservice.getByUserNum(userNum);

			String accessToken = accessTokenAPI.getAccessToken();
			String customerUid = mdto.getBillingKey();

			// PG사 DB에서 빌링키 삭제
			String delPgBillingKey = billingKeyAPI.delBillingKey(accessToken, customerUid, userNum);

			// 회원정보에서 빌링키 삭제(0)
			System.out.println("유저빌링키 삭제 전: " + mdto.getBillingKey());
			mdto.setBillingKey("0");
			mdto = mservice.save(mdto);
			System.out.println("유저빌링키 삭제 후: " + mdto.getBillingKey());

			map.put("msg", "성공");
		} catch (Exception e) {
			map.put("msg", "실패" + e.getMessage());
		}

		return map;
	}

	// 파티 생성, 찾기 마무리 단계에서 100원 결제/환불
	// again api + 유저빌링키 이용하기
	@PostMapping("/add100")
	public Map add100(PaymentDto dto) {

		Map map = new HashMap<>();
		PaymentDto newDto;

		String accessToken = accessTokenAPI.getAccessToken();
		String customerUid = dto.getUserNum().getBillingKey();
		String merchantUid = dto.getOrderNum();
		int amount = dto.getTotalPayment();
		String name = dto.getHistory();

		int pay100 = 1;
		// 클라이언트 사이드에서 결제금액 조작하는 지 검증하기
		if (amount == 100) {
			// 결제금액 맞으면 결제/환불 진행
			pay100 = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);
			if (pay100 == 0) {
				int checksum = amount;
				int cancel100 = paymentsAPI.cancelPayment(accessToken, merchantUid, amount, checksum);
				newDto = pservice.save(dto);
				map.put("checkMsg", "결제수단 유효성이 확인되었습니다.");
				if (cancel100 == 2) {
					newDto.setHistory("결제수단 유효성 - 환불완료");
					newDto = pservice.save(newDto);
				} else {
					newDto.setHistory("결제수단 유효성 - 환불실패");
					newDto = pservice.save(newDto);
				}
				map.put("nextStep", pay100);
				map.put("newDto", newDto);
			} else {
				map.put("checkMsg", "결제수단 유효성 확인에 실패하였습니다.");
				map.put("nextStep", pay100);
			}
		} else {
			map.put("checkMsg", "결제수단 유효성 확인에 실패하였습니다.");
			map.put("nextStep", pay100);
		}

		return map;
	}

	// 100원 결제 내역 게시글번호, 마지막회차, 정기결제일 부분 수정
	@PostMapping("/update100")
	public Map update100(PaymentDto dto) {

		Map map = new HashMap<>();
		PaymentDto newDto = pservice.getPayment(dto.getOrderNum());

		System.out.println("업데이트 전 dto: " + newDto);

		newDto.setBoardNum(dto.getBoardNum());
		newDto.setFinalInstallment(dto.getFinalInstallment());
		newDto.setRecurringDay(dto.getRecurringDay());
		newDto = pservice.save(newDto);

		System.out.println("업데이트 후 dto: " + newDto);

		map.put("newDto", newDto);

		return map;
	}

	// 정기결제.. scheduled annotation.. localdate + recurringday 비교
	// 월구독료 기준으로 보증금, 수수료 계산해서 유저빌링키 이용해서 정기결제!
	// 예약 api를 쓸까, scheduled + again api를 쓸까 고민..
//	@Scheduled(cron = "0 0 0 * * ?")
	@PostMapping("/recurringPayments")
	public void recurring() {
		LocalDateTime today = LocalDateTime.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		int nextMonth = today.getMonthValue() + 1;
		int recurringDay = today.getDayOfMonth();
		int hh = today.getHour();
		int mm = today.getMinute();
		int ss = today.getSecond();
		String date = String.format("%02d/%02d/%02d", year % 100, month, recurringDay);
		String next = String.format("%02d/%02d/%02d", year % 100, nextMonth, recurringDay);
		DateTimeFormatter changePattern = DateTimeFormatter.ofPattern("yy/MM/dd");
		LocalDate payDate = LocalDate.parse(date, changePattern);
		LocalDate nextDate = LocalDate.parse(next, changePattern);

		// recurringday & pay_installment(결제회차)=0인 애들 리스트 뽑아
		ArrayList<PaymentDto> rcList = pservice.getByRcrDay(recurringDay);
		String accessToken = "";
		String customerUid = "";
		String merchantUid = "";
		String orderNum = "";
		int deposit = 0;
		int monthPrice = 0;
		int commission = 0;
		int totalPayment = 0;
		int amount = 0;
		String name = "";
		int payments = 0;
		for (int i = 0; i < rcList.size(); i++) {
			PaymentDto dto = rcList.get(i);
			// 그리고 결제 회원이랑 결제된 게시글의 회원이 같으면(파티장이면)
			System.out.println("결제할 회원은 누구?: " + dto.getUserNum().getUserNum());
			System.out.println("파티장은 누구?: " + dto.getBoardNum().getUserNum().getUserNum());
			if (dto.getUserNum().getUserNum() == dto.getBoardNum().getUserNum().getUserNum()) {
				accessToken = accessTokenAPI.getAccessToken();
				customerUid = dto.getUserNum().getBillingKey();
				System.out.println("파티장 빌링키: " + customerUid);
				merchantUid = "HNP" + String.valueOf(year).substring(2) + String.format("%02d", month)
						+ String.format("%02d", recurringDay) + String.format("%02d", hh) + String.format("%02d", mm)
						+ String.format("%02d", i);
				orderNum = merchantUid;
				deposit = dto.getBoardNum().getMonthPrice() * dto.getBoardNum().getMaxPpl();
				commission = (int) (dto.getBoardNum().getMonthPrice() * 0.1);
				totalPayment = deposit + commission;
				amount = totalPayment;
				name = "1회차 결제";
				payments = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);

				dto.setPayInstallment(-1);
				dto = pservice.save(dto);
				PaymentDto newDto = pservice.save(dto);
				newDto.setOrderNum(orderNum);
				newDto.setPayInstallment(1);
				newDto.setPayDate(payDate);
				newDto.setNextDate(nextDate);
				newDto.setTotalPayment(totalPayment);
				newDto.setDeposit(deposit);
				newDto.setMonthPrice(0);
				newDto.setCommission(commission);
				newDto.setHistory(name);
				newDto.setOttType(dto.getBoardNum().getType().getType());
				newDto = pservice.save(newDto);
				// 파티원이면,
			} else {
				accessToken = accessTokenAPI.getAccessToken();
				customerUid = dto.getUserNum().getBillingKey();
				System.out.println("파티원 빌링키: " + customerUid);
				merchantUid = "HNP" + String.valueOf(year).substring(2) + String.format("%02d", month)
						+ String.format("%02d", recurringDay) + String.format("%02d", hh) + String.format("%02d", mm)
						+ String.format("%02d", i + rcList.size());
				orderNum = merchantUid;
				deposit = dto.getBoardNum().getMonthPrice() * 2;
				monthPrice = dto.getBoardNum().getMonthPrice();
				commission = (int) (dto.getBoardNum().getMonthPrice() * 0.2);
				totalPayment = deposit + monthPrice + commission;
				amount = totalPayment;
				name = "1회차 결제";
				payments = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);

				dto.setPayInstallment(-1);
				dto = pservice.save(dto);
				PaymentDto newDto = pservice.save(dto);
				newDto.setOrderNum(orderNum);
				newDto.setPayInstallment(1);
				newDto.setPayDate(payDate);
				newDto.setNextDate(nextDate);
				newDto.setTotalPayment(totalPayment);
				newDto.setDeposit(deposit);
				newDto.setMonthPrice(monthPrice);
				newDto.setCommission(commission);
				newDto.setHistory(name);
				newDto.setOttType(dto.getBoardNum().getType().getType());
				newDto = pservice.save(newDto);
			}
		}

		// 오늘(결제일 = payDate)이 nextDate(다음결제일)이면서 결제회차가 마지막회차인 애들 뽑아
		ArrayList<PaymentDto> ndList = pservice.getByNextDate(nextDate);
		for (int i = 0; i < ndList.size(); i++) {
			PaymentDto ndDto = ndList.get(i);
			System.out.println("결제할 회원은?: " + ndDto.getUserNum().getUserNum());
			System.out.println("파티장은 누구?: " + ndDto.getBoardNum().getUserNum().getUserNum());
			System.out.println("결제할 회차는?: " + ndDto.getPayInstallment());
			// 파티장이라면,
			if (ndDto.getUserNum().getUserNum() == ndDto.getBoardNum().getUserNum().getUserNum()) {
				accessToken = accessTokenAPI.getAccessToken();
				customerUid = ndDto.getUserNum().getBillingKey();
				System.out.println(ndDto.getPayInstallment() + "회차 파티장 빌링키: " + customerUid);
				merchantUid = "HNP" + String.valueOf(year).substring(2) + String.format("%02d", month)
						+ String.format("%02d", recurringDay) + String.format("%02d", hh) + String.format("%02d", mm)
						+ String.format("%02d", i + rcList.size() + ndList.size());
				orderNum = merchantUid;
				commission = (int) (ndDto.getBoardNum().getMonthPrice() * 0.1);
				totalPayment = commission;
				amount = totalPayment;
				name = ndDto.getPayInstallment() + "회차 결제";
				payments = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);
				
				PaymentDto newDto = pservice.save(ndDto);
				newDto.setOrderNum(orderNum);
				newDto.setPayInstallment(ndDto.getPayInstallment() + 1);
				newDto.setPayDate(payDate);
				newDto.setNextDate(nextDate);
				newDto.setTotalPayment(totalPayment);
				newDto.setDeposit(0);
				newDto.setCommission(commission);
				newDto.setHistory(name);
				newDto = pservice.save(newDto);
				// 파티원이면,
			} else {
				accessToken = accessTokenAPI.getAccessToken();
				customerUid = ndDto.getUserNum().getBillingKey();
				System.out.println(ndDto.getPayInstallment() + "회차 파티장 빌링키: " + customerUid);
				merchantUid = "HNP" + String.valueOf(year).substring(2) + String.format("%02d", month)
						+ String.format("%02d", recurringDay) + String.format("%02d", hh) + String.format("%02d", mm)
						+ String.format("%02d", i + rcList.size() + ndList.size() * 2);
				orderNum = merchantUid;
				monthPrice = ndDto.getMonthPrice();
				commission = (int) (ndDto.getBoardNum().getMonthPrice() * 0.2);
				totalPayment = monthPrice + commission;
				amount = totalPayment;
				name = ndDto.getPayInstallment() + "회차 결제";
				payments = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);
				
				PaymentDto newDto = pservice.save(ndDto);
				newDto.setOrderNum(orderNum);
				newDto.setPayInstallment(ndDto.getPayInstallment() + 1);
				newDto.setPayDate(payDate);
				newDto.setNextDate(nextDate);
				newDto.setTotalPayment(totalPayment);
				newDto.setDeposit(0);
				newDto.setMonthPrice(monthPrice);
				newDto.setCommission(commission);
				newDto.setHistory(name);
				newDto = pservice.save(newDto);
			}
		}
	}
}
