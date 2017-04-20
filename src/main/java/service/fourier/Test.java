package service.fourier;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jtransforms.fft.DoubleFFT_1D;
import service.model.WavFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

/**
 * Created by vlad on 20.04.17.
 */
public class Test {
    final static Logger logger =Logger.getLogger(Test.class);

    //final static String WAV_FILE_PATH ="/home/vlad/Downloads/are_you_online.wav";
    final static String WAV_FILE_PATH ="/home/vlad/Downloads/are_you_online.wav";

    static int SAMPLE_RATE=1;

    public static void main(String[] args) throws Exception {




    }

    private static void drawAndSave(double[] fft,int [] framesFiltered){
        //*DRAWING*//*
        double frequen[]=new double[framesFiltered.length];

        for(int i=0;i<framesFiltered.length-1;i++){
            frequen[i]=Math.sqrt(fft[2*i]*fft[2*i]+fft[2*i+1]*fft[2*i+1]);
        }
        frequen[0]=1;

        JFrame filtFrame=drawFrame(frequen,framesFiltered,"FIltered chart");

        saveJframeAsImage(filtFrame,"filtered");
    }

    private static void saveJframeAsImage(JFrame filtFrame, String filtered) {
        Image image=filtFrame.createImage(300,200);
        BufferedImage bi =toBufferedImage(image);

        Container content = filtFrame.getContentPane();
        BufferedImage img = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        content.printAll(g2d);
        g2d.dispose();

        try {
            ImageIO.write(img, "png", new File(filtered+".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }


    public static JFrame drawFrame(double[]frequenFilt/*frequen*/,int[] filterData/*samples*/,String title/*title*/){
        XYSeriesCollection datasetFiltFreq = new XYSeriesCollection( );
        XYSeriesCollection datasetFiltSampl = new XYSeriesCollection( );

        XYSeries seriesFiltFreq = new XYSeries("Filtered Frequency");
        XYSeries seriesFiltSampl= new XYSeries("Filtered Samples");

        double normalizeCoeff=(double)SAMPLE_RATE/filterData.length;

        for(int i=0;i<filterData.length;i++){
            seriesFiltFreq.add(i*normalizeCoeff, frequenFilt[i]);
            seriesFiltSampl.add(i, filterData[i]);
        }

        datasetFiltFreq.addSeries(seriesFiltFreq);
        datasetFiltSampl.addSeries(seriesFiltSampl);

        JFreeChart chartFiltFreq = ChartFactory.createXYLineChart(
                "Frequncy Chart",      // chart title
                "X",                      // x axis label
                "Y",                      // y axis label
                datasetFiltFreq,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        JFreeChart chartFiltSampl = ChartFactory.createXYLineChart(
                "Samples Chart",      // chart title
                "X",                      // x axis label
                "Y",                      // y axis label
                datasetFiltSampl,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        ChartPanel chartPanelFiltFreq = new ChartPanel( chartFiltFreq );
        chartPanelFiltFreq.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );

        ChartPanel chartPanelFiltSampl = new ChartPanel( chartFiltSampl );
        chartPanelFiltSampl.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );

        JFrame filtFrame = new JFrame(title);

        filtFrame.getContentPane().add(chartPanelFiltFreq,BorderLayout.EAST);
        filtFrame.getContentPane().add(chartPanelFiltSampl,BorderLayout.WEST);
        filtFrame.pack();
        filtFrame.setVisible(true);
        return filtFrame;
    }

}
