
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

	// 앞면 true, 뒷면 false
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

		// 카드 난이도에 맞게 사이즈 설정
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
				// 정답처리 안된 카드일 경우만
				if (!correct) {
					// 시도 횟수 증가
					GameStartUI.clickCount++;
					GameStartUI.labelMessage.setText("Find same Card!" + " Try " + GameStartUI.clickCount);
					cardClick();
				}
			}
		});
	}

	// wall 생성
	public Card() {
		super();
		open = false;
		correct = false;

		setEnabled(false);
		isWall = false;
		front = "wall.jpg";
		back = "wall.jpg";

		// 카드 난이도에 맞게 사이즈 설정
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

		// 앞면인 경우
		if (open) {
			setIcon(changeImage(back));
			open = false;
			GameStartUI.openCardNumber--;
		}
		// 뒷면인 경우
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

		// 정답일 경우
		if (isCorrect()) {
			soundPlay("correct");
			first.correct = true;
			second.correct = true;
		}

		else {

			soundPlay("wrong");
			// 클릭 겹치는 일 없도록 먼저 처리
			first.open = false;
			second.open = false;

			// 시간 딜레이
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// 뒷면 처리
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

	
	// 0.jpg 는 뒷면이니 for문 1부터
	
	// 카드 덱 생성 함수
	static ArrayList<Card> createEasyDeck() {

		ArrayList<Card> deck = new ArrayList<Card>();

		// 쌍으로
		for (int i = 1; i <= 4; ++i) {
			Card newCard1 = new Card(i);
			Card newCard2 = new Card(i);

			deck.add(newCard1);
			deck.add(newCard2);
		}

		// deck 섞기
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));

		// 가운데 벽 추가
		Card wall = new Card();
		deck.add(4, wall);

		return deck;
	}

	static ArrayList<Card> createNormalDeck() {

		ArrayList<Card> deck = new ArrayList<Card>();

		// 쌍으로
		for (int i = 1; i <= 8; ++i) {
			Card newCard1 = new Card(i);
			Card newCard2 = new Card(i);

			deck.add(newCard1);
			deck.add(newCard2);
		}

		// deck 섞기
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));

		return deck;
	}

	static ArrayList<Card> createHardDeck() {

		ArrayList<Card> deck = new ArrayList<Card>();

		// 쌍으로
		for (int i = 1; i <= 12; ++i) {
			Card newCard1 = new Card(i);
			Card newCard2 = new Card(i);

			deck.add(newCard1);
			deck.add(newCard2);
		}

		// deck 섞기
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));

		// 가운데 벽 추가
		Card wall = new Card();
		deck.add(12, wall);

		return deck;
	}

//	 게임 시작시 카드 잠깐 보여주기
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

	// 카드 뒤집기 - 위에 카드 잠깐 보여주기에서 쓸 함수
	public void flip() {
		// 벽이 아닐 경우
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

	// 매개변수에 맞는 효과음 실행
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

	// 이미지 바꾸는 메소드
	static ImageIcon changeImage(String filename) {
		ImageIcon icon = new ImageIcon("./images/" + filename);
		Image originImage = icon.getImage();
		// 이미지 사이즈 바꿔서 저장할 메소드
		Image changedImage = originImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
	}
}
