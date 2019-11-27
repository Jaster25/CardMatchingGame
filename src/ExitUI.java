import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExitUI extends JFrame {

	// 게임 종료 안내문 프레임
	static JFrame exitFrame;
	// 게임 종료 안내문 패널
	static JPanel panelNorth;
	static JPanel panelCenter;
	// 게임 종료 안내문 메시지
	static JLabel exitMessage;
	// 게임 종료 선택 버튼
	static RoundButton yesButton;
	static RoundButton noButton;

	public void Exit() {
		// 종료 안내 프레임 띄우기
		exitFrame = new JFrame();
		exitFrame.setTitle("게임 종료");
		exitFrame.setSize(400, 200);
		exitFrame.setVisible(true);
		exitFrame.setLocationRelativeTo(null);
		exitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 게임 종료 안내문
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 1));
		panelNorth.setPreferredSize(new Dimension(400, 80));
		panelNorth.setBackground(Color.GRAY);

		exitMessage = new JLabel("난이도를 다시 선택하시겠습니까?");
		exitMessage.setPreferredSize(new Dimension(400, 80));
		exitMessage.setForeground(Color.WHITE);
		exitMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		exitMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(exitMessage);
		exitFrame.add("North", panelNorth);

		// 선택 버튼
		panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(400, 100));
		panelCenter.setLayout(new GridLayout(1, 2));

		yesButton = new RoundButton(Utility.changeButtonImage("yes.png"));
		yesButton.setPreferredSize(new Dimension(150, 50));
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitFrame.removeAll();
				exitFrame.dispose();
				GameStartUI.tryCount = 0;
				CardGame.replay();
			}
		});

		noButton = new RoundButton(Utility.changeButtonImage("cancel.png"));
		noButton.setPreferredSize(new Dimension(150, 50));
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});

		panelCenter.add(yesButton, "CENTER");
		panelCenter.add(noButton, "CENTER");

		exitFrame.add("Center", panelCenter);
	}
}
