package com.jobsity.bowling;

import com.jobsity.bowling.game.IGameParser;
import com.jobsity.bowling.game.BowlingProgram;
import com.jobsity.bowling.game.impl.FileParserImpl;
import com.jobsity.bowling.utils.AppUtils;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    
    private static final Logger log = Logger.getLogger(App.class.getSimpleName());
    
    public static void main( String[] args )
    {
    
        
        if(args == null || args.length == 0){
            log.severe("No input file specified.");
            log.severe("Usage: java -jar bowling.jar <file-path>");
            System.exit(0);
        }
        
        final String path = AppUtils.strip(args[0]);
        
        
        if(path == null){
            log.severe("The file path can not be empty whitespace.");
            System.exit(0);
        }
        IGameParser p = new FileParserImpl(path);
    
        BowlingProgram program = new BowlingProgram();
        program.setParser(p);
        
        program.start();
        program.display();
    }
    
}
