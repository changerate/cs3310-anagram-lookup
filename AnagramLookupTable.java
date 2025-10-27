import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnagramLookupTable { 
    private static final Map<Character, Integer> LETTER_PRIMES = new HashMap<>() {{
        put('a', 2);
        put('b', 3);
        put('c', 5);
        put('d', 7);
        put('e', 11);
        put('f', 13);
        put('g', 17);
        put('h', 19);
        put('i', 23);
        put('j', 29);
        put('k', 31);
        put('l', 37);
        put('m', 41);
        put('n', 43);
        put('o', 47);
        put('p', 53);
        put('q', 59);
        put('r', 61);
        put('s', 67);
        put('t', 71);
        put('u', 73);
        put('v', 79);
        put('w', 83);
        put('x', 89);
        put('y', 97);
        put('z', 101);
        put('\'', 103);
    }};

    
    // void buildTable(String dictionaryFile) {
    //     try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile))) {
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //         // Process each line (e.g., add to a data structure)
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    int computeUnorderedHash(String word) {
        long hash = 1L; // long necessary for multuiplication
        final long M  = 2147483647L; // 2^31-1 as long

        for (char ch : word.toLowerCase().toCharArray()) {
            Integer prime = LETTER_PRIMES.get(ch);
            if (prime == null) continue; // for non-alphacharacters (excluding ')
            hash = (hash * prime) % M; 
        }
        return (int) hash;
    }
}