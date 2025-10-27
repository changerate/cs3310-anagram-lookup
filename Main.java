public class Main {
    public static void main(String[] args) {
        // String filename = "words.txt"; // class file - 100k words
        String filename = "words"; // /usr/share/dict/words - 250k words
        // String filename = "wordswords"; // /usr/share/dict/words multiplied - 10 million words
        // String filename = "words2.txt"; // my custom file

        AnagramUtility anagrams = new AnagramUtility(filename);
        
        System.out.println("\n-------------------------------------------------------");
        // anagrams.setTester("Outfile");
        System.out.println("\n-------------------------------------------------------");
        // anagrams.hashChecker("deoppilation", "cenospecies", true);
        System.out.println("\n-------------------------------------------------------");
        anagrams.checkForDifferences();
        System.out.println("\n-------------------------------------------------------");
    }
}



// deoppilation
// cenospecies