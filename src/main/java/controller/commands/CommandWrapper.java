package controller.commands;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vlad on 20.04.17.
 */
public abstract class CommandWrapper implements Command{
    private static String DEFAULT_ERROR_PAGE="/WEB-INF/view/errorPage.jsp";
    private static final Logger logger=Logger.getLogger(CommandWrapper.class);

    public abstract String processExecute(HttpServletRequest request, HttpServletResponse response);

    public String execute(HttpServletRequest request, HttpServletResponse response){
        try{
            return processExecute(request, response);
        }
        catch (Exception e){
            logger.error("command execution error",e);
            return DEFAULT_ERROR_PAGE;
        }
    }

}
