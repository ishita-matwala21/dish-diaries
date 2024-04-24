package FoodBook;
public class Recipe {
    private int serialNo;
    private String name;
    private String ingredients;
    private String description;
    private int rating;
    public Recipe(int serialNo, String name, String description, String ingredients, int rating) {
        this.serialNo = serialNo;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.rating = rating;
    }
    public int getSerialNo() { return serialNo; }
    public void setSerialNo(int serialNo) { this.serialNo = serialNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

}
