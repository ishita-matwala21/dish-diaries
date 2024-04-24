package FoodBook;

import javax.swing.*;

public class RecipeBookMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Recipe Book");
        frame.setContentPane(new RecipeBookGUI().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
