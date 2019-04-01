
@IgnoreExtraProperties
public class HitsObject {

    @SerializedName("hits")
    @Expose
    private HitsList hits;

    public HitsList getHits() {
        return hits;
    }

    public void setHits(HitsList hits) {
        this.hits = hits;
    }
}