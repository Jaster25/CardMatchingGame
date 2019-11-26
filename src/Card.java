
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.*;
public class Card extends JButton {

	static private int CARD_SIZE;

	// �ո� true, �޸� false
	boolean open;
	boolean correct;
	boolean isWall;

	String front, back;

	// ���� ���� �ȳ��� ������
	static JFrame exitFrame;
	// ���� ���� �ȳ��� �г�
	static JPanel panelNorth;
	static JPanel panelCenter;
	// ���� ���� �ȳ��� �޽���
	static JLabel exitMessage;
	// ���� ���� ���� ��ư
	static JButton yes;
	static JButton no;
	
	public Card(int i, int size) {
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
					GameStartUI.labelMessage.setText("Find same Card!" + " Try " + GameStartUI.clickCount/2);
					cardClick(size);
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

	public void cardClick(int size) {

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

			checking(size);
		}
	}

	public boolean isCorrect() {
		return GameStartUI.firstSelect.front.equals(GameStartUI.secondSelect.front);
	}

	public void checking(int size) {

		GameStartUI.openCardNumber = 0;

		Card first = GameStartUI.firstSelect;
		Card second = GameStartUI.secondSelect;

		// ������ ���
		if (isCorrect()) {
			soundPlay("correct");
			first.correct = true;
			second.correct = true;
			//�����̸� ���� ī�� ������ ī��Ʈ�ϰ� ��ü ī�尳���� ���� ������ ���� �����Ǵ�
			GameStartUI.correctedCardCnt++;
			if(GameStartUI.correctedCardCnt == size)
			{
				// ���� �ȳ� ������ ����
				exitFrame = new JFrame();
				exitFrame.setTitle("���� ����");
				exitFrame.setLocation(500,500);
				exitFrame.setSize(400,400);
				exitFrame.setVisible(true);
				exitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// ���� ���� �ȳ���
				panelNorth = new JPanel();
				panelNorth.setLayout(new GridLayout(1,1));
				panelNorth.setPreferredSize(new Dimension(400, 100));
				panelNorth.setBackground(Color.GRAY);

				exitMessage = new JLabel("���̵��� �ٽ� �����Ͻðڽ��ϱ�?");
				exitMessage.setPreferredSize(new Dimension(400, 100));
				exitMessage.setForeground(Color.WHITE);
				exitMessage.setFont(new Font("Monaco", Font.BOLD, 20));
				exitMessage.setHorizontalAlignment(JLabel.CENTER);
				panelNorth.add(exitMessage); 
				exitFrame.add("North", panelNorth);
				
				//���� ��ư
				panelCenter = new JPanel();				
				panelCenter.setPreferredSize(new Dimension(400,100));
				panelCenter.setLayout(new GridLayout(1,2));
				
				yes = new JButton("��");
				yes.setPreferredSize(new Dimension(150,100));
				yes.setFont(new Font("Monaco", Font.BOLD, 25));
				yes.setForeground(Color.WHITE);
				yes.setBackground(Color.DARK_GRAY);
				
				no = new JButton("�ƴϿ�");
				no.setPreferredSize(new Dimension(150,100));
				no.setFont(new Font("Monaco", Font.BOLD, 25));
				no.setForeground(Color.WHITE);
				no.setBackground(Color.DARK_GRAY);
				
				panelCenter.add(yes,"CENTER");
				panelCenter.add(no,"CENTER");
				
				exitFrame.add("Center",panelCenter);
			}
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
			Card newCard1 = new Card(i,4);
			Card newCard2 = new Card(i,4);

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
			Card newCard1 = new Card(i,8);
			Card newCard2 = new Card(i,8);

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
			Card newCard1 = new Card(i,12);
			Card newCard2 = new Card(i,12);

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
