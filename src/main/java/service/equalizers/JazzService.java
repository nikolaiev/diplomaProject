package service.equalizers;

import org.apache.log4j.Logger;
import org.jtransforms.fft.DoubleFFT_1D;
import service.AbstractService;
import service.IService;
import service.exception.WavFileException;
import service.model.WavFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by vlad on 26.04.17.
 */
public class JazzService extends AbstractService implements IService {
    private final static Logger logger =Logger.getLogger(JazzService.class);
    private final int BEGIN_FREQUENCY=1000;
    private final int END_FREQUENCY=5000;
    private final double VALUER =0.2;

    @Override
    public void filterFile(String FILE_NAME, String FILTERED_FILE_NAME, Integer beginFrequency, Integer endFrequency) throws IOException, WavFileException {
        simpleFilter(logger,FILE_NAME,FILTERED_FILE_NAME,BEGIN_FREQUENCY,END_FREQUENCY,VALUER);
    }
}
