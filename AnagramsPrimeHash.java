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
        put('a', 2);
        put('á', 3);
        put('à', 5);
        put('â', 7);
        put('ä', 11);
        put('ã', 13);
        put('å', 12);
        put('b', 17); 
        put('c', 19);
        put('ç', 23);
        put('d', 29); 
        put('e', 31);
        put('é', 37);
        put('è', 41);
        put('ê', 43);
        put('ë', 47);
        put('f', 53); 
        put('g', 59); 
        put('h', 61);
        put('i', 67);
        put('í', 71);
        put('ì', 73);
        put('î', 79);
        put('ï', 83);
        put('j', 89); 
        put('k', 97); 
        put('l', 101);
        put('m', 103); 
        put('n', 107);
        put('ñ', 109);
        put('o', 113);
        put('ó', 127);
        put('ò', 131);
        put('ô', 137);
        put('ö', 139);
        put('õ', 149);
        put('p', 151); 
        put('q', 157); 
        put('r', 163);
        put('s', 167); 
        put('t', 173);
        put('u', 179);
        put('ú', 181);
        put('ù', 191);
        put('û', 193);
        put('ü', 197);
        put('v', 199); 
        put('w', 211); 
        put('x', 223);
        put('y', 227);
        put('ý', 229);
        put('ÿ', 233);
        put('z', 239); 
        put('\'', 241);
        put('-', 251);
    }};

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
            start = System.nanoTime();
            String word;
            while ((word = reader.readLine()) != null) {
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

        executionTime = (end - start) / 1_000_000.0;
        numWordsInFile = wordCount;
        // System.out.println("[Prime Hash] Execution time: " + executionTime + " ms");
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
