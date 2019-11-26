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

	// 난이도 변수
	private int level;

	// 정보 관련 / 클릭 횟수, 남은 카드 수 , 정답 처리 카드 수
	static JLabel labelMessage;
	static int clickCount;
	static int remains;
	static int correct;

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

		labelMessage = new JLabel("Find same Card!" + " Try " + clickCount);
		labelMessage.setPreferredSize(new Dimension(1000, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage); // 패널 상단에 위치시키기
		this.add("North", panelNorth);

		// 게임 칸
		panelCenter = new JPanel();

		// easy 난이도
		if (level == 0) {
			deck = Card.createEasyDeck();
			panelCenter.setLayout(new GridLayout(3, 3));// 카드 개수 바뀌면 이부분 수정하면됨
			panelCenter.setPreferredSize(new Dimension(600, 600));

			for (Card card : deck) {
				panelCenter.add(card);
			}
		} else if (level == 1) {
			deck = Card.createNormalDeck();
			panelCenter.setLayout(new GridLayout(4, 4));// 카드 개수 바뀌면 이부분 수정하면됨
			panelCenter.setPreferredSize(new Dimension(600, 600));

			for (Card card : deck) {
				panelCenter.add(card);
			}
		} else if (level == 2) {
			deck = Card.createHardDeck();
			panelCenter.setLayout(new GridLayout(5, 5));// 카드 개수 바뀌면 이부분 수정하면됨
			panelCenter.setPreferredSize(new Dimension(600, 600));

			for (Card card : deck) {
				panelCenter.add(card);
			}
		}
		this.add("Center", panelCenter);

		// 포기 버튼
		panelSouth = new JPanel();

		panelSouth.setPreferredSize(new Dimension(1000, 100));
		Giveup = new JButton("게임 포기");
		Giveup.setForeground(Color.WHITE);
		Giveup.setBackground(Color.BLACK);

		panelSouth.add(Giveup, "SOUTH");
		this.add("SOUTH", panelSouth);

		Giveup.addActionListener(new MyActionListener());
		
		
		// 카드 잠깐 보여주기
		Card.startEffect(deck);
	}

	class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			window.change("panel_1");
		}
	}
}
