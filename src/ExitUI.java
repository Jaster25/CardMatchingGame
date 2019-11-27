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

	// ���� ���� �ȳ��� ������
	static JFrame exitFrame;
	// ���� ���� �ȳ��� �г�
	static JPanel panelNorth;
	static JPanel panelCenter;
	// ���� ���� �ȳ��� �޽���
	static JLabel exitMessage;
	// ���� ���� ���� ��ư
	static JButton yesButton;
	static JButton noButton;

	public void Exit() {
		// ���� �ȳ� ������ ����
		exitFrame = new JFrame();
		exitFrame.setTitle("���� ����");
		exitFrame.setSize(400, 200);
		exitFrame.setVisible(true);
		exitFrame.setLocationRelativeTo(null);
		exitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ���� ���� �ȳ���
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 1));
		panelNorth.setPreferredSize(new Dimension(400, 80));
		panelNorth.setBackground(Color.GRAY);

		exitMessage = new JLabel("���̵��� �ٽ� �����Ͻðڽ��ϱ�?");
		exitMessage.setPreferredSize(new Dimension(400, 80));
		exitMessage.setForeground(Color.WHITE);
		exitMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		exitMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(exitMessage);
		exitFrame.add("North", panelNorth);

		// ���� ��ư
		panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(400, 100));
		panelCenter.setLayout(new GridLayout(1, 2));

		yesButton = new JButton(Utility.changeButtonImage("yes.png"));
		yesButton.setPreferredSize(new Dimension(150, 50));
//		yesButton.setFont(new Font("Monaco", Font.BOLD, 25));
//		yesButton.setForeground(Color.WHITE);
//		yesButton.setBackground(Color.DARK_GRAY);
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitFrame.removeAll();
				exitFrame.dispose();
				GameStartUI.tryCount = 0;
				CardGame.replay();
			}
		});

		noButton = new JButton(Utility.changeButtonImage("cancel.png"));
		noButton.setPreferredSize(new Dimension(150, 50));
//		noButton.setFont(new Font("Monaco", Font.BOLD, 25));
//		noButton.setForeground(Color.WHITE);
//		noButton.setBackground(Color.DARK_GRAY);
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
