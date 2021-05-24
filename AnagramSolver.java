//Daven Giftian Tejalaksana
//Sunday, 16 May 2021
//CSE 143
//Instructor: Stuart Reges
//TA: Andrew Cheng
//Assignment #6
//This program takes in a given phrase and finds anagrams of it based on the given dictionary.

import java.util.*;

public class AnagramSolver {
   Map<String, LetterInventory> dictionaryMap; //map of dictionary words to their letter inventory
   List<String> dictionaryList; //list of dictionary words in order given by constructor
   
   //Post: Constructs an anagram solver that will use given list as its dictionary.
      //List should not be changed in any way.
      //Assume that dictionary is a nonempty collection of nonempty sequences of letters,
         //contains no duplicates, doesn't change in state as program executes.
   public AnagramSolver(List<String> list) {
      dictionaryList = list; //initializing dictionaryList
      dictionaryMap = new HashMap<>(); //initializing dictionaryMap
      for (String word: list) {
         dictionaryMap.put(word, new LetterInventory(word));
      }
   }
   
   //Pre: max should not be less than 0 (throws IllegalArgumentException if not).
   //Post: Use recursive backtracking to find word combinations with same letters as string s.
      //Prints to System.out all combination of words from dictionary that are anagrams of s
         //and that include at most max words (or unlimited number of words if max is 0).
      //Prunes the dictionary to include only the relevant words to string s before starting
         //the recursive backtracking.
   public void print(String s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("Max should not be less than 0.");
      }
      LetterInventory phrase = new LetterInventory(s); //String s in letter inventory form
      List<String> relevantWords = new ArrayList<>(); //Relevant dictionary words to string s
      for (String word: dictionaryList) { 
         LetterInventory comparison = phrase.subtract(dictionaryMap.get(word));
         if (comparison != null) { //Pruning dictionary
            relevantWords.add(word);
         }  
      }
      List<String> anagram = new ArrayList<>();
      print(phrase, relevantWords, anagram, max);
   }
   
   //This is the sub-method of the print method which performs recursive backtracking.
   //It takes in letterinventory form of string s, list of relevant words,
      //anagram result list, and max words for the anagram.
   //Post: It searches and prints all anagram combinations of the given string s (based on max).
   private void print(LetterInventory phrase, List<String> relevantWords, 
                      List<String> anagram, int max) {
      if (phrase.isEmpty()) {
         System.out.println(anagram); //base case
      } else {
         if (max == 0 || anagram.size() <= max - 1) {
            for (String word: relevantWords) { 
               LetterInventory comparedResult = phrase.subtract(dictionaryMap.get(word));
               if (comparedResult != null) {
                  anagram.add(word);
                  print(comparedResult, relevantWords, anagram, max); //recursive case
                  anagram.remove(word);
               }
            }
         } 
      }
   }
}