import java.awt.Image;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

// ��ƿ��Ƽ �޼ҵ�� Ŭ����
public class Utility {
	
	// �Ű������� �´� ȿ���� ����
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

		// �̹��� �ٲٴ� �޼ҵ�
		static ImageIcon changeImage(String filename) {
			ImageIcon icon = new ImageIcon("./images/" + filename);
			Image originImage = icon.getImage();
			// �̹��� ������ �ٲ㼭 ������ �޼ҵ�
			Image changedImage = originImage.getScaledInstance(Card.CARD_SIZE, Card.CARD_SIZE, Image.SCALE_SMOOTH);

			ImageIcon icon_new = new ImageIcon(changedImage);
			return icon_new;
		}
}
