package jpabook.model.entityCascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeamCascade {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "teamCascade",
//            cascade = {CascadeType.PERSIST
//    )
//    @OneToMany(mappedBy = "teamCascade",
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
//    )
//    @OneToMany(mappedBy = "teamCascade",
//            cascade = {CascadeType.PERSIST},
//            orphanRemoval = true // 고아 객체를 삭제
//    )
    @OneToMany(mappedBy = "teamCascade",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    private List<MemberCascade> memberCascades = new ArrayList<>();

    public TeamCascade(){}

    public TeamCascade(String name) {
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

    public List<MemberCascade> getMembers() {
        return memberCascades;
    }

    public void setMembers(List<MemberCascade> memberCascades) {
        this.memberCascades = memberCascades;
    }

    public void addChild(MemberCascade memberCascade) {
        memberCascades.add(memberCascade);
        memberCascade.setTeam(this);
    }
}
