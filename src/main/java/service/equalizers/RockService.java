package service.equalizers;

import org.apache.log4j.Logger;
import service.AbstractService;
import service.IService;
import service.exception.WavFileException;

import java.io.IOException;

/**
 * Created by vlad on 26.04.17.
 */
public class RockService extends AbstractService implements IService {
    private final static Logger logger =Logger.getLogger(RockService.class);
    private final int BEGIN_FREQUENCY=100;
    private final int END_FREQUENCY=3000;
    private final double VALUER =0.35;

    @Override
    public void filterFile(String FILE_NAME, String FILTERED_FILE_NAME, Integer beginFrequency, Integer endFrequency) throws IOException, WavFileException {
        simpleFilter(logger,FILE_NAME,FILTERED_FILE_NAME,BEGIN_FREQUENCY,END_FREQUENCY,VALUER);
    }
}
