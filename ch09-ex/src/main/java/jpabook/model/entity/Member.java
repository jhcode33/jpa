package jpabook.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by holyeye on 2014. 3. 11..
 */

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private int age;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name ="city", column = @Column(name = "work_city")),
            @AttributeOverride(name ="street", column = @Column(name = "work_street")),
            @AttributeOverride(name ="zipcode", column = @Column(name = "work_zipcode"))
    })
    private Address workAddress;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name ="city", column = @Column(name = "home_city")),
            @AttributeOverride(name ="street", column = @Column(name = "home_street")),
            @AttributeOverride(name ="zipcode", column = @Column(name = "home_zipcode"))
    })
    private Address homeAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
