import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Arrays;


public class AnagramsSortedStringHash {
    private Map<String, Set<String>> anagramSets = new HashMap<>();


    public void buildSets(String wordListFilename) {
        System.out.println("[Sorted String Hash] Building sets of anagrams for: " + wordListFilename);

        try (BufferedReader reader = new BufferedReader(new FileReader(wordListFilename))) {
            String word;
            while ((word = reader.readLine()) != null) {
                String hash = computeSortedStringHash(word);
                if (hash.isEmpty()) continue;
                anagramSets.computeIfAbsent(hash, k -> new HashSet<>()).add(word);
                // System.out.println("[Sorted String Hash] Word: \'" + word + "\'");
                // System.out.println("[Sorted String Hash] Hash: " + hash);
                // System.out.println("[Sorted String Hash] Set: " + anagramSets.get(hash));
            }
        } catch (IOException e) {
            System.out.println("[Sorted String Hash] [ERROR] " + e);
        }
    }


    private String computeSortedStringHash(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();
        Arrays.sort(wordArray);
        return new String(wordArray);
    }

    
    public void printSets(boolean allSizes) {
        System.out.println("[Sorted String Hash] The sets: ");
        for (Map.Entry<String, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
            String hash = anagramMapEntry.getKey();
            Set<String> anagramArray = anagramMapEntry.getValue();
            
            if (allSizes || anagramArray.size() > 1) { 
                System.out.println("[Sorted String Hash] Key: " + hash + " → " + anagramArray);
            }
        }
    }


    public void printNumberOfSets(boolean allSizes) { 
        if (allSizes) { 
            // TODO: make this print statement clear
            System.out.println("[Sorted String Hash] There are " + anagramSets.size() + " sets of anagrams in this file.");
        } else { 
            Integer count = 0;
            for (Map.Entry<String, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                Set<String> anagramArray = anagramMapEntry.getValue();
                
                if (anagramArray.size() > 1) { 
                    count += 1;
                }
            }
            System.out.println("[Sorted String Hash] There are " + count + " sets of anagrams in this file.");
        }
    }

    public void saveSetsToFile(boolean allSizes, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("The sets:\n");
            for (Map.Entry<String, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                String hash = anagramMapEntry.getKey();
                Set<String> anagramArray = anagramMapEntry.getValue();

                if (allSizes || anagramArray.size() > 1) {
                    writer.write("Key: " + hash + " → " + anagramArray + "\n");
                }
            }
            System.out.println("[Sorted String Hash] Anagram sets saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
