
package ads_assg;
package ads_ki_ass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
//from   ww w. j a v  a  2s  .c  om
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.Position;
import javax.swing.text.View;

public class Ads_assg {

    static MyHighlightPainter myHighlightPainter = new MyHighlightPainter(
            Color.YELLOW);

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Ads_assg a = new Ads_assg();
        JFrame f = new JFrame();

        ArrayList<Integer> lastIndex = new ArrayList<Integer>();
        JPanel panel = new JPanel();
        JTextPane textPane = new JTextPane();
        JTextField tf = new JTextField("is");
        JButton button = new JButton("ENTER");
        button.setBounds(250, 1, 50, 20);
        JTextField t = new JTextField(17);
        String word = "";
        String s = "", l;
        s = "Sarah: Hello Jason, how are you, it's been a long time since we last met?\n"
                + "Jason: Oh, hi Sarah I'm have got a new job now and is going great. How about you?\n"
                + "Sarah: Not too bad.\n"
                + "Jason: How often do you eat at this cafe?\n"
                + "Sarah: This is my first time my friends kept telling me the food was great, so tonight I decided to try it. What have you been up to?\n"
                + "Jason: I have been so busy with my new job that I have not had the time to do much else, but otherwise, me and the family are all fine.\n"
                + "Sarah: Well, I hope you and your family have a lovely meal.\n"
                + "Jason: Yes you too.";
        textPane.setText(s);
        textPane.setFont(new Font("Serif", Font.PLAIN, 17));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<Integer> lastIndex1;
                final String i = t.getText().toString();
                final String p = textPane.getText().toString();
                lastIndex1 = a.kmp(i, p);
                int j = 0;
                int end;
                while (j < lastIndex1.size()) {
                    end = lastIndex1.get(j) + i.length();
                    System.out.println(end);
                    Highlighter hilite = textPane.getHighlighter();
                    try {
                        hilite.addHighlight(lastIndex1.get(j), end, myHighlightPainter);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Ads_assg.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    j = j + 1;
                }

            }
        });
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Enter word, then press ENTER key: "), "West");
        panel.add(tf, "Center");
        panel.add(button, "East");
        panel.add(t, "Center");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(panel, "South");
        f.add(new JScrollPane(textPane), "Center");
        f.setSize(640, 480);
        f.setVisible(true);

    }

    public int[] compute_prefix_function(String p) {
        int m = p.length();
        char[] pattern = new char[m + 1];
        pattern[0] = '#';
        for (int i = 1; i < pattern.length; i++) {
            pattern[i] = p.charAt(i - 1);
        }
        int[] pie = new int[m + 1];
        int k = 0;
        pie[0] = -1;
        pie[1] = 0;
        for (int q = 2; q <= m; q++) {
            while (k > 0 && pattern[k + 1] != pattern[q]) {
                k = pie[k];
            }
            if (pattern[k + 1] == pattern[q]) {
                k = k + 1;
            }
            pie[q] = k;
        }
        for (int i = 0; i < pie.length; i++) {
            System.out.print(pie[i] + "|");
        }
        System.out.println("");
        return pie;
    }

    public ArrayList<Integer> kmp(String word, String s) {

        ArrayList<Integer> lastIndex = new ArrayList<Integer>();
        s = s.toLowerCase();
        word = word.toLowerCase();
        int n = s.length();
        int m = word.length();
        char[] text = new char[n + 1];
        char[] pattern = new char[m + 1];
        pattern[0] = '#';
        for (int i = 1; i < pattern.length; i++) {
            pattern[i] = word.charAt(i - 1);
        }

        text[0] = '#';

        for (int q = 1; q < n + 1; q++) {
            text[q] = s.charAt(q - 1);
        }

        int[] pie = compute_prefix_function(word);
        int q = 0;
        for (int i = 1; i < n; i++) {
            while (q > 0 && pattern[q + 1] != text[i]) {
                q = pie[q];
            }
            if (pattern[q + 1] == text[i]) {
                q = q + 1;
            }
            if (q == m) {
                System.out.println("pattern found at ::" + (i - m));
                lastIndex.add(i - m);
                q = pie[q];
            }

        }
        return lastIndex;
    }
}

class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

    public MyHighlightPainter(Color color) {
        super(color);
    }
}

