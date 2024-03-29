import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStartUI extends JPanel {

	private CardGame window;
	static boolean run = false;
	static int sec;

	static JPanel panelNorth;
	static JPanel panelCenter;
	static JPanel panelSouth;

	// 포기, 일시정지 버튼
	static CustomButton giveUpButton;
	static CustomButton pauseButton;
	static CustomButton timerButton;

	// 게임 난이도 변수
	private int level;

	// 게임 상태 관련 변수들
	static JLabel labelMessage;
	static int tryCount = 0;
	static int remains;
	static int score = 0;
	static int combo = 0;
	static int maxTime;

	// 게임에 사용할 그림 카드들
	static ArrayList<Card> deck;

	// 하단 레이블 메시지 (remains, scoreMessage, try)
	static JLabel remainMessage;
	static JLabel tryMessage;
	static JLabel scoreMessage;

	// 정답 확인을 위한 변수
	static Card firstSelect;
	static Card secondSelect;

	// 0,1,2
	static int openCardNumber;

	// 게임판 생성자
	public GameStartUI(CardGame window) {
		level = CardGame.stepLevel;
		this.window = window;

		// 게임 안내문
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 2));
		panelNorth.setPreferredSize(new Dimension(600, 120));
		panelNorth.setBackground(Color.DARK_GRAY);

		labelMessage = new JLabel("Timer : 0");
		labelMessage.setPreferredSize(new Dimension(600, 120));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);

		// 일시 정지, 재시작 버튼
		pauseButton = new CustomButton(Utility.changeButtonImage("pause.png"));
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});

		// 중단버튼 - 메뉴로
		giveUpButton = new CustomButton(Utility.changeButtonImage("exit.png"));
		giveUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (run)
					pause();
				ExitUI.goToMenuUI(window);
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
			maxTime = 20;
			remains = 8;
		} else if (level == 1) {
			deck = Card.createNormalDeck();
			panelCenter.setLayout(new GridLayout(4, 4));
			remains = 16;
			maxTime = 30;
		} else if (level == 2) {
			deck = Card.createHardDeck();
			panelCenter.setLayout(new GridLayout(5, 5));
			remains = 24;
			maxTime = 45;
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

			if (maxTime > sec)
				score += (100 + (maxTime - sec) * 50) * ++combo;
			else
				score += 100 * ++combo;
			remains -= 2;

			Utility.soundPlay("correct");
			first.correct = true;
			second.correct = true;

			// 모든 카드의 짝을 맞출 경우 종료 창 띄우기
			if (remains == 0) {
				pause();
				ExitUI.clearUI();
			}
		} else {

			combo = 0;

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

	// 게임 정보 변수들 초기화 메소드
	public static void reset() {

		run = false;
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

	static void timerStart() {

		Timer gameTimer = new Timer();

		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				if (run) {
					sec++;
					labelMessageUpdate();

					Utility.soundPlay("timer");
				}
			}
		};

		gameTimer.schedule(timerTask, 1, 1000);
	}

	// 게임 정보 JLabel 업데이트
	public static void labelMessageUpdate() {
		remainMessage.setText("Remains Card : " + remains);
		tryMessage.setText("try : " + tryCount);
		scoreMessage.setText(score + "");
		labelMessage.setText("Timer : " + GameStartUI.sec);
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
	}

	private Image background;

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		background = new ImageIcon("./images/BGI2.png").getImage();
		g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}

}
