import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HealthCareSpending {


    public static final int MAX_SIZE = 10;
    private static NumberFormat formatter;
    private static String healthFile;

    /**
     * Reads the parsed healthcare spending file and ouptuts top spending states and categories
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        //formatter for currency
        formatter = NumberFormat.getCurrencyInstance();
        healthFile = "Healthcare_Spending_Per_Capita.csv";


        //parse each csv file to a spending cell
        CsvToBean<Spending> bean = new CsvToBean<Spending>();
        //map column names from csv to fields in spending class
        Map<String, String> columnMapping = new HashMap<String, String>();
        columnMapping.put("Code", "code");
        columnMapping.put("Item", "item");
        columnMapping.put("Group", "group");
        columnMapping.put("Region_Number", "region_number");
        columnMapping.put("Region_Name", "region_name");
        columnMapping.put("State_Name", "state_name");
        columnMapping.put("Y2000", "y2000");
        columnMapping.put("Y2009", "y2009");
        columnMapping.put("Average_Annual_Percent_Growth", "average_annual_percent_growth");

        //when parsing csv file map columns to fields in Spending class
        HeaderColumnNameTranslateMappingStrategy<Spending> strategy = new HeaderColumnNameTranslateMappingStrategy<Spending>();
        strategy.setType(Spending.class);
        strategy.setColumnMapping(columnMapping);

        List<Spending> list = null;

        CSVReader reader = new CSVReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(healthFile)));
        list = bean.parse(strategy, reader);

        //convert to Java8 stream
        //filter cells that don't have state
        //group by state name and sum Y2009 expenditures
        //get stream of grouped result
        //sort by total sales in reverse order
        //limit to 10 results
        Stream<Map.Entry<String, Double>> topStates = list.stream()
                .filter(s -> !(s.getState_name().isEmpty()))
                .collect(
                        Collectors.groupingBy(
                                Spending::getState_name,
                                Collectors.summingDouble(Spending::getY2009))
                )
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(MAX_SIZE);


        System.out.println("Top "+MAX_SIZE +" states with the highest healthcare spending");
        System.out.println();

        //print each of the states
        topStates.forEachOrdered(s ->
                        System.out.printf("%s: %s\n",
                                s.getKey(), formatter.format(s.getValue()))
        );


        //calculate spending by categories
        //group by ItemCategory and calculate Y2009 sales
        //get stream
        //sort in reverse order of sales
        Stream<Map.Entry<String, Double>> topCategories = list.stream()
                .collect(
                        Collectors.groupingBy(
                            Spending::getItem, Collectors.summingDouble(Spending::getY2009)))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));


        System.out.println();
        System.out.println("healthcare spending by category");
        System.out.println();
        topCategories.forEachOrdered(s ->
                        System.out.printf("%s: %s\n",
                                s.getKey(), formatter.format(s.getValue()))
        );


    }
}
