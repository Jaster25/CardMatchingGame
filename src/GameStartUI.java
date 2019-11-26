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

	static JPanel panelNorth;
	static JPanel panelCenter;
	static JPanel panelSouth;

	// 게임 난이도 변수
	private int level;

	// 정보 관련 : 시도 횟수, 남은 카드 수 , 시간(걸린 시간)
	static JLabel labelMessage;
	static int tryCount;
	static int remains;

	static ArrayList<Card> deck;

	// 포기 버튼
	static JButton Giveup;
	// 정답 확인을 위한 변수
	static Card firstSelect;
	static Card secondSelect;

	// 0,1,2
	static int openCardNumber;

	public GameStartUI(CardGame window) {
		level = CardGame.stepLevel;
		this.window = window;

		// 게임 안내문
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 1));
		panelNorth.setPreferredSize(new Dimension(1000, 100));
		panelNorth.setBackground(Color.DARK_GRAY);

		labelMessage = new JLabel("Find same Card!" + " Try " + tryCount);
		labelMessage.setPreferredSize(new Dimension(1000, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage); // 패널 상단에 위치시키기
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

		// 포기 버튼
		panelSouth = new JPanel();
		panelSouth.setPreferredSize(new Dimension(1000, 100));
		Giveup = new JButton("게임 포기");
		Giveup.setForeground(Color.WHITE);
		Giveup.setBackground(Color.BLACK);
		Giveup.addActionListener(new MyActionListener());

		panelSouth.add(Giveup, "SOUTH");
		this.add("SOUTH", panelSouth);

		// 카드 잠깐 보여주기
		Card.startEffect(deck);
	}

	class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			window.change("panel_1");
			window.resize(500, 400);
			window.setLocationRelativeTo(null);
		}
	}

	public static boolean isCorrect() {
		return firstSelect.front.equals(secondSelect.front);
	}

	public static void checking() {

		tryCount++;
		labelMessageUpdate();
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
					first.setIcon(Utility.changeImage(first.back));
				}
			}, 500);

			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					Utility.soundPlay("flip");
					second.setIcon(Utility.changeImage(second.back));
				}
			}, 700);
		}
	}

	// 상단 + 하단? JLabel 업데이트
	public static void labelMessageUpdate() {
		labelMessage.setText("Find same Card!" + " Try " + tryCount);
	}
}
