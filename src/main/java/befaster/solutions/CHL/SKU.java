package befaster.solutions.CHL;

public class SKU {
    private String ietm;
    private Integer qty;

    public SKU(String ietm, Integer qty) {
        this.ietm = ietm;
        this.qty = qty;
    }

    public String getIetm() {
        return ietm;
    }

    public Integer getQty() {
        return qty;
    }
}
