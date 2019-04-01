

public class Product {

    private String product_id;
    private String name;
    private String desc;
    private double price;
    private double rating;
    private String type;
    private String wilaya;
    private String store_id

    public Product(String product_id, String name, String desc, double price,  double rating,
                String type, String wilaya, String store_id) {
        this.product_id = product_id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.rating = rating;
        this.type = type;
        this.wilaya = wilaya;
        this.store_id = store_id;
    }

    public Product() {

    }

    public String getproduct_id() {
        return product_id;
    }

    public void setproduct_id(String product_id) {
        this.product_id = product_id;
    }
   
}
