import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class AnagramsPrimeHash extends AnagramsClass {
    private Map<Integer, Set<String>> anagramSets = new HashMap<>();
    private static final Map<Character, Integer> LETTER_PRIMES = new HashMap<>() {{
        // This is a hashmap sorted by frequency of letters in the file words.txt
        put('s', 2);
        put('e', 3);
        put('i', 5);
        put('a', 7);
        put('r', 11);
        put('n', 13);
        put('t', 17);
        put('o', 19);
        put('l', 23);
        put('c', 29);
        put('d', 31);
        put('\'', 37);
        put('u', 41);
        put('g', 43);
        put('m', 47);
        put('p', 53);
        put('h', 59);
        put('b', 61);
        put('y', 67);
        put('f', 71);
        put('k', 73);
        put('v', 79);
        put('w', 83);
        put('z', 89);
        put('x', 97);
        put('j', 101);
        put('q', 103);
        put('é', 107);
        put('è', 109);
        put('ö', 113);
        put('ü', 127);
        put('á', 131);
        put('ñ', 137);
        put('ó', 139);
        put('ä', 149);
        put('â', 151);
        put('ê', 157);
        put('ç', 163);
        put('å', 167);
        put('û', 173);
        put('í', 179);
        put('ô', 181);
        // everything below here is not in the words.txt file
        put('à', 191);
        put('ã', 193);
        put('ë', 197);
        put('ì', 199);
        put('î', 223);
        put('ï', 227);
        put('ò', 229);
        put('õ', 233);
        put('ú', 239);
        put('ù', 241);
        put('ý', 251);
        put('ÿ', 257);
        put('-', 263);
    }};

    /**********************************************
     * Constructor 
     * @param inputFile
     **********************************************/
    public AnagramsPrimeHash(String inputFile) {
        super(inputFile);
        hashMethod = "primes";
    }

    @Override 
    public void buildSets() {
        System.out.println("[Prime Hash] Building sets of anagrams for: " + filename);
        int wordCount = 0;
        long start;
        long end;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String word;
            start = System.nanoTime();
            while ((word = reader.readLine()) != null) {
                wordCount++;
                Integer hash = computePrimeHash(word, false);
                if (hash <= 1) continue;

                // first check if the string has the same number of chars
                if (anagramSets.get(hash) != null) {
                    String wordPrev = anagramSets.get(hash).iterator().next();
                    Integer sizePrev = wordPrev.length();
                    Integer sizeCurr = word.length();

                    if (sizePrev != sizeCurr) {
                        // System.out.println("[Prime Hash] Clashing hashes! ");
                        // System.out.println("\texist:\t" + wordPrev);
                        // System.out.println("\tnew:\t" + word);
                        continue;
                    } 
                    else anagramSets.get(hash).add(word);
                }
                else {
                    Set<String> newSet = new HashSet<>();
                    newSet.add(word);
                    anagramSets.put(hash, newSet);
                }
            }
            end = System.nanoTime();
        } catch (IOException e) {
            System.out.println("[Prime Hash] [ERROR] " + e);
            return;
        }

        fileReadAndSetBuildTime = (end - start) / 1_000_000.0;
        numWordsInFile = wordCount;
        // System.out.println("[Prime Hash] Execution time: " + fileReadAndSetBuildTime + " ms");
    }


    public Integer computePrimeHash(String word, boolean robustOutput) {
        long hash = 1L; // long necessary for multuiplication
        final long M  = 2_147_483_647L; // 2^31-1 as long

        for (char ch : word.toLowerCase().toCharArray()) {
            Integer prime = LETTER_PRIMES.get(ch);
            if (prime == null) {
                System.out.println("[Prime Hash] 👺 Skipping word with the char: " + ch);
                continue; 
            }
            hash = (hash * prime) % M; 
            if (robustOutput) System.out.println("\t\tThe hash: " + hash);
        }
        return (int) hash;
    }


    @Override 
    public String computeSortedStringHash(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();
        Arrays.sort(wordArray);
        return new String(wordArray);
    }


    @Override 
    public void saveSetsToFile(boolean allSizes, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("The sets:\n");
            for (Map.Entry<Integer, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                Set<String> anagramArray = anagramMapEntry.getValue();
                if (allSizes || anagramArray.size() > 1) {
                    writer.write(computeSortedStringHash(anagramArray.iterator().next()) + " → " + anagramArray + "\n");
                }
            }
            System.out.println("[Prime Hash] Anagram sets saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override 
    public void printSets(boolean allSizes) {
        System.out.println("[Prime Hash] The sets: ");
        for (Map.Entry<Integer, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
            Set<String> anagramArray = anagramMapEntry.getValue();
            if (allSizes || anagramArray.size() > 1) { 
                String hash = computeSortedStringHash(anagramArray.iterator().next());
            
                System.out.println("[Prime Hash] " + hash + " → " + anagramArray);
            }
        }
    }


    @Override 
    public void printNumberOfSets(boolean allSizes) { 
        if (allSizes) { 
            // TODO: make this print statement clear
            System.out.println("[Prime Hash] There are " + anagramSets.size() + " sets of anagrams in this file.");
        } else { 
            Integer count = 0;
            for (Map.Entry<Integer, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                Set<String> anagramArray = anagramMapEntry.getValue();
                if (anagramArray.size() > 1) { 
                    count += 1;
                }
            }
            System.out.println("[Prime Hash] There are " + count + " sets of anagrams in this file.");
        }
    }


    // ==== ACCESSORS 

    @Override 
    public Integer getNumberOfSets(boolean allSizes) { 
        if (allSizes) { 
            // TODO: make this print statement clear
            return anagramSets.size();
        } else { 
            Integer count = 0;
            for (Map.Entry<Integer, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
                Set<String> anagramArray = anagramMapEntry.getValue();
                if (anagramArray.size() > 1)
                    count += 1;
            }
            return count;
        }
    }
    @Override 
    public Map<String, Set<String>> getSets() {
        Map<String, Set<String>> sets = new HashMap<>();

        for (Map.Entry<Integer, Set<String>> anagramMapEntry : anagramSets.entrySet()) {
            Set<String> anagramArray = anagramMapEntry.getValue();
            String hash = computeSortedStringHash(anagramArray.iterator().next());
            if (anagramArray.size() > 1)
                sets.computeIfAbsent(hash, k -> new HashSet<>()).addAll(anagramArray);
        }
        return sets;
    }
}
