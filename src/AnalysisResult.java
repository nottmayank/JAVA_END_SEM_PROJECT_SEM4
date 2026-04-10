import java.util.Set;

public class AnalysisResult {


    private final Set<String> jobKeywords;


    private final Set<String> matchedKeywords;

   
    private final Set<String> missingKeywords;

  
    private final double matchPercentage;

    // ---- Constructor -------------------------------------------

    public AnalysisResult(Set<String> jobKeywords,
                          Set<String> matchedKeywords,
                          Set<String> missingKeywords,
                          double matchPercentage) {
        this.jobKeywords      = jobKeywords;
        this.matchedKeywords  = matchedKeywords;
        this.missingKeywords  = missingKeywords;
        this.matchPercentage  = matchPercentage;
    }

    // ---- Getters -----------------------------------------------

    public Set<String> getJobKeywords()     { return jobKeywords;     }
    public Set<String> getMatchedKeywords() { return matchedKeywords; }
    public Set<String> getMissingKeywords() { return missingKeywords; }
    public double      getMatchPercentage() { return matchPercentage; }

    // ---- Convenience Methods -----------------------------------

 
    public int getTotalJobKeywords()  { return jobKeywords.size();     }

   
    public int getMatchedCount()      { return matchedKeywords.size(); }

    public int getMissingCount()      { return missingKeywords.size(); }


    public String getMatchLabel() {
        if (matchPercentage >= 80) return "Excellent Match! 🎉";
        if (matchPercentage >= 60) return "Good Match 👍";
        if (matchPercentage >= 40) return "Average Match 🤔";
        if (matchPercentage >= 20) return "Poor Match ⚠️";
        return "Very Low Match ❌";
    }
}
