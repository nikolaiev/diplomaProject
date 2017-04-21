package controller.commands.impl.api;

import controller.commands.Command;
import controller.commands.CommandWrapper;
import controller.commands.exception.CommandException;
import controller.commands.helper.ParamExtractor;
import controller.commands.helper.RequestParamExtractor;
import org.apache.log4j.Logger;
import service.exception.WavFileException;
import service.fourier.FilterService;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.io.File;

/**
 * Created by vlad on 20.04.17.
 */
public class FilterFileCommand extends CommandWrapper implements Command {
    private static Logger logger= Logger.getLogger(FilterFileCommand.class);
    private static final ParamExtractor paramExtractor =new RequestParamExtractor();
    private static final String EXTENSION ="wav";
    @Override
    public String processExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {

        FilterService service=new FilterService();
        HttpSession session=request.getSession();

        String initFileName= (String) session.getAttribute("initFileName");

        int bFreq=paramExtractor.getIntParam(request,"begin_freq");
        int eFreq=paramExtractor.getIntParam(request,"end_freq");
        String filesLocation=request.getServletContext().getInitParameter("upload.location");
        String filteredFileName= UUID.randomUUID().toString().replace("-","_")+"."+ EXTENSION;
        String filteredFullFileName=filesLocation+filteredFileName;


        try {
            service.filterFile(initFileName,filteredFullFileName,bFreq,eFreq);
            /*remove previous filtered file*/
            Optional.ofNullable((String)session.getAttribute("filteredFileName")).ifPresent(fileName->this.removeFile(fileName,filesLocation));

            /*successful execution*/
            session.setAttribute("filteredFileName",filteredFileName);

        }
        catch (WavFileException e){
            logger.error("Wav file filter exception",e);
            throw new CommandException("Filtering error. "+e.toString());
        }

        response.sendRedirect("/wav/download");
        return "REDIRECTED";
    }

    private void removeFile(String fileName, String filePath) {
        File file = new File(filePath+fileName);

        if(file.exists()&&file.delete()){
            logger.info(file.getName() + " is deleted!");
        }else{
            logger.warn("Delete operation is failed."+file.getName());
        }
    }
}
