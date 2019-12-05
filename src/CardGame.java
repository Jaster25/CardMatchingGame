import javax.swing.JFrame;

public class CardGame extends JFrame {

	public SelectLevelUI panel_1 = null; // 초기화면 UI패널
	public GameStartUI panel_2 = null; // 게임 실행 되는 UI패널
	static CardGame window;

	static int stepLevel;

	public void change(String panelName) {
		if (panelName.equals("panel_1")) {
			getContentPane().removeAll();
			getContentPane().add(panel_1);
			revalidate();
			repaint();
		} else {
			getContentPane().removeAll();
			getContentPane().add(panel_2);
			revalidate();
			repaint();
		}
	}

	public static void main(String args[]) {

		window = new CardGame();
		GameStartUI.reset();
		GameStartUI.timerStart();

		window.setTitle("같은 그림 찾기 게임");
		window.panel_1 = new SelectLevelUI(window);
		window.add(window.panel_1);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(500, 335);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
