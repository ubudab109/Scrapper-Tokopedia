package BackendEngineer;

import BackendEngineer.Helpers.ScrapperException;
import BackendEngineer.Services.ScrapperServices;
import org.apache.commons.lang3.math.NumberUtils;


public class App {
    public static void main(String[] args) {
        int count = 100;
        if (args.length > 1 && NumberUtils.isCreatable(args[1])) {
            count = NumberUtils.createInteger(args[1]);
        }
        System.out.println("Extracting "+ count + " " + "Mobile Phones Products...");
        ScrapperServices scrapperServices = new ScrapperServices();
        try {
            String csv = scrapperServices.downloadDataToCsv(count);
            System.out.println("Extracting Completed. File was saved to " + csv);
        } catch (ScrapperException err) {
            System.err.println(err.getMessage());
        }

    }
}
