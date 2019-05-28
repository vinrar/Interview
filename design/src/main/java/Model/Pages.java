package Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import sun.jvm.hotspot.debugger.Page;

@JsonPropertyOrder({"Pages"})
public class Pages {
    @JsonProperty("Pages")
    private Page page;

    @JsonProperty("Pages")
    public Page getPage() {
        return page;
    }

    @JsonProperty("Pages")
    public void setPage(Page page) {
        this.page = page;
    }
}