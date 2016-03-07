/*
    ADD YOUR HEADER HERE
 */

package assignment4;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

// do not change class name or interface it implements
public class WordLadderSolver implements Assignment4Interface
{
    private ArrayList<String> dict;
    private List<String> ladder;
    private ArrayList<String> usedWords;
    private ArrayList<String> dictionaryCopy;
    
	// declare class members here.

    // add a constructor for this object. HINT: it would be a good idea to set up the dictionary there

    public WordLadderSolver(ArrayList<String> dictionary) {
		// TODO Auto-generated constructor stub
    	this.dict = dictionary;
    	this.ladder = new ArrayList<String>();
    	this.usedWords = new ArrayList<String>();
    	this.dictionaryCopy = new ArrayList<String>();
    		
    	
	}

	// do not change signature of the method implemented from the interface
    @Override
    public List<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException 
    {
       // implement this method
    	this.dictionaryCopy = new ArrayList<String>();
    	this.dictionaryCopy.addAll(this.dict);
    	this.ladder = new ArrayList<String>();
    	makeLadder(startWord, endWord, 0);
    	if(this.ladder.isEmpty()){
    		throw new NoSuchLadderException("No ladder exists between " + startWord + " and " + endWord);
    	}
    	return this.ladder;
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
    	if(this.ladder.isEmpty()) //we just started, change the position to -1 so we can change the first letter
    	{
    		position = -1;
    	}
    	String newStart = "";
    	if(startWord.substring(0, 1).matches("\\d")) //if the startword has a number in front
    	{
    		newStart = startWord.substring(1, startWord.length());
    	}
    	else
    	{
    		newStart = startWord;
    	}
    	if(newStart.length() != 5 || endWord.length() != 5){return null;} //cannot compare these words, they do not exist in the dictionary
    	if(areWeDone(newStart, endWord))
    	{
    		this.ladder.add(newStart);
    		this.ladder.add(endWord);
    		return this.ladder;
    	}
    	
    	this.ladder.add(newStart);
    	this.usedWords.add(newStart);
    	//now, we need to find similar words to the startword
    	
    	ArrayList<String> similarWords = new ArrayList<String>();
    	//similarWords = getSimilar(newStart, endWord, position);
    	//if(similarWords.isEmpty()){ //if the preffered match is empty, try the unpreffered matching 
    	similarWords = getSimilarWords(newStart, endWord, position);
    	//}
    
    	/*if(similarWords.isEmpty())
    	{
    		similarWords = getSimilarWords(newStart, endWord, position);
    		if(similarWords.isEmpty())
    		{
    			return this.ladder;
    		}
    	}*/
    	
    	for(int simI = 0; simI < similarWords.size(); simI ++)
    	{
    		makeLadder(similarWords.get(simI), endWord, whatPositionChanged(newStart, similarWords.get(simI))); //call similar words
    		if(this.ladder.get(this.ladder.size()-1).equalsIgnoreCase(endWord)){return this.ladder;} //we found a word ladder
    		//no word ladder found? remove this from the ladder because we are not going to use this word
    		this.ladder.remove(similarWords.get(simI).substring(1, similarWords.get(simI).length()));
    		//this.dictionaryCopy.remove(similarWords.get(simI).substring(1, similarWords.get(simI).length())); //remove this word from the dictionary because it didn't work
    		
    	}
    	//if we are out of the for loop, didn't find a word ladder, so we can remove the startword from the ladder and return
    	this.ladder.remove(newStart);
    	this.dictionaryCopy.remove(newStart); //remove this word from the dictionary because it didn't work
    
    	return this.ladder;
    }
    
   
      
