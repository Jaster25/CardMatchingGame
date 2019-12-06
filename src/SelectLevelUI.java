import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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
	private Image background;

	// 게임 종료 버튼
	static CustomButton exitButton;

	// 초기화면 생성자
	public SelectLevelUI(CardGame window) {

		this.window = window;
		// 게임 안내문
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(500, 100));
		panelNorth.setOpaque(false);
		labelMessage = new JLabel(Utility.designImage("Title.jpg"));
		labelMessage.setPreferredSize(new Dimension(400, 100));

		panelNorth.add(labelMessage, "NORTH");
		this.add("NORTH", panelNorth);
		// 게임 칸
		panelCenter = new JPanel();
		panelCenter.setOpaque(false);
		panelCenter.setPreferredSize(new Dimension(500, 110));
		buttons.add(new JButton(Utility.changeButtonImage("easy.jpg")));
		buttons.add(new JButton(Utility.changeButtonImage("normal.jpg")));
		buttons.add(new JButton(Utility.changeButtonImage("hard.jpg")));

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
		panelSouth.setOpaque(false);
		panelSouth.setPreferredSize(new Dimension(500, 100));
		exitButton = new CustomButton(Utility.changeButtonImage("tent.png"));
		exitButton.setPreferredSize(new Dimension(100, 80));

		panelSouth.add(exitButton, "SOUTH");
		this.add("SOUTH", panelSouth);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExitUI.exitUI();
			}
		});
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		background = new ImageIcon("./images/BGI1.png").getImage();
		g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
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
			window.resize(800, 900);
			window.setLocationRelativeTo(null);
			window.change("panel_2");
		}
	}
}
