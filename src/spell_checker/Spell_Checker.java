package spell_checker;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Spell_Checker {

    /**
     * FILE READING AND MAKING OF DICTIONARY
     *
     */
    public static HashSet<String> FileReader(String filePath) {
        HashSet<String> set = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            File file = new File(filePath);

            String line;

            Iterator<String> itr = set.iterator();
            while ((line = br.readLine()) != null) {
                set.add(line);
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
        return set;
    }

    /**
     * THIS IS WHERE THE NUMBER OF EDITED DISTANCE
     *
     * @param s s - the correct word
     * @param user_input t - the word to be edited
     * @return the number of edited distance
     */
    public static int editDistance(String s, String user_input) {
        int m = s.length();
        int n = user_input.length();
        int[][] d = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            d[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                if (s.charAt(i - 1) == user_input.charAt(j - 1)) {
                    d[i][j] = d[i - 1][j - 1];
                } else {
                    d[i][j] = min((d[i - 1][j] + 1), (d[i][j - 1] + 1), (d[i - 1][j - 1] + 1));
                }
            }
        }
        return (d[m][n]);
    }

    public static int min(int a, int b, int c) {
        return (Math.min(Math.min(a, b), c));

    }

    /**
     * CHECK THE INPUT WORD IF IT IS CORRECT AND SUGGESTS WORDS IF INCORRECT
     */
    public static void SpellChckandSuggest(String user_input) {

        Set<String> dict = FileReader("C:\\Users\\herreratd\\Documents\\NetBeansProjects\\SpellChecker\\src\\spell_checker\\ANIMALS.txt");
        ArrayList<String> suggestions = new ArrayList<>();
        if (dict.contains(user_input)) {
            System.out.println(user_input + " is spelled correctly");
        } else {
            System.out.println("suggestions: ");
            for (String perWord : dict) {
                int distance = editDistance(perWord, user_input);

                if (distance <= 2) {
                    suggestions.add(perWord);

                }
            }
        }

        for (String suggestionPerWord : suggestions) {

            System.out.println(suggestionPerWord);

        }
    }

    public static void main(String[] args) {

        //GUI 
        JFrame frame = new JFrame("Animal Spell Checker");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel lbl = new JLabel("<html><body><p>Enter <font color='#ff8c1a'>ANIMAL</font> name:</p></body></html>");
        JTextField fld = new JTextField();
        JButton btn = new JButton("Check");

        //ACTION LISTENERS
         fld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    btn.doClick();
            }
        });
        btn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    btn.doClick();
            }
        });
        btn.addActionListener((ActionEvent e) -> {
            SpellChckandSuggest(fld.getText());
            Set<String> dict = FileReader("C:\\Users\\herreratd\\Documents\\NetBeansProjects\\SpellChecker\\src\\spell_checker\\ANIMALS.txt");
            ArrayList<String> suggestions = new ArrayList<>();
            if (dict.contains(fld.getText())) {
                JOptionPane.showMessageDialog(panel2, fld.getText() + " is spelled correctly.");
            } else {

                String str = "";

                for (String perWord : dict) {

                    int distance = editDistance(perWord, fld.getText());

                    if (distance <= 2) {
                        suggestions.add(perWord);
                        str += perWord + "<br>";

                    }
                }
                
                JOptionPane.showMessageDialog(panel2,"<html><body>Suggested <font color='#ff8c1a'>ANIMAL</font> name:<br>" + str + "</body></html>");
            }
        });

        panel1.add(lbl);
        panel2.add(btn);
        frame.add(panel1);
        frame.add(fld);
        frame.add(panel2);
        frame.setSize(300, 125);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
