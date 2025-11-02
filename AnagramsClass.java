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

    

    /*********************************************************
     * Function AnagramsClass; constructor initializes 
     * the input filename and prepares data structures.
     * @param inputFile the name of the file containing words
     *********************************************************/
    public AnagramsClass(String inputFile) {
        this.filename = inputFile;
    }



    /*********************************************************
     * Function buildSets; this method is abstract and implemented 
     * differently depending on the hash method used (e.g., prime or sorted).
     *********************************************************/
    public abstract void buildSets(); 



    /*********************************************************
     * Function computeSortedStringHash; this method takes a 
     * given string and findes the sorted version of it. 
     * @param word
     * @return the alphabetically sorted word 
     *********************************************************/
    public String computeSortedStringHash(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();
        Arrays.sort(wordArray);
        return new String(wordArray);
    }



    /*********************************************************
     * Function saveSetsToFile; this method writes all the 
     * anagram sets to a file, either all sets or only those 
     * with more than one element.
     * @param allSizes if true, include all sets; otherwise include only sets of size > 1
     * @param filename the output file name
     *********************************************************/
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



    /*********************************************************
     * Function printSets; prints all the anagram sets to the console.
     * @param allSizes if true, include all sets; otherwise include only sets of size > 1
     *********************************************************/
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



    /*********************************************************
     * Function printNumberOfSets; prints how many anagram sets 
     * exist in the file, depending on whether single-word sets 
     * are counted.
     * @param allSizes if true, count all sets; otherwise count only sets of size > 1
     *********************************************************/
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



    /*********************************************************
     * Function displayStats; displays overall statistics about 
     * the file processed and the generated anagram sets.
     *********************************************************/
    public void displayStats() {
        System.out.println("File: " + filename);
        System.out.println("Using the " + hashMethod + " method of hashing");
        System.out.println("Num words found:\t" + numWordsInFile);
        System.out.println("Total sets:\t\t" + getNumberOfSets(true));
        System.out.println("Sets of two or more:\t" + getNumberOfSets(false));
        System.out.println("Time to read file AND\n  to find these sets:\t" + Math.round(fileReadAndSetBuildTime * 100.0) / 100.0  + "ms");
    }


        
    // =====================================================================
    //                          ACCESSORS 
    // =====================================================================


    /*********************************************************
     * Function getNumberOfSets; returns the number of anagram 
     * sets, depending on whether single-word sets are counted.
     * @param allSizes if true, count all sets; otherwise count only sets of size > 1
     * @return the number of sets
     *********************************************************/
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



    /*********************************************************
     * Function getSets; returns the map of all anagram sets 
     * that contain at least two words.
     * @return a map of hashes to their corresponding anagram sets
     *********************************************************/
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



    /*********************************************************
     * Function getTime; returns the total time taken to read 
     * the file and build the anagram sets.
     * @return the total processing time in milliseconds
     *********************************************************/
    public double getTime() { 
        return fileReadAndSetBuildTime;
    }



    /*********************************************************
     * Function getWordCount; returns the number of words read 
     * from the file.
     * @return the number of words in the file
     *********************************************************/
    public Integer getWordCount() { 
        return numWordsInFile;
    }
}