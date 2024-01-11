package main.domain;

import main.config.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Report {
    private List<SaleTransaction> transactions = new ArrayList<>();


    /***
     * Get most popular items
     * @return most popular items
     */
    public String getMostPopularItems() {
        Map<String, Integer> itemCountMap = new HashMap<>();

        for (SaleTransaction transaction : transactions) {
            if (itemCountMap.containsKey(transaction.getItemId())) {
                itemCountMap.put(transaction.getItemId(), itemCountMap.get(transaction.getItemId()) + transaction.getItemQuantity());
            } else {
                itemCountMap.put(transaction.getItemId(), transaction.getItemQuantity());
            }
        }

        List<SoldItem> soldItems = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : itemCountMap.entrySet()) {
            SoldItem soldItem = new SoldItem(entry.getKey(), entry.getValue());
            soldItems.add(soldItem);
        }

        soldItems.sort(Comparator.comparing(SoldItem::getItemQuantity).reversed().thenComparing(SoldItem::getItemId));

        Integer maxItemCount = soldItems.get(0).getItemQuantity();

        List<String> mostPopularItems = new ArrayList<>();

        for (SoldItem item : soldItems) {
            if (item.getItemQuantity() == maxItemCount) {
                mostPopularItems.add(item.getItemId());
            } else {
                break;
            }
        }

        return convertItemListToString(mostPopularItems);
    }

    /***
     * Get dates with highest revenue
     * @return dates with highest revenue
     */
    public String getDatesWithHighestRevenue() {
        Map<Date, BigDecimal> itemCountMap = new HashMap<>();

        for (SaleTransaction transaction : transactions) {
            if (itemCountMap.containsKey(transaction.getTransactionDate())) {
                itemCountMap.put(transaction.getTransactionDate(), itemCountMap.get(transaction.getTransactionDate()).add(BigDecimal.valueOf(transaction.getItemQuantity() * transaction.getItemPrice())));
            } else {
                itemCountMap.put(transaction.getTransactionDate(), BigDecimal.valueOf(transaction.getItemQuantity() * transaction.getItemPrice()));
            }
        }
        List<DateRevenue> datesWithRevenue = new ArrayList<>();

        for (Map.Entry<Date, BigDecimal> entry : itemCountMap.entrySet()) {
            DateRevenue dateRevenue = new DateRevenue(entry.getKey(), entry.getValue().doubleValue());
            datesWithRevenue.add(dateRevenue);
        }

        datesWithRevenue.sort(Comparator.comparing(DateRevenue::getRevenue).reversed().thenComparing(DateRevenue::getTransactionDate));
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

        BigDecimal highestRevenue = BigDecimal.valueOf(datesWithRevenue.get(0).getRevenue());

        List<String> datesWithHighestRevenue = new ArrayList<>();

        for (DateRevenue item : datesWithRevenue) {
            if (BigDecimal.valueOf(item.getRevenue()).equals(highestRevenue)) {
                datesWithHighestRevenue.add(dateFormat.format(item.getTransactionDate()));
            } else {
                break;
            }
        }

        return convertItemListToString(datesWithHighestRevenue);
    }

    /***
     * Count unique customers
     * @return unique customers count
     */
    public int calculateUniqueCustomersCount() {
        return (int) transactions.stream().map(SaleTransaction::getCustomerId).distinct().count();
    }

    /***
     * Calculate total revenue
     * @return calculated total revenue
     */
    public Double calculateTotalRevenue() {
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (SaleTransaction transaction : transactions) {
            totalRevenue = totalRevenue.add(BigDecimal.valueOf(transaction.getItemPrice()).multiply(BigDecimal.valueOf(transaction.getItemQuantity())));
        }

        return totalRevenue.doubleValue();
    }

    /***
     * Read data from file and add object to object list
     * @param filename
     */
    public void readDataFromFile(String filename) throws FileNotFoundException {
        List<SaleTransaction> records = new ArrayList<>();

        URL dataFileUrl = getClass().getResource(Constants.PATH_TO_DATA_FILE + filename);
        if (dataFileUrl == null) {
            throw new FileNotFoundException("File not found: " + Constants.PATH_TO_DATA_FILE + filename);
        }

        try (Scanner scanner = new Scanner(new File(dataFileUrl.toURI()))) {
            boolean firstLine = true;
            while (scanner.hasNextLine()) {
                if (!firstLine) {
                    List<String> result = getRecordFromLine(scanner.nextLine());
                    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
                    records.add(new SaleTransaction(result.get(0), result.get(1), result.get(2), dateFormat.parse(result.get(3)), Double.parseDouble(result.get(4)), Integer.parseInt(result.get(5))));
                } else {
                    scanner.nextLine();
                    firstLine = false;
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        transactions.addAll(records);
    }


    /***
     * Split line and add values to String list
     * @param line
     * @return a list of split string values
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(Constants.DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    /***
     * Join list items into a single string
     * @param listToConvert
     * @return list items joined into a single line
     */
    private String convertItemListToString (List<String> listToConvert){
        StringJoiner stringJoiner = new StringJoiner(Constants.DELIMITER_TO_SEPARATE_VALUES);
        for (String listItem: listToConvert) {
            stringJoiner.add(listItem);
        }
        return stringJoiner.toString();
    }
}

