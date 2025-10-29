import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


public class AnagramsSortedStringHash extends AnagramsClass {
    /**********************************************
     * Constructor 
     * @param inputFile
     **********************************************/
    public AnagramsSortedStringHash(String inputFile) {
        super(inputFile);
        hashMethod = "Sorted String Hash";
    }
    

    /**********************************************
     * 
     **********************************************/
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
