package jpa.model.entity;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String name;

    @ManyToOne // owner
    @JoinColumn(name = "TEAM_ID") // FK key
    private Team team; // team의 참조를 보관

    public Member(){};

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getter, Setter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(Team team) {
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }
}
