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

	// ���� ���̵� ����
	private int level;

	// ���� ���� : �õ� Ƚ��, ���� ī�� �� , �ð�(�ɸ� �ð�)
	static JLabel labelMessage;
	static int tryCount;
	static int remains;

	static ArrayList<Card> deck;

	// ���� ��ư
	static JButton Giveup;
	// ���� Ȯ���� ���� ����
	static Card firstSelect;
	static Card secondSelect;

	// 0,1,2
	static int openCardNumber;

	public GameStartUI(CardGame window) {
		level = CardGame.stepLevel;
		this.window = window;

		// ���� �ȳ���
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 1));
		panelNorth.setPreferredSize(new Dimension(1000, 100));
		panelNorth.setBackground(Color.DARK_GRAY);

		labelMessage = new JLabel("Find same Card!" + " Try " + tryCount);
		labelMessage.setPreferredSize(new Dimension(1000, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage); // �г� ��ܿ� ��ġ��Ű��
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

		// ���� ��ư
		panelSouth = new JPanel();
		panelSouth.setPreferredSize(new Dimension(1000, 100));
		Giveup = new JButton("���� ����");
		Giveup.setForeground(Color.WHITE);
		Giveup.setBackground(Color.BLACK);
		Giveup.addActionListener(new MyActionListener());

		panelSouth.add(Giveup, "SOUTH");
		this.add("SOUTH", panelSouth);

		// ī�� ��� �����ֱ�
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

	// ��� + �ϴ�? JLabel ������Ʈ
	public static void labelMessageUpdate() {
		labelMessage.setText("Find same Card!" + " Try " + tryCount);
	}
}
