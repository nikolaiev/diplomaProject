package controller.commands.impl;

import controller.commands.Command;
import controller.commands.CommandWrapper;
import controller.commands.exception.CommandException;
import org.apache.log4j.Logger;
import service.exception.WavFileException;
import service.fourier.FilterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vlad on 20.04.17.
 */
public class GoDownloadCommand extends CommandWrapper implements Command {
    private static final Logger logger= Logger.getLogger(GoDownloadCommand.class);
    @Override
    public String processExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        FilterService service=new FilterService();
        String filesLocation=request.getServletContext().getInitParameter("upload.location");
        String filteredFileName= (String) request.getSession().getAttribute("filteredFileName");

        if(filteredFileName!=null)
            try {
                String fullFileName=filesLocation+filteredFileName;
                int [] frequencies=service.getInitialFrequencies(fullFileName);
                int [] samples=service.getInitialSamples(fullFileName);
                int maxFrequency=service.getSampleRate(fullFileName);
                request.getSession().setAttribute("max_freq",maxFrequency);
                request.getSession().setAttribute("samples",samples);
                request.getSession().setAttribute("freq",frequencies);
            }
            catch (WavFileException e) {
                logger.error("File download error. "+e.toString());
                throw new CommandException("Wav file exception. "+e.toString());
            }
        return "/WEB-INF/view/downloadPage.jsp";
    }

}
