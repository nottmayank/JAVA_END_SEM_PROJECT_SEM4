set -e

SRC_DIR="src"
OUT_DIR="out"
JAR_NAME="SmartResumeAnalyzer.jar"
MAIN_CLASS="Main"

echo "🔧  Compiling Java source files..."
mkdir -p "$OUT_DIR"

javac -d "$OUT_DIR" \
    "$SRC_DIR/AnalysisResult.java" \
    "$SRC_DIR/TextProcessor.java" \
    "$SRC_DIR/Analyzer.java" \
    "$SRC_DIR/ResumeAnalyzerUI.java" \
    "$SRC_DIR/Main.java"

echo "📦  Creating JAR file..."
cd "$OUT_DIR"
echo "Main-Class: $MAIN_CLASS" > manifest.txt
jar cfm "../$JAR_NAME" manifest.txt *.class
cd ..

echo ""
echo "✅  Build successful!"
echo "    JAR created: $JAR_NAME"
echo ""
echo "▶️   To run: java -jar $JAR_NAME"
