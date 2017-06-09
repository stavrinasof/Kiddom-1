package kiddom.model;

/**
 * Created by eleni on 02-Jun-17.
**/

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subcategories", schema = "mydb")
public class SubcategoriesEntity {
    private String name;
    private CategoriesEntity categoriesByCatId;

    @Transient // means "not a DB field"
    private Integer remove; // boolean flag
    public Integer getRemove()
    {
        return remove;
    }
    public void setRemove(Integer set){this.remove=set;}


    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcategoriesEntity that = (SubcategoriesEntity) o;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "category_name", referencedColumnName = "name")
    public CategoriesEntity getCategoriesByCatId() {
        return categoriesByCatId;
    }

    public void setCategoriesByCatId(CategoriesEntity categoriesByCatId) {
        this.categoriesByCatId = categoriesByCatId;
    }
}