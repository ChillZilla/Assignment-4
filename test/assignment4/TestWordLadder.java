
package assignment4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TestWordLadder {
	
	static String beginWord;
	static String endWord;
	static String filename = "input-words.txt";
	
   public static String getBeginWord() {
		return beginWord;
	}

	public static String getEndWord() {
		return endWord;
	}

	public static String[] readInputWords() //updateInputWords(String startWord, String endWord)
    {
	   String[] output = {"0", "1"};
	   
	    try {
		   FileReader    freader = new FileReader(filename);
		   BufferedReader reader = new BufferedReader(freader);

		   String input = reader.readLine();
		   output = input.split(" ");
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

	    return output;   
    }
	
	public static void updateInputWords(String beginWord, String endWord) //updateInputWords(String startWord, String endWord)
    {
	   
	    try {
		   PrintWriter writer = new PrintWriter(filename);
		   writer.print(beginWord + " " + endWord);
		   writer.close();
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
	
	
	@Test		// blockBox -- Accomplish Functional Testing
	public void blackBox() {
		
		ArrayList<String> Dictionary = Assign4Driver.processLinesInDictionary("A4-words.txt");
		Assignment4Interface wordLadderSolver = new WordLadderSolver(Dictionary);
		
		List<String> ladderResult = new ArrayList<String>();
		
		for(int y = 0; y < 20; y++)	// step up
		{
			for(int x = 0; x < 20; x++ )
			{
				try {
					System.out.print( "Beginning test between " + Dictionary.get(x) + " and " + Dictionary.get(y) + "..." );
					ladderResult = wordLadderSolver.computeLadder( Dictionary.get(x) , Dictionary.get(y) );
				}
				catch(NoSuchLadderException e)
				{			
					System.out.println( " No word ladder found." );  // works
				}
				
				assertEquals( ladderResult.get(0) , ladderResult.get( ladderResult.size() - 1 )  );
				
				System.out.println( " Completed." );
			}
		}
	}
	
	
	@Test		// whiteBox -- complete branch coverage testing
	public void whiteBox() {
		
	}
	
	
	
	
	/*
   @Test
   public void testAdd() {
      String str= "Junit is working fine";
      assertEquals("Junit is working fine",str);
   } */
}