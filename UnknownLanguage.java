package CodeU_Assignment5;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

/**
 * Assignment 5: Unknown Language 
 * Given a dictionary (a list of words in lexicographic order) of all words in an
 * unknown/invented language, find the alphabet (an ordered list of characters)
 * of that language.
 *
 */

public class UnknownLanguage {
    
    /** findAlphabetList(dictionary) returns an ordered list of characters
     *      + create a DAG to represent character precedence based on two consecutive words
     *      + do topological sort on the DAG to get the ordered list of characters
     * 
     * @param dictionary
     * @return
     */
    public static ArrayList<Character> findAlphabetList(ArrayList<String> dictionary){
        if(dictionary == null || dictionary.size() == 0){
            return null;
        }
        
        if(dictionary.size() == 1){
            String word = dictionary.get(0);
            ArrayList<Character> sortedCharacters = new ArrayList<Character>();
            for(int i=0; i<word.length(); i++){
               sortedCharacters.add(word.charAt(i));
            }
            return sortedCharacters;
        }
        
        DAG dag = new DAG();
        String currentWord = dictionary.get(0);
        String nextWord;
        for(int i=1; i<dictionary.size(); i++){
            boolean isComparable = false;
            nextWord = dictionary.get(i);
            //look at the two characters  in a particular index "j"
            //in currentWord (say, currentChar) and in nextWord (say, nextChar)
            for(int j = 0; j<nextWord.length() && j<currentWord.length(); j++){
                char currentChar = currentWord.charAt(j);
                char nextChar = nextWord.charAt(j);
                //if already a mismatch found in an index k < j, then add 
                // currentChar and nextChar to the DAG (with no precedence/edge)
                if(isComparable){
                    dag.addNode(currentChar);
                    dag.addNode(nextChar);
                }
                //if all characters at indices k < j have been same for the two consecutive words,
                //then, if the characters at j differ, add an edge currentCHar to nextChar
                //      if not, just add the (identical) char in the DAG               
                else{
                    if(currentChar != nextChar){
                        dag.addDirectedEdge(currentChar, nextChar);
                        isComparable = true;
                    }
                    else{
                        dag.addNode(currentChar);
                    }       
                }
            }
            if(nextWord.length() < currentWord.length()){
                for(int j=nextWord.length(); j<currentWord.length(); j++){
                    dag.addNode(currentWord.charAt(j));
                }
            }
            if(nextWord.length() > currentWord.length()){
                for(int j=currentWord.length(); j<nextWord.length(); j++){
                    dag.addNode(nextWord.charAt(j));
                }
            }
            currentWord = nextWord;
        }
        //after creating the dag, do a topological sort
        return topologicalSort(dag);
    }
    
    /** topologicalSort(dag) returns an ordered list of characters in topological order
     * 
     * @param dag : a directed acyclic graph of type DAG
     * @return
     */
    public static ArrayList<Character> topologicalSort(DAG dag){
        ArrayList<Character> sortedCharacters = new ArrayList<Character>();
        //Until the set of "sources" is empty,
        //   For each source,
        //      add source (data) to the ordered list
        //      remove all the outgoing edges from the source
        //      remove source from dag
        //   Compute new set of "sources" in the dag and repeat
        while(!dag.sources.isEmpty()){
            HashSet<Node> sources = new HashSet<Node>();
            sources.addAll(dag.sources);
            for(Node node: sources){
                sortedCharacters.add(node.getData());
                dag.removeSource(node);
            }
        }
        return sortedCharacters;
        
    }

    public static void main(String[] args) {
        TestNullDictionary(); // Null dictionary
        TestEmptyDictionary(); // Empty dictionary
        TestOneWordDictionary(); // When only one word is there in the dictionary
        TestSingleCharacterWordsDictionary(); //When all words are of size 1 (onr character only)
        TestDifferentLengthWordsDictionary(); //When the words are of different lengths
        TestNonComparableCharactersDictionary(); //When the adjacent words are not comparable (no same character appears in both the words)
        TestExample();        
    }
    
    @Test
    public static void TestNullDictionary(){
        assertNull(findAlphabetList(null));
    }
    
