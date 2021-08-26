package BLL;

public class Product {
    private int id;
    private String title;
    private float coast;

    public Product(int id, String title, float coast) {
        this.id = id;
        this.title = title;
        this.coast = coast;
    }

    @Override
    public String toString() {
        return String.format("id-[%s], %s, coast %s", id, title, coast + " RUB");
    }
}
