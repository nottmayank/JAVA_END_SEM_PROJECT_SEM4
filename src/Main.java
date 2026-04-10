import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
   
        SwingUtilities.invokeLater(() -> {
            ResumeAnalyzerUI ui = new ResumeAnalyzerUI();
            ui.setVisible(true);
        });
    }
}
