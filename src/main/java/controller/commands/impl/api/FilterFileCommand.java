package controller.commands.impl.api;

import controller.commands.Command;
import controller.commands.CommandWrapper;
import controller.commands.exception.CommandException;
import controller.commands.helper.ParamExtractor;
import controller.commands.helper.RequestParamExtractor;
import org.apache.log4j.Logger;
import service.IService;
import service.ServiceFactory;
import service.ServiceType;
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
    private static final String EXTENSION ="wav";
    @Override
    public String processExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        HttpSession session=request.getSession();

        String initFileName= (String) session.getAttribute("initFileName");

        logger.info("Getting intOrNull params");
        Integer bFreq=paramExtractor.getIntParamOrNull(request,"begin_freq");
        Integer eFreq=paramExtractor.getIntParamOrNull(request,"end_freq");
        String filesLocation=request.getServletContext().getInitParameter("upload.location");
        String filteredFileName= UUID.randomUUID().toString().replace("-","_")+"."+ EXTENSION;
        String filteredFullFileName=filesLocation+filteredFileName;

        ServiceType serviceType=paramExtractor.getEnumParamOrNull(request,"service_type",ServiceType.class);

        ServiceFactory serviceFactory=ServiceFactory.getInstance();
        IService service=serviceFactory.getService(serviceType);

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
