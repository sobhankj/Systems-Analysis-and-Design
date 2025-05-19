package org.example;

//////////////////////////////////////////////////////////////////////////// step 3
// public class Main {
//     public static void main(String[] args) {

//         Date date = new Date(29, 12, 1402);
//         Date nextDay = date.nextDay();

//         System.out.println("Today: " + date);
//         System.out.println("Tomorrow: " + nextDay);
//     }
// }
/////////////////////////////////////////////////////////////////////////////



//////////////////////////////////////////////////////////////////////////// step 4
// import java.util.Arrays;
// public class Main {
//     public static void main(String[] args) {
//         Date[] dates = {
//             new Date(18, 11, 1402),
//             new Date(1, 1, 1400),
//             new Date(20, 3, 1401),
//             new Date(5, 5, 1402)
//         };
//         Arrays.sort(dates);
//         for (Date date : dates) {
//             System.out.println(date);
//         }
//     }
// }
////////////////////////////////////////////////////////////////////////////



//////////////////////////////////////////////////////////////////////////// step 7
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "D:\\A0\\src\\main\\java\\org\\resources\\input.csv";
        Map<String, Actor> actors = new HashMap<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] parts;
            while ((parts = csvReader.readNext()) != null) {
                if (parts.length != 8) {
                    System.err.println("Invalid CSV format: " + String.join(",", parts));
                    continue;
                }

                String actorName = parts[0];
                String movieName = parts[1];
                int startDay = Integer.parseInt(parts[2]);
                int startMonth = Integer.parseInt(parts[3]);
                int startYear = Integer.parseInt(parts[4]);
                int endDay = Integer.parseInt(parts[5]);
                int endMonth = Integer.parseInt(parts[6]);
                int endYear = Integer.parseInt(parts[7]);

                Date startDate = new Date(startDay, startMonth, startYear);
                Date endDate = new Date(endDay, endMonth, endYear);
                Starring role = new Starring(movieName, startDate, endDate);

                actors.putIfAbsent(actorName, new Actor(actorName));
                actors.get(actorName).addRole(role);
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        String targetActor = "James Stewart";
        if (actors.containsKey(targetActor)) {
            int totalDays = actors.get(targetActor).totalDaysActing();
            System.out.println(targetActor + " has acted for " + totalDays + " days.");
        } else {
            System.out.println(targetActor + " not found in the dataset.");
        }
    }
}
///////////////////////////////////////////////////////////////////////////