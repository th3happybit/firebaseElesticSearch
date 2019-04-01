
@IgnoreExtraProperties
public class ProductSource {

    @SerializedName("_source")
    @Expose
    private Product Product;


    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product Product) {
        this.Product = Product;
    }
}
