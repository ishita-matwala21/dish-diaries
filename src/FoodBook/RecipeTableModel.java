package FoodBook;

import FoodBook.db.DatabaseManager;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeTableModel extends AbstractTableModel {
    final List<Recipe> recipes;
    private final List<Recipe> originalRecipes;
    private final String[] columnNames = {"Serial No", "Name", "Ingredients", "Rating"};

    public RecipeTableModel() {
        recipes = new ArrayList<>();
        originalRecipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        originalRecipes.add(recipe);
        fireTableRowsInserted(recipes.size() - 1, recipes.size() - 1);
    }

    @Override
    public int getRowCount() {
        return recipes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Recipe recipe = recipes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> recipe.getSerialNo();
            case 1 -> recipe.getName();
            case 2 -> recipe.getIngredients();
            case 3 -> recipe.getRating();
            default -> null;
        };
    }

    // Method to filter recipes by name - binary search
    public void filterByName(String searchTerm) {
        recipes.clear();
        for (Recipe recipe : originalRecipes) {
            if (recipe.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                recipes.add(recipe);
            }
        }
        fireTableDataChanged();
    }

    public void clearSearch(){
        recipes.clear();
        try {
            List<Recipe> allRecipes = DatabaseManager.getAllRecipes();
            recipes.addAll(allRecipes);
        } catch (SQLException e){
            e.printStackTrace();
        }
        fireTableDataChanged();
    }

    public Recipe getRecipeAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < recipes.size()){
            return recipes.get(rowIndex);
        }
        return null;
    }

    //bubble sort
    public void sortByRating() {
        int n = recipes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (recipes.get(j).getRating() < recipes.get(j + 1).getRating()) {
                    Recipe temp = recipes.get(j);
                    recipes.set(j, recipes.get(j + 1));
                    recipes.set(j + 1, temp);
                }
            }
        }
        fireTableDataChanged();
    }

}
