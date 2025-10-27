public class Main {
    public static void main(String[] args) {
        AnagramSetBuilder setBuilder = new AnagramSetBuilder();
        
        // setBuilder.buildSets("words.txt");
        // setBuilder.buildSets("words");
        // setBuilder.buildSets("wordswords");
        setBuilder.buildSets("words2.txt");
        setBuilder.printNumberOfSets(true);
        setBuilder.printSets(false);
    }
}
