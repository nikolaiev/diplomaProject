package service.fourier;

import service.exception.WavFileException;

import java.io.IOException;

/**
 * Created by vlad on 21.04.17.
 */
public interface IFilterService {
    int getSampleRate(String FILE_NAME) throws IOException, WavFileException;

    int [] getInitialSamples(final String FILE_NAME) throws IOException, WavFileException;

    int[] getInitialFrequensies(final String FILE_NAME) throws IOException, WavFileException;

    void filterFile(final String FILE_NAME,final String FILTERED_FILE_NAME,int beginFrequency,int endFrequency)
            throws IOException, WavFileException;
}
