package controller.commands.impl.api;

import controller.commands.Command;
import controller.commands.CommandWrapper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by vlad on 20.04.17.
 */
public class DownloadFileCommand extends CommandWrapper implements Command {
    @Override
    public String processExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=filtered.wav");


        HttpSession session=request.getSession();
        String fileName=(String) session.getAttribute("filteredFileName");
        fileName=request.getServletContext().getInitParameter("upload.location")+fileName;

        File file = new File(fileName);
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();

        byte[] outputByte = new byte[4096];
        //copy binary contect to output stream
        while(fileIn.read(outputByte, 0, 4096) != -1)
        {
            out.write(outputByte, 0, 4096);
        }
        fileIn.close();
        out.flush();
        out.close();

        return "REDIRECTED";
    }
}
