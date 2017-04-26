package controller;

import controller.commands.Command;
import controller.commands.CommandFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vlad on 20.04.17.
 */
public class FrontController extends HttpServlet{
    private static final Logger logger=Logger.getLogger(FrontController.class);
    private static String DEPLOY_PATH;

    public FrontController(){
        DEPLOY_PATH=getServletContext().getContextPath();
    }
    @Override
    public void init(){
        logger.info("Front controller initiation");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("processRequest method call");

        String url=req.getMethod()+":"+req.getRequestURI();

        Command command= CommandFactory.getInstance().getCommand(url);

        String view=command.execute(req,resp);

        logger.info("view is "+view);
        //check if request is not redirected
        if(!"REDIRECTED".equals(view))
            req.getRequestDispatcher(view).forward(req,resp);
    }
}
