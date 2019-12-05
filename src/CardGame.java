import javax.swing.JFrame;

public class CardGame extends JFrame {

	public SelectLevelUI panel_1 = null; // �ʱ�ȭ�� UI�г�
	public GameStartUI panel_2 = null; // ���� ���� �Ǵ� UI�г�
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

		window.setTitle("���� �׸� ã�� ����");
		window.panel_1 = new SelectLevelUI(window);
		window.add(window.panel_1);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(500, 335);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
