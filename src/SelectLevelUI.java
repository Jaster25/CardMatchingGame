import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectLevelUI extends JPanel {

	private CardGame window;

	static JPanel panelNorth;
	static JPanel panelCenter;
	static JPanel panelSouth;
	static JLabel labelMessage;

	static ArrayList<JButton> buttons = new ArrayList<JButton>();

	// 게임 종료 버튼
	static JButton exitButton;

	// 초기화면 생성자
	public SelectLevelUI(CardGame window) {

		this.window = window;

		// 게임 안내문
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(800, 100));
		panelNorth.setBackground(Color.DARK_GRAY);

		labelMessage = new JLabel("난이도를 선택하세요");
		labelMessage.setPreferredSize(new Dimension(800, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage, "NORTH"); // 패널 상단에 위치시키기
		this.add("NORTH", panelNorth);

		// 게임 칸
		panelCenter = new JPanel();

		panelCenter.setPreferredSize(new Dimension(500, 150));
		buttons.add(new JButton("EASY"));
		buttons.add(new JButton("NORMAL"));
		buttons.add(new JButton("HARD"));

		for (int i = 0; i < 3; i++) {
			buttons.get(i).setBackground(Color.WHITE);
			buttons.get(i).setPreferredSize(new Dimension(100, 100));
		}

		panelCenter.add(buttons.get(0), "CENTER");
		panelCenter.add(buttons.get(1), "CENTER");
		panelCenter.add(buttons.get(2), "CENTER");

		this.add("CENTER", panelCenter);

		for (int i = 0; i < 3; ++i)
			buttons.get(i).addActionListener(new MyActionListener(i));

		// exit버튼
		panelSouth = new JPanel();

		panelSouth.setPreferredSize(new Dimension(500, 80));
		exitButton = new JButton("게임 종료");
		exitButton.setPreferredSize(new Dimension(150, 70));
		exitButton.setFont(new Font("Monaco", Font.BOLD, 25));
		exitButton.setForeground(Color.WHITE);
		exitButton.setBackground(Color.BLACK);

		panelSouth.add(exitButton, "SOUTH");
		this.add("SOUTH", panelSouth);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	}

	class MyActionListener implements ActionListener {
		int level;

		public MyActionListener(int level) {
			super();
			this.level = level;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			CardGame.stepLevel = this.level;

			window.panel_2 = new GameStartUI(window);
			window.resize(900, 900);
			window.setLocationRelativeTo(null);
			window.change("panel_2");
		}
	}
}