    public ArrayList<String> getSimilarWords(String startWord, String endWord, int position){
    	//need to compare words that have one letter different from startword
    	ArrayList<String> similarWords = new ArrayList<String>();
    	
    for(int dIndex = 0; dIndex < this.dictionaryCopy.size(); dIndex ++ )
    	{
    		//need to check if the dictionary word was already used. 
    		if(alreadyUsed(this.dictionaryCopy.get(dIndex)))
    		{
    				continue;
    		} //if this word is already used in our ladder, go to the next word, otherwise move on
    		
    		boolean fourLettersame = compareWord(startWord, this.dictionaryCopy.get(dIndex), position); //returns if they have one letter the same
    		if(fourLettersame == true)
    		{//need to add (but also sort) the word into the similar words list.
    			String word = appendDist(this.dictionaryCopy.get(dIndex), endWord); //adds the distance the word is from the endword to the word
    			int addedWord = 0;
    			for(int similarI = 0; similarI < similarWords.size(); similarI ++) //need to sort the list accordingly
    			{
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
    		
    	}
    	
    	return similarWords;
    }
           
    

public ArrayList<String> getSimilar(String startword, String endword, int position)
    {
    	ArrayList<String> similarWords = new ArrayList<String>();
    	
    	for(int wordIndex = 0; wordIndex < startword.length(); wordIndex ++) //checking each letter to see if we can make a direct change
    	{	if(wordIndex == position)//if we have changed this letter position previously, continue
    		{
    			continue; 
    		}
    		String endLetter = endword.substring(wordIndex, wordIndex + 1);
    		String wordLetter = startword.substring(wordIndex, wordIndex + 1);
    		
    		for(int dI = 0; dI < this.dictionaryCopy.size(); dI ++) //need to check the whole dictionary and see if we have a direct change of letters startword[1] -> endword[1]
    		{
    			String dictWord = this.dictionaryCopy.get(dI);
    			if(alreadyUsed(dictWord))//we already have this word in our ladder list, so skip it.
        		{
    				continue;
        		} 
    			String dictLetter = dictWord.substring(wordIndex, wordIndex + 1); //get the same letter as the others
    			
    			if(dictLetter.equalsIgnoreCase(endLetter))//if the letter we isolated equals the end word's letter we isolated, then compare the rest of the word with the startword
    			{	
    				if(compareWord(startword, dictWord, position)) //if only one word different, we can compare
    				{
    					String word = appendDist(dictWord, endword); //adds the distance the word is from the endword to the wor
    					int addedWord = 0;
    	    			for(int similarI = 0; similarI < similarWords.size(); similarI ++) //need to sort the list when adding accordingly
    	    			{
    	    				int compareD = Integer.parseInt(similarWords.get(similarI).substring(0, 1));
    	    				int compareW = Integer.parseInt(word.substring(0, 1));
    	    				if(compareD > compareW) //the word in the similar word list is greater than the word we compared it to (meaning its number in front is less)
    	    				{
    	    					similarWords.add(similarI, word); //add the word to the array List at the index we compared 
    	    					addedWord = 1; //activate this "flag" for if statement check below
    	    					break;//get out of the for loop
    	    				}
    	    			} 
    	    			if(addedWord != 1 || similarWords.size() == 0) //getting out of this for loop means we added an object or didnt...need to check
    	    			{
    	    				similarWords.add(word);
    	    			}
    				}
    			}
    		}
    	}
    	
    	return similarWords;
    }


public ArrayList<String> getFamiliarWords(String startword, String endword, int position)
{
	ArrayList<String> similarWords = new ArrayList<String>();
	//need words from the dictionary to compare 
	for(int dIndex = 0; dIndex < this.dictionaryCopy.size(); dIndex ++)
	{
		//need to get the dictionary word
		String dWord = this.dictionaryCopy.get(dIndex);
		//need to check if we already have used this word
		if(alreadyUsed(dWord))
		{
			continue; //move to the next word because we cannot use this one
		}
		//need to see if this word has 4 letters in common with the start
		if(compareWord(startword, dWord, position)) //if they do share 4 letters, then we can use this in the similar words list
		{
			//need to find how far away from the end word this word is
			String newDWord = appendDist(dWord, endword); //add the distance to the front.
			
			//need to add to similar words, while sorting.
			if(similarWords.isEmpty())
			{
				similarWords.add(newDWord);
				continue; //skip the rest of the loop because we added this word
			}
			else{
				int flag = 0;
				for(int sortI = 0; sortI < similarWords.size(); sortI ++)
			
					{
						String compSim = similarWords.get(sortI);
						int simNum = Integer.parseInt(compSim.substring(0, 1));
						String compNew = newDWord.substring(0, 1);
						int newNum = Integer.parseInt(compNew);
						if (simNum > newNum) // new num has a lesser number
						{
							flag = 1;
							similarWords.add(sortI, dWord);
							break;
						}
				
				}
				if(flag != 0)
				{
					similarWords.add(dWord);
				}
			}
		}
		
		
		
	}

	return similarWords;
}


public boolean compareWord(String startWord, String dWord, int position)
        {
        	//this checks if the words have only one letter different
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
		if(startLetter.equalsIgnoreCase(similarLetter))
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
	if(countSame == 4){return true;} //they are only one letter apart or they are the same word (each word is only 5 letters)
	
	return false;
}


public String appendDist(String word, String endWord)
{
	int notSimilar = 0; //keep track of how many letters these words have in common
	for(int wordIndex = 0; wordIndex < word.length(); wordIndex ++)
	{
		String wordLetter = word.substring(wordIndex, wordIndex + 1); //get one letter of word
		String endLetter = endWord.substring(wordIndex, wordIndex + 1); //get the same indexed letter of endword
		if(!wordLetter.equalsIgnoreCase(endLetter)) //if they are not equal, then add 1 to notSimilar
		{
			notSimilar = notSimilar + 1;
		}
	}
		
	return Integer.toString(notSimilar) + word; //concatenate how similar the word is to the front of the word
}


public boolean alreadyUsed(String word) //checks to see if we have already used this word in our word ladder
{
	for(int usedIndex = 0; usedIndex < this.ladder.size(); usedIndex ++)
	{
		if(word.equalsIgnoreCase(this.ladder.get(usedIndex)))
		{
			return true;
		}
	}
	return false;
	
}

}
