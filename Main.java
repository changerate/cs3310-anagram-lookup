import java.io.File;



public class Main {
    public static void main(String[] args) {

        String filename = "words.txt"; // class file - 99,171 words
        // String filename = "words"; // from /usr/share/dict/words - 235,976 words

        if (args.length != 0)
            filename = args[0];

        System.out.println("\n--------------------------------------------------------------------------");
        AnagramUtility anagrams = new AnagramUtility(filename);
        
        System.out.println("\n-------------------------------------------------------");
        anagrams.printSets(false);

        System.out.println("\n--------------------------------------------------------------------------");
        anagrams.saveSetsToFile(filename);
        
        System.out.println("\n");
        anagrams.displayStats();

        File file = new File(filename);
        if (!file.exists())
            System.out.println("\nWARNING: The file \'" + filename + "\' was not found.");
        System.out.println("\n\n");
    }
}
