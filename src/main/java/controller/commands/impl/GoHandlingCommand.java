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
public class GoHandlingCommand extends CommandWrapper implements Command {
    private static final Logger logger= Logger.getLogger(GoHandlingCommand.class);
    @Override
    public String processExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        FilterService service=new FilterService();

        String fileName= (String) request.getSession().getAttribute("initFileName");
        if(fileName!=null)
            try {
                int [] initialSamples=service.getInitialSamples(fileName);
                int [] initialFreq=service.getInitialFrequensies(fileName);
                int maxFrequency=service.getSampleRate(fileName);
                request.getSession().setAttribute("max_freq",maxFrequency);
                request.getSession().setAttribute("samples",initialSamples);
                request.getSession().setAttribute("freq",initialFreq);

            } catch (IOException | WavFileException e) {
                logger.error("Getting info error. "+e.toString());
                throw new CommandException(e.toString());
            }

        return "/WEB-INF/view/handlePage.jsp";
    }

}
