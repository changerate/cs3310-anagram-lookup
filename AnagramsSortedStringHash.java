/**************************************************************
 * Carlos Vargas
 * Cal Poly Pomona -- CS 3310
 * Fall 2025
 * Programming Assignment 2
 * ------------------------------------------------------------
 * File: AnagramSortedStringHash.java
 * Purpose: this class extends the AnagramsClass abstract class 
 * in order to add the implementation of hashing using the
 * sorted string of the given word. 
 **************************************************************/


 import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


public class AnagramsSortedStringHash extends AnagramsClass {


    /*********************************************************
     * Constructor 
     * @param inputFile
     *********************************************************/
    public AnagramsSortedStringHash(String inputFile) {
        super(inputFile);
        hashMethod = "Sorted String Hash";
    }
    

    
    /*********************************************************
     * Function buildSets; this simultansoulsly reads a file 
     * and builds sets of anagrams. For each word/line it 
     * finds the matching sorted string hash then addes it to
     * the corresponding set. 
     * 
     * Because of the nature of this hashing method, set 
     * matching is robust (it will never double count, and 
     * it will be able to sort characters it has not accounted 
     * for).
     * 
     * @return this fills out the anagramSets class var with 
     * ALL the sets of anagrams. 
     *********************************************************/
    @Override 
    public void buildSets() {
        System.out.println("[Sorted String Hash] Building sets of anagrams for: " + filename);
        int wordCount = 0;
        long start;
        long end; 

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            start = System.nanoTime(); // start timer
            String word;
            while ((word = reader.readLine()) != null) {
                wordCount++;
                String hash = computeSortedStringHash(word);
                if (hash.isEmpty()) continue;
                anagramSets.computeIfAbsent(hash, k -> new HashSet<>()).add(word);
            }
            end = System.nanoTime(); // end timer
        } catch (IOException e) {
            System.out.println("[Sorted String Hash] [ERROR] " + e);
            return;
        }
        
        fileReadAndSetBuildTime = (end - start) / 1_000_000.0;
        numWordsInFile = wordCount;
        // System.out.println("[Sorted String Hash] Execution time: " + fileReadAndSetBuildTime + " ms");
    }
}
