import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExitUI extends JFrame {

	// static CardGame base_window;
	// ���� ���� �ȳ��� ������
	static JFrame exitFrame;
	// ���� ���� �ȳ��� �г�
	static JPanel panelNorth;
	static JPanel panelCenter;
	static JPanel panelSouth;
	// ���� ���� �ȳ��� �޽���
	static JLabel scoreMessage;
	static JLabel timeScoreMessage;
	static JLabel exitMessage;
	// ���� ���� ���� ��ư
	static RoundButton yesButton;
	static RoundButton noButton;

	// GameStartUI Ŭ����� �ߴ� �޴� - �Ѱ� ��
	public static void clearUI() {

		GameStartUI.pause();
		// ���� �ȳ� ������ ����
		exitFrame = new JFrame();
		exitFrame.setTitle("���� �׸� ã�� ����");
		exitFrame.setSize(400, 190);
		exitFrame.setVisible(true);
		exitFrame.setLocationRelativeTo(null);
		exitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ���� ������ Ŭ���� �ð� �ȳ���
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 2));
		panelNorth.setPreferredSize(new Dimension(400, 50));
		panelNorth.setBackground(Color.DARK_GRAY);

		scoreMessage = new JLabel("Score : " + GameStartUI.score);
		scoreMessage.setPreferredSize(new Dimension(200, 50));
		scoreMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		scoreMessage.setHorizontalAlignment(JLabel.CENTER);
		scoreMessage.setForeground(Color.WHITE);

		timeScoreMessage = new JLabel("Time Record : " + GameStartUI.sec);
		timeScoreMessage.setPreferredSize(new Dimension(200, 50));
		timeScoreMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		timeScoreMessage.setHorizontalAlignment(JLabel.CENTER);
		timeScoreMessage.setForeground(Color.WHITE);

		panelNorth.add(scoreMessage);
		panelNorth.add(timeScoreMessage);
		exitFrame.add("North", panelNorth);

		// ���� ���� �ȳ���
		panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(1, 1));
		panelCenter.setPreferredSize(new Dimension(400, 90));
		panelCenter.setBackground(Color.DARK_GRAY);

		exitMessage = new JLabel("���̵��� �ٽ� �����Ͻðڽ��ϱ�?");
		exitMessage.setPreferredSize(new Dimension(400, 90));
		exitMessage.setForeground(Color.WHITE);
		exitMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		exitMessage.setHorizontalAlignment(JLabel.CENTER);
		panelCenter.add(exitMessage);
		exitFrame.add("Center", panelCenter);

		// ���� ��ư
		panelSouth = new JPanel();
		panelSouth.setPreferredSize(new Dimension(400, 50));
		panelSouth.setLayout(new GridLayout(1, 2));

		yesButton = new RoundButton(Utility.changeButtonImage("yes.png"));
		yesButton.setPreferredSize(new Dimension(200, 50));
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitFrame.removeAll();
				exitFrame.dispose();
				GameStartUI.reset();
				CardGame.window.panel_1 = new SelectLevelUI(CardGame.window);
				CardGame.window.resize(500, 335);
				CardGame.window.setLocationRelativeTo(null);
				CardGame.window.change("panel_1");
			}
		});

		noButton = new RoundButton(Utility.changeButtonImage("cancel.png"));
		noButton.setPreferredSize(new Dimension(200, 50));
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ��������
				System.exit(0);
			}
		});

		panelSouth.add(yesButton, "CENTER");
		panelSouth.add(noButton, "CENTER");

		exitFrame.add("South", panelSouth);
	}

	// GameStartUI ������ ��ư ������ - ��¥�� ������ ���� UI
	public static void goToMenuUI(CardGame window) {

		// ���� �ȳ� ������ ����
		exitFrame = new JFrame();
		exitFrame.setTitle("���� ����");
		exitFrame.setSize(400, 200);
		exitFrame.setVisible(true);
		exitFrame.setLocationRelativeTo(null);
		exitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// ���� ���� �ȳ���
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(1, 1));
		panelNorth.setPreferredSize(new Dimension(400, 80));
		panelNorth.setBackground(Color.DARK_GRAY);

		exitMessage = new JLabel("���� �޴��� ���ðڽ��ϱ�?");
		exitMessage.setPreferredSize(new Dimension(400, 80));
		exitMessage.setForeground(Color.WHITE);
		exitMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		exitMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(exitMessage);
		exitFrame.add("North", panelNorth);

		// ���� ��ư
		// YES��ư Ŭ���� ���� ����
		panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(400, 100));
		panelCenter.setLayout(new GridLayout(1, 2));

		yesButton = new RoundButton(Utility.changeButtonImage("yes.png"));
		yesButton.setPreferredSize(new Dimension(150, 50));
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ���� ����
				exitFrame.removeAll();
				exitFrame.dispose();

				GameStartUI.reset();

				window.panel_1 = new SelectLevelUI(window);
				window.resize(500, 335);
				window.setLocationRelativeTo(null);
				window.change("panel_1");
			}
		});

		noButton = new RoundButton(Utility.changeButtonImage("cancel.png"));
		noButton.setPreferredSize(new Dimension(150, 50));
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				exitFrame.removeAll();
				exitFrame.dispose();

			}
		});

		panelCenter.add(yesButton, "CENTER");
		panelCenter.add(noButton, "CENTER");

		exitFrame.add("Center", panelCenter);
	}

	// SelectLevelUI ������ ��ư ������ - ���� �����Ұ��� ���� UI
	public static void exitUI() {
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

		exitMessage = new JLabel("�����Ͻðڽ��ϱ�?");
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

		yesButton = new RoundButton(Utility.changeButtonImage("yes.png"));
		yesButton.setPreferredSize(new Dimension(150, 50));
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				exitFrame.removeAll();
				exitFrame.dispose();
				System.exit(0);
			}
		});

		noButton = new RoundButton(Utility.changeButtonImage("cancel.png"));
		noButton.setPreferredSize(new Dimension(150, 50));
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitFrame.removeAll();
				exitFrame.dispose();
			}
		});

		panelCenter.add(yesButton, "CENTER");
		panelCenter.add(noButton, "CENTER");

		exitFrame.add("Center", panelCenter);
	}
}
