package jpabook.model;

import jpabook.model.entityCascade.MemberCascade;
import jpabook.model.entityCascade.TeamCascade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Cascade {
    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            saveRemoveOrphan(em, tx);

            //allAndOrphan(em, tx);

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static Long[] saveWithCascade(EntityManager em) {
        System.out.println("========================== Save cascade ================================");
        MemberCascade memberCascade1 = new MemberCascade("member1");
        MemberCascade memberCascade2 = new MemberCascade("member2");

        TeamCascade teamCascade1 = new TeamCascade("team1");
        em.persist(teamCascade1);

        memberCascade1.setTeam(teamCascade1);
        memberCascade2.setTeam(teamCascade1);
        teamCascade1.getMembers().add(memberCascade1);
        teamCascade1.getMembers().add(memberCascade2);

        em.persist(teamCascade1); // 부모만 저장해도 자식까지 함께 저장

        //== 다른 메소드에서 테스트를 연결해서 하기 위한 작업 ==//
        Long[] ids = new Long[5];
        ids[0] = memberCascade1.getId();
        ids[1] = memberCascade2.getId();
        ids[2] = teamCascade1.getId();
        return ids;
    }

    public static void removeParent(EntityManager em, Long team1Id) {
        System.out.println("======================== Remove Parent ================================");
        TeamCascade team1 = em.find(TeamCascade.class, team1Id);
        em.remove(team1);
    }

    public static void removeOrphan(EntityManager em, Long team1Id) {
        System.out.println("======================== Remove orphan ================================");
        TeamCascade teamCascade = em.find(TeamCascade.class, team1Id);
        teamCascade.getMembers().clear(); // = CascadeType.REMOVE
    }

    public static Long[] allAndOrphanPersist(EntityManager em) {
        System.out.println("======================== All & Orphan Persist =========================");
        MemberCascade memberCascade1 = new MemberCascade("member1");
        MemberCascade memberCascade2 = new MemberCascade("member2");

        TeamCascade teamCascade = new TeamCascade("team1");
        teamCascade.addChild(memberCascade1);
        teamCascade.addChild(memberCascade2);

        em.persist(teamCascade);

        Long[] ids = new Long[5];
        ids[0] = memberCascade1.getId();
        ids[1] = memberCascade2.getId();
        ids[2] = teamCascade.getId();
        return ids;
    }

    public static void allAndOrphanRemove(EntityManager em, Long member1Id, Long member2Id, Long team1Id) {
        em.clear();
        System.out.println("======================== All & Orphan Remove =========================");
        MemberCascade memberCascade1 = em.find(MemberCascade.class, member1Id);
        MemberCascade memberCascade2 = em.find(MemberCascade.class, member2Id);
        TeamCascade teamCascade = em.find(TeamCascade.class, team1Id);

        teamCascade.getMembers().remove(memberCascade1);
        teamCascade.getMembers().remove(memberCascade2);
    }

    public static void saveRemoveOrphan(EntityManager em, EntityTransaction tx) throws Exception {
        tx.begin(); //트랜잭션 시작

        // saveWithCascade
        Long[] ids = saveWithCascade(em);
        Long member1Id = ids[0];
        Long member2Id = ids[1];
        Long team1Id = ids[2];

        tx.commit(); //트랜잭션 커밋
        em.clear();

        tx.begin();

        // removeParent
        removeParent(em, team1Id);

        // removeOrphan
        removeOrphan(em, team1Id);
        tx.commit();
    }

    public static void allAndOrphan(EntityManager em, EntityTransaction tx) throws Exception {
        tx.begin(); //트랜잭션 시작

        Long[] idsAll = allAndOrphanPersist(em);
        Long member1Id = idsAll[0];
        Long member2Id = idsAll[1];
        Long team1Id = idsAll[2];

        tx.commit();//트랜잭션 커밋
        em.clear();

        tx.begin();

        allAndOrphanRemove(em, member1Id, member2Id, team1Id);
        tx.commit();
    }
}
