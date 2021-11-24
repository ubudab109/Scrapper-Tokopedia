package BackendEngineer.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@JsonPropertyOrder({
        "product_name",
        "desc",
        "image",
        "price",
        "rating",
        "store",
    })
public class Product {
    public static final String SEPARATOR = ",";
    public static final String DOUBLE_QUOTES = "\"";

    @JsonIgnore
    private String name;
    private String desc;
    private String image;
    private String price;
    private Double rating;
    private String store;

    public String toString() {
        return name + SEPARATOR + DOUBLE_QUOTES + desc + DOUBLE_QUOTES + SEPARATOR + image + SEPARATOR + price + SEPARATOR + rating + "/5" +  SEPARATOR + store;
    }

    public abstract static class ProductFormat {
        @JsonProperty("Name")
        abstract String getName();

        @JsonProperty("Desc")
        abstract String getDesc();

        @JsonProperty("Image")
        abstract String getImage();

        @JsonProperty("Price")
        abstract String getPrice();

        @JsonProperty("Rating")
        abstract String getRating();

        @JsonProperty("Store")
        abstract String getStore();
    }
}
