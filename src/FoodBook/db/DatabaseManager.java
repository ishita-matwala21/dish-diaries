package FoodBook.db;

import FoodBook.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/practicejdbc", "root", "Ish@nMysql1");
        }
        return connection;
    }

    public static void addRecipe(Recipe recipe) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO recipes (serial_no, name, description, ingredients, rating) VALUES (?, ?, ?, ?, ?)")) {
            statement.setInt(1, recipe.getSerialNo());
            statement.setString(2, recipe.getName());
            statement.setString(3, recipe.getDescription());
            statement.setString(4, recipe.getIngredients());
            statement.setInt(5, recipe.getRating());
            statement.executeUpdate();
        }
    }
    public static List<Recipe> getAllRecipes() throws SQLException {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM recipes")) {
            while (resultSet.next()) {
                int serialNo = resultSet.getInt("serial_no");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String ingredients = resultSet.getString("ingredients");
                int rating = resultSet.getInt("rating");
                Recipe recipe = new Recipe(serialNo, name, description, ingredients, rating);
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    public static void updateRecipe(Recipe recipe) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement("UPDATE recipes SET name = ?, description = ?, ingredients = ?, rating = ? WHERE serial_no = ?")) {
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getDescription());
            statement.setString(3, recipe.getIngredients());
            statement.setInt(4,recipe.getRating());
            statement.setInt(5, recipe.getSerialNo());
            statement.executeUpdate();
        }
    }

    public static void deleteRecipe(int serialNo) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement("DELETE FROM recipes WHERE serial_no = ?")) {
            statement.setInt(1, serialNo);
            statement.executeUpdate();
        }
    }
}
