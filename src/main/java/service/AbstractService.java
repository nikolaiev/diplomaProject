package service;

import org.jtransforms.fft.DoubleFFT_1D;
import service.exception.WavFileException;
import service.model.WavFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by vlad on 26.04.17.
 */
public abstract class AbstractService implements IService {

    /*sample rate value in Hz*/
    protected int SAMPLE_RATE=1000;

    @Override
    public int getSampleRate(final String FILE_NAME) throws IOException, WavFileException {
        WavFile wavFile=WavFile.openWavFile(new File(FILE_NAME));
        return (int) wavFile.getSampleRate();
    }

    @Override
    public int [] getInitialSamples(final String FILE_NAME) throws IOException, WavFileException {
        WavFile wavFile=WavFile.openWavFile(new File(FILE_NAME));
        SAMPLE_RATE=(int) wavFile.getSampleRate();
        int [] result =new int[(int) wavFile.getNumFrames()];
        wavFile.readFrames(result, (int)wavFile.getNumFrames());
        return result;

    }

    @Override
    public int[] getInitialFrequencies(final String FILE_NAME) throws IOException, WavFileException {
        int fileFramesCount;
        double[] fft;

        WavFile wavFile = WavFile.openWavFile(new File(FILE_NAME));
        SAMPLE_RATE = (int) wavFile.getSampleRate();
        fileFramesCount = (int) wavFile.getNumFrames();
        int[] frames = new int[fileFramesCount];
        wavFile.readFrames(frames, (int) wavFile.getNumFrames());

    /*create appropriate fft helper object */
        DoubleFFT_1D fftDo = new DoubleFFT_1D(fileFramesCount);

    /*fft data container*/
        fft = new double[fileFramesCount * 2];

    /*write Re[i] to fft data*/
        for (int i = 0; i < frames.length; i++) {
            fft[i] = frames[i];
        }

        fftDo.realForwardFull(fft);


        int []result=new int[SAMPLE_RATE];

        double normalizeCoeff=(double)SAMPLE_RATE/fileFramesCount;
        for(int i=0;i<fileFramesCount-1;i++){
            int freqValue=(int)(i*normalizeCoeff);

            if(result[freqValue]!=0)
                continue;

            double frequencyAmplitude=Math.sqrt(fft[2*i]* fft[2*i]+ fft[2*i+1]* fft[2*i+1]);
            result[freqValue]=(int)frequencyAmplitude;
        }
        /*set zero freq*/
        result[0]=0;
        return result;

    }

    public abstract void filterFile(final String FILE_NAME,final String FILTERED_FILE_NAME,int beginFrequency,int endFrequency) throws IOException, WavFileException;

}