    @Test
    public static void TestEmptyDictionary(){
        assertNull(findAlphabetList(new ArrayList<String>()));
    }
    
    @Test
    public static void TestOneWordDictionary(){
        ArrayList<String> dictionary = new ArrayList<String>();
        dictionary.add("AR");
        ArrayList<Character> expectedResult1 = new ArrayList<Character>();
        expectedResult1.add('A');
        expectedResult1.add('R');
        ArrayList<Character> expectedResult2 = new ArrayList<Character>();
        expectedResult2.add('R');
        expectedResult2.add('A');
        HashSet<ArrayList<Character>> expectedResults = new HashSet<ArrayList<Character>>();
        expectedResults.add(expectedResult1);
        expectedResults.add(expectedResult2);
        assertTrue(expectedResults.contains(findAlphabetList(dictionary)));
    }
    
    @Test
    public static void TestSingleCharacterWordsDictionary(){
        ArrayList<String> dictionary = new ArrayList<String>();
        dictionary.add("K");
        dictionary.add("E");
        dictionary.add("N");
        ArrayList<Character> expectedResult = new ArrayList<Character>();
        expectedResult.add('K');
        expectedResult.add('E');
        expectedResult.add('N');
        assertEquals(expectedResult, findAlphabetList(dictionary));
    }
    
    @Test
    public static void TestDifferentLengthWordsDictionary(){
        ArrayList<String> dictionary = new ArrayList<String>();
        dictionary.add("");
        dictionary.add("AT");
        dictionary.add("ART");
        dictionary.add("EATER");
        dictionary.add("TREAT");
        ArrayList<Character> expectedResult = new ArrayList<Character>();
        expectedResult.add('A');
        expectedResult.add('E');
        expectedResult.add('T');
        expectedResult.add('R');
        assertEquals(expectedResult, findAlphabetList(dictionary));
    }
    
    @Test
    public static void TestNonComparableCharactersDictionary(){
        ArrayList<String> dictionary = new ArrayList<String>();
        dictionary.add("A");
        dictionary.add("B");
        ArrayList<Character> expectedResult1 = new ArrayList<Character>();
        expectedResult1.add('A');
        expectedResult1.add('B');
        ArrayList<Character> expectedResult2 = new ArrayList<Character>();
        expectedResult2.add('B');
        expectedResult2.add('A');
        HashSet<ArrayList<Character>> expectedResults = new HashSet<ArrayList<Character>>();
        expectedResults.add(expectedResult1);
        expectedResults.add(expectedResult2);
        assertTrue(expectedResults.contains(findAlphabetList(dictionary)));
        
        dictionary = new ArrayList<String>();
        dictionary.add("A");
        dictionary.add("AT");
        expectedResult1 = new ArrayList<Character>();
        expectedResult1.add('A');
        expectedResult1.add('T');
        expectedResult2 = new ArrayList<Character>();
        expectedResult2.add('T');
        expectedResult2.add('A');
        expectedResults = new HashSet<ArrayList<Character>>();
        expectedResults.add(expectedResult1);
        expectedResults.add(expectedResult2);
        assertTrue(expectedResults.contains(findAlphabetList(dictionary)));
    }
    
    @Test
    public static void TestExample(){
        ArrayList<String> dictionary = new ArrayList<String>();
        dictionary.add("ART");
        dictionary.add("RAT");
        dictionary.add("CAT");
        dictionary.add("CAR");
        ArrayList<Character> expectedResult1 = new ArrayList<Character>();
        expectedResult1.add('T');
        expectedResult1.add('A');
        expectedResult1.add('R');
        expectedResult1.add('C');
        ArrayList<Character> expectedResult2 = new ArrayList<Character>();
        expectedResult2.add('A');
        expectedResult2.add('T');
        expectedResult2.add('R');
        expectedResult2.add('C');
        HashSet<ArrayList<Character>> expectedResults = new HashSet<ArrayList<Character>>();
        expectedResults.add(expectedResult1);
        expectedResults.add(expectedResult2);
        assertTrue(expectedResults.contains(findAlphabetList(dictionary)));
    }

}
