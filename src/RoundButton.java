import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class RoundButton extends JButton {

	public RoundButton(Action a) {
		super(a);
		setting();
	}

	public RoundButton(Icon icon) {
		super(icon);
		setting();
	}

	public RoundButton(String text, Icon icon) {
		super(text, icon);
		setting();
	}

	public RoundButton(String text) {
		super(text);
		setting();
	}

	void setting() {

		setBorderPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);
		
	}

}
