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

	// ���� ��ư
	static JButton giveUpButton;
	static JButton pauseButton;

	// Base Pannel
//	static JPanel basePanel;
//	ImageIcon bgi = new ImageIcon("C:\\BGI.JPG");
//	
	// ���� ���̵� ����
	private int level;

	// ���� ���� : �õ� Ƚ��, ���� ī�� �� , �ð�(�ɸ� �ð�)
	static JLabel labelMessage;
	static int tryCount;
	static int remains;

	static ArrayList<Card> deck;

	// �ϴ� ���̺� �޽��� (remains, try)
	static JLabel remainMessage;
	static JLabel tryMessage;

	// ���� Ȯ���� ���� ����
	static Card firstSelect;
	static Card secondSelect;

	// 0,1,2
	static int openCardNumber;

	public GameStartUI(CardGame window) {
		level = CardGame.stepLevel;
		this.window = window;

		// basePanel�� ����̹��� ����
		// basePanel = new JPanel();

		// ���� �ȳ���
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 2));
		panelNorth.setPreferredSize(new Dimension(600, 120));
		panelNorth.setBackground(Color.DARK_GRAY);

		labelMessage = new JLabel("����� �ð� �������� ");
		labelMessage.setPreferredSize(new Dimension(600, 120));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);

		// �Ͻ� ����/���� ��ư
		pauseButton = new JButton(Utility.changeButtonImage("pause.png"));
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

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
		});

		// �ߴܹ�ư - �޴���
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

		panelNorth.add(labelMessage); // �г� ��ܿ� ��ġ��Ű��
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

		// ī�� ��� �����ֱ�
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

		// ������ ���
		if (isCorrect()) {
			remains -= 2;
			Utility.soundPlay("correct");
			first.correct = true;
			second.correct = true;

			// ��� ī���� ¦�� ���� ��� ���� ������ ����
			if (remains == 0) {
				ExitUI exitFrame = new ExitUI();
				exitFrame.Exit();
			}
		} else {
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

	// ��� + �ϴ�? JLabel ������Ʈ
	public static void labelMessageUpdate() {
		labelMessage.setText("����� �ð� ��������");
		labelMessage.setHorizontalAlignment(JLabel.CENTER);

		remainMessage.setText("Remains Card : " + remains);
		tryMessage.setText("try : " + tryCount);
	}
}
