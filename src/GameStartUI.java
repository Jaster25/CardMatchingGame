import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStartUI extends JPanel {

	private CardGame window;
	static boolean run = false;
	static boolean timerSoundRun = false;
	static int sec;

	static JPanel panelNorth;
	static JPanel panelCenter;
	static JPanel panelSouth;

	// 포기, 일시정지 버튼
	static RoundButton giveUpButton;
	static RoundButton pauseButton;
	static RoundButton timerButton;

	// 게임 난이도 변수
	private int level;

	// 정보 관련 : 시도 횟수, 남은 카드 수 , 시간(걸린 시간), 점수
	static JLabel labelMessage;
	static int tryCount = 0;
	static int remains;
	static int score = 0;
	static int combo = 0;

	static ArrayList<Card> deck;

	// 하단 레이블 메시지 (remains, try, scoreMessage)
	static JLabel remainMessage;
	static JLabel tryMessage;
	static JLabel scoreMessage;

	// 정답 확인을 위한 변수
	static Card firstSelect;
	static Card secondSelect;

	// 0,1,2
	static int openCardNumber;

	public static int count;

	public GameStartUI(CardGame window) {
		level = CardGame.stepLevel;
		this.window = window;

		// 게임 안내문
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 2));
		panelNorth.setPreferredSize(new Dimension(600, 120));
		panelNorth.setBackground(Color.DARK_GRAY);

		labelMessage = new JLabel("Timer : 0초");
		labelMessage.setPreferredSize(new Dimension(600, 120));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);

//		timerButton = new RoundButton(Utility.changeButtonImage("timer.png"));
//		timerButton.addComponentListener(null);

		// 일시 정지, 재시작 버튼
		pauseButton = new RoundButton(Utility.changeButtonImage("pause.png"));
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});

		// 중단버튼 - 메뉴로
		giveUpButton = new RoundButton(Utility.changeButtonImage("exit.png"));
		giveUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExitUI.goToMenuUI();
			}
		});
		// 패널 상단에 위치시키기
		panelNorth.add(labelMessage); 
		panelNorth.add(pauseButton);
		panelNorth.add(giveUpButton);
		this.add("North", panelNorth);

		// 게임 칸
		panelCenter = new JPanel();

		// 난이도 별로 나누기
		if (level == 0) {
			deck = Card.createEasyDeck();
			panelCenter.setLayout(new GridLayout(3, 3));
			remains = 8;
		} else if (level == 1) {
			deck = Card.createNormalDeck();
			panelCenter.setLayout(new GridLayout(4, 4));
			remains = 16;
		} else if (level == 2) {
			deck = Card.createHardDeck();
			panelCenter.setLayout(new GridLayout(5, 5));
			remains = 24;
		}

		panelCenter.setPreferredSize(new Dimension(600, 600));
		for (Card card : deck)
			panelCenter.add(card);

		this.add("Center", panelCenter);

		// 하단 레이블 메시지(remains, try)
		panelSouth = new JPanel();
		panelSouth.setPreferredSize(new Dimension(600, 120));
		panelSouth.setLayout(new GridLayout(1, 3));
		panelSouth.setBackground(Color.DARK_GRAY);

		remainMessage = new JLabel("Remains Card : " + remains);
		remainMessage.setPreferredSize(new Dimension(200, 120));
		remainMessage.setForeground(Color.WHITE);
		remainMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		remainMessage.setHorizontalAlignment(JLabel.CENTER);

		scoreMessage = new JLabel(score + "");
		scoreMessage.setPreferredSize(new Dimension(200, 120));
		scoreMessage.setForeground(Color.WHITE);
		scoreMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		scoreMessage.setHorizontalAlignment(JLabel.CENTER);

		tryMessage = new JLabel("try : " + tryCount);
		tryMessage.setPreferredSize(new Dimension(200, 120));
		tryMessage.setForeground(Color.WHITE);
		tryMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		tryMessage.setHorizontalAlignment(JLabel.CENTER);

		panelSouth.add(remainMessage);
		panelSouth.add(scoreMessage);
		panelSouth.add(tryMessage);
		this.add("SOUTH", panelSouth);

		// 카드 잠깐 보여주기
		Card.startEffect(deck);

//		// 타이머 사운드
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				// 게임 중이면
//				if (GameStartUI.run) {
//
//					Media timerSound = new Media("./sounds/timerSound.mp3");
//				}
//			}
//		}, 0, 60000);

		// 게임 시간 타이머 시작
		Timer gameTimer = new Timer();
		gameTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (run) {

					sec++;
					labelMessage.setText("Timer : " + sec);
					labelMessage.setHorizontalAlignment(JLabel.CENTER);

					// 사운드
					if (!timerSoundRun) {

						Utility.soundPlay("timer");
						timerSoundRun = false;

						// 틀고 4초 뒤 꺼지니 타이머 설정
						gameTimer.schedule(new TimerTask() {

							@Override
							public void run() {
								timerSoundRun = false;
							}
						}, 4000);
					}

				}
//	            else {
//	               gameTimer.cancel();
				// }
			}
		}, 0, 1000);
	}

	public static boolean isCorrect() {
		return firstSelect.front.equals(secondSelect.front);
	}

	public static void checking() {

		tryCount++;
		openCardNumber = 0;

		Card first = firstSelect;
		Card second = secondSelect;

		// 정답일 경우
		if (isCorrect()) {

			combo++;
			score += combo * 100;
			remains -= 2;
			Utility.soundPlay("correct");
			first.correct = true;
			second.correct = true;

			// 모든 카드의 짝을 맞출 경우 종료 프레임 띄우기
			if (remains == 0) {
				ExitUI.clearUI();
			}
		} else {

			combo = 0;
			score -= 100;

			Utility.soundPlay("wrong");
			first.open = false;
			second.open = false;

			// 시간 딜레이
			Timer timer = new Timer();

			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					Utility.soundPlay("flip");
					first.setIcon(Utility.changeCardImage(first.back));
				}
			}, 500);

			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					Utility.soundPlay("flip");
					second.setIcon(Utility.changeCardImage(second.back));
				}
			}, 700);
		}

		labelMessageUpdate();
	}

	// 게임정보 리셋 메소드
	public static void reset() {

		run = false;
		timerSoundRun = false;
		sec = 0;
		tryCount = 0;
		score = 0;
		combo = 0;
		openCardNumber = 0;
	}

	// 일시정지 메소드
	public static void pause() {

		// 게임 -> 일시정지
		if (run) {
			// 상태 바꿔주기
			run = false;
			// 모든 카드 비활성화
			for (Card card : deck)
				card.setEnabled(false);

			// 아이콘 바꿔주기
			pauseButton.setIcon(Utility.changeButtonImage("play.png"));
		}
		// 일시정지 -> 게임
		else {
			// 상태 바꿔주기
			run = true;
			// 모든 카드 활성화
			for (Card card : deck)
				card.setEnabled(true);

			// 아이콘 바꿔주기
			pauseButton.setIcon(Utility.changeButtonImage("pause.png"));
		}
	}

	// 상단 + 하단? JLabel 업데이트
	public static void labelMessageUpdate() {
		remainMessage.setText("Remains Card : " + remains);
		tryMessage.setText("try : " + tryCount);
		scoreMessage.setText(score + "");
	}
}
