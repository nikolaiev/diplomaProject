package service.fourier;

public class FftTest {
    final static int SIZE =90000;
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }


}


class A<U> implements Comparable<U>{
    public int compareTo(U o) {
        return 0;
    }

}

class B<U,R> extends A<Comparable<R>>{

}

        /*double realParts[]=new double[SIZE];
        for(int i=0; i < realParts.length; i++){
            realParts[i] = (double)Math.round((Integer.MAX_VALUE/2)*
                    (Math.sin(2*Math.PI*440*i/22050)))+
                    (double)Math.round((Integer.MAX_VALUE/3)*
                            (Math.sin(2*Math.PI*1000*i/22050)))
                    +
                    (double)Math.round((Integer.MAX_VALUE/3)*
                            (Math.sin(2*Math.PI*30*i/22050)))
            ;
            //imageParts[i]=0;
        }

        DoubleFFT_1D fftDo = new DoubleFFT_1D(realParts.length);
        double[] fft = new double[realParts.length * 2];
        System.arraycopy(realParts, 0, fft, 0, realParts.length);
        fftDo.realForwardFull(fft);

        /*for(double d: fft) {
            System.out.println(d);
        }* /

        double frequen[]=new double[SIZE];

        for(int i=0;i<realParts.length;i++){
            frequen[i]=Math.sqrt(fft[2*i]*fft[2*i]+fft[2*i+1]*fft[2*i+1]);
        }


        XYSeriesCollection dataset = new XYSeriesCollection( );
        XYSeries series1 = new XYSeries("First");

        for(int i=0;i<SIZE;i++){
            series1.add(i*22050/SIZE, frequen[i]);
        }

        dataset.addSeries(series1);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Line Chart Demo 6",      // chart title
                "X",                      // x axis label
                "Y",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        ApplicationFrame applicationFrame=new ApplicationFrame("Spectrum");
        applicationFrame.setContentPane(chartPanel);

        applicationFrame.pack( );
        RefineryUtilities.centerFrameOnScreen( applicationFrame );
        applicationFrame.setVisible( true );
    }
}*/