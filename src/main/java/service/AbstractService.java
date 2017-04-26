package service;

import org.apache.log4j.Logger;
import org.jtransforms.fft.DoubleFFT_1D;
import service.exception.WavFileException;
import service.model.WavFile;

import java.io.File;
import java.io.IOException;
/**
 * Created by vlad on 26.04.17.
 */
public abstract class AbstractService implements IService {

    protected final static String FILE_SIZE_EXCEPTION="File is to big";

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

    @Override
    public abstract void filterFile(final String FILE_NAME,final String FILTERED_FILE_NAME,Integer beginFrequency,Integer endFrequency) throws IOException, WavFileException;

    protected void simpleFilter(Logger logger,  String fileName, String filteredFileName, int beginFrequency, int endFrequency,
                                double multiplier) throws IOException, WavFileException {

        WavFile wavFile=WavFile.openWavFile(new File(fileName));

        SAMPLE_RATE=(int) wavFile.getSampleRate();

        if(wavFile.getNumFrames()/2>Integer.MAX_VALUE-5) {
            logger.error(FILE_SIZE_EXCEPTION);
            throw new WavFileException(FILE_SIZE_EXCEPTION);
        }

        /*read file data*/
        int fileFramesCount=(int)wavFile.getNumFrames();
        int frames[]=new int[fileFramesCount];

        /*read data into frames*/
        wavFile.readFrames(frames, fileFramesCount);

        /*create appropriate fft helper object */
        DoubleFFT_1D fftDo = new DoubleFFT_1D(fileFramesCount);

        /*fft data container*/
        double [] fft=new double[fileFramesCount*2];

        /*write Re[i] to fft data*/
        for(int i=0;i<frames.length;i++){
            fft[i]=frames[i];
        }

        /*Frequency normalization coefficient
        * Used to find appropriate frequency Real and Imaginary parts
        * */
        double normCoeff=(double)SAMPLE_RATE/fileFramesCount;

        /*find fft data array indexes for desired frequency diapason*/
        int beginFreqIndex = (int) (beginFrequency /normCoeff);
        int endFreqIndex = (int) (endFrequency /normCoeff);

        /*filtered frames*/
        int framesFiltered[]=new int[fileFramesCount];


        /*FILTERING PROCESS*/
        fftDo.realForwardFull(fft);

        /*update freq range data*/


        for (int i = 0; i < beginFreqIndex; i++) {
            fft[2 * i] *= multiplier;
            fft[2 * i + 1] *= multiplier;
        }

        for (int i = endFreqIndex; i < fft.length/2-1; i++) {
            fft[2 * i] *= multiplier;
            fft[2 * i + 1]*= multiplier;
        }

        /*get inverse data*/
        fftDo.complexInverse(fft, true);


        /*getting filtered frames*/
        for(int i=0;i<fileFramesCount-1;i++){
            framesFiltered[i]=(int)fft[2*i];
        }

        fftDo.realForwardFull(fft);

        /*save filtered file*/
        WavFile newFile=WavFile.newWavFile(new File(filteredFileName),wavFile.getNumChannels(),
                framesFiltered.length,wavFile.getValidBits(),wavFile.getSampleRate());

        newFile.writeFrames(framesFiltered,framesFiltered.length);

        //close file streams
        newFile.close();
        wavFile.close();
        logger.info("file was filtered. New file is "+filteredFileName);
    }

}
