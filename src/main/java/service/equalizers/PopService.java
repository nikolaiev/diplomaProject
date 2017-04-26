package service.equalizers;

import service.AbstractService;
import service.IService;
import service.exception.WavFileException;

import java.io.IOException;

/**
 * Created by vlad on 26.04.17.
 */
public class PopService extends AbstractService implements IService {
    @Override
    public void filterFile(String FILE_NAME, String FILTERED_FILE_NAME, int beginFrequency, int endFrequency) throws IOException, WavFileException {

    }
}
