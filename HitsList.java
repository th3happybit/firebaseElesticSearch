

@IgnoreExtraProperties
public class HitsList {

    @SerializedName("hits")
    @Expose
    private List<ProductSource> ProductIndex;


    public List<ProductSource> getProductIndex() {
        return ProductIndex;
    }

    public void setProductIndex(List<ProductSource> ProductIndex) {
        this.ProductIndex = ProductIndex;
    }
}