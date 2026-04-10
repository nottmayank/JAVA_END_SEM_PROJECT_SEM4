import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class TextProcessor {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "a", "an", "the", "and", "or", "but", "in", "on", "at", "to",
        "for", "of", "with", "by", "from", "is", "are", "was", "were",
        "be", "been", "being", "have", "has", "had", "do", "does", "did",
        "will", "would", "could", "should", "may", "might", "shall",
        "not", "no", "nor", "so", "yet", "both", "either", "each",
        "it", "its", "this", "that", "these", "those", "i", "me", "my",
        "we", "our", "you", "your", "he", "she", "they", "them", "their",
        "what", "which", "who", "whom", "how", "when", "where", "why",
        "all", "any", "few", "more", "most", "other", "some", "such",
        "than", "too", "very", "just", "as", "if", "while", "about",
        "into", "through", "during", "before", "after", "above", "below",
        "up", "down", "out", "off", "over", "under", "again", "then",
        "once", "here", "there", "can", "also", "well", "etc", "per",
        "use", "used", "using", "work", "working", "able", "need"
    ));

    /**
     * Main method: takes raw text and returns a clean set of unique keywords.
     *
     * Steps:
     *  1. Lowercase everything
     *  2. Remove all non-alphabetic characters (punctuation, numbers, symbols)
     *  3. Split by whitespace into individual words
     *  4. Remove stop words
     *  5. Remove very short words (length <= 2)
     *
     * We use LinkedHashSet to preserve insertion order while ensuring uniqueness.
     *
     * @param text  Raw input text (resume or job description)
     * @return      A Set of cleaned, meaningful keywords
     */
    public Set<String> extractKeywords(String text) {
        Set<String> keywords = new LinkedHashSet<>();

        if (text == null || text.trim().isEmpty()) {
            return keywords; 
        }

        String lowerText = text.toLowerCase();

        String cleanText = lowerText.replaceAll("[^a-z\\s]", " ");

   
        String[] words = cleanText.split("\\s+");

        for (String word : words) {
            word = word.trim();
            if (word.length() > 2 && !STOP_WORDS.contains(word)) {
                keywords.add(word);
            }
        }

        return keywords;
    }

    /**
     * Returns the set of stop words (useful for documentation/display purposes).
     *
     * @return  Unmodifiable view of the stop words set
     */
    public Set<String> getStopWords() {
        return java.util.Collections.unmodifiableSet(STOP_WORDS);
    }
}
