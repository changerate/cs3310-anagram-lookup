import java.util.Set;
import java.util.Map;


public abstract class AnagramsClass {
    protected double fileReadAndSetBuildTime;
    protected int numWordsInFile; // Number of words in the input file
    protected String filename;
    protected String hashMethod;

    public AnagramsClass(String inputFile) {
        this.filename = inputFile;
    }
    public abstract void buildSets();
    public abstract String computeSortedStringHash(String word);
    public abstract void saveSetsToFile(boolean allSizes, String filename);

    public abstract void printSets(boolean allSizes);
    public abstract void printNumberOfSets(boolean allSizes);
    public void displayStats() {
        System.out.println("==========================================================================");
        System.out.println("Using the " + hashMethod + " method of hashing:");
        System.out.println("Within the file \'" + filename + "\', " + numWordsInFile + " words were found.");
        System.out.println("Total sets: " + getNumberOfSets(true));
        System.out.println("Sets of two or more: " + getNumberOfSets(false));
        System.out.println("Execution time to read the file AND find these sets: " + fileReadAndSetBuildTime + "ms");
        System.out.println("==========================================================================");
    }
        
    // ========== ACCESSORS 
    public abstract Integer getNumberOfSets(boolean allSizes);
    public abstract Map<String, Set<String>> getSets();
    public double getTime() { 
        return fileReadAndSetBuildTime;
    }
    public Integer getWordCount() { 
        return numWordsInFile;
    }
}
