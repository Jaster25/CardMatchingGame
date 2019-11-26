import java.awt.*;
import javax.swing.*;

public class ExitUI extends JFrame{
	
	// 게임 종료 안내문 프레임
		static JFrame exitFrame;
		// 게임 종료 안내문 패널
		static JPanel panelNorth;
		static JPanel panelCenter;
		// 게임 종료 안내문 메시지
		static JLabel exitMessage;
		// 게임 종료 선택 버튼
		static JButton yes;
		static JButton no;
		
		int remain_cnt = GameStartUI.remains;
		
		public void Exit() {
			if(remain_cnt==0)
			{
				// 종료 안내 프레임 띄우기
				exitFrame = new JFrame();
				exitFrame.setTitle("게임 종료");
				exitFrame.setLocation(500,500);
				exitFrame.setSize(400,400);
				exitFrame.setVisible(true);
				exitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// 게임 종료 안내문
				panelNorth = new JPanel();
				panelNorth.setLayout(new GridLayout(1,1));
				panelNorth.setPreferredSize(new Dimension(400, 100));
				panelNorth.setBackground(Color.GRAY);

				exitMessage = new JLabel("난이도를 다시 선택하시겠습니까?");
				exitMessage.setPreferredSize(new Dimension(400, 100));
				exitMessage.setForeground(Color.WHITE);
				exitMessage.setFont(new Font("Monaco", Font.BOLD, 20));
				exitMessage.setHorizontalAlignment(JLabel.CENTER);
				panelNorth.add(exitMessage); 
				exitFrame.add("North", panelNorth);
				
				//선택 버튼
				panelCenter = new JPanel();				
				panelCenter.setPreferredSize(new Dimension(400,100));
				panelCenter.setLayout(new GridLayout(1,2));
				
				yes = new JButton("예");
				yes.setPreferredSize(new Dimension(150,100));
				yes.setFont(new Font("Monaco", Font.BOLD, 25));
				yes.setForeground(Color.WHITE);
				yes.setBackground(Color.DARK_GRAY);
				
				no = new JButton("아니오");
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
