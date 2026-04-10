import java.util.LinkedHashSet;
import java.util.Set;

public class Analyzer {

    private final TextProcessor textProcessor;

    // ---- Constructor -------------------------------------------

    public Analyzer() {
        this.textProcessor = new TextProcessor();
    }

    // ---- Main Analysis Method ----------------------------------

    /**
     * 
     *
     * @param resumeText      Raw resume text pasted by the user
     * @param jobDescription  Raw job description text pasted by the user
     * @return                An AnalysisResult containing score and keyword lists
     * @throws IllegalArgumentException if either input is null or blank
     */
    public AnalysisResult analyze(String resumeText, String jobDescription) {

        if (resumeText == null || resumeText.trim().isEmpty()) {
            throw new IllegalArgumentException("Resume text cannot be empty.");
        }
        if (jobDescription == null || jobDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Job description cannot be empty.");
        }

        Set<String> resumeKeywords = textProcessor.extractKeywords(resumeText);
        Set<String> jobKeywords    = textProcessor.extractKeywords(jobDescription);

    
        if (jobKeywords.isEmpty()) {
            throw new IllegalArgumentException(
                "No meaningful keywords found in job description. " +
                "Please provide more detailed text.");
        }

   
        Set<String> matchedKeywords = new LinkedHashSet<>();
        for (String keyword : jobKeywords) {
            if (resumeKeywords.contains(keyword)) {
                matchedKeywords.add(keyword);
            }
        }


        Set<String> missingKeywords = new LinkedHashSet<>();
        for (String keyword : jobKeywords) {
            if (!resumeKeywords.contains(keyword)) {
                missingKeywords.add(keyword);
            }
        }


        double score = ((double) matchedKeywords.size() / jobKeywords.size()) * 100.0;

        score = Math.round(score * 100.0) / 100.0;

  
        return new AnalysisResult(jobKeywords, matchedKeywords, missingKeywords, score);
    }
}
