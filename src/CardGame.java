import javax.swing.JFrame;

public class CardGame extends JFrame {

	public SelectLevelUI panel_1 = null; //초기화면 UI패널
	public GameStartUI panel_2 = null; //게임 실행 되는 UI패널
	
	static int stepLevel;
	
	public void change(String panelName)
	{
		if(panelName.equals("panel_1")){
		
			getContentPane().removeAll();
			getContentPane().add(panel_1);
			revalidate();
			repaint();
		}
		else{
			getContentPane().removeAll();
			getContentPane().add(panel_2);
			revalidate();
			repaint();
		}
	}
	
	public static void main(String args[]) {
		
		CardGame window = new CardGame();
		
		window.setTitle("카드 짝 맞추기 게임");
		
		window.panel_1 = new SelectLevelUI(window);
		
		window.add(window.panel_1);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(500, 400);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		/*
		 * int stepLevel; //난이도 클래스 호출(매개변수 받기) Scanner input = new Scanner(System.in);
		 * System.out.print("난이도를 입력하세유 : "); stepLevel = input.nextInt(); //게임창 생성 시
		 * 난이도 클래스에서 받은 인자 값을 매개변수로 보내기 new Board(stepLevel);
		 */

		// new InitWindowUI();
	}
}
