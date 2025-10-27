public class Main {
    public static void main(String[] args) {
        AnagramSetBuilder setBuilder = new AnagramSetBuilder();
        
        // setBuilder.buildSets("words.txt"); // class file - 100k words
        // setBuilder.buildSets("words"); // /usr/share/dict/words - 250k words
        // setBuilder.buildSets("wordswords"); // /usr/share/dict/words multiplied - 10 million words
        setBuilder.buildSets("words2.txt");
        setBuilder.printNumberOfSets(true);
        setBuilder.printSets(false);
    }
}
