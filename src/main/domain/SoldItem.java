package main.domain;

public class SoldItem {

    private String itemId;
    private int itemQuantity;

    public SoldItem(String itemId, int itemQuantity) {
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
    }

    public String getItemId() {
        return itemId;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }
}
