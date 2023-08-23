package com.example.demo.payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cash.CashDto;
import com.example.demo.cash.CashService;
import com.example.demo.hostboard.HostBoardService;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import com.example.demo.partygroup.PartyGroupService;
import com.example.demo.pending.PendingDto;
import com.example.demo.pending.PendingService;

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

	@Autowired
	private LocalDateService localDateService;

//	@Autowired
//	private WithdrawlNh withdrawlAPI;

	@Autowired
	private PaymentService pservice;

	@Autowired
	private PendingService pdservice;

	@Autowired
	private CashService cashservcie;

	@Autowired
	private MemberService mservice;
	
	@Autowired
	private PartyGroupService PGservice;
	
	@Autowired
	private HostBoardService HBservice;

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

	// 파티 생성 직전 단계에서 보증금+수수료 결제
	// 파티장 보증금 = 앞단에서 불러온 ott 구독료
	// again api + 유저빌링키 이용하기
	@PostMapping("/leaderDeposits")
	public Map leaderDeposit(PaymentDto dto) {

		Map map = new HashMap<>();
		PaymentDto newDto;

		String accessToken = accessTokenAPI.getAccessToken();
		String customerUid = dto.getUserNum().getBillingKey();
		String merchantUid = dto.getOrderNum();
		int deposit = dto.getDeposit();
		int commission = dto.getCommission();
		int totalPayment = dto.getTotalPayment();
		int amount = totalPayment;
		String name = dto.getHistory();

		String paymentStatus = "";
		// 결제금액 맞으면 결제/환불 진행
		paymentStatus = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);
		System.out.println("결제여부: " + paymentStatus);
		if (paymentStatus.equals("paid")) {
			System.out.println("paid 인식함");
			map.put("checkMsg", "보증금 및 수수료가 결제되었습니다.");
			newDto = pservice.save(dto);
			map.put("paymentStatus", paymentStatus); // paid
		} else {
			System.out.println("paid 인식못함");
			map.put("checkMsg", "보증금 및 수수료 결제에 실패했습니다.");
			map.put("paymentStatus", paymentStatus); // ready, failed, canceled
		}
		return map;
	}

	// 파티장 보증금 및 수수료 결제 후 게시글번호, 마지막회차, 정기결제일 부분 업데이트
	// + pending table에 파티장 보증금 저장
	@PostMapping("/updateLeaderDeposits")
	public Map updateLeaderDeposit(PaymentDto dto) {

		Map map = new HashMap<>();
		PaymentDto newDto = pservice.getPayment(dto.getOrderNum());

		System.out.println("업데이트 전 dto: " + newDto);

		newDto.setBoardNum(dto.getBoardNum());
		newDto.setFinalInstallment(dto.getFinalInstallment());
		newDto.setRecurringDay(dto.getRecurringDay());
		newDto = pservice.save(newDto);

		System.out.println("업데이트 후 dto: " + newDto);

		map.put("newDto", newDto);

		// 파티장 보증금
		int amount = dto.getBoardNum().getType().getPrice();
		PendingDto depositDto = new PendingDto(0, dto.getBoardNum(), dto.getUserNum(), null,
				dto.getBoardNum().getSubEnd(), amount, 1);

		return map;
	}

	// 파티원 보증금 결제/저장 + pending table에 파티원 보증금 저장
	// 
	// 앞단에서 dto 정보 다 보내기
	// 회원번호, 글번호, 마지막회차, 실제결제일, 보증금, ott 타입
	@PostMapping("/memberDeposits")
	public Map memberDeposit(PaymentDto dto) {
		LocalDate payDate = localDateService.getPayDate();
		LocalDate nextDate = localDateService.getNextDate();
		
		Map map = new HashMap<>();
		PaymentDto newDto;
		String accessToken = accessTokenAPI.getAccessToken();
		String customerUid = dto.getUserNum().getBillingKey();
		String merchantUid = dto.getOrderNum();
		int amount = dto.getDeposit();
		String name = dto.getHistory();
		String paymentStatus = "";
		// 현재 결제일이 파티 시작일보다 이전일 때,
		// 보증금만 결제 및 0회차 등록
		if(dto.getPayDate().compareTo(dto.getBoardNum().getSubStart()) > 0) {
			paymentStatus = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);
			System.out.println("결제여부: " + paymentStatus);
			if (paymentStatus.equals("paid")) {
				System.out.println("paid 인식함");
				map.put("checkMsg", "보증금이 결제되었습니다.");
				
				// 
				newDto = pservice.save(dto);

				// 파티원 보증금
				PendingDto depositDto = new PendingDto(0, dto.getBoardNum(), dto.getUserNum(), null,
						dto.getBoardNum().getSubEnd(), amount, 1);

				map.put("paymentStatus", paymentStatus); // paid
			} else {
				System.out.println("paid 인식못함");
				map.put("checkMsg", "보증금 결제에 실패했습니다.");
				map.put("paymentStatus", paymentStatus); // ready, failed, canceled
			}
		// 현재 결제일이 파티 시작일보다 이후일 때..
		} else if(dto.getPayDate().compareTo(dto.getBoardNum().getSubStart()) < 0) {
			// 7월15일 시작 파티인데 현재는 8월23일
			// 보증금 및 (30 - ((현재 결제일) - (파티 시작일의 일))) * 일할구독료 결제
			// 7월26일 시작 파티인데 현재는 8월23일
			// 보증금 및 ((파티 시작일의 일) - (현재 결제일)) * 일할구독료 결제
		
		// 현재 결제일과 파티 시작일이 같을 때,
		} else {
			
		}

		return map;
	}

	
	// 정기결제.. scheduled annotation.. localdate + recurringday 비교
	// 월구독료 기준으로 보증금, 수수료 계산해서 유저빌링키 이용해서 정기결제!
