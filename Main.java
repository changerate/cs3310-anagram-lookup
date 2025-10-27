public class Main {
    public static void main(String[] args) {
        // Create an instance of AnagramLookupTable
        AnagramSetBuilder setBuilder = new AnagramSetBuilder();
        
        String word = "blacks";
        // int hash = table.computeUnorderedHash(word);
        // System.out.println("\nHash value for \'" + word + "\': " + hash + "\n");
        
        // setBuilder.buildSets("fuck");
        // setBuilder.buildSets("words.txt");
        // setBuilder.buildSets("words");
        setBuilder.buildSets("words2.txt");
        // setBuilder.printSets();
        setBuilder.printNumberOfSets(false);
    }
}
