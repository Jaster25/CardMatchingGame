import java.awt.Image;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

// 유틸리티 메소드용 클래스
public class Utility {
	
	// 매개변수에 맞는 효과음 실행
		static void soundPlay(String sound) {

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
			Image changedImage = originImage.getScaledInstance(Card.CARD_SIZE, Card.CARD_SIZE, Image.SCALE_SMOOTH);

			ImageIcon icon_new = new ImageIcon(changedImage);
			return icon_new;
		}
}