//	@Scheduled(cron = "0 0 0 * * ?")
	@PostMapping("/recurringPayments")
	public void recurring() {
//		LocalDateTime today = LocalDateTime.now();
//		int year = today.getYear();
//		int month = today.getMonthValue();
//		int nextMonth = today.getMonthValue() + 1;
//		int recurringDay = today.getDayOfMonth();
//		int hh = today.getHour();
//		int mm = today.getMinute();
//		int ss = today.getSecond();
//		String date = String.format("%02d/%02d/%02d", year % 100, month, recurringDay);
//		String next = String.format("%02d/%02d/%02d", year % 100, nextMonth, recurringDay);
//		DateTimeFormatter changePattern = DateTimeFormatter.ofPattern("yy/MM/dd");
//		LocalDate payDate = LocalDate.parse(date, changePattern);
//		LocalDate nextDate = LocalDate.parse(next, changePattern);
//		LocalDate againDate = payDate.plusWeeks(1);

		LocalDate payDate = localDateService.getPayDate();
		LocalDate nextDate = localDateService.getNextDate();
		LocalDate againDate = localDateService.getAgainDate();
		LocalDate earningDate = localDateService.getEarningDate(payDate);
		int recurringDay = localDateService.getRecurringDay();

		// recurringday & pay_installment(결제회차)=0인 애들 리스트 뽑아
		ArrayList<PaymentDto> rcList = pservice.getByRcrDay(recurringDay);
		String accessToken = "";
		String customerUid = "";
		String merchantUid = "";
		String orderNum = "";
		int monthPrice = 0;
		int commission = 0;
		int totalPayment = 0;
		int amount = 0;
		String name = "";
		String paymentStatus = "";
		try {
			for (int i = 0; i < rcList.size(); i++) {
				PaymentDto dto = rcList.get(i);
				System.out.println("결제할 회원은 누구?: " + dto.getUserNum().getUserNum());
				System.out.println("파티장은 누구?: " + dto.getBoardNum().getUserNum().getUserNum());
				if (dto.getUserNum().getUserNum().equals(dto.getBoardNum().getUserNum().getUserNum())) {
					// 그리고 결제 회원이랑 결제된 게시글의 회원이 같으면(파티장이면)
					dto.setPayInstallment(-1);
					dto = pservice.save(dto);
					PaymentDto newDto = pservice.save(dto);
				} else {
					// 파티원이면,
					accessToken = accessTokenAPI.getAccessToken();
					customerUid = dto.getUserNum().getBillingKey();
					System.out.println("파티원 빌링키: " + customerUid);
					merchantUid = localDateService.getMerchantUid() + String.format("%02d", i);
					orderNum = merchantUid;
					monthPrice = dto.getBoardNum().getMonthPrice();
					commission = (int) (dto.getBoardNum().getMonthPrice() * 0.2);
					totalPayment = monthPrice + commission;
					amount = totalPayment;
					name = "1회차 결제";
					paymentStatus = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);

					if (paymentStatus.equals("paid")) {
						dto.setPayInstallment(-1);
						dto = pservice.save(dto);
						PaymentDto newDto = pservice.save(dto);
						newDto.setOrderNum(orderNum);
						newDto.setPayInstallment(1);
						newDto.setPayDate(payDate);
						newDto.setNextDate(nextDate);
						newDto.setTotalPayment(totalPayment);
						newDto.setDeposit(0);
						newDto.setMonthPrice(monthPrice);
						newDto.setCommission(commission);
						newDto.setHistory(name);
						newDto.setOttType(dto.getBoardNum().getType().getType());
						newDto = pservice.save(newDto);
						// 파티원 구독료
						PendingDto monthPriceDto = new PendingDto(0, dto.getBoardNum(), dto.getUserNum(), earningDate,
								null, monthPrice, 0);
					} else {
						dto.setPayInstallment(-1);
						dto = pservice.save(dto);
						PaymentDto newDto = pservice.save(dto);
						newDto.setOrderNum(orderNum);
						newDto.setPayInstallment(1);
						newDto.setPayDate(payDate);
						newDto.setNextDate(nextDate);
						newDto.setAgainDate(againDate);
						newDto.setTotalPayment(totalPayment);
						newDto.setDeposit(0);
						newDto.setMonthPrice(monthPrice);
						newDto.setCommission(commission);
						newDto.setHistory("결제 실패");
						newDto.setPaymentStatus(-1);
						newDto.setOttType(dto.getBoardNum().getType().getType());
						newDto = pservice.save(newDto);
					}
				}
			}

			// 오늘(결제일 = payDate)이 nextDate(다음결제일)이면서 결제회차 < 마지막회차이고 결제여부 0인 애들 뽑아
			ArrayList<PaymentDto> ndList = pservice.getByNextDate(nextDate);
			for (int i = 0; i < ndList.size(); i++) {
				PaymentDto ndDto = ndList.get(i);
				System.out.println("결제할 회원은?: " + ndDto.getUserNum().getUserNum());
				System.out.println("파티장은 누구?: " + ndDto.getBoardNum().getUserNum().getUserNum());
				System.out.println("결제할 회차는?: " + ndDto.getPayInstallment());
				// 파티원이라면,
				if (!ndDto.getUserNum().getUserNum().equals(ndDto.getBoardNum().getUserNum().getUserNum())) {
					accessToken = accessTokenAPI.getAccessToken();
					customerUid = ndDto.getUserNum().getBillingKey();
					System.out.println(ndDto.getPayInstallment() + "회차 파티원 빌링키: " + customerUid);
					merchantUid = localDateService.getMerchantUid() + rcList.size() + String.format("%02d", i);
					orderNum = merchantUid;
					monthPrice = ndDto.getMonthPrice();
					commission = (int) (ndDto.getBoardNum().getMonthPrice() * 0.2);
					totalPayment = monthPrice + commission;
					amount = totalPayment;
					name = ndDto.getPayInstallment() + "회차 결제";
					paymentStatus = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);

					if (paymentStatus.equals("paid")) {
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
						// 파티원 구독료
						PendingDto monthPriceDto = new PendingDto(0, ndDto.getBoardNum(), ndDto.getUserNum(),
								earningDate, null, monthPrice, 0);
					} else {
						PaymentDto newDto = pservice.save(ndDto);
						newDto.setOrderNum(orderNum);
						newDto.setPayInstallment(ndDto.getPayInstallment() + 1);
						newDto.setPayDate(payDate);
						newDto.setNextDate(nextDate);
						newDto.setAgainDate(againDate);
						newDto.setTotalPayment(totalPayment);
						newDto.setDeposit(0);
						newDto.setMonthPrice(monthPrice);
						newDto.setCommission(commission);
						newDto.setHistory(name + " 실패");
						newDto.setPaymentStatus(-1);
						newDto = pservice.save(newDto);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	정기결제일에 결제 실패한 유저 재결제 시도(결제여부 -1)
//	@Scheduled(cron = "0 0 0 * * ?")
	@PostMapping("/againDates")
	public void paymentAgain() {
		LocalDate againDate = localDateService.getAgainDate();

		ArrayList<PaymentDto> againList = pservice.getByAgainList(againDate);

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
		String paymentStatus = "";
		for (int i = 0; i < againList.size(); i++) {
			PaymentDto againDto = againList.get(i);
			LocalDate earningDate = localDateService.getEarningDate(againDto.getPayDate());

			// 파티원이면,
			if (!againDto.getUserNum().getUserNum().equals(againDto.getBoardNum().getUserNum().getUserNum())) {
				accessToken = accessTokenAPI.getAccessToken();
				customerUid = againDto.getUserNum().getBillingKey();
				merchantUid = localDateService.getMerchantUid() + String.format("%02d", i);
				orderNum = merchantUid;
				monthPrice = againDto.getMonthPrice();
				commission = againDto.getCommission();
				totalPayment = againDto.getTotalPayment();
				amount = totalPayment;
				name = againDto.getPayInstallment() + "회차 결제";
				paymentStatus = paymentsAPI.doPayment(accessToken, customerUid, merchantUid, amount, name);

				if (paymentStatus.equals("paid")) {
					// 재결제가 성공적이면,
					againDto.setOrderNum(orderNum);
					againDto.setPayInstallment(againDto.getPayInstallment() + 1);
					againDto.setAgainDate(null);
					againDto.setHistory(name);
					againDto.setPaymentStatus(0);
					// 파티원 구독료
					PendingDto monthPriceDto = new PendingDto(0, againDto.getBoardNum(), againDto.getUserNum(),
							earningDate, null, monthPrice, 0);
				} else {
					// 재결제도 실패하면,
					againDto.setOrderNum(orderNum);
					againDto.setNextDate(null);
					againDto.setAgainDate(null);
					againDto.setHistory(name + " 최종 실패");
					againDto.setPaymentStatus(-2); // -2는 회생불가
					
					// 보증금 => 위약금(파티장에게)
					PendingDto pdDto = pdservice.getDeposit(againDto.getBoardNum(), againDto.getUserNum());
					pdDto.setMsg(2);
					pdDto = pdservice.save(pdDto);
					LocalDate addDate = againDate;
					// 파티장에게 적립
					CashDto cashDto = new CashDto(0, pdDto.getBoardNum().getUserNum(), addDate, "위약금 입금", pdDto.getAmount());

					// Start 1 => 3으로 변경해서 파티에서 제외
					PGservice.editStartTo3(againDto.getBoardNum().getBoardNum(), againDto.getUserNum().getUserNum());
					
					// max=4라고 가정, 탈주로 인해 4->3이나 3->2나 어차피 ing는 0으로 가야됨
					HBservice.changIngToZero(againDto.getBoardNum().getBoardNum());
				
				}
			}
		}
	}

//	Pending table에서 Cash table로 구독료 적립(파티원 => 파티장)
//	@Scheduled(cron = "0 0 0 * * ?")
	@PostMapping("/earnings")
	public void earningCash() {
		// getPayDate = 오늘 날짜 구하는 메서드
		LocalDate earningDate = localDateService.getPayDate();
		LocalDate addDate = earningDate;
		ArrayList<PendingDto> earningList = pdservice.getEarningList(earningDate);

		for (int i = 0; i < earningList.size(); i++) {
			PendingDto pdDto = earningList.get(i);
			// 각 유저의 구독료를 파티장 캐시테이블에 적립
			CashDto cashDto = new CashDto(0, pdDto.getBoardNum().getUserNum(), addDate, "구독료 입금", pdDto.getAmount());
			pdDto.setMsg(2); // 돈나감
			pdDto = pdservice.save(pdDto); // 돈나감 업데이트
		}
	}

//	Pending table에서 Cash table로 보증금 정상 반환(파티 무사히 끝남)
//	@Scheduled(cron = "0 0 0 * * ?")
	@PostMapping("/return")
	public void returnCash() {
		// getPayDate = 오늘 날짜 구하는 메서드
		LocalDate returnDate = localDateService.getPayDate();
		LocalDate addDate = returnDate;
		ArrayList<PendingDto> returnList = pdservice.getReturnList(returnDate);

		for (int i = 0; i < returnList.size(); i++) {
			PendingDto pdDto = returnList.get(i);
			// 각자의 보증금 반환일에 반환(반환일 = 파티 끝나는 날)
			CashDto cashDto = new CashDto(0, pdDto.getUserNum(), addDate, "보증금 반환 입금", pdDto.getAmount());
			pdDto.setMsg(2); // 돈나감
			pdDto = pdservice.save(pdDto); // 돈나감 업데이트
		}
	}
}
