public class Main {
    public static void main(String[] args) {

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
}
