package controller.commands.impl.api;

import controller.commands.Command;
import controller.commands.CommandWrapper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vlad on 20.04.17.
 */
public class UploadFileCommand extends CommandWrapper implements Command {
    private static final Logger logger=Logger.getLogger(UploadFileCommand.class);
    @Override
    public String processExecute(HttpServletRequest request, HttpServletResponse response) {
        //TODO implement
        logger.warn("Functionality is not defined");
        throw new UnsupportedOperationException("Upload fileCommand");
    }
}
