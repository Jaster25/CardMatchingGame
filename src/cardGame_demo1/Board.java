
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
	
	// 정보 관련 / 클릭 횟수, 남은 카드 수 , 정답 처리 카드 수
	static JLabel labelMessage;
	static int clickCount;
	static int remains;
	static int correct;
	
	// 후에 동적으로 변경
	static JButton[] cards = new JButton[16];// 배열 크기는 모드에 따라 달라질 것
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
		this.setSize(500, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initUI(this);

		// 여백, 가장자리의 비어있는 공간 자리잡아주기
		this.pack();
	}

	// 패널 위치
	static void initUI(Board board) {
		// 게임 안내문
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(400, 100));
		panelNorth.setBackground(Color.DARK_GRAY);
		
		labelMessage = new JLabel("Find same Card!" + " Try " + clickCount);
		labelMessage.setPreferredSize(new Dimension(400, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage); // 패널 상단에 위치시키기
		board.add("North", panelNorth);

		// 게임 칸
		panelCenter = new JPanel();
		
		panelCenter.setLayout(new GridLayout(4, 4));// 카드 개수 바뀌면 이부분 수정하면됨
		panelCenter.setPreferredSize(new Dimension(400, 400));
		
		deck = Card.createDeck(16);
		
		for (int i = 0; i < 16; i++) {
			cards[i] = deck.get(i);
			panelCenter.add(cards[i]);
		}
		
		board.add("Center", panelCenter);
	}

}
