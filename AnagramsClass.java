import java.util.Set;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public abstract class AnagramsClass {
    protected double fileReadAndSetBuildTime;
    protected int numWordsInFile; // Number of words in the input file
    protected String filename;
    protected String hashMethod;
    protected Map<String, Set<String>> anagramSets = new HashMap<>();


    public AnagramsClass(String inputFile) {
        this.filename = inputFile;
    }



    // implemented differently depending on the hash method type
    public abstract void buildSets(); 


    
    public String computeSortedStringHash(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();
        Arrays.sort(wordArray);
        return new String(wordArray);
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
            System.out.println("[" + hashMethod + "] Anagram sets saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void printSets(boolean allSizes) {
        System.out.println("[Prime Hash] The sets: ");
        for (Map.Entry<String, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
            Set<String> anagramArray = anagramMapEntry.getValue();
            if (allSizes || anagramArray.size() > 1) { 
                String hash = computeSortedStringHash(anagramArray.iterator().next());
            
                System.out.println("[" + hashMethod + "] " + hash + " → " + anagramArray);
            }
        }
    }



    public void printNumberOfSets(boolean allSizes) { 
        if (allSizes) { 
            // TODO: make this print statement clear
            System.out.println("[Prime Hash] There are " + anagramSets.size() + " sets of anagrams in this file.");
        } else { 
            Integer count = 0;
            for (Map.Entry<String, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                Set<String> anagramArray = anagramMapEntry.getValue();
                if (anagramArray.size() > 1) { 
                    count += 1;
                }
            }
            System.out.println("[Prime Hash] There are " + count + " sets of anagrams in this file.");
        }
    }



    public void displayStats() {
        System.out.println("==========================================================================");
        System.out.println("File: " + filename);
        System.out.println("Using the " + hashMethod + " method of hashing");
        System.out.println("Num words found:\t" + numWordsInFile);
        System.out.println("Total sets:\t\t" + getNumberOfSets(true));
        System.out.println("Sets of two or more:\t" + getNumberOfSets(false));
        System.out.println("Time to read file AND\n  to find these sets:\t" + Math.round(fileReadAndSetBuildTime * 100.0) / 100.0  + "ms");
        System.out.println("==========================================================================");
    }


        
    // =====================================================
    //                          ACCESSORS 
    // =====================================================

    public Integer getNumberOfSets(boolean allSizes) { 
        if (allSizes) { 
            // TODO: make this print statement clear
            return anagramSets.size();
        } else { 
            Integer count = 0;
            for (Map.Entry<String, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                Set<String> anagramArray = anagramMapEntry.getValue();
                if (anagramArray.size() > 1)
                    count += 1;
            }
            return count;
        }
    }



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



    public double getTime() { 
        return fileReadAndSetBuildTime;
    }
    public Integer getWordCount() { 
        return numWordsInFile;
    }
}