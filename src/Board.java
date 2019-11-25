
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


// �Ⱦ��°�?
public class Board extends JFrame {

	static JPanel panelNorth;
	static JPanel panelCenter;
	
	// ���� ���� / Ŭ�� Ƚ��, ���� ī�� �� , ���� ó�� ī�� ��
	static JLabel labelMessage;
	static int clickCount;
	static int remains;
	static int correct;
	
	// �Ŀ� �������� ����
	static JButton[] cards1 = new JButton[16];// �迭 ũ��� ��忡 ���� �޶��� ��
	static JButton[] cards2 = new JButton[36];
	static JButton[] cards3 = new JButton[64];
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
		this.setSize(800, 800);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initUI(this,level);

		// ����, �����ڸ��� ����ִ� ���� �ڸ�����ֱ�
		this.pack();
	}

	// �г� ��ġ
	static void initUI(Board board, int level) {
		// ���� �ȳ���
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(800, 100));
		panelNorth.setBackground(Color.DARK_GRAY);
		
		labelMessage = new JLabel("Find same Card!" + " Try " + clickCount);
		labelMessage.setPreferredSize(new Dimension(800, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage); // �г� ��ܿ� ��ġ��Ű��
		board.add("North", panelNorth);

		// ���� ĭ
		panelCenter = new JPanel();
		
	
		
		if(level==1)
		{
//			deck = Card.createDeck(16);
			panelCenter.setLayout(new GridLayout(4, 4));// ī�� ���� �ٲ�� �̺κ� �����ϸ��
			panelCenter.setPreferredSize(new Dimension(800, 700));
			for (int i = 0; i < 16; i++) {
				cards1[i] = deck.get(i);
				panelCenter.add(cards1[i]);
			}
		}
		else if(level==2)
		{
//			deck = Card.createDeck(36);
			panelCenter.setLayout(new GridLayout(6, 6));// ī�� ���� �ٲ�� �̺κ� �����ϸ��
			panelCenter.setPreferredSize(new Dimension(800, 700));
			for (int i = 0; i < 36; i++) {
				cards2[i] = deck.get(i);
				panelCenter.add(cards2[i]);
			}
		}
		else
		{
//			deck = Card.createDeck(64);
			panelCenter.setLayout(new GridLayout(8, 8));// ī�� ���� �ٲ�� �̺κ� �����ϸ��
			panelCenter.setPreferredSize(new Dimension(800, 700));
			for (int i = 0; i < 64; i++) {
				cards3[i] = deck.get(i);
				panelCenter.add(cards3[i]);
			}
		}
		board.add("Center", panelCenter);
	}

}

