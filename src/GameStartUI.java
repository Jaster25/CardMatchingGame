import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStartUI extends JPanel {

	private CardGame window;
	static boolean run = true;

	static JPanel panelNorth;
	static JPanel panelCenter;
	static JPanel panelSouth;

	// 포기 버튼
	static JButton giveUpButton;
	static JButton pauseButton;

	// Base Pannel
//	static JPanel basePanel;
//	ImageIcon bgi = new ImageIcon("C:\\BGI.JPG");
//	
	// 게임 난이도 변수
	private int level;

	// 정보 관련 : 시도 횟수, 남은 카드 수 , 시간(걸린 시간)
	static JLabel labelMessage;
	static int tryCount;
	static int remains;

	static ArrayList<Card> deck;

	// 하단 레이블 메시지 (remains, try)
	static JLabel remainMessage;
	static JLabel tryMessage;

	// 정답 확인을 위한 변수
	static Card firstSelect;
	static Card secondSelect;

	// 0,1,2
	static int openCardNumber;

	public GameStartUI(CardGame window) {
		level = CardGame.stepLevel;
		this.window = window;

		// basePanel에 배경이미지 삽입
		// basePanel = new JPanel();

		// 게임 안내문
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 2));
		panelNorth.setPreferredSize(new Dimension(600, 120));
		panelNorth.setBackground(Color.DARK_GRAY);

		labelMessage = new JLabel("여기다 시간 넣을거임 ");
		labelMessage.setPreferredSize(new Dimension(600, 120));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);

		// 일시 정지/시작 버튼
		pauseButton = new JButton(Utility.changeButtonImage("pause.png"));
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

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
		});

		// 중단버튼 - 메뉴로
		giveUpButton = new JButton(Utility.changeButtonImage("exit.png"));
		giveUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.change("panel_1");
				GameStartUI.tryCount = 0;

				window.resize(500, 400);
				window.setLocationRelativeTo(null);
			}
		});

		panelNorth.add(labelMessage); // 패널 상단에 위치시키기
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
		panelSouth.setLayout(new GridLayout(1, 2));
		panelSouth.setBackground(Color.DARK_GRAY);

		remainMessage = new JLabel("Remains Card : " + remains);
		remainMessage.setPreferredSize(new Dimension(300, 120));
		remainMessage.setForeground(Color.WHITE);
		remainMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		remainMessage.setHorizontalAlignment(JLabel.CENTER);

		tryMessage = new JLabel("try : " + tryCount);
		tryMessage.setPreferredSize(new Dimension(300, 120));
		tryMessage.setForeground(Color.WHITE);
		tryMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		tryMessage.setHorizontalAlignment(JLabel.CENTER);

		panelSouth.add(remainMessage);
		panelSouth.add(tryMessage);
		this.add("SOUTH", panelSouth);

		// 카드 잠깐 보여주기
		Card.startEffect(deck);
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
			remains -= 2;
			Utility.soundPlay("correct");
			first.correct = true;
			second.correct = true;

			// 모든 카드의 짝을 맞출 경우 종료 프레임 띄우기
			if (remains == 0) {
				ExitUI exitFrame = new ExitUI();
				exitFrame.Exit();
			}
		} else {
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

	// 상단 + 하단? JLabel 업데이트
	public static void labelMessageUpdate() {
		labelMessage.setText("여기다 시간 넣을거임");
		labelMessage.setHorizontalAlignment(JLabel.CENTER);

		remainMessage.setText("Remains Card : " + remains);
		tryMessage.setText("try : " + tryCount);
	}
}
