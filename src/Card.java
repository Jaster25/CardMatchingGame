
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
				// 정답처리 안된 카드일 경우만
				if (!correct) {
					// 시도 횟수 증가
					Board.clickCount++;
					Board.labelMessage.setText("Find same Card!" + " Try " + Board.clickCount);
					cardClick();
				}
			}
		});
	}

	public void cardClick() {

		soundPlay("flip");

		// 앞면인 경우
		if (open) {
			setIcon(changeImage(back));
			open = false;
			Board.openCardNumber--;
		}
		// 뒷면인 경우
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

	// 카드 덱 생성 함수
	static ArrayList<Card> createDeck(int size) {

		ArrayList<Card> deck = new ArrayList<Card>();

		// 쌍으로
		for (int i = 1; i <= size / 2; ++i) {
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
