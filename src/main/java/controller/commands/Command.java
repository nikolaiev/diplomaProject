package controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vlad on 20.04.17.
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
