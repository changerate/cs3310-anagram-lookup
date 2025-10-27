import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class AnagramsPrimeHash {
    private Map<Integer, Set<String>> anagramSets = new HashMap<>();
    private static final Map<Character, Integer> LETTER_PRIMES = new HashMap<>() {{
        put('a', 2);  put('á', 3);  put('à', 5);  put('â', 7);  put('ä', 11);  put('ã', 13);
        put('b', 17); put('c', 19); put('ç', 23);
        put('d', 29); put('e', 31); put('é', 37); put('è', 41); put('ê', 43); put('ë', 47);
        put('f', 53); put('g', 59); put('h', 61);
        put('i', 67); put('í', 71); put('ì', 73); put('î', 79); put('ï', 83);
        put('j', 89); put('k', 97); put('l', 101);
        put('m', 103); put('n', 107); put('ñ', 109);
        put('o', 113); put('ó', 127); put('ò', 131); put('ô', 137); put('ö', 139); put('õ', 149);
        put('p', 151); put('q', 157); put('r', 163);
        put('s', 167); put('t', 173);
        put('u', 179); put('ú', 181); put('ù', 191); put('û', 193); put('ü', 197);
        put('v', 199); put('w', 211); put('x', 223);
        put('y', 227); put('ý', 229); put('ÿ', 233);
        put('z', 239); put('\'', 241);
    }};

    public void buildSets(String wordListFilename) {
        System.out.println("[Prime Hash] Building sets of anagrams for: " + wordListFilename);

        try (BufferedReader reader = new BufferedReader(new FileReader(wordListFilename))) {
            String word;
            while ((word = reader.readLine()) != null) {
                Integer hash = computePrimeHash(word);
                if (hash <= 1) continue;
                anagramSets.computeIfAbsent(hash, k -> new HashSet<>()).add(word);
                // System.out.println("[Prime Hash] Word: \'" + word + "\'");
                // System.out.println("[Prime Hash] Hash: " + hash);
                // System.out.println("[Prime Hash] Set: " + anagramSets.get(hash));
            }
        } catch (IOException e) {
            System.out.println("[Prime Hash] [ERROR] " + e);
        }
    }


    public Integer computePrimeHash(String word) {
        long hash = 1L; // long necessary for multuiplication
        final long M  = 2147483647L; // 2^31-1 as long

        for (char ch : word.toLowerCase().toCharArray()) {
            Integer prime = LETTER_PRIMES.get(ch);
            // System.out.println("[Prime Hash] The prime: " + prime);
            if (prime == null) continue; // for non-alphacharacters (excluding ')
            hash = (hash * prime) % M; 
        }
        return (int) hash;
    }


    private String computeSortedStringHash(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();
        Arrays.sort(wordArray);
        return new String(wordArray);
    }


    public void printSets(boolean allSizes) {
        System.out.println("[Prime Hash] The sets: ");
        for (Map.Entry<Integer, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
            Set<String> anagramArray = anagramMapEntry.getValue();
            if (allSizes || anagramArray.size() > 1) { 
                String hash = computeSortedStringHash(anagramArray.iterator().next());
            
                System.out.println("[Prime Hash] " + hash + " → " + anagramArray);
            }
        }
    }


    public void printNumberOfSets(boolean allSizes) { 
        if (allSizes) { 
            // TODO: make this print statement clear
            System.out.println("[Prime Hash] There are " + anagramSets.size() + " sets of anagrams in this file.");
        } else { 
            Integer count = 0;
            for (Map.Entry<Integer, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                Set<String> anagramArray = anagramMapEntry.getValue();
                
                if (anagramArray.size() > 1) { 
                    count += 1;
                }
            }
            System.out.println("[Prime Hash] There are " + count + " sets of anagrams in this file.");
        }
    }

    public void saveSetsToFile(boolean allSizes, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("The sets:\n");
            for (Map.Entry<Integer, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                Set<String> anagramArray = anagramMapEntry.getValue();
                if (allSizes || anagramArray.size() > 1) {
                    writer.write(computeSortedStringHash(anagramArray.iterator().next()) + " → " + anagramArray + "\n");
                }
            }
            System.out.println("[Prime Hash] Anagram sets saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Map<String, Set<String>> getSets() {
        Map<String, Set<String>> sets = new HashMap<>();

        for (Map.Entry<Integer, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
            Set<String> anagramArray = anagramMapEntry.getValue();
            String hash = computeSortedStringHash(anagramArray.iterator().next());
            if (anagramArray.size() > 1)
                sets.computeIfAbsent(hash, k -> new HashSet<>()).addAll(anagramArray);
        }
        System.out.println("[Prime Hash] There are " + sets.size() + " sets of anagrams in this file.");

        return sets;
    }
}
