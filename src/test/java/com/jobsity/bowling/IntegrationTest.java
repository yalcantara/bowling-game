package com.jobsity.bowling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.jobsity.bowling.core.BowlingParser;
import com.jobsity.bowling.core.BowlingProgram;
import com.jobsity.bowling.core.FileParser;
import org.junit.Test;


public class IntegrationTest
{
    @Test
    public void sampleGameTest(){
    
        BowlingProgram p = new BowlingProgram();
    
        BowlingParser parser = new FileParser("files/test-sample.txt");
   
        p.setParser(parser);
    
        p.start();
    
        assertEquals(20, p.score("Jeff", 1));
        assertEquals(39, p.score("Jeff", 2));
        assertEquals(48, p.score("Jeff", 3));
        assertEquals(66, p.score("Jeff", 4));
        assertEquals(74, p.score("Jeff", 5));
        assertEquals(84, p.score("Jeff", 6));
        assertEquals(90, p.score("Jeff", 7));
        assertEquals(120, p.score("Jeff", 8));
        assertEquals(148, p.score("Jeff", 9));
        assertEquals(167, p.score("Jeff", 10));
        
        assertEquals(16, p.score("John", 1));
        assertEquals(25, p.score("John", 2));
        assertEquals(44, p.score("John", 3));
        assertEquals(53, p.score("John", 4));
        assertEquals(82, p.score("John", 5));
        assertEquals(101, p.score("John", 6));
        assertEquals(110, p.score("John", 7));
        assertEquals(124, p.score("John", 8));
        assertEquals(132, p.score("John", 9));
        assertEquals(151, p.score("John", 10));
    }
   
    @Test
    public void allStrikeTest()
    {
    
        BowlingProgram program = new BowlingProgram();
        
        BowlingParser parser = new FileParser("files/test-strike.txt");
        program.setParser(parser);
        
        program.start();
        
        assertEquals(300, program.score("Zoe", 10));
    }
    
    @Test
    public void all10Test()
    {
        
        BowlingProgram program = new BowlingProgram();
        
        BowlingParser parser = new FileParser("files/test-10.txt");
        program.setParser(parser);
        
        program.start();
        
        assertEquals(300, program.score("Carl", 10));
    }
    
    @Test
    public void allFoulsTest()
    {
        
        BowlingProgram program = new BowlingProgram();
        
        BowlingParser parser = new FileParser("files/test-fouls.txt");
        program.setParser(parser);
        
        program.start();
        
        assertEquals(0, program.score("Ivan", 10));
    }
}
