import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void setTester() {
        AnagramsPrimeHash setBuilderPrimeHash = new AnagramsPrimeHash();
        AnagramsSortedStringHash setBuilderStringHash = new AnagramsSortedStringHash();
        
        // USING A PRIME HASH
        setBuilderPrimeHash.buildSets("words.txt"); // class file - 100k words
        // setBuilderPrimeHash.buildSets("words"); // /usr/share/dict/words - 250k words
        // setBuilderPrimeHash.buildSets("wordswords"); // /usr/share/dict/words multiplied - 10 million words
        // setBuilderPrimeHash.buildSets("words2.txt"); // my custom file
        
        // USING A STRING HASH
        setBuilderStringHash.buildSets("words.txt"); // class file - 100k words
        // setBuilderStringHash.buildSets("words"); // /usr/share/dict/words - 250k words
        // setBuilderStringHash.buildSets("wordswords"); // /usr/share/dict/words multiplied - 10 million words
        // setBuilderStringHash.buildSets("words2.txt"); // my custom file
    
        System.out.println("\n--------------------------------------------");
        
        // USING A PRIME HASH
        setBuilderPrimeHash.printNumberOfSets(false);
        // setBuilderPrimeHash.printSets(true);
        setBuilderPrimeHash.saveSetsToFile(false, "setsPrime.txt");
    
        // USING A STRING HASH
        setBuilderStringHash.printNumberOfSets(false);
        // setBuilderStringHash.printSets(false);
        setBuilderStringHash.saveSetsToFile(false, "setsString.txt");
    }


    // check for differences 
    public static void checkForDifferences() {
        AnagramsPrimeHash setBuilderPrimeHash = new AnagramsPrimeHash();
        AnagramsSortedStringHash setBuilderStringHash = new AnagramsSortedStringHash();
        
        // USING A PRIME HASH
        setBuilderPrimeHash.buildSets("words.txt"); // class file - 100k words
        // setBuilderPrimeHash.buildSets("words"); // /usr/share/dict/words - 250k words
        // setBuilderPrimeHash.buildSets("wordswords"); // /usr/share/dict/words multiplied - 10 million words
        // setBuilderPrimeHash.buildSets("words2.txt"); // my custom file
        
        // USING A STRING HASH
        setBuilderStringHash.buildSets("words.txt"); // class file - 100k words
        // setBuilderStringHash.buildSets("words"); // /usr/share/dict/words - 250k words
        // setBuilderStringHash.buildSets("wordswords"); // /usr/share/dict/words multiplied - 10 million words
        // setBuilderStringHash.buildSets("words2.txt"); // my custom file
        
        Map<String, Set<String>> primeHash = setBuilderPrimeHash.getSets();
        Map<String, Set<String>> stringHash = setBuilderStringHash.getSets();
        
        Set<String> onlyInMap1 = new HashSet<>(primeHash.keySet());
        onlyInMap1.removeAll(stringHash.keySet());
        Set<String> onlyInMap2 = new HashSet<>(stringHash.keySet());
        onlyInMap2.removeAll(primeHash.keySet());
    
        System.out.println("Only in prime hash map: " + onlyInMap1);
        System.out.println("Only in string hash map: " + onlyInMap2);
    }


    public static void hashChecker() {
        AnagramsPrimeHash setBuilderPrimeHash = new AnagramsPrimeHash();
        AnagramsSortedStringHash setBuilderStringHash = new AnagramsSortedStringHash();

        String word = "ortué";
        Integer primeHash = setBuilderPrimeHash.computePrimeHash(word);
        System.out.println("Hash from word \'" + word + "\': " + primeHash);

        word = "rout";
        primeHash = setBuilderPrimeHash.computePrimeHash(word);
        System.out.println("Hash from word \'" + word + "\': " + primeHash);
    }


    public static void main(String[] args) {
        setTester();
        // hashChecker();
        // checkForDifferences();
    }
}
