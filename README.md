# ⚡ Smart Resume Analyzer — Keyword Matching System
### Java End Semester Project | Swing GUI 

---

## 📁 Project Structure

```
SmartResumeAnalyzer/
├── src/
│   ├── Main.java              ← Entry point, launches the Swing UI
│   ├── TextProcessor.java     ← Text cleaning and keyword extraction
│   ├── Analyzer.java          ← Core keyword matching logic
│   ├── AnalysisResult.java    ← Data class to hold results
│   └── ResumeAnalyzerUI.java  ← Full Swing user interface
├── build.sh                   ← Build + JAR packaging script
└── README.md                  ← This file
```

---

## 🚀 How to Build & Run

### Prerequisites
- Java JDK 8 or higher installed
- Run `java -version` to check

### Option 1: Compile manually
```bash
# From the project root directory:

# Step 1: Create output folder
mkdir out

# Step 2: Compile all source files
javac -d out src/AnalysisResult.java src/TextProcessor.java src/Analyzer.java src/ResumeAnalyzerUI.java src/Main.java

# Step 3: Create JAR
cd out
echo "Main-Class: Main" > manifest.txt
jar cfm ../SmartResumeAnalyzer.jar manifest.txt *.class
cd ..

# Step 4: Run the application
java -jar SmartResumeAnalyzer.jar
```

### Option 2: Use the build script
```bash
chmod +x build.sh
./build.sh
java -jar SmartResumeAnalyzer.jar
```

---

## 🎯 How the Application Works

### Step-by-Step Flow:
```
User Input (Resume + Job Description)
        ↓
TextProcessor.extractKeywords()
  - Lowercase text
  - Remove punctuation
  - Split into words
  - Remove stop words ("the", "is", "and"...)
  - Remove short words (≤ 2 chars)
        ↓
Analyzer.analyze()
  - Extract job keywords
  - Extract resume keywords
  - Find: matched = resume ∩ job
  - Find: missing = job - resume
  - Score = (matched / total_job_keywords) × 100
        ↓
AnalysisResult (data object)
        ↓
ResumeAnalyzerUI.displayResults()
  - Show score %
  - Show match label
  - Show matched keywords (green)
  - Show missing keywords (red)
```

### Score Formula:
```
Score = (Number of Matched Keywords / Total Job Keywords) × 100
```

---

## 🧩 Class Descriptions

| Class | Responsibility |
|-------|----------------|
| `Main` | Entry point. Starts the Swing app safely on the Event Dispatch Thread. |
| `TextProcessor` | Cleans text: lowercases, removes punctuation, splits words, filters stop words. |
| `Analyzer` | Takes resume + job description, uses TextProcessor, finds matched/missing keywords, calculates score. |
| `AnalysisResult` | Simple data holder (POJO) for analysis output — score, matched set, missing set. |
| `ResumeAnalyzerUI` | Builds and manages the entire Swing GUI. Handles user interactions and displays results. |

---

## ✅ Features Implemented

- [x] Paste resume text
- [x] Paste job description text
- [x] Text cleaning (lowercase, remove punctuation)
- [x] Stop word filtering (50+ words)
- [x] Keyword extraction
- [x] Matched keyword detection
- [x] Missing keyword detection
- [x] Score calculation (percentage)
- [x] Match quality label (Excellent / Good / Average / Poor)
- [x] Statistics display (total, matched, missing counts)
- [x] Clear All button
- [x] Input validation with user-friendly error dialogs
- [x] Color-coded results (green = good, red = needs work)

---

## 💡 Sample Test Input

**Resume:**
```
Experienced Java developer with 3 years of experience in Spring Boot, REST APIs,
and Microservices. Proficient in MySQL, PostgreSQL databases. Familiar with Docker,
Git version control, and Agile development methodology. Strong problem-solving skills.
```

**Job Description:**
```
We are looking for a Java developer skilled in Spring Boot, Microservices, Docker,
Kubernetes, REST APIs, and MySQL. Experience with Agile and CI/CD pipelines required.
Knowledge of Git and cloud platforms like AWS is a plus.
```

**Expected Result:** ~40–60% match

---

