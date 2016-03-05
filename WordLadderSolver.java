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

	// declare class members here.

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
    	String newStartWord = startWord;
    	if(startWord.substring(0, 1).matches("\\d")) //need to strip the number of places that the word is from the end word off the front of the word.
    	{
    		newStartWord = startWord.substring(1, startWord.length());
    	}
    	if(areWeDone(newStartWord, endWord))
    	{//add these words to the word ladder
    		this.ladder.add(newStartWord);
    		this.ladder.add(endWord);
    		return this.ladder;
    	}
    	//what if word is same?
    	
    	this.ladder.add(newStartWord); //add the first word the the word ladder
    	ArrayList<String> similarWords = getSimilarWords(newStartWord, endWord, position); //need to keep track of the letter we just recently changed
    	if(similarWords.size() == 0)
    	{ //throw exception?? no similar words to this word
    		return this.ladder;
    	}
    	List<String> wordLadder = makeLadder(similarWords.get(0), endWord, whatPositionChanged(newStartWord, similarWords.get(0))); 
    	int indexDict = 0;
    	
    	while(wordLadder != null && indexDict < similarWords.size()) //iterate through all possible similar words, if wordladder is null, and we haven't tried all similar words, try the next one
    	{
    		int sizeLadder = wordLadder.size();
    		if(wordLadder.get(sizeLadder-1).contentEquals(endWord)){break;}
    		wordLadder = makeLadder(similarWords.get(indexDict), endWord, whatPositionChanged(newStartWord, similarWords.get(indexDict))); 
    				
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
    		{//need to add (but also sort) the word into the similar words list.
    			String word = appendDist(this.dict.get(dIndex), endWord); //adds the distance the word is from the endword to the word
    			int addedWord = 0;
    			for(int similarI = 0; similarI < similarWords.size(); similarI ++)
    			{
    				//int compareTo = similarWords.get(similarI).compareToIgnoreCase(word);
    				int compareDict = Integer.parseInt(similarWords.get(similarI).substring(0, 1));
    				int compareWord = Integer.parseInt(word.substring(0, 1));
    				if(compareDict > compareWord) //the word in the similar word list is greater than the word we compared it to (meaning its number in front is less)
    				{
    					similarWords.add(similarI, word); //add the word to the array List at the index we compared 
    					addedWord = addedWord + 1; //activate this "flag" for if statement check below
    					break;//get out of the for loop
    				}
    			}
    			if(addedWord != 1 || similarWords.size() == 0) //getting out of this for loop means we added an object or didnt...need to check
    			{
    				similarWords.add(word);
    			}
    		}
    		dIndex += 1; //go to next work in the dictionary and see if it is also a similar word
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
	String newSimilarWord = similarWord.substring(1, similarWord.length()); //get rid of the number in front
	int differentLetter = 0;
	for(int stIndex = 0; stIndex < startWord.length(); stIndex ++)
	{
		String startLetter = startWord.substring(stIndex, stIndex + 1);
		String similarLetter = newSimilarWord.substring(stIndex, stIndex + 1);
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
	int countSame = 0; //keep track of how many letters these words have in common
	for(int wIndex = 0; wIndex < word.length(); wIndex ++)
	{
		String wLetter = word.substring(wIndex, wIndex + 1); //get one letter of word
		String endLetter = endWord.substring(wIndex, wIndex + 1); //get same indexed letter of endWord
		if(wLetter.equalsIgnoreCase(endLetter)) //if they are the same, add one to the count of similar letters
		{
			countSame = countSame + 1;
		}
	}
	if(countSame >= 4){return true;} //they are only one letter apart or they are the same word (each word is only 5 letters)
	
	return false;
}


public String appendDist(String word, String endWord)
{
	int notSimilar = 0; //keep track of how many letters these words have in common
	for(int wordIndex = 0; wordIndex < word.length(); wordIndex ++)
	{
		String wordLetter = word.substring(wordIndex, wordIndex + 1); //get one letter of word
		String endLetter = endWord.substring(wordIndex, wordIndex + 1); //get the same indexed letter of endword
		if(!wordLetter.equalsIgnoreCase(endLetter)) //if they are equal, then add 1 to howSimilar
		{
			notSimilar = notSimilar + 1;
		}
	}
	
	
	return Integer.toString(notSimilar) + word; //concatenate how similar the word is to the front of the word
}
}
