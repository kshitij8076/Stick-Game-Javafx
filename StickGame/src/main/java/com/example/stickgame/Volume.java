package com.example.stickgame;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Volume {
    private Long currentFrame;
    private Clip clip;

    // current status of clip
    private String status;

    public String getFilePath() {
        return filePath;
    }

    private AudioInputStream audioInputStream;
    private String filePath = "sounds/music1.wav";

    public void  setClip()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {

        getFilePath();
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());


        clip = AudioSystem.getClip();

        clip.open(audioInputStream);

//        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //start the clip
        setClip();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();

        status = "play";
    }

    public void playGameover() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        setClip();
        clip.start();
        status = "play";
    }
    public void pause()
    {
        if (status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame =
                this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        if (status.equals("play"))
        {
            System.out.println("Audio is already "+
                    "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    public void restart() throws IOException, LineUnavailableException,
            UnsupportedAudioFileException
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    public void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }


    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setFilePath(String path){
        filePath = path;
    }
}
