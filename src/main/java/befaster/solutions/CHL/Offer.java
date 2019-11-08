package befaster.solutions.CHL;

public   class Offer {
    private int qty;
    private String item;
    private Integer price;

    public Offer(int qty, String item, Integer price) {
        this.qty = qty;
        this.item = item;
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public String getItem() {
        return item;
    }

    public Integer getPrice() {
        return price;
    }
}
