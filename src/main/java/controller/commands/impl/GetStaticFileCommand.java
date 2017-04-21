package controller.commands.impl;

import controller.commands.Command;
import controller.commands.CommandWrapper;
import controller.commands.exception.CommandException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

/**
 * Created by vlad on 20.04.17.
 */
public class GetStaticFileCommand extends CommandWrapper implements Command {
    private static final String FILE_NOT_EXISTS="Requested file does not exist.";
    private static final Logger logger=Logger.getLogger(GetStaticFileCommand.class);
    @Override
    public String processExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        File file = new File(request.getServletContext().getInitParameter("upload.location"), filename);

        if(!file.exists()||!file.isFile()) {
            logger.error(FILE_NOT_EXISTS+" "+filename);
            throw new CommandException(FILE_NOT_EXISTS);
        }

        response.setHeader("Content-Type", request.getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());

        return "REDIRECTED";
    }
}
