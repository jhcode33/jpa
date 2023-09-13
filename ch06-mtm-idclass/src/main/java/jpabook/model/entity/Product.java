package jpabook.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    private String id;

//    @OneToMany(mappedBy = "product")
//    private List<MemberProduct> memberProducts;

    private String name;

    public Product(){}

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
