package jpabook.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String name;

    @OneToMany(mappedBy = "member")
    private List<Order> member = new ArrayList<>();

    public Member(){}

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //== getter, setter ==//
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getListOrder() {
        return member;
    }

    public void setMember(List<Order> member) {
        this.member = member;
    }
}
