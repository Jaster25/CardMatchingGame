import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStartUI extends JPanel {

	private CardGame window;

	static JPanel panelNorth;
	static JPanel panelCenter;
	static JPanel panelSouth;

	// ���̵� ����
	private int level;

	// ���� ���� / Ŭ�� Ƚ��, ���� ī�� �� , ���� ó�� ī�� ��
	static JLabel labelMessage;
	static int clickCount;
	static int remains;
	static int correct;

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

		labelMessage = new JLabel("Find same Card!" + " Try " + clickCount);
		labelMessage.setPreferredSize(new Dimension(1000, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage); // �г� ��ܿ� ��ġ��Ű��
		this.add("North", panelNorth);

		// ���� ĭ
		panelCenter = new JPanel();

		// easy ���̵�
		if (level == 0) {
			deck = Card.createEasyDeck();
			panelCenter.setLayout(new GridLayout(3, 3));// ī�� ���� �ٲ�� �̺κ� �����ϸ��
			panelCenter.setPreferredSize(new Dimension(600, 600));

			for (Card card : deck) {
				panelCenter.add(card);
			}
		} else if (level == 1) {
			deck = Card.createNormalDeck();
			panelCenter.setLayout(new GridLayout(4, 4));// ī�� ���� �ٲ�� �̺κ� �����ϸ��
			panelCenter.setPreferredSize(new Dimension(600, 600));

			for (Card card : deck) {
				panelCenter.add(card);
			}
		} else if (level == 2) {
			deck = Card.createHardDeck();
			panelCenter.setLayout(new GridLayout(5, 5));// ī�� ���� �ٲ�� �̺κ� �����ϸ��
			panelCenter.setPreferredSize(new Dimension(600, 600));

			for (Card card : deck) {
				panelCenter.add(card);
			}
		}
		this.add("Center", panelCenter);

		// ���� ��ư
		panelSouth = new JPanel();

		panelSouth.setPreferredSize(new Dimension(1000, 100));
		Giveup = new JButton("���� ����");
		Giveup.setForeground(Color.WHITE);
		Giveup.setBackground(Color.BLACK);

		panelSouth.add(Giveup, "SOUTH");
		this.add("SOUTH", panelSouth);

		Giveup.addActionListener(new MyActionListener());
		
		
		// ī�� ��� �����ֱ�
		Card.startEffect(deck);
	}

	class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			window.change("panel_1");
		}
	}
}
