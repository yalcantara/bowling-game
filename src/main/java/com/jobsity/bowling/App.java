package com.jobsity.bowling;

import com.jobsity.bowling.core.BowlingGame;
import com.jobsity.bowling.core.BowlingParser;
import com.jobsity.bowling.core.BowlingProgram;
import com.jobsity.bowling.core.FileParser;
import com.jobsity.bowling.model.Sheet;
import com.jobsity.bowling.utils.AppUtils;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
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
        BowlingParser p = new FileParser(path);
    
        BowlingProgram program = new BowlingProgram();
        program.setParser(p);
        
        program.start();
        program.display();
    }
    
}
