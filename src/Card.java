
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

	static private int CARD_SIZE;

	// �ո� true, �޸� false
	boolean open;
	boolean correct;
	boolean isWall;

	String front, back;

	public Card(int i) {
		super();
		open = false;
		correct = false;
		isWall = false;

		front = i + ".png";
		back = "0.png";

		// ī�� ���̵��� �°� ������ ����
		if (CardGame.stepLevel == 0) {
			CARD_SIZE = 200;
		} else if (CardGame.stepLevel == 1) {
			CARD_SIZE = 150;
		} else {
			CARD_SIZE = 120;
		}

		this.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE));
		this.setIcon(changeImage(back));

		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ����ó�� �ȵ� ī���� ��츸
				if (!correct) {
					// �õ� Ƚ�� ����
					GameStartUI.clickCount++;
					GameStartUI.labelMessage.setText("Find same Card!" + " Try " + GameStartUI.clickCount);
					cardClick();
				}
			}
		});
	}

	// wall ����
	public Card() {
		super();
		open = false;
		correct = false;

		isWall = false;
		front = "wall.png";
		back = "wall.png";

		// ī�� ���̵��� �°� ������ ����
		if (CardGame.stepLevel == 0) {
			CARD_SIZE = 200;
		} else if (CardGame.stepLevel == 1) {
			CARD_SIZE = 150;
		} else {
			CARD_SIZE = 120;
		}

		this.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE));
		this.setIcon(changeImage(back));
	}

	public void cardClick() {

		// �ո��� ���
		if (open) {
			flip();
			GameStartUI.openCardNumber--;
		}
		// �޸��� ���
		else {
			flip();
			GameStartUI.openCardNumber++;
		}

		if (GameStartUI.openCardNumber == 1) {
			GameStartUI.firstSelect = this;
		} else if (GameStartUI.openCardNumber == 2) {
			GameStartUI.secondSelect = this;

			checking();
		}
	}

	public boolean isCorrect() {
		return GameStartUI.firstSelect.front.equals(GameStartUI.secondSelect.front);
	}

	public void checking() {

		GameStartUI.openCardNumber = 0;

		Card first = GameStartUI.firstSelect;
		Card second = GameStartUI.secondSelect;

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

	// 0.jpg �� �޸��̴� for�� 1����

	// ī�� �� ���� �Լ�
	static ArrayList<Card> createEasyDeck() {

		ArrayList<Card> deck = new ArrayList<Card>();

		// ������
		for (int i = 1; i <= 4; ++i) {
			Card newCard1 = new Card(i);
			Card newCard2 = new Card(i);

			deck.add(newCard1);
			deck.add(newCard2);
		}

		// deck ����
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));

		// ��� �� �߰�
		Card wall = new Card();
		deck.add(4, wall);

		return deck;
	}

	static ArrayList<Card> createNormalDeck() {

		ArrayList<Card> deck = new ArrayList<Card>();

		// ������
		for (int i = 1; i <= 8; ++i) {
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

	static ArrayList<Card> createHardDeck() {

		ArrayList<Card> deck = new ArrayList<Card>();

		// ������
		for (int i = 1; i <= 12; ++i) {
			Card newCard1 = new Card(i);
			Card newCard2 = new Card(i);

			deck.add(newCard1);
			deck.add(newCard2);
		}

		// deck ����
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));

		// ��� �� �߰�
		Card wall = new Card();
		deck.add(12, wall);

		return deck;
	}

// ������ ���� ����?
// ���� ���۽� ī�� ��� �����ֱ�
	public static void startEffect(ArrayList<Card> deck) {

		for (Card card : deck) {
			card.flip();
		}

		// �ð� ������
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				for (Card card : deck)
					card.flip();
			}
		}, 500);

	}

	// ī�� ������ - ���� ī�� ��� �����ֱ⿡�� �� �Լ�
	public void flip() {
		// ���� �ƴ� ���
		if (!isWall) {
			soundPlay("flip");

			if (open) {
				setIcon(changeImage(back));
				open = false;
			} else {
				setIcon(changeImage(front));
				open = true;
			}

		}
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
	ImageIcon changeImage(String filename) {
		ImageIcon icon = new ImageIcon("./images/" + filename);
		Image originImage = icon.getImage();
		// �̹��� ������ �ٲ㼭 ������ �޼ҵ�
		Image changedImage = originImage.getScaledInstance(CARD_SIZE, CARD_SIZE, Image.SCALE_SMOOTH);

		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
	}
}
