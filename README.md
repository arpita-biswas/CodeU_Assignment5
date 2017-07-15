# CodeU_Assignment5  

##Problem: Unknown Language   
Given a dictionary (a list of words in lexicographic order) of all words in an unknown/invented language, find the alphabet (an ordered list of characters) of that language.

Assumption: The ordering of words in the dictionary is consistent with some underlying (lexicographic) ordering of the characters.

##Solution:  
1. If the dictionary is null or empty, return null.  
2. If the dictionary has only one word, output the list (in any order) of characters present in the word.   
3. For each of two consecutive words (say, "currentWord" and "nextWord") in the dictionary  
    i) Initialize a directed acyclic graph (DAG).  
    ii) The first index "j" at which two characters ("currentChar" and "nextChar") corresponding to the two words differ, create a directed edge in the DAG from currentChar to nextChar. and put all remaining characters in the DAG as node (if not already present) with no extra edges.  
    iii) After the DAG is built with all the words in the dictionary, do a topological sort to get an ordered list of characters. Return this ordered list.  
