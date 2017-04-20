package service.fourier;

import org.apache.log4j.Logger;
import org.jtransforms.fft.DoubleFFT_1D;
import service.exception.WavFileException;
import service.model.WavFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by vlad on 20.04.17.
 */
public class FilterService {
    private final static String ILLEGAL_ARG_EXCEPTION_MESSAGE="End frequency must higher than Start";
    private final static String FILE_SIZE_EXCEPTION="File is to big";
    private final static Logger logger =Logger.getLogger(FilterService.class);

    /*sample rate value in Hz*/
    private int SAMPLE_RATE=1000;

    /*level of filtering
    * 1-Lowest
    * 10-Highest
    * */
    private final int ITERATION_COUNT =10;


    public void filterFile(final String FILE_NAME,final String FILTERED_FILE_NAME,int beginFrequency,int endFrequency) throws IOException, WavFileException {
        logger.info("filterFile function start");

        if(beginFrequency>endFrequency) {
            logger.error(ILLEGAL_ARG_EXCEPTION_MESSAGE);
            throw new IllegalArgumentException(ILLEGAL_ARG_EXCEPTION_MESSAGE);
        }

        WavFile wavFile=WavFile.openWavFile(new File(FILE_NAME));

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
        for(int numFiltration = 0; numFiltration< ITERATION_COUNT; numFiltration++) {
            fftDo.realForwardFull(fft);
            //fft[0]=0;
//            if(numFiltration==0)
//                drawAndSave(fft, frames);

            /*update freq range data*/
            for (int i = beginFreqIndex; i < endFreqIndex; i++) {
                fft[2 * i] = 0.001;
                fft[2 * i + 1] = 0.001;
                //fft[i]=0;
            }

            /*get inverse data*/
            fftDo.complexInverse(fft, true);

            /*prepare data to backward conversion*/
            if(numFiltration< ITERATION_COUNT -1)
                for(int i=0;i<fft.length/2;i++){
                    fft[i]=fft[2*i];
                    fft[2*i+1]=0;
                }

        }//for ITERATION_COUNT
        /*FILTERING PROCESS END*/


        /*getting filtered frames*/
        for(int i=0;i<fileFramesCount-1;i++){
            framesFiltered[i]=(int)fft[2*i];
        }

        /*for(int i=0;i<fileFramesCount;i++){
            fft[i]=fft[2*i];
            fft[2*i+1]=0;
        }
        fftDo.realForwardFull(fft);*/
        //drawAndSave(fft,framesFiltered);




        /*save filtered file*/
        WavFile newFile=WavFile.newWavFile(new File(FILTERED_FILE_NAME),wavFile.getNumChannels(),
                framesFiltered.length,wavFile.getValidBits(),wavFile.getSampleRate());

        newFile.writeFrames(framesFiltered,framesFiltered.length);

        //close file streams
        newFile.close();
        wavFile.close();
        logger.info("file was filtered. New file is "+FILTERED_FILE_NAME);
    }
}
