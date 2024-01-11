package main;

import main.domain.Report;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Report report = new Report();
        report.readDataFromFile("purchases.csv");
        System.out.println("Total revenue: " + report.calculateTotalRevenue());
        System.out.println("Unique Customers: " + report.calculateUniqueCustomersCount());
        System.out.println("Most popular items(s): " + report.getMostPopularItems());
        System.out.println("Date(s) with highest revenue: " + report.getDatesWithHighestRevenue());
    }
}
