import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class CustomButton extends JButton {

	public CustomButton(Action a) {
		super(a);
		setting();
	}

	public CustomButton(Icon icon) {
		super(icon);
		setting();
	}

	public CustomButton(String text, Icon icon) {
		super(text, icon);
		setting();
	}

	public CustomButton(String text) {
		super(text);
		setting();
	}

	void setting() {

		setSize(50, 50);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Utility.soundPlay("buttonClickSound");
			}
		});
	}
}
