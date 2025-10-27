import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;


public class AnagramSetBuilder {
    private Map<Integer, Set<String>> anagramSets = new HashMap<>();
    private Map<String, Set<String>> anagramSetsStringHash = new HashMap<>();
    private static final Map<Character, Integer> LETTER_PRIMES = new HashMap<>() {{
        put('a', 2);
        put('b', 3);
        put('c', 5);
        put('d', 7);
        put('e', 11);
        put('f', 13);
        put('g', 17);
        put('h', 19);
        put('i', 23);
        put('j', 29);
        put('k', 31);
        put('l', 37);
        put('m', 41);
        put('n', 43);
        put('o', 47);
        put('p', 53);
        put('q', 59);
        put('r', 61);
        put('s', 67);
        put('t', 71);
        put('u', 73);
        put('v', 79);
        put('w', 83);
        put('x', 89);
        put('y', 97);
        put('z', 101);
        put('\'', 103);
    }};

    
    public void buildSets(String wordListFilename, boolean primeHash) {
        if (primeHash) buildSetsPrimeHash(wordListFilename);
        else buildSetsStringHash(wordListFilename);
    }


    public void buildSetsStringHash(String wordListFilename) {
        System.out.println("Building sets of anagrams for: " + wordListFilename);

        try (BufferedReader reader = new BufferedReader(new FileReader(wordListFilename))) {
            String word;
            while ((word = reader.readLine()) != null) {
                String hash = computeSortedWordHash(word);
                if (hash.isEmpty()) continue;
                anagramSetsStringHash.computeIfAbsent(hash, k -> new HashSet<>()).add(word);
                // System.out.println("Word: \'" + word + "\'");
                // System.out.println("Hash: " + hash);
                // System.out.println("Set: " + anagramSetsStringHash.get(hash));
            }
        } catch (IOException e) {
            System.out.println("\n[ERROR] " + e);
        }
    }


    public void buildSetsPrimeHash(String wordListFilename) {
        System.out.println("Building sets of anagrams for: " + wordListFilename);

        try (BufferedReader reader = new BufferedReader(new FileReader(wordListFilename))) {
            String word;
            while ((word = reader.readLine()) != null) {
                Integer hash = computeUnorderedHash(word);
                if (hash <= 1) continue;
                anagramSets.computeIfAbsent(hash, k -> new HashSet<>()).add(word);
                // System.out.println("Word: \'" + word + "\'");
                // System.out.println("Hash: " + hash);
                // System.out.println("Set: " + anagramSets.get(hash));
            }
        } catch (IOException e) {
            System.out.println("\n[ERROR] " + e);
        }
    }


    private int computeUnorderedHash(String word) {
        long hash = 1L; // long necessary for multuiplication
        final long M  = 2147483647L; // 2^31-1 as long

        for (char ch : word.toLowerCase().toCharArray()) {
            Integer prime = LETTER_PRIMES.get(ch);
            // System.out.println("The prime: " + prime);
            if (prime == null) continue; // for non-alphacharacters (excluding ')
            hash = (hash * prime) % M; 
        }
        return (int) hash;
    }


    private String computeSortedWordHash(String word) {
        char[] wordArray = word.toCharArray();
        Arrays.sort(wordArray);
        return new String(wordArray);
    }


    public void printSets(boolean allSizes) {
        System.out.println("\nThe sets: ");
        for (Map.Entry<Integer, Set<String>> anagramMap : anagramSets.entrySet()) {
            Integer hash = anagramMap.getKey();
            Set<String> anagramArray = anagramMap.getValue();
            
            if (allSizes || anagramArray.size() > 1) { 
                System.out.println("Key: " + hash + " → " + anagramArray);
            }
        }
    }


    public void printNumberOfSets(boolean allSizes) { 
        if (allSizes) { 
            // TODO: make this print statement clear
            System.out.println("\nThere are " + anagramSets.size() + " sets of anagrams in this file.");
        } else { 
            Integer count = 0;
            for (Map.Entry<Integer, Set<String>> anagramMap : anagramSets.entrySet()) {
                Integer hash = anagramMap.getKey();
                Set<String> anagramArray = anagramMap.getValue();
                
                if (anagramArray.size() > 1) { 
                    count += 1;
                }
            }
            System.out.println("\nThere are " + count + " sets of anagrams in this file.");
        }
    }

    
    public void printSetsSorted(boolean allSizes) {
        System.out.println("\nThe sets: ");
        for (Map.Entry<String, Set<String>> anagramMap : anagramSetsStringHash.entrySet()) {
            String hash = anagramMap.getKey();
            Set<String> anagramArray = anagramMap.getValue();
            
            if (allSizes || anagramArray.size() > 1) { 
                System.out.println("Key: " + hash + " → " + anagramArray);
            }
        }
    }


    public void printNumberOfSetsSorted(boolean allSizes) { 
        if (allSizes) { 
            // TODO: make this print statement clear
            System.out.println("\nThere are " + anagramSetsStringHash.size() + " sets of anagrams in this file.");
        } else { 
            Integer count = 0;
            for (Map.Entry<String, Set<String>> anagramMap : anagramSetsStringHash.entrySet()) {
                Set<String> anagramArray = anagramMap.getValue();
                
                if (anagramArray.size() > 1) { 
                    count += 1;
                }
            }
            System.out.println("\nThere are " + count + " sets of anagrams in this file.");
        }
    }

    
}
