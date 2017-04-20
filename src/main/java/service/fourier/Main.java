package service.fourier;

import javafx.application.Application;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import service.model.WaveFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by vlad on 03.10.16.
 */
public class Main {

    final static int SIZE=44100;
    final static String TEST_FILE_PATH="/home/vlad/Downloads/egotistical.wav";
    final static Logger logger=Logger.getLogger(Main.class);
    public static void main(String[] args) throws Exception {


        // создание одноканального wave-файла из массива целых чисел
        /*System.out.println("Создание моно-файла...");
        int []samples=new int[100000];
        int x=0;
        for(int i=0; i < samples.length; i++){
            samples[i++] = (int)Math.round((Integer.MAX_VALUE/2)*
                    (Math.sin(2*Math.PI*440*x/44100)));
            x++;
        }

        service.model.WaveFile wf = new service.model.WaveFile(4, 44100, 1, samples);
        wf.saveFile(new File("testwav1.wav"));
        System.out.println("Продолжительность моно-файла: "+wf.getDurationTime()+ " сек.");

        // Создание стерео-файла
        System.out.println("Создание стерео-файла...");

        wf = new service.model.WaveFile(4, 44100, 1, samples);
        wf.saveFile(new File("/home/vlad/test/testwav2.wav"));
        System.out.println("Продолжительность стерео-файла: "+wf.getDurationTime()+ " сек.");*/

       /* // Чтение данных из файла
        System.out.println("Чтение данных из моно-файла:");
        wf = new service.model.WaveFile(new File("/home/vlad/test/testwav1.wav"));
        for(int i=0; i<50000; i+=200){
            System.out.println(wf.getSampleInt(i));
        }* /

        /*========TESTING==========*/


        //double[] realParts=new double[SIZE];

      //  double amp[]={0.9999,0.9999,0.99999}; //затухание
        //double freq[]={3000,250,400}; //затухание

   /*     for(int i=0; i < realParts.length; i++){
            realParts[i] =
                    (double)Math.round((Integer.MAX_VALUE/2)*Math.pow(amp[0],i)*
                    (Math.sin(2*Math.PI*freq[0]*i/22050)))
                            +
                    (double)Math.round((Integer.MAX_VALUE/2)*Math.pow(amp[1],i)*
                            (Math.sin(2*Math.PI*freq[1]*i/22050)))
                            +
                    (double)Math.round((Integer.MAX_VALUE/2)*Math.pow(amp[2],i)*
                            (Math.sin(2*Math.PI*freq[2]*i/22050)));

            //imageParts[i]=0;
        }
*/
        //creating wav file
        //createWav(realParts,"initial");

        WaveFile waveFile=new WaveFile(new File(TEST_FILE_PATH));
        logger.info("discretization "+waveFile.getFramesCount()/waveFile.getSampleSize());
        logger.info("channels "+waveFile.getChannels());
        logger.info("sample rate "+waveFile.getSampleRate());
        logger.info("sample size "+waveFile.getSampleSize());
        //double[] realParts;
        byte[] wavFileData=waveFile.getData();
        //int dataLength=wavFileData.length;
        System.out.println(wavFileData[0]);
        System.out.println(wavFileData[1]);
        System.out.println(wavFileData[2]);

        /*getting frequencies*/
        /*DoubleFFT_1D fftDo = new DoubleFFT_1D(dataLength);

        double[] fft = new double[dataLength * 2];

        //System.arraycopy(wavFileData, 0, fft, 0, wavFileData.length);
        for(int i=0;i<wavFileData.length;i++){
            fft[i]=wavFileData[i];
        }
         fftDo.realForwardFull(fft);

        double frequen[]=new double[44100];
        logger.info("frequen.length "+frequen.length);
        logger.info("fft.length "+fft.length );

        for(int i=0;i<44100;i++){
            frequen[i]=Math.sqrt(fft[2*i]*fft[2*i]+fft[2*i+1]*fft[2*i+1]);
        }
//*/
//        int [] wavFileDataDouble =IntStream.range(0, dataLength).map(i -> wavFileData[i]).toArray();
//
//        createWav(wavFileDataDouble,waveFile.getSampleSize(),waveFile.getSampleRate(),
//                waveFile.getChannels(),"init2");
        //JFrame initFrame=drawFrame(frequen,wavFileDataDouble,"Initial chart");
        //saveJframeAsImage(initFrame,"intitial");


        /*SIMPLE FILTERING*/
        /*double alpha=0.999;

        double filterData[]=new double[SIZE];
        for(int i=1;i<SIZE;i++){
            filterData[i]=(1-alpha)*wavFileData[i]
                    +
                    alpha*filterData[i-1];
        }*/

        /*getting frequencies*/
        /*DoubleFFT_1D fftDo2 = new DoubleFFT_1D(filterData.length);
        double[] fft2 = new double[filterData.length * 2];
        System.arraycopy(filterData, 0, fft2, 0, filterData.length);
        fftDo2.realForwardFull(fft2);

        double frequenFilt[]=new double[SIZE];

        for(int i=0;i<filterData.length;i++){
            frequenFilt[i]=Math.sqrt(fft2[2*i]*fft2[2*i]+fft2[2*i+1]*fft2[2*i+1]);
        }

        *//*DRAWING*//*
        JFrame filtFrame=drawFrame(frequenFilt,filterData,"FIltered chart");

        //creating wav file
        createWav(filterData,"filtered");

        saveJframeAsImage(filtFrame,"filtered");*/

    }


}


