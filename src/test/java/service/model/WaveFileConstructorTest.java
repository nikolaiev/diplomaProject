package service.model;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by vlad on 20.04.17.
 */
@RunWith(Parameterized.class)
public class WaveFileConstructorTest {
    private static final Logger logger=Logger.getLogger(WaveFileConstructorTest.class);

    @Parameterized.Parameters
    public static Iterable<? extends Object> data() {
        return Arrays.asList("initial.wav", "are_you_online.wav");
    }

    private String path;
    public WaveFileConstructorTest(String path){
        this.path=path;

    }

    @Test
    public void defaultConstructor() throws IOException, UnsupportedAudioFileException {
        logger.info("wav file path "+path);
        File wavFile=new File(path);
        WaveFile waveFile = new WaveFile(wavFile);
        logger.info("duration time "+waveFile .getDurationTime());
        logger.info("audio format "+waveFile .getAudioFormat());
        logger.info("frames count "+waveFile .getFramesCount());
        logger.info("sample size "+waveFile .getSampleSize());
        assertNotNull(waveFile);
    }
}
