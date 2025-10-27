import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AnagramSetBuilder {
    private Map<Integer, ArrayList<String>> anagramSets = new HashMap<>();
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

    
    public void buildSets(String wordListFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(wordListFileName))) {
            String word;
            while ((word = reader.readLine()) != null) {
                // System.out.println("Word: \'" + word + "\'");

                Integer hash = computeUnorderedHash(word);
                // System.out.println("Hash: " + hash);
                if (hash <= 1) continue;

                anagramSets.computeIfAbsent(hash, k -> new ArrayList<>()).add(word);
                // System.out.println("Set: " + anagramSets.get(hash));
            }
        } catch (IOException e) {
            System.out.println("\n[ERROR] " + e);
        }
    }


    public void printSets() {
        System.out.println("\nThe set: ");
        for (Map.Entry<Integer, ArrayList<String>> set : anagramSets.entrySet()) { 
            System.out.println(set);
        }
    }


    public void printNumberOfSets(boolean allSizes) { 
        if (allSizes) { 
            System.out.println("\nThere are " + anagramSets.size() + " sets of anagrams in this file.");

        } else { 
            Integer count = 0;
            for (Map.Entry<Integer, ArrayList<String>> anagramMap : anagramSets.entrySet()) {
                Integer hash = anagramMap.getKey();
                ArrayList<String> anagramArray = anagramMap.getValue();
                
                if (anagramArray.size() > 1) { 
                    count += 1;
                    System.out.println("Key: " + hash + " → " + anagramArray);
                }
            }
            System.out.println("\nThere are " + count + " sets of anagrams in this file.");
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

}