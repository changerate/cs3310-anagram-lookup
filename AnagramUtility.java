import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnagramUtility {
    private static AnagramsPrimeHash setBuilderPrimeHash = new AnagramsPrimeHash();
    private static AnagramsSortedStringHash setBuilderStringHash = new AnagramsSortedStringHash();
    private static String wordListFilename;

    
    public AnagramUtility(String filename) {
        setBuilderPrimeHash.buildSets(filename);
        setBuilderStringHash.buildSets(filename);
        wordListFilename = filename;
    }

    public void setTester(String postfix) {
        setBuilderPrimeHash.saveSetsToFile(false, "setsPrime" + postfix + ".txt");
        setBuilderStringHash.saveSetsToFile(false, "setsString" + postfix + ".txt");
    }


    // check for differences 
    public void checkForDifferences() {
        Map<String, Set<String>> primeHashes = setBuilderPrimeHash.getSets();
        Map<String, Set<String>> stringHashes = setBuilderStringHash.getSets();
        
        Set<String> onlyInMap1 = new HashSet<>(primeHashes.keySet());
        onlyInMap1.removeAll(stringHashes.keySet());
        Set<String> onlyInMap2 = new HashSet<>(stringHashes.keySet());
        onlyInMap2.removeAll(primeHashes.keySet());
    
        System.out.println("Only in prime hash map: " + onlyInMap1);
        System.out.println("Only in string hash map: " + onlyInMap2);
    }


    public void hashChecker(String word1, String word2, boolean robustOutput) {
        Integer primeHash = setBuilderPrimeHash.computePrimeHash(word1, robustOutput);
        System.out.println("[Prime Hash] Hash from word \'" + word1 + "\': " + primeHash);

        primeHash = setBuilderPrimeHash.computePrimeHash(word2, robustOutput);
        System.out.println("[Prime Hash] Hash from word \'" + word2 + "\': " + primeHash);
    }
}