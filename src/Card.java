import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;

public class Card extends JButton {

	static int CARD_SIZE;

	// ī�� �̹��� �� ���� = 18��
	static final int CARD_TYPES = 18;

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
		this.setIcon(Utility.changeCardImage(back));

		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardClick();
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
		this.setIcon(Utility.changeCardImage(back));
	}

	public void cardClick() {
		// ���� ó�� �ȵ� ��츸 ����
		if (!correct) {
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

				GameStartUI.checking();
			}
		}
	}

	// ���̵��� �´� ī�� �� ���� �Լ�
	static ArrayList<Card> createEasyDeck() {

		ArrayList<Card> deck = new ArrayList<Card>();
		ArrayList<Integer> usedCardTypes = new ArrayList<Integer>();

		// ������
		for (int i = 1; i <= 4; ++i) {

			int randomType;

			while (true) {
				randomType = (int) (Math.random() * CARD_TYPES + 1);

				if (!usedCardTypes.contains(randomType)) {
					usedCardTypes.add(randomType);
					break;
				}
			}

			Card newCard1 = new Card(randomType);
			Card newCard2 = new Card(randomType);

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
		ArrayList<Integer> usedCardTypes = new ArrayList<Integer>();

		// ������
		for (int i = 1; i <= 8; ++i) {

			int randomType;

			while (true) {
				randomType = (int) (Math.random() * CARD_TYPES + 1);

				if (!usedCardTypes.contains(randomType)) {
					usedCardTypes.add(randomType);
					break;
				}
			}

			Card newCard1 = new Card(randomType);
			Card newCard2 = new Card(randomType);

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
		ArrayList<Integer> usedCardTypes = new ArrayList<Integer>();

		// ������
		for (int i = 1; i <= 12; ++i) {

			int randomType;

			while (true) {
				randomType = (int) (Math.random() * CARD_TYPES + 1);

				if (!usedCardTypes.contains(randomType)) {
					usedCardTypes.add(randomType);
					break;
				}
			}

			Card newCard1 = new Card(randomType);
			Card newCard2 = new Card(randomType);

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

		Timer timer2 = new Timer();
		timer2.schedule(new TimerTask() {
			@Override
			public void run() {

				GameStartUI.run = true;
			}
		}, 500);
	}

	// ī�� ������ - ���� ī�� ��� �����ֱ⿡�� �� �Լ�
	public void flip() {
		// ���� �ƴ� ���
		if (!isWall) {
			Utility.soundPlay("flip");

			if (open) {
				setIcon(Utility.changeCardImage(back));
				open = false;
			} else {
				setIcon(Utility.changeCardImage(front));
				open = true;
			}

		}
	}
}
