import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnagramUtility {
    private static AnagramsPrimeHash setBuilderPrimeHash;
    private static AnagramsSortedStringHash setBuilderStringHash;

    
    public AnagramUtility(String filename) {
        setBuilderPrimeHash = new AnagramsPrimeHash(filename);
        setBuilderStringHash = new AnagramsSortedStringHash(filename);
        setBuilderPrimeHash.buildSets();
        setBuilderStringHash.buildSets();
    }

    public void saveSetsToFile(String postfix) {
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


    public void displayStats(String hashMethod) {
        if (hashMethod == "sorted string")
            setBuilderStringHash.displayStats();
        else if (hashMethod == "primes") 
            setBuilderPrimeHash.displayStats();
        else
            System.out.println("[ERROR. displayStats()] Unknown hash method: " + hashMethod);
    }


    public void printNumberOfSets(String hashMethod, boolean allSizes) {
        if (hashMethod == "sorted string")
            setBuilderStringHash.printNumberOfSets(allSizes);
        else if (hashMethod == "primes") 
            setBuilderPrimeHash.printNumberOfSets(allSizes);
        else
            System.out.println("[ERROR. printNumberOfSets()] Unknown hash method: " + hashMethod);
    }


    public void printSets(String hashMethod, boolean allSizes) {
        if (hashMethod == "sorted string")
            setBuilderStringHash.printSets(allSizes);
        else if (hashMethod == "primes") 
            setBuilderPrimeHash.printSets(allSizes);
        else
            System.out.println("[ERROR. printSets()] Unknown hash method: " + hashMethod);
    }
}