public class Main {
    public static void main(String[] args) {
        String filename = "words.txt"; // class file - ~100k words
        // String filename = "words"; // /usr/share/dict/words - ~250k words

        System.out.println("\n--------------------------------------------------------------------------");
        AnagramUtility anagrams = new AnagramUtility(filename);

        // System.out.println("\n--------------------------------------------------------------------------");
        // anagrams.checkForDifferences();

        System.out.println("\n--------------------------------------------------------------------------");
        anagrams.saveSetsToFile(filename);
        
        System.out.println("\n");
        anagrams.displayStats();

        // System.out.println("\n-------------------------------------------------------");
        // anagrams.printSets("sorted string", false);
    }
}
