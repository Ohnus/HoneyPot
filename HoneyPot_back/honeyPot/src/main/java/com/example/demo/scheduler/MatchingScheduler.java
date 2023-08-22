package com.example.demo.scheduler;

import java.time.LocalDate;
import java.util.ArrayList;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.automatching.AutoMatchingDto;
import com.example.demo.automatching.AutoMatchingService;
import com.example.demo.hostboard.HostBoard;
import com.example.demo.hostboard.HostBoardDto;
import com.example.demo.hostboard.HostBoardService;
import com.example.demo.member.Member;
import com.example.demo.partygroup.PartyGroupService;

@EnableScheduling
@Component
public class MatchingScheduler {

	@Autowired
	private AutoMatchingService AMService;

	@Autowired
	private PartyGroupService PGService;

	@Autowired
	private HostBoardService HBService;

	
	//매일 자정에 실행 됨 
	@Scheduled(cron = "0 0 * * * ?")
	public void combinedMethod() {
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);

		ArrayList<HostBoardDto> hostBoards = HBService.getAll(); //게사판 전체리스트 뽑아
		ArrayList<AutoMatchingDto> autoMatchingList = AMService.getAll(); //자동매칭 전체리스트 뽑아

		for (HostBoardDto dto : hostBoards) {
			HostBoard vo = new HostBoard();
			vo.setBoardNum(dto.getBoardNum()); //글번호 배치하고 

			if (dto.getSubStart().isEqual(today)) { //글 중에 시작 날자가 오늘이라면 
				//파티그룹에서 해당 글번호를 찾아서 몇명인지 숫자를 세어봐 
				int currentParticipants = PGService.findByBoardNum(vo); 
				
				//숫자 카운트 한게 미니먼 사람보다 크거나 같으면 
				if (currentParticipants >= dto.getMinPpl()) {
					//파티그룹의 시작유무를 시작했다는 뜻인 1로 바꾸고 
					PGService.editStartTo1(vo.getBoardNum());
					System.out.println("@@@@@@ editStartTo1 완료 되었다구요~ ");
				} else {
					//아니라면 미안한데 오늘시작인데 인원 안차서 시작 못해~ 그래서 글 자동 삭제 
					PGService.delByBoardNum(vo.getBoardNum());
					System.out.println("@안@녕@하@세@요@ delByBoardNum 완료되었다구요");
				}
			} else if (dto.getSubEnd().isEqual(today)) { //글의 종료일이 오늘 날짜라면 
				PGService.editStartTo2(vo.getBoardNum()); //종료 되었다고 파티그룹을 수정해줘 
				HBService.changIngToZero(0); //보드가 1일 수도 있으니까 0으로 바꿔서 안보이게 해 
			}
		}

		//오토매칭 
		//예시 오늘이 8월 23일 
		for (AutoMatchingDto dto : autoMatchingList) { 
			//오토매칭의 마감 날짜가 어제(22일)고 IsMatching 컬럼이 0 (매칭안됌) 이라면  
			if (dto.getExDate().isEqual(yesterday) && dto.getIsMatching() == 0) {
				// matchingNum을 2로 바꿔 -> 추후에 뷰에서 매칭관련한 리스트를 보여줄 예정 
				AMService.isMatchingChangeTo2(dto.getMatchingNum());
				
			}
		}
	}

	// 1시간에 한번씩 돌아야 할듯! 
	@Scheduled(cron = "0 0 * * * ?")
	public void autoMatch() {
		ArrayList<AutoMatchingDto> autoMatchingList = AMService.getUnmatched(); // 매칭 안된 0 인 애들만 가져와
		System.out.println("###1번 : " + autoMatchingList);
		ArrayList<HostBoardDto> HBlist = HBService.getIngZero(); // 게시판에 사람 구하고 있는 글들만 가져와
		System.out.println("###2번 : " + HBlist);
		for (AutoMatchingDto autoMatching : autoMatchingList) { //오토매칭 이 한바퀴씩 돌면서 
			for (HostBoardDto hostBoard : HBlist) { //게시판도 한바퀴 돌아 
				//그러면서 HostBoard의 게시글을 가지고 있는 데이터가 몇개인지 세아려봐 
				int count = PGService.findByBoardNumUsingInt(hostBoard.getBoardNum());
				
				if (hostBoard.getType().getType().equals(autoMatching.getType().getType()) && // ott 타입이 같고
						hostBoard.getSubStart().compareTo(autoMatching.getStartDate()) >= 0 && // 시작날짜
						hostBoard.getSubStart().compareTo(autoMatching.getExDate()) <= 0 && // 끝날짜 사이
						hostBoard.getMonth() >= autoMatching.getMonthMin() && // 미니멈 먼스
						hostBoard.getMonth() <= autoMatching.getMonthMax()) { // 맥스 먼스
					
					// PartyGroup Service 에 add 하는데 partyGroup에 해당 automatching Num 이 있나 확인 해야 해
					
					//그래서 findByMatchingNum이 true 를 반환하고 아까 위에서 숫자를 센 count가 최대인원보다 작으면 
					if (PGService.findByMatchingNum(autoMatching.getMatchingNum()) && 
							count < hostBoard.getMaxPpl()) {
						System.out.println("###3번 : " + autoMatching);
						System.out.println("###4번 : " + hostBoard);
						AMService.changeIsMatching(autoMatching, hostBoard); // Matching 1로 바꾸고 partyGroup에도 추가

					}

				}
			}
		}
	}

}
