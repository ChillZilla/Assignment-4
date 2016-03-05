/*
    ADD YOUR HEADER HERE
 */

package assignment4;

import java.util.List;
import java.util.ArrayList;

// do not change class name or interface it implements
public class WordLadderSolver implements Assignment4Interface
{
    private ArrayList<String> dict;
    private List<String> ladder;

	// delcare class members here.

    // add a constructor for this object. HINT: it would be a good idea to set up the dictionary there

    public WordLadderSolver(ArrayList<String> dictionary) {
		// TODO Auto-generated constructor stub
    	this.dict = dictionary;
    	this.ladder = new ArrayList<String>();
    	
	}

	// do not change signature of the method implemented from the interface
    @Override
    public List<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException 
    {
        // implement this method
    	
    	List<String> ladder = makeLadder(startWord, endWord, 0);
    	
    	return ladder;
       // throw new UnsupportedOperationException("Not implemented yet!");
       
    }

    @Override
    public boolean validateResult(String startWord, String endWord, List<String> wordLadder) 
    {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    // add additional methods here
    public List<String> makeLadder(String startWord, String endWord, int position)
    {
    	//base case : if endword one letter away from startword
    	if(areWeDone(startWord, endWord))
    	{
    		return this.ladder;
    	}
    	//what if word is same?
    	
    	this.ladder.add(startWord); //add the first word the the word ladder
    	ArrayList<String> similarWords = getSimilarWords(startWord, endWord, position); //need to keep track of the letter we just recently changed
    	if(similarWords.size() == 0)
    	{ //throw exception?? no similar words to this word
    		return this.ladder;
    	}
    	List<String> wordLadder = makeLadder(similarWords.get(0), endWord, whatPositionChanged(startWord, similarWords.get(0))); 
    	int indexDict = 0;
    	while(wordLadder != null && indexDict < similarWords.size()) //iterate through all possible similar words, if wordladder is null, and we haven't tried all similar words, try the next one
    	{
    		wordLadder = makeLadder(similarWords.get(indexDict), endWord, whatPositionChanged(startWord, similarWords.get(indexDict))); 
    		indexDict = indexDict + 1;
    	}
    	
    	
    	return this.ladder;
    }
    
    public  ArrayList<String> getSimilarWords(String startWord, String endWord, int position){
    	//need to compare words that have one letter different from startword
    	ArrayList<String> similarWords = new ArrayList<String>();
    	int dIndex = 0;
    	while(dIndex < this.dict.size())
    	{
    		boolean fourLettersame = compareWord(startWord, this.dict.get(dIndex), position); //returns if they have one letter the same
    		if(fourLettersame == true)
    		{
    			
    			similarWords.add(this.dict.get(dIndex));
    		}
    		dIndex += 1;
    	}
    	
    	return similarWords;
    }
           
        public boolean compareWord(String startWord, String dWord, int position)
        {
        	//if the words have only one letter in common, return a true.
        	if(startWord.equalsIgnoreCase(dWord))
        	{
        		return false;
        	}
        	int letterCount = 0;
        	for(int startIndex = 0; startIndex < startWord.length(); startIndex ++)
        	{	String startSubstring = startWord.substring(startIndex, startIndex +1);
        		String dWordSubstring = dWord.substring(startIndex, startIndex +1);
        		if(startSubstring.equalsIgnoreCase(dWordSubstring))
        		{
        			
        			letterCount = letterCount + 1;
        		}
        		//not equal!
        		else //need to check if this letter is in a position similar to the one we just changed
        		{
        			if(startIndex == position)
        			{
        				return false; //can't use this word because we already changed this letter
        			}
        		}
        		
        	}
        	if(letterCount == 4) //same words except for one letter
        	{ 
        	return true;
        	}
        	else return false;
        }
public int whatPositionChanged(String startWord, String similarWord)
{
	//compare and find the letter spot that is different
	int differentLetter = 0;
	for(int stIndex = 0; stIndex < startWord.length(); stIndex ++)
	{
		String startLetter = startWord.substring(stIndex, stIndex + 1);
		String similarLetter = similarWord.substring(stIndex, stIndex + 1);
		if(startWord.substring(stIndex, stIndex +1).equalsIgnoreCase(similarLetter))
		{
			continue;
		}
		else{
			differentLetter = stIndex;
			break;
		}
	}
	//if(differentLetter < 4){return differentLetter + 1;} //if the letter position changed is 0, 1, 2, or 3 return different letter + 1;
	return differentLetter; //return the position that changed
}

public boolean areWeDone(String word, String endWord)
{
	int countSame = 0;
	for(int wIndex = 0; wIndex < word.length(); wIndex ++)
	{
		String wLetter = word.substring(wIndex, wIndex ++);
		String endLetter = endWord.substring(wIndex, wIndex ++);
		if(wLetter.equalsIgnoreCase(endLetter))
		{
			countSame = countSame + 1;
		}
	}
	if(countSame == 4){return true;}
	
	return false;
}
   
}
