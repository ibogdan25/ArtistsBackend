package xmlparsers;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private List<String> subcategories;

    @XmlElementWrapper(name = "subcategories")
    @XmlElement(name = "subcategory")
    public List<String> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<String> subcategoryList) {
        this.subcategories = subcategoryList;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
