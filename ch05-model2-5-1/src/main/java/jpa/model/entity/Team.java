package jpa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @Column(name = "TEAM_ID")
    private String id;
    private String name;

    @OneToMany(mappedBy = "team") // not owner, use mappedBy, team is member's team field
    public List<Member> members = new ArrayList<>();  // 제네릭으로 타입 정보를 알 수 있다

    public Team(){};

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
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
}
