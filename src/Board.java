
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


// 안쓰는거?
public class Board extends JFrame {

	static JPanel panelNorth;
	static JPanel panelCenter;
	
	// 정보 관련 / 클릭 횟수, 남은 카드 수 , 정답 처리 카드 수
	static JLabel labelMessage;
	static int clickCount;
	static int remains;
	static int correct;
	
	// 후에 동적으로 변경
	static JButton[] cards1 = new JButton[16];// 배열 크기는 모드에 따라 달라질 것
	static JButton[] cards2 = new JButton[36];
	static JButton[] cards3 = new JButton[64];
	static ArrayList<Card> deck;
	// 정답 확인을 위한 변수
	static Card firstSelect;
	static Card secondSelect;
	
	// 0,1,2
	static int openCardNumber;
	
	// 게임판 생성자 (난이도 매개변수)
	public Board(int level) {
		super("카드 짝 맞추기 게임");
		this.setLayout(new BorderLayout()); // 기본프레임 = BorderLayout
		this.setSize(800, 800);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initUI(this,level);

		// 여백, 가장자리의 비어있는 공간 자리잡아주기
		this.pack();
	}

	// 패널 위치
	static void initUI(Board board, int level) {
		// 게임 안내문
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(800, 100));
		panelNorth.setBackground(Color.DARK_GRAY);
		
		labelMessage = new JLabel("Find same Card!" + " Try " + clickCount);
		labelMessage.setPreferredSize(new Dimension(800, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage); // 패널 상단에 위치시키기
		board.add("North", panelNorth);

		// 게임 칸
		panelCenter = new JPanel();
		
	
		
		if(level==1)
		{
//			deck = Card.createDeck(16);
			panelCenter.setLayout(new GridLayout(4, 4));// 카드 개수 바뀌면 이부분 수정하면됨
			panelCenter.setPreferredSize(new Dimension(800, 700));
			for (int i = 0; i < 16; i++) {
				cards1[i] = deck.get(i);
				panelCenter.add(cards1[i]);
			}
		}
		else if(level==2)
		{
//			deck = Card.createDeck(36);
			panelCenter.setLayout(new GridLayout(6, 6));// 카드 개수 바뀌면 이부분 수정하면됨
			panelCenter.setPreferredSize(new Dimension(800, 700));
			for (int i = 0; i < 36; i++) {
				cards2[i] = deck.get(i);
				panelCenter.add(cards2[i]);
			}
		}
		else
		{
//			deck = Card.createDeck(64);
			panelCenter.setLayout(new GridLayout(8, 8));// 카드 개수 바뀌면 이부분 수정하면됨
			panelCenter.setPreferredSize(new Dimension(800, 700));
			for (int i = 0; i < 64; i++) {
				cards3[i] = deck.get(i);
				panelCenter.add(cards3[i]);
			}
		}
		board.add("Center", panelCenter);
	}

}

