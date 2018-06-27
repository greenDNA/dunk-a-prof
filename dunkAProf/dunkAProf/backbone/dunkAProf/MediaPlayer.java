import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;


public class MediaPlayer {
    private Mixer mixer;
    private Clip clip;
    private Mixer.Info [] mixerInfo = AudioSystem.getMixerInfo();
    private DataLine.Info dataInfo;
    private AudioInputStream audioStream;
    private String fileName;

    public  MediaPlayer (String fileName) {

        this.fileName = fileName;

    }

    /**
     * Plays the audio file specified in the constructor.
     *
     */
    public void play() {
        try {
        	
            //The AudioSystem class acts as the entry point to the sampled-audio system resources.
            this.mixer = AudioSystem.getMixer(mixerInfo[0]);

            //Obtains an audio input stream from the provided File.
            this.audioStream = AudioSystem.getAudioInputStream(new File(this.fileName));

            //Construct the data line's info object from the specified information, which includes a single audio format.
            this.dataInfo = new DataLine.Info(Clip.class, audioStream.getFormat());

            this.clip = (Clip) mixer.getLine(dataInfo);
            
            //Opens the clip with the format and audio data present in the provided audio input stream.
            this.clip.open(audioStream);
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.clip.start();
        
    }
}