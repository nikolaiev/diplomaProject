package controller.commands;

import controller.commands.exception.CommandException;
import controller.commands.helper.ParamExtractor;
import controller.commands.helper.RequestParamExtractor;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vlad on 20.04.17.
 */
public abstract class CommandWrapper implements Command{
    protected static final ParamExtractor paramExtractor =new RequestParamExtractor();
    private static String DEFAULT_ERROR_PAGE="/WEB-INF/view/errorPage.jsp";
    private static final Logger logger=Logger.getLogger(CommandWrapper.class);

    public abstract String processExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException;

    public String execute(HttpServletRequest request, HttpServletResponse response){
        try{
            return processExecute(request, response);
        }
        catch (Exception e){
            logger.error("command execution error",e);
            request.setAttribute("error",e.toString());
            return DEFAULT_ERROR_PAGE;
        }
    }

}
