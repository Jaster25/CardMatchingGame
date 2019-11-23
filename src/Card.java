
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Card extends JButton {

	// �ո� true, �޸� false
	boolean open;
	boolean correct;

	String front, back;

	public Card(int i) {
		super();
		this.open = false;
		this.correct = false;

		this.front = i + ".jpg";
		this.back = "0.png";

		this.setPreferredSize(new Dimension(75, 75));
		this.setIcon(changeImage(back));

		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ����ó�� �ȵ� ī���� ��츸
				if (!correct) {
					// �õ� Ƚ�� ����
					Board.clickCount++;
					Board.labelMessage.setText("Find same Card!" + " Try " + Board.clickCount);
					cardClick();
				}
			}
		});
	}

	public void cardClick() {

		soundPlay("flip");

		// �ո��� ���
		if (open) {
			setIcon(changeImage(back));
			open = false;
			Board.openCardNumber--;
		}
		// �޸��� ���
		else {
			setIcon(changeImage(front));
			open = true;
			Board.openCardNumber++;
		}

		if (Board.openCardNumber == 1) {
			Board.firstSelect = this;
		} else if (Board.openCardNumber == 2) {
			Board.secondSelect = this;

			checking();
		}

		System.out.println(this.front);
	}

	public boolean isCorrect() {
		return Board.firstSelect.front.equals(Board.secondSelect.front);
	}

	public void checking() {

		Board.openCardNumber = 0;

		Card first = Board.firstSelect;
		Card second = Board.secondSelect;

		// ������ ���
		if (isCorrect()) {
			soundPlay("correct");
			first.correct = true;
			second.correct = true;
		}

		else {

			soundPlay("wrong");
			// Ŭ�� ��ġ�� �� ������ ���� ó��
			first.open = false;
			second.open = false;

			// �ð� ������
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// �޸� ó��
					soundPlay("flip");
					first.setIcon(changeImage(first.back));
				}
			}, 500);

			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					soundPlay("flip");
					second.setIcon(changeImage(second.back));
				}
			}, 700);

		}
	}

	// ī�� �� ���� �Լ�
	static ArrayList<Card> createDeck(int size) {

		ArrayList<Card> deck = new ArrayList<Card>();

		// ������
		for (int i = 1; i <= size / 2; ++i) {
			Card newCard1 = new Card(i);
			Card newCard2 = new Card(i);

			deck.add(newCard1);
			deck.add(newCard2);
		}

		// deck ����
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));

		return deck;
	}

	// �Ű������� �´� ȿ���� ����
	void soundPlay(String sound) {

		File file = new File("./sounds/" + sound + ".wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �̹��� �ٲٴ� �޼ҵ�
	static ImageIcon changeImage(String filename) {
		ImageIcon icon = new ImageIcon("./images/" + filename);
		Image originImage = icon.getImage();
		// �̹��� ������ �ٲ㼭 ������ �޼ҵ�
		Image changedImage = originImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
	}
}
