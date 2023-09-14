package jpabook.model.entityCascade;

import javax.persistence.*;

@Entity
public class MemberCascade {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    //@ManyToOne : default EAGER
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private TeamCascade teamCascade;

    public MemberCascade(){}

    public MemberCascade(String name) {
        this.name = name;
    }

    //== getter, setter ==//
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamCascade getTeam() {
        return teamCascade;
    }

    public void setTeam(TeamCascade teamCascade) {
        this.teamCascade = teamCascade;
    }
}
