package com.chickenachiever.audio;
import javax.sound.sampled.*;

public class AudioPlayer {

    private Clip clip;

    public AudioPlayer(String s) {

	try {
	    AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(s));
	    AudioFormat baseFormat = ais.getFormat();
	    AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
	    AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
	    clip = AudioSystem.getClip();
	    clip.open(dais);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public void play() {
	if (clip == null)
	    return;
	stop();
	clip.setFramePosition(0);
	clip.start();
    }

    public void stop() {
	if (clip.isRunning())
	    clip.stop();
    }
    
    public void loop() {
	stop();
	clip.setLoopPoints(0, clip.getFrameLength() - 1);
	clip.setFramePosition(0);
	clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void close() {
	stop();
	clip.close();
    }

}
