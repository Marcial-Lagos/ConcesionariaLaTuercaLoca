package cl.pixelandbean.model;

public class Product {
    private final int id;
    private String name;
    private String category;
    private String type;
    private double price;
    private boolean active;

    public Product(int id, String name, String category, String type, double price, boolean active) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0");
        }
        this.id = id;
        this.name = name.trim();
        this.category = category == null ? "" : category.trim();
        this.type = type == null ? "" : type.trim();
        this.price = price;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
