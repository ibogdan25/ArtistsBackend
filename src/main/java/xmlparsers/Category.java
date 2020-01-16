package xmlparsers;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String photoUrl;

    private List<Subcategory> subcategories;


    @XmlElementWrapper(name = "subcategories")
    @XmlElement(name = "subcategory")
    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Subcategory> subcategoryList) {
        this.subcategories = subcategoryList;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public String getPhotoUrl(){return photoUrl; }

    public void setPhotoUrl(String photoUrl) {this.photoUrl = photoUrl;}

    public void setName(String name) {
        this.name = name;
    }
}
