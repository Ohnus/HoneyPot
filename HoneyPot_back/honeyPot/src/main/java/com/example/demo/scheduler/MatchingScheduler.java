package com.example.demo.scheduler;

import java.time.LocalDate;
import java.util.ArrayList;

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

	@Scheduled(cron = "0 31 * * * ?")
	public void combinedMethod() {
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);

		ArrayList<HostBoardDto> hostBoards = HBService.getAll();
		ArrayList<AutoMatchingDto> autoMatchingList = AMService.getAll();

		for (HostBoardDto dto : hostBoards) {
			HostBoard vo = new HostBoard();
			vo.setBoardNum(dto.getBoardNum());

			if (dto.getSubStart().isEqual(today)) {
				int currentParticipants = PGService.findByBoardNum(vo);
				if (currentParticipants >= dto.getMinPpl()) {
					PGService.editStartTo1(vo.getBoardNum());
					System.out.println("@@@@@@ editStartTo1 완료 되었다구요~ ");
				} else {
					PGService.delByBoardNum(vo.getBoardNum());
					System.out.println("@안@녕@하@세@요@ delByBoardNum 완료되었다구요");
				}
			} else if (dto.getSubEnd().isEqual(today)) {
				PGService.editStartTo2(vo.getBoardNum());
				System.out.println("마지막 else if 까지 왔음!!!!!");
			}
		}

		for (AutoMatchingDto dto : autoMatchingList) {
			if (dto.getExDate().isEqual(yesterday) && dto.getIsMatching() == 0) {
				AMService.del(dto.getMatchingNum());
			}
		}
	}

	// 1시간에 한번씩 돌아야 할듯! -> 고객한테는 다음날 부터 시작한다고 말해놓기
	@Scheduled(cron = "0 33 * * * ?")
	public void autoMatch() {
		ArrayList<AutoMatchingDto> autoMatchingList = AMService.getUnmatched(); // 매칭 안된 0 인 애들만 가져와
		ArrayList<HostBoardDto> HBlist = HBService.getIngZero(); // 게시판에 사람 구하고 있는 글들만 가져와
		for (AutoMatchingDto autoMatching : autoMatchingList) {
			for (HostBoardDto hostBoard : HBlist) {
				int count = PGService.findByBoardNumUsingInt(hostBoard.getBoardNum());
				if (hostBoard.getType().equals(autoMatching.getType()) && // ott 타입이 같고
						hostBoard.getSubStart().compareTo(autoMatching.getStartDate()) >= 0 && // 시작날짜
						hostBoard.getSubStart().compareTo(autoMatching.getExDate()) <= 0 && // 끝날짜 사이
						hostBoard.getMonth() >= autoMatching.getMonthMin() && // 미니멈 먼스
						hostBoard.getMonth() <= autoMatching.getMonthMax()) { // 맥스 먼스
					// PartyGroup Service 에 add 하는데 partyGroup에 해당 automatching Num 이 있나 확인 해야 해
					if (!PGService.findByMatchingNum(autoMatching.getMatchingNum()) && count < hostBoard.getMaxPpl()) {
						AMService.changeIsMatching(autoMatching, hostBoard); // Matching 1로 바꾸고 partyGroup에도 추가

					}

				}
			}
		}
	}

}
