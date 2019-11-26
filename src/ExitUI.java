import java.awt.*;
import javax.swing.*;

public class ExitUI extends JFrame{
	
	// ���� ���� �ȳ��� ������
		static JFrame exitFrame;
		// ���� ���� �ȳ��� �г�
		static JPanel panelNorth;
		static JPanel panelCenter;
		// ���� ���� �ȳ��� �޽���
		static JLabel exitMessage;
		// ���� ���� ���� ��ư
		static JButton yes;
		static JButton no;
		
		int remain_cnt = GameStartUI.remains;
		
		public void Exit() {
			if(remain_cnt==0)
			{
				// ���� �ȳ� ������ ����
				exitFrame = new JFrame();
				exitFrame.setTitle("���� ����");
				exitFrame.setLocation(500,500);
				exitFrame.setSize(400,400);
				exitFrame.setVisible(true);
				exitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// ���� ���� �ȳ���
				panelNorth = new JPanel();
				panelNorth.setLayout(new GridLayout(1,1));
				panelNorth.setPreferredSize(new Dimension(400, 100));
				panelNorth.setBackground(Color.GRAY);

				exitMessage = new JLabel("���̵��� �ٽ� �����Ͻðڽ��ϱ�?");
				exitMessage.setPreferredSize(new Dimension(400, 100));
				exitMessage.setForeground(Color.WHITE);
				exitMessage.setFont(new Font("Monaco", Font.BOLD, 20));
				exitMessage.setHorizontalAlignment(JLabel.CENTER);
				panelNorth.add(exitMessage); 
				exitFrame.add("North", panelNorth);
				
				//���� ��ư
				panelCenter = new JPanel();				
				panelCenter.setPreferredSize(new Dimension(400,100));
				panelCenter.setLayout(new GridLayout(1,2));
				
				yes = new JButton("��");
				yes.setPreferredSize(new Dimension(150,100));
				yes.setFont(new Font("Monaco", Font.BOLD, 25));
				yes.setForeground(Color.WHITE);
				yes.setBackground(Color.DARK_GRAY);
				
				no = new JButton("�ƴϿ�");
				no.setPreferredSize(new Dimension(150,100));
				no.setFont(new Font("Monaco", Font.BOLD, 25));
				no.setForeground(Color.WHITE);
				no.setBackground(Color.DARK_GRAY);
				
				panelCenter.add(yes,"CENTER");
				panelCenter.add(no,"CENTER");
				
				exitFrame.add("Center",panelCenter);
			}
		}
}
