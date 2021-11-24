package BackendEngineer.Services;

import BackendEngineer.Entity.Product;
import BackendEngineer.Helpers.Scrapper;
import BackendEngineer.Helpers.ScrapperException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.annotations.VisibleForTesting;

import java.io.File;
import java.util.List;

public class ScrapperServices {

    private static final String UNDERSCORE = "_";
    private static final String FILENAME = "Product";
    private static final String FORMAT = ".csv";

    private Scrapper scrapper;

    public ScrapperServices() {
        scrapper = new Scrapper();
    }

    @VisibleForTesting
    public ScrapperServices(Scrapper scrapper) {
        this.scrapper = scrapper;
    }

    public String downloadDataToCsv(int count) throws ScrapperException {
        String filename = FILENAME + UNDERSCORE + "DATA" + UNDERSCORE + System.currentTimeMillis() + FORMAT;
        List<Product> products = scrapper.productList(count);

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        csvMapper.addMixIn(Product.class, Product.ProductFormat.class);
        CsvSchema csvSchema = csvMapper.schemaFor(Product.class).withHeader();

        try {
            File file = new File(filename);
            csvMapper.writer(csvSchema).writeValue(file, products);
            return filename;
        } catch (Exception err) {
            throw new ScrapperException(err.getMessage());
        }
    }
}
