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
	static JLabel labelMessage;

	static ArrayList<JButton> buttons = new ArrayList<JButton>();

	// �ʱ�ȭ�� ������
	public SelectLevelUI(CardGame window) {

		this.window = window;

		// ���� �ȳ���
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(800, 100));
		panelNorth.setBackground(Color.DARK_GRAY);

		labelMessage = new JLabel("���̵��� �����ϼ���");
		labelMessage.setPreferredSize(new Dimension(800, 100));
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(new Font("Monaco", Font.BOLD, 20));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(labelMessage, "NORTH"); // �г� ��ܿ� ��ġ��Ű��
		this.add("NORTH", panelNorth);

		// ���� ĭ
		panelCenter = new JPanel();

		panelCenter.setPreferredSize(new Dimension(800, 700));
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
		
		for(int i=0 ; i<3 ; ++i)
		buttons.get(i).addActionListener(new MyActionListener(i));

	}

	class MyActionListener implements ActionListener {
		int level;

		public MyActionListener(int level) {
			super();
			this.level = level;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			CardGame.stepLevel = this.level;
			
			window.panel_2 = new GameStartUI(window);
			window.change("panel_2"); 
		}
	}
}

