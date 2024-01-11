package main.domain;

import java.util.Date;
import java.util.Objects;

public class SaleTransaction {

    private String transactionId;
    private String customerId;
    private String itemId;
    private Date transactionDate;
    private Double itemPrice;
    private Integer itemQuantity;

    public SaleTransaction(String transactionId, String customerId, String itemId, Date transactionDate, Double itemPrice, Integer itemQuantity) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.itemId = itemId;
        this.transactionDate = transactionDate;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleTransaction that = (SaleTransaction) o;
        return transactionId.equals(that.transactionId) &&
                customerId.equals(that.customerId) &&
                itemId.equals(that.itemId) &&
                transactionDate.equals(that.transactionDate) &&
                itemPrice.equals(that.itemPrice) &&
                itemQuantity.equals(that.itemQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, customerId, itemId, transactionDate, itemPrice, itemQuantity);
    }

    @Override
    public String toString() {
        return "SaleTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", transactionDate=" + transactionDate +
                ", itemPrice=" + itemPrice +
                ", itemQuantity=" + itemQuantity +
                '}';
    }
}
