package Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"pages"})
public class Data {
    @JsonProperty("pages")
    private List<Pages> pages;

    @JsonProperty("pages")
    public List<Pages> getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(List<Pages> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return pages.toString();
    }
}