package controller.commands;

import controller.commands.impl.*;
import controller.commands.impl.api.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vlad on 20.04.17.
 */
public class CommandFactory {
    private final String GET_PATH="GET:";
    private final String POST_PATH="POST:";
    private final Command INVALID_URL_COMMAND;

    private Map<String,Command> commands;

    private CommandFactory(){
        INVALID_URL_COMMAND =new GoInvalidUrlCommand();
        init();
    }

    private static class InstanceHolder{
        static CommandFactory commandFactory=new CommandFactory();
    }

    public static CommandFactory getInstance(){
        return InstanceHolder.commandFactory;
    }

    private void init(){
        commands=new HashMap<>();

        /*COMMON COMMANDS*/
        commands.put(GET_PATH + "/wav",new GoHomeCommand());
        commands.put(GET_PATH + "/wav/handle",new GoHandlingCommand());
        commands.put(GET_PATH + "/wav/download",new GoDownloadCommand());
        commands.put(GET_PATH + "/wav/about",new GoAboutCommand());

        commands.put(POST_PATH + "/api/upload",new UploadFileCommand());
        commands.put(POST_PATH + "/api/download",new DownloadFileCommand());
        commands.put(POST_PATH + "/api/filter",new FilterFileCommand());
    }

    public Command getCommand(String url) {

        if(url.startsWith(GET_PATH + "/static"))
            return new GetStaticFileCommand();

        return commands.getOrDefault(url,INVALID_URL_COMMAND);

    }
}
