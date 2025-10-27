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


public class AnagramsSortedStringHash extends AnagramsClass {
    private Map<String, Set<String>> anagramSets = new HashMap<>();

    public AnagramsSortedStringHash(String inputFile) {
        super(inputFile);
        hashMethod = "sorted strings";
    }
    

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
        
        executionTime = (end - start) / 1_000_000.0;
        numWordsInFile = wordCount;
        // System.out.println("[Sorted String Hash] Execution time: " + executionTime + " ms");
    }


    public String computeSortedStringHash(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();
        Arrays.sort(wordArray);
        return new String(wordArray);
    }


    public void printSets(boolean allSizes) {
        System.out.println("[Sorted String Hash] The sets: ");
        for (Map.Entry<String, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
            Set<String> anagramArray = anagramMapEntry.getValue();
            if (allSizes || anagramArray.size() > 1) { 
                String hash = anagramMapEntry.getKey();
            
                System.out.println("[Sorted String Hash] " + hash + " → " + anagramArray);
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
                Set<String> anagramArray = anagramMapEntry.getValue();
                if (allSizes || anagramArray.size() > 1) {
                    writer.write(computeSortedStringHash(anagramArray.iterator().next()) + " → " + anagramArray + "\n");
                }
            }
            System.out.println("[Sorted String Hash] Anagram sets saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    // ==== ACCESSORS 
    public Map<String, Set<String>> getSets() {
        Map<String, Set<String>> sets = new HashMap<>();

        for (Map.Entry<String, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
            Set<String> anagramArray = anagramMapEntry.getValue();
            String hash = computeSortedStringHash(anagramArray.iterator().next());
            if (anagramArray.size() > 1)
                sets.computeIfAbsent(hash, k -> new HashSet<>()).addAll(anagramArray);
        }
        return sets;
    }


    public Integer getNumberOfSets(boolean allSizes) { 
        if (allSizes) { 
            // TODO: make this print statement clear
            return anagramSets.size();
        } else { 
            Integer count = 0;
            for (Map.Entry<String, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                Set<String> anagramArray = anagramMapEntry.getValue();
                
                if (anagramArray.size() > 1) { 
                    count += 1;
                }
            }
            return count;
        }
    }
}
