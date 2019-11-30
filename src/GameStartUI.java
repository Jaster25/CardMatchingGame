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

	// ����, �Ͻ����� ��ư
	static RoundButton giveUpButton;
	static RoundButton pauseButton;
	static RoundButton timerButton;

	// ���� ���̵� ����
	private int level;

	// ���� ���� : �õ� Ƚ��, ���� ī�� �� , �ð�(�ɸ� �ð�), ����
	static JLabel labelMessage;
	static int tryCount = 0;
	static int remains;
	static int score = 0;
	static int combo = 0;

	static ArrayList<Card> deck;

	// �ϴ� ���̺� �޽��� (remains, try, scoreMessage)
	static JLabel remainMessage;
	static JLabel tryMessage;
	static JLabel scoreMessage;

	// ���� Ȯ���� ���� ����
	static Card firstSelect;
	static Card secondSelect;

	// 0,1,2
	static int openCardNumber;

	public static int count;

	public GameStartUI(CardGame window) {
		level = CardGame.stepLevel;
		this.window = window;

		// ���� �ȳ���
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 2));
		panelNorth.setPreferredSize(new Dimension(600, 120));
		panelNorth.setBackground(Color.DARK_GRAY);

		labelMessage = new JLabel("Timer : 0��");
		labelMessage.setPreferredSize(new Dimension(600, 120));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);

//		timerButton = new RoundButton(Utility.changeButtonImage("timer.png"));
//		timerButton.addComponentListener(null);

		// �Ͻ� ����, ����� ��ư
		pauseButton = new RoundButton(Utility.changeButtonImage("pause.png"));
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});

		// �ߴܹ�ư - �޴���
		giveUpButton = new RoundButton(Utility.changeButtonImage("exit.png"));
		giveUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExitUI.goToMenuUI();
			}
		});
		// �г� ��ܿ� ��ġ��Ű��
		panelNorth.add(labelMessage); 
		panelNorth.add(pauseButton);
		panelNorth.add(giveUpButton);
		this.add("North", panelNorth);

		// ���� ĭ
		panelCenter = new JPanel();

		// ���̵� ���� ������
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

		// �ϴ� ���̺� �޽���(remains, try)
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

		// ī�� ��� �����ֱ�
		Card.startEffect(deck);

//		// Ÿ�̸� ����
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				// ���� ���̸�
//				if (GameStartUI.run) {
//
//					Media timerSound = new Media("./sounds/timerSound.mp3");
//				}
//			}
//		}, 0, 60000);

		// ���� �ð� Ÿ�̸� ����
		Timer gameTimer = new Timer();
		gameTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (run) {

					sec++;
					labelMessage.setText("Timer : " + sec);
					labelMessage.setHorizontalAlignment(JLabel.CENTER);

					// ����
					if (!timerSoundRun) {

						Utility.soundPlay("timer");
						timerSoundRun = false;

						// Ʋ�� 4�� �� ������ Ÿ�̸� ����
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

		// ������ ���
		if (isCorrect()) {

			combo++;
			score += combo * 100;
			remains -= 2;
			Utility.soundPlay("correct");
			first.correct = true;
			second.correct = true;

			// ��� ī���� ¦�� ���� ��� ���� ������ ����
			if (remains == 0) {
				ExitUI.clearUI();
			}
		} else {

			combo = 0;
			score -= 100;

			Utility.soundPlay("wrong");
			first.open = false;
			second.open = false;

			// �ð� ������
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

	// �������� ���� �޼ҵ�
	public static void reset() {

		run = false;
		timerSoundRun = false;
		sec = 0;
		tryCount = 0;
		score = 0;
		combo = 0;
		openCardNumber = 0;
	}

	// �Ͻ����� �޼ҵ�
	public static void pause() {

		// ���� -> �Ͻ�����
		if (run) {
			// ���� �ٲ��ֱ�
			run = false;
			// ��� ī�� ��Ȱ��ȭ
			for (Card card : deck)
				card.setEnabled(false);

			// ������ �ٲ��ֱ�
			pauseButton.setIcon(Utility.changeButtonImage("play.png"));
		}
		// �Ͻ����� -> ����
		else {
			// ���� �ٲ��ֱ�
			run = true;
			// ��� ī�� Ȱ��ȭ
			for (Card card : deck)
				card.setEnabled(true);

			// ������ �ٲ��ֱ�
			pauseButton.setIcon(Utility.changeButtonImage("pause.png"));
		}
	}

	// ��� + �ϴ�? JLabel ������Ʈ
	public static void labelMessageUpdate() {
		remainMessage.setText("Remains Card : " + remains);
		tryMessage.setText("try : " + tryCount);
		scoreMessage.setText(score + "");
	}
}
