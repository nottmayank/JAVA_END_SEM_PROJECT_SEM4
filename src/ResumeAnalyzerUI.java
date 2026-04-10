import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class ResumeAnalyzerUI extends JFrame {

    // ---- UI Components ----------------------------------------

    private JTextArea resumeTextArea;        // User pastes resume here
    private JTextArea jobDescTextArea;       // User pastes job description here
    private JButton   analyzeButton;         // Triggers analysis
    private JButton   clearButton;           // Clears all fields

    // Result display components
    private JLabel    scoreLabel;            // Shows percentage score
    private JLabel    matchLabelDisplay;     // Shows match quality label
    private JTextArea matchedKeywordsArea;   // Lists matched keywords
    private JTextArea missingKeywordsArea;   // Lists missing keywords
    private JLabel    statsLabel;            // Shows total/matched/missing counts

    // ---- Core Logic Objects -----------------------------------

    private final Analyzer analyzer;        // Handles matching logic

    // ---- Color Palette ----------------------------------------


    private static final Color BG_DARK       = new Color(15, 20, 35);
    private static final Color BG_PANEL      = new Color(25, 32, 55);
    private static final Color BG_INPUT      = new Color(20, 27, 46);
    private static final Color ACCENT_BLUE   = new Color(64, 132, 255);
    private static final Color ACCENT_GREEN  = new Color(46, 213, 115);
    private static final Color ACCENT_RED    = new Color(255, 71, 87);
    private static final Color ACCENT_YELLOW = new Color(255, 199, 0);
    private static final Color TEXT_PRIMARY  = new Color(230, 235, 255);
    private static final Color TEXT_MUTED    = new Color(130, 145, 185);
    private static final Color BORDER_COLOR  = new Color(45, 55, 90);

    // ---- Constructor ------------------------------------------

    public ResumeAnalyzerUI() {
        analyzer = new Analyzer();
        initializeWindow();
        buildUI();
    }

    // ---- Window Setup -----------------------------------------

    /**
     * Configure the main JFrame properties.
     */
    private void initializeWindow() {
        setTitle("Smart Resume Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null); // Center on screen
        getContentPane().setBackground(BG_DARK);
    }

    // ---- UI Construction --------------------------------------

  
    private void buildUI() {
        setLayout(new BorderLayout(0, 0));

        add(buildHeaderPanel(), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BG_DARK);
        centerPanel.setBorder(new EmptyBorder(0, 15, 10, 15));

        centerPanel.add(buildInputPanel(), BorderLayout.NORTH);

        centerPanel.add(buildButtonPanel(), BorderLayout.CENTER);

        centerPanel.add(buildResultsPanel(), BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        add(buildFooterPanel(), BorderLayout.SOUTH);
    }

    // ---- Header Panel -----------------------------------------

    private JPanel buildHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_PANEL);
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 2, 0, ACCENT_BLUE),
            new EmptyBorder(18, 25, 18, 25)
        ));

        // App title
        JLabel title = new JLabel("⚡ Smart Resume Analyzer");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_PRIMARY);

        // Subtitle
        JLabel subtitle = new JLabel("Keyword Matching System  —  Compare your resume against any job description");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_MUTED);

        JPanel titleGroup = new JPanel(new GridLayout(2, 1, 0, 4));
        titleGroup.setBackground(BG_PANEL);
        titleGroup.add(title);
        titleGroup.add(subtitle);

        header.add(titleGroup, BorderLayout.WEST);

        // Version badge on the right
        JLabel badge = new JLabel("v1.0  |  CS Project");
        badge.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        badge.setForeground(TEXT_MUTED);
        badge.setHorizontalAlignment(SwingConstants.RIGHT);
        header.add(badge, BorderLayout.EAST);

        return header;
    }

    // ---- Input Panel (Two Text Areas) -------------------------

    private JPanel buildInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        inputPanel.setBackground(BG_DARK);
        inputPanel.setBorder(new EmptyBorder(12, 0, 8, 0));
        inputPanel.setPreferredSize(new Dimension(0, 240));

        inputPanel.add(buildTextAreaSection(
            "📄  Your Resume",
            "Paste your resume text here...\n\nExample:\nJava developer with 2 years of experience in Spring Boot, REST APIs, MySQL...",
            true
        ));

       
        inputPanel.add(buildTextAreaSection(
            "💼  Job Description",
            "Paste the job description here...\n\nExample:\nWe are looking for a Java developer skilled in Spring Boot, Microservices, Docker...",
            false
        ));

        return inputPanel;
    }

    /**
     * Creates a labeled text area section (card-style panel).
     *
     * @param labelText    Section title shown above the textarea
     * @param placeholder  Placeholder hint text
     * @param isResume     True = resume area, False = job description area
     */
    private JPanel buildTextAreaSection(String labelText, String placeholder, boolean isResume) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(BG_PANEL);
        card.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(12, 14, 12, 14)
        ));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(isResume ? ACCENT_BLUE : ACCENT_YELLOW);
        card.add(label, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea(placeholder);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setBackground(BG_INPUT);
        textArea.setForeground(TEXT_MUTED);
        textArea.setCaretColor(TEXT_PRIMARY);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(new EmptyBorder(8, 8, 8, 8));

        textArea.addFocusListener(new FocusAdapter() {
            private boolean firstClick = true;
            @Override
            public void focusGained(FocusEvent e) {
                if (firstClick) {
                    textArea.setText("");
                    textArea.setForeground(TEXT_PRIMARY);
                    firstClick = false;
                }
            }
        });

        if (isResume) {
            resumeTextArea = textArea;
        } else {
            jobDescTextArea = textArea;
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(BG_INPUT);
        scrollPane.getVerticalScrollBar().setBackground(BG_PANEL);

        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    // ---- Button Panel -----------------------------------------

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        panel.setBackground(BG_DARK);


        analyzeButton = createStyledButton("🔍  Analyze Resume", ACCENT_BLUE, Color.WHITE);
        analyzeButton.setPreferredSize(new Dimension(200, 42));
        analyzeButton.addActionListener(e -> performAnalysis());

        clearButton = createStyledButton("🗑  Clear All", BG_PANEL, TEXT_MUTED);
        clearButton.setPreferredSize(new Dimension(140, 42));
        clearButton.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(5, 15, 5, 15)
        ));
        clearButton.addActionListener(e -> clearAll());

        panel.add(analyzeButton);
        panel.add(clearButton);
        return panel;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.brighter());
                btn.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
                btn.repaint();
            }
        });
        return btn;
    }

    // ---- Results Panel ----------------------------------------

    private JPanel buildResultsPanel() {
        JPanel resultsWrapper = new JPanel(new BorderLayout(0, 8));
        resultsWrapper.setBackground(BG_DARK);
        resultsWrapper.setBorder(new EmptyBorder(4, 0, 0, 0));

        // ---- Score Bar (top of results) ----
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 6));
        scorePanel.setBackground(BG_PANEL);
        scorePanel.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(10, 20, 10, 20)
        ));

        scoreLabel = new JLabel("— %");
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        scoreLabel.setForeground(ACCENT_BLUE);

        matchLabelDisplay = new JLabel("Run an analysis to see results");
        matchLabelDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        matchLabelDisplay.setForeground(TEXT_MUTED);

        statsLabel = new JLabel("");
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statsLabel.setForeground(TEXT_MUTED);

        scorePanel.add(scoreLabel);
        scorePanel.add(makeSeparator());
        scorePanel.add(matchLabelDisplay);
        scorePanel.add(makeSeparator());
        scorePanel.add(statsLabel);

        resultsWrapper.add(scorePanel, BorderLayout.NORTH);

        // ---- Keyword panels (two columns) ----
        JPanel keywordsPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        keywordsPanel.setBackground(BG_DARK);
        keywordsPanel.setPreferredSize(new Dimension(0, 165));

        // Matched keywords
        matchedKeywordsArea = buildKeywordArea();
        keywordsPanel.add(buildKeywordCard(
            "✅  Matched Keywords",
            ACCENT_GREEN,
            matchedKeywordsArea
        ));

        // Missing keywords
        missingKeywordsArea = buildKeywordArea();
        keywordsPanel.add(buildKeywordCard(
            "❌  Missing Keywords",
            ACCENT_RED,
            missingKeywordsArea
        ));

        resultsWrapper.add(keywordsPanel, BorderLayout.CENTER);
        return resultsWrapper;
    }

    private JLabel makeSeparator() {
        JLabel sep = new JLabel("|");
        sep.setForeground(BORDER_COLOR);
        sep.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        return sep;
    }

  
    private JTextArea buildKeywordArea() {
        JTextArea area = new JTextArea("Keywords will appear here after analysis...");
        area.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        area.setBackground(BG_INPUT);
        area.setForeground(TEXT_MUTED);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(8, 8, 8, 8));
        return area;
    }

    private JPanel buildKeywordCard(String title, Color titleColor, JTextArea area) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(BG_PANEL);
        card.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(titleColor);
        card.add(label, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(new LineBorder(BORDER_COLOR, 1));
        scroll.getViewport().setBackground(BG_INPUT);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    // ---- Footer Panel -----------------------------------------

    private JPanel buildFooterPanel() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(BG_PANEL);
        footer.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_COLOR));

        JLabel footerLabel = new JLabel("Smart Resume Analyzer  •  End Semester Project  •  Java + Swing");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(TEXT_MUTED);
        footer.add(footerLabel);
        return footer;
    }

    // ---- Core Action: Perform Analysis ------------------------


    private void performAnalysis() {
        String resumeText = resumeTextArea.getText().trim();
        String jobText    = jobDescTextArea.getText().trim();

        try {
            // Run the analysis — may throw IllegalArgumentException on bad input
            AnalysisResult result = analyzer.analyze(resumeText, jobText);

            // Display the results on screen
            displayResults(result);

        } catch (IllegalArgumentException ex) {
            // Show a friendly error dialog to the user
            JOptionPane.showMessageDialog(
                this,
                "⚠️  " + ex.getMessage(),
                "Input Error",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Updates all result UI components with data from the AnalysisResult.
     *
     * @param result  The analysis output from Analyzer.analyze()
     */
    private void displayResults(AnalysisResult result) {
        double score = result.getMatchPercentage();

        // --- Score label ---
        scoreLabel.setText(String.format("%.1f%%", score));

        // Color-code the score based on value
        if (score >= 80)      scoreLabel.setForeground(ACCENT_GREEN);
        else if (score >= 50) scoreLabel.setForeground(ACCENT_YELLOW);
        else                  scoreLabel.setForeground(ACCENT_RED);

        // --- Match quality label ---
        matchLabelDisplay.setText(result.getMatchLabel());
        matchLabelDisplay.setForeground(TEXT_PRIMARY);

        // --- Stats summary ---
        statsLabel.setText(String.format(
            "Total Keywords: %d   |   Matched: %d   |   Missing: %d",
            result.getTotalJobKeywords(),
            result.getMatchedCount(),
            result.getMissingCount()
        ));

        // --- Matched keywords list ---
        Set<String> matched = result.getMatchedKeywords();
        if (matched.isEmpty()) {
            matchedKeywordsArea.setText("No matching keywords found.");
            matchedKeywordsArea.setForeground(TEXT_MUTED);
        } else {
            matchedKeywordsArea.setText(formatKeywords(matched));
            matchedKeywordsArea.setForeground(ACCENT_GREEN);
        }
        matchedKeywordsArea.setCaretPosition(0); // Scroll to top

        // --- Missing keywords list ---
        Set<String> missing = result.getMissingKeywords();
        if (missing.isEmpty()) {
            missingKeywordsArea.setText("🎉 All keywords matched! Great resume!");
            missingKeywordsArea.setForeground(ACCENT_GREEN);
        } else {
            missingKeywordsArea.setText(formatKeywords(missing));
            missingKeywordsArea.setForeground(ACCENT_RED);
        }
        missingKeywordsArea.setCaretPosition(0); // Scroll to top
    }

    /**
     * Formats a Set of keywords into a comma-separated, line-wrapped string.
     * Groups keywords 6 per line for readability.
     *
     * @param keywords  Set of keyword strings
     * @return          Formatted display string
     */
    private String formatKeywords(Set<String> keywords) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String kw : keywords) {
            sb.append(kw);
            count++;
            // Add comma unless it's the last word
            if (count < keywords.size()) sb.append(",  ");
            // Line break every 6 keywords
            if (count % 6 == 0 && count < keywords.size()) sb.append("\n");
        }
        return sb.toString();
    }

    // ---- Action: Clear All ------------------------------------

    private void clearAll() {
        // Reset text areas
        resumeTextArea.setText("");
        resumeTextArea.setForeground(TEXT_PRIMARY);

        jobDescTextArea.setText("");
        jobDescTextArea.setForeground(TEXT_PRIMARY);

        // Reset result components
        scoreLabel.setText("— %");
        scoreLabel.setForeground(ACCENT_BLUE);
        matchLabelDisplay.setText("Run an analysis to see results");
        matchLabelDisplay.setForeground(TEXT_MUTED);
        statsLabel.setText("");

        matchedKeywordsArea.setText("Keywords will appear here after analysis...");
        matchedKeywordsArea.setForeground(TEXT_MUTED);

        missingKeywordsArea.setText("Keywords will appear here after analysis...");
        missingKeywordsArea.setForeground(TEXT_MUTED);
    }
}
