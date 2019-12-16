package xmlparsers;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Categories {
    private List<Category> category;

    public Categories() {

    }

    @XmlElement(name = "category")
    public List<Category> getCategories() {
        return category;
    }

    public void setCategories(List<Category> categories) {
        this.category = categories;
    }


}
