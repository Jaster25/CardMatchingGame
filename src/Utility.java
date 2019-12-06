import java.awt.Image;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

// ��ƿ��Ƽ �޼ҵ�� Ŭ����
public class Utility {

	// �Ű������� �´� ȿ���� ����
	static void soundPlay(String fileName) {

		File file = new File("./sounds/" + fileName + ".wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ī���� �̹��� �ٲٴ� �޼ҵ�
	static ImageIcon changeCardImage(String fileName) {
		ImageIcon icon = new ImageIcon("./images/" + fileName);
		Image originImage = icon.getImage();
		Image changedImage = originImage.getScaledInstance(Card.CARD_SIZE, Card.CARD_SIZE, Image.SCALE_SMOOTH);

		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
	}

	// ���, Ÿ��Ʋ ��� �� ������ �޼ҵ�
	static ImageIcon designImage(String fileName) {
		ImageIcon icon = new ImageIcon("./images/" + fileName);
		Image originImage = icon.getImage();

		// Ÿ��Ʋ ���
		if (fileName.equals("Title.jpg")) {
			Image changedImage = originImage.getScaledInstance(400, 100, Image.SCALE_SMOOTH);

			ImageIcon icon_new = new ImageIcon(changedImage);
			return icon_new;
		}
		Image changedImage = originImage.getScaledInstance(500, 100, Image.SCALE_SMOOTH);

		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
	}

	// ��ư�� �̹��� �ٲٴ� �޼ҵ�
	static ImageIcon changeButtonImage(String fileName) {
		ImageIcon icon = new ImageIcon("./images/" + fileName);
		Image originImage = icon.getImage();

		// ���̵� ���� ��ư
		if (fileName.equals("easy.jpg") || fileName.equals("normal.jpg") || fileName.equals("hard.jpg")) {
			Image changedImage = originImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

			ImageIcon icon_new = new ImageIcon(changedImage);
			return icon_new;
		}
		// ������, �Ͻ����� ��ư
		Image changedImage = originImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
	}
}
