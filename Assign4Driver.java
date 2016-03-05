package assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
public class Assign4Driver
{
    public static void main(String[] args)
    {
        // Create a word ladder solver object
       
        
        if (args.length != 2) 
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		ArrayList<String> dictionary = processLinesInDictionary(args[0]);
        processLinesInFile (args[1], dictionary);
		 
    }
   
    public static void processLinesInFile (String filename, ArrayList<String> dictionary) 
	{ 
    	 Assignment4Interface wordLadderSolver = new WordLadderSolver(dictionary);
		try 
		{
			FileReader freader = new FileReader(filename);
			BufferedReader reader = new BufferedReader(freader);
			
			for (String s = reader.readLine(); s != null; s = reader.readLine()) 
			{
				//parse line function that returns array of two strings (start and end word)
				String[] parsedLine = parseLine(s); //get an array of two words
				if(parsedLine == null){continue;} //we are skipping this line, so we can go to next iteration
		        try 
		        {
		            List<String> result = wordLadderSolver.computeLadder(parsedLine[0], parsedLine[1]);
		            boolean correct = wordLadderSolver.validateResult(parsedLine[0], parsedLine[1], result);
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
   
   public static ArrayList<String> processLinesInDictionary(String filename)
   {//need to make the dictionary a hash table
	  // HashMap<String,String> dictionary = new HashMap();
	   ArrayList<String> dictionary = new ArrayList<String>();
	   try 
		{
			FileReader freader = new FileReader(filename);
			BufferedReader reader = new BufferedReader(freader);
			
			for (String s = reader.readLine(); s != null; s = reader.readLine()) 
			{
				//parse line function that returns array of two strings (start and end word)
				String parsedword = parseWord(s);
				if(parsedword == null){continue;} //we are skipping this line, so we can go to next iteration
				//add the word to the hash table
				/*dictionary.put(parsed, parsed);
				dictionary.get(parsed);
				Collection<String> words = dictionary.values();*/
				dictionary.add(parsedword); //should add alphabetical anyways
				
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
	   return dictionary;
   }
    
    public static String[] parseLine(String s)
    {	
    	if(s.substring(0, 1).contentEquals("*")){return null;} //ignore this string
    	String[] split = s.split("\\P{Alpha}+"); //split the string based on non-alphabetic symbols
    	if(split.length != 2){return null;}
    	return split;
    }
    public static String parseWord(String s)
    {	
    	if(s.substring(0, 1).contentEquals("*")){return null;} //ignore this string
    	String[]  split = s.split("\\P{Alpha}+"); //split the string based on non-alphabetic symbols
    	return split[0];
    }
}
