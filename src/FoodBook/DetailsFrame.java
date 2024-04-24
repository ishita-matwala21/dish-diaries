package FoodBook;

import javax.swing.*;

public class DetailsFrame extends JFrame {
    private JPanel MainPanel;
    private JLabel nameValue;
    private JLabel nameLabel;
    private JLabel IngredientsLabel;
    private JLabel DescriptionLabel;
    private JLabel RatingLabel;
    private JLabel RatingValue;
    private JTextPane IngredientsValue;
    private JTextPane DescriptionValue;

    public DetailsFrame(Recipe recipe) {
        JFrame frame = new JFrame("Recipe Details");
        //frame.pack();
        setSize(450, 450);
        initComponents(recipe);
        add(MainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(Recipe recipe) {
        nameValue.setText(recipe.getName());
        IngredientsValue.setText(recipe.getIngredients());
        IngredientsValue.setEditable(false);
        DescriptionValue.setText(recipe.getDescription());
        DescriptionValue.setEditable(false);
        RatingValue.setText(String.valueOf(recipe.getRating()));
    }
}
