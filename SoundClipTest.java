package mao;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundClipTest {

	public static void main(String filename) {
		new SoundClipTest(filename);

	}

	public SoundClipTest(String filename) {
		try {
			Scanner x = new Scanner(new File("src/userdata/currentaccount.txt"));
			Scanner in = new Scanner(new File("src/userdata/" + x.nextLine() + ".txt"));
			in.nextLine(); in.nextLine(); in.nextLine();
			float volume = Float.parseFloat(in.nextLine())/100;
			in.reset();

			File soundFile = new File(filename);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();


			FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);

			if(volume == 0) {
				gainControl.setValue(gainControl.getMinimum());
			}

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
