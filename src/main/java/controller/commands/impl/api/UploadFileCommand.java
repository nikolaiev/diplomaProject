package controller.commands.impl.api;

import controller.commands.Command;
import controller.commands.CommandWrapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by vlad on 20.04.17.
 */
public class UploadFileCommand extends CommandWrapper implements Command {
    private static final Logger logger=Logger.getLogger(UploadFileCommand.class);
    @Override
    public String processExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("Try to load file");

        /*multipart data processing*/
        Part filePart=request.getPart("file_name");
        String extension=getFileExtension(filePart);
        String uniqueName = UUID.randomUUID().toString().replace("-","_")+"."+extension;
        String savedFileName=request.getServletContext().getInitParameter("upload.location")+uniqueName;
        File file = new File(savedFileName);

        /*save uploaded image*/
        try(InputStream inputStream=filePart.getInputStream();
            FileOutputStream fileOutputStream=new FileOutputStream(file);
        ){
            byte[] buffer = new byte[100];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        }

        HttpSession session=request.getSession();
        session.setAttribute("initFileName",savedFileName);
        session.setAttribute("initFileNameShort",uniqueName);
        response.getWriter().write("File successfully uploaded");
        //response.sendRedirect(request.getContextPath()+"/wav/handle");

        return "REDIRECTED";
    }

    private String getFileExtension(Part filePart) {
        String[] temp=filePart.getSubmittedFileName().split("\\.");
        return temp[temp.length-1];
    }
}
