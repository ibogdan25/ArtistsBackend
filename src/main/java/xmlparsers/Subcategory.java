package xmlparsers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "subcategory")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Subcategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String photoUrl;


    public Subcategory(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public Subcategory(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name){ this.name = name; }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl){this.photoUrl = photoUrl;}
}