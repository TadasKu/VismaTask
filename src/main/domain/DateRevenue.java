package main.domain;

import java.util.Date;

public class DateRevenue {

    private Date transactionDate;
    private Double revenue;

    public DateRevenue(Date transactionDate, Double revenue) {
        this.transactionDate = transactionDate;
        this.revenue = revenue;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Double getRevenue() {
        return revenue;
    }
}
