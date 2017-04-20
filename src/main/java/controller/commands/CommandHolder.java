package controller.commands;

import controller.commands.impl.*;
import controller.commands.impl.api.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vlad on 20.04.17.
 */
public class CommandHolder {
    private final String GET_PATH="GET:";
    private final String POST_PATH="POST:";
    private final String DEPLOY_PATH;
    private final Command INVALID_URL_COMMAND;

    private Map<String,Command> commands;
    private Map<String,Command> regexCommands;

    public CommandHolder(String deployPath){
        this.DEPLOY_PATH =deployPath;
        INVALID_URL_COMMAND =new GoInvalidUrlCommand();
        init();
    }

    private void init(){
        commands=new HashMap<>();

        /*COMMON COMMANDS*/
        commands.put(GET_PATH + DEPLOY_PATH + "/wav",new GoHomeCommand());
        commands.put(GET_PATH + DEPLOY_PATH + "/wav/handle",new WavHandlingCommand());
        commands.put(GET_PATH + DEPLOY_PATH + "/wav/download",new GoDownloadCommand());

        commands.put(POST_PATH + DEPLOY_PATH + "/api/upload",new UploadFileCommand());
        commands.put(POST_PATH + DEPLOY_PATH + "/api/download",new DownloadFileCommand());
        commands.put(POST_PATH + DEPLOY_PATH + "/api/filter",new FilterFileCommand());
    }

    public Command getCommand(String url) {
        if(url.startsWith(GET_PATH + DEPLOY_PATH + "/static"))
            return new GetStaticFileCommand();
        return commands.getOrDefault(url,INVALID_URL_COMMAND);

    }
}
