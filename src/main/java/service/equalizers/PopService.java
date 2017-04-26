package service.equalizers;

import org.apache.log4j.Logger;
import service.AbstractService;
import service.IService;
import service.exception.WavFileException;

import java.io.IOException;

/**
 * Created by vlad on 26.04.17.
 */
public class PopService extends AbstractService implements IService {
    private final static Logger logger =Logger.getLogger(PopService.class);
    private final int BEGIN_FREQUENCY=1;
    private final int END_FREQUENCY=5500;
    private final double VALUER =0.1;

    @Override
    public void filterFile(String FILE_NAME, String FILTERED_FILE_NAME, Integer beginFrequency, Integer endFrequency) throws IOException, WavFileException {
        simpleFilter(logger,FILE_NAME,FILTERED_FILE_NAME,BEGIN_FREQUENCY,END_FREQUENCY,VALUER);
    }
}
