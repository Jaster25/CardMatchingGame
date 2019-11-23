
package cardGame_demo1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JFrame {

	static JPanel panelNorth;
	static JPanel panelCenter;
	
	// ���� ���� / Ŭ�� Ƚ��, ���� ī�� �� , ���� ó�� ī�� ��
	static JLabel labelMessage;
	static int clickCount;
	static int remains;
	static int correct;
	
	// �Ŀ� �������� ����
	static JButton[] cards = new JButton[16];// �迭 ũ��� ��忡 ���� �޶��� ��
	static ArrayList<Card> deck;
	// ���� Ȯ���� ���� ����
	static Card firstSelect;
	static Card secondSelect;
	
	// 0,1,2
	static int openCardNumber;
	
	// ������ ������ (���̵� �Ű�����)
	public Board(int level) {
		super("ī�� ¦ ���߱� ����");
		this.setLayout(new BorderLayout()); // �⺻������ = BorderLayout
		this.setSize(500, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initUI(this);

		// ����, �����ڸ��� ����ִ� ���� �ڸ�����ֱ�
		this.pack();
	}

	// �г� ��ġ
	static void initUI(Board board) {
		// ���� �ȳ���
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(400, 100));
		panelNorth.setBackground(Color.DARK_GRAY);
		
		labelMessage = new JLabel("Find same Card!" + " Try " + clickCount);
		labelMessage.setPreferredSize(new Dimension(400, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage); // �г� ��ܿ� ��ġ��Ű��
		board.add("North", panelNorth);

		// ���� ĭ
		panelCenter = new JPanel();
		
		panelCenter.setLayout(new GridLayout(4, 4));// ī�� ���� �ٲ�� �̺κ� �����ϸ��
		panelCenter.setPreferredSize(new Dimension(400, 400));
		
		deck = Card.createDeck(16);
		
		for (int i = 0; i < 16; i++) {
			cards[i] = deck.get(i);
			panelCenter.add(cards[i]);
		}
		
		board.add("Center", panelCenter);
	}

}
