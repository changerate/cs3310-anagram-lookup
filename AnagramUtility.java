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
        setBuilderPrimeHash.saveSetsToFile(false, "setsPrime" + postfix);
        setBuilderStringHash.saveSetsToFile(false, "setsString" + postfix);
    }


    // check for differences 
    public void checkForDifferences() {
        Map<String, Set<String>> stringHashes = setBuilderStringHash.getSets();
        Map<String, Set<String>> primeHashes = setBuilderPrimeHash.getSets();
        
        Set<String> onlyInMap2 = new HashSet<>(stringHashes.keySet());
        onlyInMap2.removeAll(primeHashes.keySet());
        Set<String> onlyInMap1 = new HashSet<>(primeHashes.keySet());
        onlyInMap1.removeAll(stringHashes.keySet());
    
        System.out.println("Only in string hash map: " + onlyInMap2);
        System.out.println("Only in prime hash map: " + onlyInMap1);
    }


    public void hashChecker(String word1, String word2, boolean robustOutput) {
        String primeHash = setBuilderPrimeHash.computePrimeHash(word2, robustOutput);
        System.out.println("[Prime Hash] Hash from word \'" + word2 + "\': " + primeHash);
        
        primeHash = setBuilderPrimeHash.computePrimeHash(word1, robustOutput);
        System.out.println("[Prime Hash] Hash from word \'" + word1 + "\': " + primeHash);
    }


    public void displayStats() {
        System.out.println("==========================================================================");
        System.out.println("|| This program primarly creates sets of anagrams bsaed on a given text file.");
        System.out.println("|| Another functionality of this program is that it compares the efficiency");
        System.out.println("|| of two different hashing methods. In the first method I create a hash out of");
        System.out.println("|| the alphabetical sorting of the string. In the second method I create a hash");
        System.out.println("|| by computing the product of the string when every character is assigned a");
        System.out.println("|| prime number (See the LETTER_PRIMES map to see how the characters are assigned primes).");
        System.out.println("--------------------------------------------------------------------------");
        setBuilderStringHash.displayStats();
        System.out.println("--------------------------------------------------------------------------");
        setBuilderPrimeHash.displayStats();
        System.out.println("==========================================================================");
    }


    public void printNumberOfSets(boolean allSizes) {
        setBuilderStringHash.printNumberOfSets(allSizes);
        setBuilderPrimeHash.printNumberOfSets(allSizes);
    }


    public void printSets(boolean allSizes) {
        setBuilderStringHash.printSets(allSizes);
        setBuilderPrimeHash.printSets(allSizes);
    }
}