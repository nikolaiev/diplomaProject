package controller.commands.impl;

import controller.commands.Command;
import controller.commands.CommandWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vlad on 20.04.17.
 */
public class GoHomeCommand extends CommandWrapper implements Command {
    @Override
    public String processExecute(HttpServletRequest request, HttpServletResponse response) {
        return "/WEB-INF/view/index.jsp";
    }
}
