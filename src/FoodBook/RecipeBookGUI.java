package FoodBook;

import FoodBook.db.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class RecipeBookGUI extends Component {
    JPanel MainPanel;
    private JPanel InputPanel;
    private JPanel TablePanel;
    private JPanel ButtonPanel;
    private JLabel S_no;
    private JTextField textField_sNo;
    private JTextField textField_name;
    private JTextField textField_ingredients;
    private JTable table;
    private JButton Add_button;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchRecipeButton;
    private JLabel Namelabel;
    private JLabel Ingredientslabel;
    private JLabel Descriptionlabel;
    private JTextArea textArea_description;
    private JButton clearSearchButton;
    private JButton sortButton;
    private JRadioButton a1RadioButton;
    private JRadioButton a2RadioButton;
    private JRadioButton a5RadioButton;
    private JRadioButton a3RadioButton;
    private JRadioButton a4RadioButton;
    private JPanel inputButtonPanel;

    private final RecipeTableModel tableModel;
    public RecipeBookGUI() {
        tableModel = new RecipeTableModel();
        table.setModel(tableModel);
        loadRecipes();

        ButtonGroup ratingbuttonsgroup = new ButtonGroup();
        ratingbuttonsgroup.add(a1RadioButton);
        ratingbuttonsgroup.add(a2RadioButton);
        ratingbuttonsgroup.add(a3RadioButton);
        ratingbuttonsgroup.add(a4RadioButton);
        ratingbuttonsgroup.add(a5RadioButton);

        final int[] rating = {1};

        a1RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(a1RadioButton.isSelected()) rating[0] = 1;
            }
        });
        a2RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(a2RadioButton.isSelected()) rating[0] = 2;
            }
        });
        a3RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(a3RadioButton.isSelected()) rating[0] = 3;
            }
        });
        a4RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(a4RadioButton.isSelected()) rating[0] = 4;
            }
        });
        a5RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(a5RadioButton.isSelected()) rating[0] = 5;
            }
        });

        Add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int serialNo = Integer.parseInt(textField_sNo.getText());
                String name = textField_name.getText();
                String description = textArea_description.getText();
                String ingredients = textField_ingredients.getText();
                Recipe newRecipe = new Recipe(serialNo, name, description, ingredients, rating[0]);

                try {
                    DatabaseManager.addRecipe(newRecipe);

                    refreshTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to add recipe to the database.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowIndex = table.getSelectedRow();
                if (rowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a recipe to update.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int serialNo = Integer.parseInt(textField_sNo.getText());
                String name = textField_name.getText();
                String description = textArea_description.getText();
                String ingredients = textField_ingredients.getText();

                Recipe updatedRecipe = new Recipe(serialNo, name, description, ingredients, rating[0]);

                try {
                    DatabaseManager.updateRecipe(updatedRecipe);
                    refreshTable(); // Refresh the table after update
                    JOptionPane.showMessageDialog(null, "Recipe updated successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to update recipe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Recipe selectedRecipe = tableModel.getRecipeAt(row);
                    if (selectedRecipe != null) {
                        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this recipe?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            try {
                                DatabaseManager.deleteRecipe(selectedRecipe.getSerialNo());
                                refreshTable();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Failed to delete recipe from the database.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Check for double-click
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        Recipe selectedRecipe = tableModel.getRecipeAt(row);
                        if (selectedRecipe != null) {
                            SwingUtilities.invokeLater(() -> {
                                new DetailsFrame(selectedRecipe);
                            });
                        }
                    }
                }
            }
        });

        searchRecipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = JOptionPane.showInputDialog(null, "Enter the recipe name to search:", "Search Recipe", JOptionPane.QUESTION_MESSAGE);
                if (searchTerm != null && !searchTerm.isEmpty()) {
                    tableModel.filterByName(searchTerm);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid search term.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clearSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.clearSearch();
            }
        });

        //add sorting function
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.sortByRating();
            }
        });
    }
    private void loadRecipes() {
        try {
            List<Recipe> recipes = DatabaseManager.getAllRecipes();
            for (Recipe recipe : recipes) {
                tableModel.addRecipe(recipe);
            }
            tableModel.fireTableDataChanged();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load recipes from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void refreshTable() {
        try {
            List<Recipe> recipes = DatabaseManager.getAllRecipes();
            tableModel.recipes.clear();
            for (Recipe recipe : recipes) {
                tableModel.addRecipe(recipe);
            }
            tableModel.fireTableDataChanged();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to refresh table data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

