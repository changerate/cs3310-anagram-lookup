/**************************************************************
 * Carlos Vargas
 * Cal Poly Pomona -- CS 3310
 * Fall 2025
 * Programming Assignment 2
 * ------------------------------------------------------------
 * File: AnagramUtility.java
 * Purpose: this utility class coordinates two different 
 * anagram set builders — one using a prime hash and one using
 * a sorted string hash — to generate, compare, and analyze 
 * anagram sets from an input file 
 **************************************************************/


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnagramUtility {
    private static AnagramsPrimeHash setBuilderPrimeHash;
    private static AnagramsSortedStringHash setBuilderStringHash;

    
    /*********************************************************
     * Function AnagramUtility; constructor initializes both 
     * prime-hash and string-hash set builders and builds their 
     * respective anagram sets from the given file.
     * @param filename the input file containing words
     *********************************************************/
    public AnagramUtility(String filename) {
        setBuilderPrimeHash = new AnagramsPrimeHash(filename);
        setBuilderStringHash = new AnagramsSortedStringHash(filename);
        setBuilderPrimeHash.buildSets();
        setBuilderStringHash.buildSets();
    }



    /*********************************************************
     * Function saveSetsToFile; saves both the prime-hash and 
     * string-hash anagram sets to files, excluding single-word sets.
     * @param postfix the filename postfix to distinguish outputs
     *********************************************************/
    public void saveSetsToFile(String postfix) {
        setBuilderPrimeHash.saveSetsToFile(false, "setsOutputPrime" + postfix);
        setBuilderStringHash.saveSetsToFile(false, "setsOutputString" + postfix);
    }



    /*********************************************************
     * Function checkForDifferences; compares the key sets of the 
     * prime-hash and string-hash maps to find discrepancies between 
     * the two hashing methods.
     *********************************************************/
    public void checkForDifferences() {
        Map<String, Set<String>> stringHashes = setBuilderStringHash.getSets();
        Map<String, Set<String>> primeHashes = setBuilderPrimeHash.getSets();
        
        Set<String> onlyInMap2 = new HashSet<>(stringHashes.keySet());
        onlyInMap2.removeAll(primeHashes.keySet());
        Set<String> onlyInMap1 = new HashSet<>(primeHashes.keySet());
        onlyInMap1.removeAll(stringHashes.keySet());
    
        System.out.println("Only in string hash map: " + onlyInMap2);
        System.out.println("Only in prime hash map: " + onlyInMap1);
    }



    /*********************************************************
     * Function hashChecker; computes and prints the prime hash 
     * for two given words to verify hash equivalence or difference.
     * @param word1 the first word to hash
     * @param word2 the second word to hash
     * @param robustOutput if true, prints intermediate hash steps
     *********************************************************/
    public void hashChecker(String word1, String word2, boolean robustOutput) {
        String primeHash = setBuilderPrimeHash.computePrimeHash(word2, robustOutput);
        System.out.println("[Prime Hash] Hash from word \'" + word2 + "\': " + primeHash);
        
        primeHash = setBuilderPrimeHash.computePrimeHash(word1, robustOutput);
        System.out.println("[Prime Hash] Hash from word \'" + word1 + "\': " + primeHash);
    }



    /*********************************************************
     * Function displayStats; prints a formatted comparison of 
     * both hashing methods, displaying statistics for the prime 
     * hash and sorted string hash approaches.
     *********************************************************/
    public void displayStats() {
        System.out.println("==========================================================================");
        System.out.println("|| This program primarly creates sets of anagrams bsaed on a given text file.");
        System.out.println("|| Another functionality of this program is that it compares the efficiency");
        System.out.println("|| of two different hashing methods. In the first method I create a hash out of");
        System.out.println("|| the alphabetical sorting of the string. In the second method I create a hash");
        System.out.println("|| by computing the product of the string when every character is assigned a");
        System.out.println("|| prime number (See the LETTER_PRIMES map to see how the characters are assigned primes).");
        System.out.println("--------------------------------------------------------------------------");
        setBuilderStringHash.displayStats();
        System.out.println("--------------------------------------------------------------------------");
        setBuilderPrimeHash.displayStats();
        System.out.println("==========================================================================");
    }



    /*********************************************************
     * Function printNumberOfSets; prints the total number of 
     * anagram sets (for both hash methods), depending on whether 
     * single-word sets are included.
     * @param allSizes if true, print all sets; otherwise only sets of size > 1
     *********************************************************/
    public void printNumberOfSets(boolean allSizes) {
        setBuilderStringHash.printNumberOfSets(allSizes);
        setBuilderPrimeHash.printNumberOfSets(allSizes);
    }


    /*********************************************************
     * Function printSets; prints all the anagram sets for both 
     * hash methods to the console.
     * @param allSizes if true, print all sets; otherwise only sets of size > 1
     *********************************************************/
    public void printSets(boolean allSizes) {
        setBuilderStringHash.printSets(allSizes);
        setBuilderPrimeHash.printSets(allSizes);
    }
}
