
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
	boolean isWall;

	static private int CARD_WIDTH;
	static private int CARD_HEIGHT;

	String front, back;

	public Card(int i) {
		super();
		open = false;
		correct = false;
		isWall = false;

		front = i + ".jpg";
		back = "0.png";

		// ī�� ���̵��� �°� ������ ����
		if (CardGame.stepLevel == 0) {
			CARD_HEIGHT = 555;
			CARD_WIDTH = 555;
		} else if (CardGame.stepLevel == 1) {
			CARD_HEIGHT = 444;
			CARD_WIDTH = 444;
		} else {
			CARD_HEIGHT = 333;
			CARD_WIDTH = 333;
		}

		this.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
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

		setEnabled(false);
		isWall = false;
		front = "wall.jpg";
		back = "wall.jpg";

		// ī�� ���̵��� �°� ������ ����
		if (CardGame.stepLevel == 0) {
			CARD_HEIGHT = 3333;
			CARD_WIDTH = 3333;
		} else if (CardGame.stepLevel == 1) {
			CARD_HEIGHT = 4444;
			CARD_WIDTH = 4444;
		} else {
			CARD_HEIGHT = 5555;
			CARD_WIDTH = 5555;
		}

		this.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
		this.setIcon(changeImage(back));
	}

	public void cardClick() {

		soundPlay("flip");

		// �ո��� ���
		if (open) {
			setIcon(changeImage(back));
			open = false;
			GameStartUI.openCardNumber--;
		}
		// �޸��� ���
		else {
			setIcon(changeImage(front));
			open = true;
			GameStartUI.openCardNumber++;
		}

		if (GameStartUI.openCardNumber == 1) {
			GameStartUI.firstSelect = this;
		} else if (GameStartUI.openCardNumber == 2) {
			GameStartUI.secondSelect = this;

			checking();
		}

		System.out.println(this.front);
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

//	 ���� ���۽� ī�� ��� �����ֱ�
	public void startEffect() {

		int level = CardGame.stepLevel;

		// 33 - 1
		if (level == 1) {
			
		}
		// 44
		else if (level == 2) {

		}
		// 55 -1
		else {

		}
	}

	// ī�� ������ - ���� ī�� ��� �����ֱ⿡�� �� �Լ�
	public void flip() {
		// ���� �ƴ� ���
		if (!isWall)
			soundPlay("flip");

		if (open) {
			setIcon(changeImage(back));
			open = false;
		} else {
			setIcon(changeImage(front));
			open = true;
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
	static ImageIcon changeImage(String filename) {
		ImageIcon icon = new ImageIcon("./images/" + filename);
		Image originImage = icon.getImage();
		// �̹��� ������ �ٲ㼭 ������ �޼ҵ�
		Image changedImage = originImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
	}
}
