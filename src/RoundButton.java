import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		// 적용되는지?
		setSize(50,50);
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
	
	// hover 효과
	// 클릭시 작아지는
	
}
