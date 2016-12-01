package com.jordanturley.thread;

import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class MusicRunnable implements Runnable {
	private AudioFormat audioFormat;
	private AudioInputStream audioInputStream;
	private SourceDataLine sourceDataLine;
	
	public MusicRunnable (URL music) throws Exception {
		initAudioVars(music);
	}
	
	public void changeMusic(URL music) throws Exception {
		initAudioVars(music);
		sourceDataLine.open(audioFormat);
		sourceDataLine.start();
	}
	
	/**
	 * Sets up the music player and starts playing music
	 * I looked this up from here: http://www.developer.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
	 * I had no idea how to do this before looking this up
	 * @throws Exception If something goes wrong with the music player or reading in music files
	 */
	private void initAudioVars(URL music) throws Exception {
		audioInputStream = AudioSystem.getAudioInputStream(music);
		audioFormat = audioInputStream.getFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
		sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
	}
	
	@Override
	public void run() {
		byte[] audioBuffer = new byte[10000];
		
		try{
			int audioCount;

			while (true) {
				DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
				
				while((audioCount = audioInputStream.read(audioBuffer, 0, audioBuffer.length)) != -1) {
					if(audioCount > 0){
						sourceDataLine.write(audioBuffer, 0, audioCount);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
