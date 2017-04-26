package service;

import org.apache.log4j.Logger;
import service.equalizers.JazzService;
import service.equalizers.PopService;
import service.equalizers.RockService;
import service.fourier.FilterService;

/**
 * Factory for filtering services
 * Created by vlad on 26.04.17.
 */

public class ServiceFactory {
    private static Logger logger=Logger.getLogger(ServiceFactory.class);

    private ServiceFactory(){

    }

    private static class InstanceHolder{
        static ServiceFactory INSTANCE =new ServiceFactory();
    }

    public static ServiceFactory getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public IService getService(ServiceType type){
        logger.info("Requested ServiceType "+type);

        if(type==null) {
            logger.error("NULL argument is not available");
            throw new IllegalArgumentException("NULL argument is not available");
        }

        switch (type){
            case POP: return new PopService();
            case ROCK: return new RockService();
            case JAZZ: return new JazzService();
            case FILTER: return new FilterService();

            default:
                logger.error("Requested ServiceType is not supported. "+type);
                throw new IllegalArgumentException("Type not supported");
        }
    }
}
