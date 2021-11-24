package BackendEngineer.Helpers;

import BackendEngineer.Entity.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Scrapper {
    private static final String BASE_URL = "https://www.tokopedia.com/p/handphone-tablet/handphone";
    private static final String DETAIL_PRODUCT_URL = "https://ta.tokopedia.com/promo";
    private static final String PAGES = "?pages=";

    private static final String PRODUCT_LIST_PATH = "//div[@data-testid='lstCL2ProductList']/div";
    private static final String PRODUCT_DETAIL_LINK = "a[@data-testid='lnkProductContainer']";
    private static final String PRODUCT_NAME = "//h1[@data-testid='lblPDPDetailProductName']";
    private static final String PRODUCT_DESCRIPTION = "//*[@data-testid='lblPDPDescriptionProduk']";
    private static final String PRODUCT_IMAGE = "//*[@data-testid='PDPImageMain']//img";
    private static final String PRODUCT_PRICE = "//*[@data-testid='lblPDPDetailProductPrice']";
    private static final String PRODUCT_RATING = "//*[@data-testid='lblPDPDetailProductRatingNumber']";
    private static final String PRODUCT_STORE = "//*[@data-testid='llbPDPFooterShopName']//h2";


    private static final String ANCHOR_HREF = "href";
    private static final String IMG_SRC_ATTR = "src";
    private static final String AMP = "&";
    private static final String PARAM_R = "r=";

    public List<Product> productList(int count) throws ScrapperException {
        final DriverHelpers driverHelpers = new DriverHelpers();
        final List<Product> products = new ArrayList<>(count);
        final String baseUrl = BASE_URL;
        try {
            List<String> tabs = driverHelpers.preparingTwoTabs();
            for (int page = 1; products.size() < count; page++) {
                String url = baseUrl + PAGES + page;
                final List<WebElement> items = driverHelpers.getElementByScrolling(url,PRODUCT_LIST_PATH, tabs.get(0)); // switch tab

                for (WebElement item : items) {
                    String path = item.findElement(By.xpath(PRODUCT_DETAIL_LINK)).getAttribute(ANCHOR_HREF);
                    if(isDetailLink(path)) {
                        path = extractDetailProduk(path);
                    }
                    driverHelpers.getWebPage(path, tabs.get(1)); // switch to new tab
                    //removing overlay when first access
                    if (products.isEmpty()) {
                        System.out.println("Product Empty");
                    }

                    // trigerring lazy load
                    driverHelpers.setScrollSmall();
                    driverHelpers.waitOnElement(PRODUCT_STORE);

                    products.add(listProduct(driverHelpers));

                    if (products.size() == count) {
                        break;
                    }

                    driverHelpers.switchTab(tabs.get(0));

                }
            }
        } catch (Exception err) {
            throw new ScrapperException(err.getMessage());
        } finally {
            driverHelpers.quit();
        }
        return products;
    }
    private Product listProduct(DriverHelpers driverHelpers) {
        String name = driverHelpers.getText(PRODUCT_NAME);
        String desc = driverHelpers.getText(PRODUCT_DESCRIPTION);
        String image = driverHelpers.getImageSrc(PRODUCT_IMAGE, IMG_SRC_ATTR);
        String price = driverHelpers.getText(PRODUCT_PRICE);
        String store = driverHelpers.getText(PRODUCT_STORE);
        String rating = driverHelpers.getText(PRODUCT_RATING);

        return Product.builder()
                .name(name)
                .desc(desc)
                .image(image)
                .price(price)
                .store(store)
                .rating(rating == null || rating.isEmpty() ? 0.0 : Double.parseDouble(rating))
                .build();
    }
    private boolean isDetailLink(String path) {
        return path.contains(DETAIL_PRODUCT_URL);
    }

    private String extractDetailProduk(String path) throws IOException {
        return URLDecoder.decode(path.substring(path.indexOf(PARAM_R) + 2).split(AMP)[0], StandardCharsets.UTF_8.name());
    }
}
