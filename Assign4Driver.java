package assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Assign4Driver
{
    public static void main(String[] args)
    {
        // Create a word ladder solver object
       
        
        if (args.length != 1) 
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		processLinesInFile (args[0]);
		 
    }
   
    public static void processLinesInFile (String filename) 
	{ 
    	 Assignment4Interface wordLadderSolver = new WordLadderSolver();
		try 
		{
			FileReader freader = new FileReader(filename);
			BufferedReader reader = new BufferedReader(freader);
			
			for (String s = reader.readLine(); s != null; s = reader.readLine()) 
			{
				//parse line function that returns array of two strings (start and end word)
		        try 
		        {
		            List<String> result = wordLadderSolver.computeLadder("money", "honey");
		            boolean correct = wordLadderSolver.validateResult("money", "honey", result);
		        } 
		        catch (NoSuchLadderException e) 
		        {
		            e.printStackTrace();
		        }
			}
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println ("Error: File not found. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) 
		{
			System.err.println ("Error: IO exception. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
	}
    
    public static String[] parseLine(String s)
    {
    	return null;
    }
}
