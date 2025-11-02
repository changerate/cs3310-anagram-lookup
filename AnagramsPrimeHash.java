/**************************************************************
 * Carlos Vargas
 * Cal Poly Pomona -- CS 3310
 * Fall 2025
 * Programming Assignment 2
 * ------------------------------------------------------------
 * File: AnagramPrimeHash.java
 * Purpose: this class extends the AnagramsClass abstract class 
 * in order to add the implementation of hashing using the
 * product of the prime numbers assocaited with each character. 
 **************************************************************/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



public class AnagramsPrimeHash extends AnagramsClass {
    private static final Map<Character, Integer> LETTER_PRIMES = new HashMap<>() {{
        // This is a hashmap sorted by frequency of letters in the file words.txt
        put('s', 2);
        put('e', 3);
        put('i', 5);
        put('a', 7);
        put('r', 11);
        put('n', 13);
        put('t', 17);
        put('o', 19);
        put('l', 23);
        put('c', 29);
        put('d', 31);
        put('\'', 37);
        put('u', 41);
        put('g', 43);
        put('m', 47);
        put('p', 53);
        put('h', 59);
        put('b', 61);
        put('y', 67);
        put('f', 71);
        put('k', 73);
        put('v', 79);
        put('w', 83);
        put('z', 89);
        put('x', 97);
        put('j', 101);
        put('q', 103);
        put('é', 107);
        put('è', 109);
        put('ö', 113);
        put('ü', 127);
        put('á', 131);
        put('ñ', 137);
        put('ó', 139);
        put('ä', 149);
        put('â', 151);
        put('ê', 157);
        put('ç', 163);
        put('å', 167);
        put('û', 173);
        put('í', 179);
        put('ô', 181);
        // everything below here is not in the words.txt file
        put('à', 191);
        put('ã', 193);
        put('ë', 197);
        put('ì', 199);
        put('î', 223);
        put('ï', 227);
        put('ò', 229);
        put('õ', 233);
        put('ú', 239);
        put('ù', 241);
        put('ý', 251);
        put('ÿ', 257);
        put('-', 263);
    }};

    /**********************************************
     * Constructor 
     * @param inputFile
     **********************************************/
    public AnagramsPrimeHash(String inputFile) {
        super(inputFile);
        hashMethod = "Primes Hash";
    }



    /*********************************************************
     * Function buildSets; this simultansoulsly reads a file 
     * and builds sets of anagrams. For each word/line it 
     * finds the matching hash using the product of the prime
     * numbers asociated with each character. 
     * 
     * Because of the nature of this hashing algorithm, it IS 
     * possible to double assign words to hashes that shouldn't
     * be anagrams. 
     * I attempt to get around this by double checking that 
     * the currect word has the same number of characters as 
     * a given word with the associated hash. This brings 
     * down the likelyhood of clashing hashes. 
     * 
     * It attempts to be efficient by storing the mapping of 
     * primes sorted by the character frequency of the characters 
     * in the file words.txt.
     * 
     * @return this fills out the anagramSets class var with 
     * ALL the sets of anagrams. 
     *********************************************************/
    @Override 
    public void buildSets() {
        System.out.println("[Prime Hash] Building sets of anagrams for: " + filename);
        int wordCount = 0;
        long start;
        long end;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String word;
            start = System.nanoTime();
            while ((word = reader.readLine()) != null) {
                wordCount++;
                String hash = computePrimeHash(word, false);
                if (hash == "1") continue;

                // first check if the string has the same number of chars
                if (anagramSets.get(hash) != null) {
                    String wordPrev = anagramSets.get(hash).iterator().next();
                    Integer sizePrev = wordPrev.length();
                    Integer sizeCurr = word.length();

                    if (sizePrev != sizeCurr) {
                        // System.out.println("[Prime Hash] Clashing hashes! ");
                        // System.out.println("\texist:\t" + wordPrev);
                        // System.out.println("\tnew:\t" + word);
                        continue;
                    } 
                    else anagramSets.get(hash).add(word);
                }
                else {
                    Set<String> newSet = new HashSet<>();
                    newSet.add(word);
                    anagramSets.put(hash, newSet);
                }
            }
            end = System.nanoTime();
        } catch (IOException e) {
            System.out.println("[Prime Hash] [ERROR] " + e);
            return;
        }

        fileReadAndSetBuildTime = (end - start) / 1_000_000.0;
        numWordsInFile = wordCount;
        // System.out.println("[Prime Hash] Execution time: " + fileReadAndSetBuildTime + " ms");
    }



    /*********************************************************
     * Function computePrimeHash; this method computes a hash 
     * value for a given word by multiplying the prime values 
     * associated with each character and taking the modulus 
     * with 2^31 - 1. 
     * 
     * @param word the input word to hash
     * @param robustOutput if true, prints intermediate hash values
     * @return the final computed hash as a string
     *********************************************************/
    public String computePrimeHash(String word, boolean robustOutput) {
        long hash = 1L; // long necessary for multuiplication
        final long M  = 2_147_483_647L; // 2^31-1 as long

        for (char ch : word.toLowerCase().toCharArray()) {
            Integer prime = LETTER_PRIMES.get(ch);
            if (prime == null) {
                System.out.println("[Prime Hash] 👺 Skipping word with the char: " + ch);
                continue; 
            }
            hash = (hash * prime) % M; 
            if (robustOutput) System.out.println("\t\tThe hash: " + hash);
        }
        return String.valueOf((int) hash);
    }
}
