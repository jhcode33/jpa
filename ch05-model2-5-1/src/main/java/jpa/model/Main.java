package jpa.model;

import jpa.model.entity.Member;
import jpa.model.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작
            //TODO 비즈니스 로직
            //logic(em);
            testPOJO(em);

            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        member1.setTeam(team1);
        member2.setTeam(team1);

        em.persist(member1);
        em.persist(member2);
    }

    //== 객체 그래프 탐색 ==//
    public static void objectGraphSearch(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        Team team = member.getTeam(); // 객체 그래프 탐색
        System.out.println("팀 이름: " + team.getName());
    }

    public static void  updateRelation(EntityManager em) {
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }

    public static void deleteRelation(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        member.setTeam(null);
    }

    public static void deleteTeam(EntityManager em) {
        // eager 모드로 가져온 entity는 영속상태가 아니다?
        Member member1 = em.find(Member.class, "member1");
        Member member2 = em.find(Member.class, "member2");
        member1.setTeam(null);
        member2.setTeam(null);

        Team team = em.find(Team.class, "team1");

        em.remove(team);
    }

    public static void findTeam(EntityManager em) {
        Team team = em.find(Team.class, "team1");

        List<Member> members = team.getMembers(); // 팀 -> 회원, 객체 그래프 탐색

        for (Member member : members) {
            System.out.println("member.username = " + member.getName());
        }
    }

//    public static void biDirection(EntityManager em) {
//        //Team team = new Team("team2", "팀2");
//
//        Member member1 = em.find(Member.class, "member1");
//        Member member2 = em.find(Member.class, "member2");
//
//        // owner가 member의 team field이기 때문에 연관관계가 자동으로 설정된다
//        //member1.setTeam(team);
//        //member2.setTeam(team);
//
//        List<Member> members = team.getMembers(); // 팀 -> 회원, 객체 그래프 탐색
//
//        for (Member member : members) {
//            System.out.println("member.username = " + member.getName());
//        }
//    }

    // 양방향 연관관계는 연관관계의 주인이 외래키를 관리한다, 즉 Member가 외래키를 관리한다
    public static void testSave(EntityManager em) {
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(team2);
        em.persist(member1);

        Member member2 = em.find(Member.class, "member2");
        member2.setTeam(team2);
        em.persist(member2);

        // owner가 아니기 때문에 연관관계 설정이 되지 않는다
        team2.getMembers().add(member1); //무시
        team2.getMembers().add(member2); //무시
    }

    //== 순수한 객체 연관관계 ==//
    public static void testPOJO(EntityManager em) {
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");

        member1.setTeam(team1);
        em.persist(member1);
        member2.setTeam(team1);
        em.persist(member2);

        List<Member> members = team1.getMembers();
        System.out.println("members.size: " + members.size());
    }

    //== JPA 객체 양방향 ==//
    public static void testSaveORM(EntityManager em) {
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);          //연관관계 설정 member1 -> team1
        team1.getMembers().add(member1); //연관관계 설정 team1 -> member1
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);          //연관관계 설정 member2 -> team1
        team1.getMembers().add(member2); //연관관계 설정 team1 -> member2
        em.persist(member2);

        Team findTeam = em.find(Team.class, "team1");
        if (team1.getMembers().size() == findTeam.getMembers().size()){
            System.out.println("연관관계가 동일합니다.");
        }
    }

    //== outerJoin 확인 Member -> Team으로 조회할 때 발생 ==//
    public static void outerJoin(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        Team team1 = member1.getTeam();

        List<Member> members = team1.getMembers(); // 팀 -> 회원, 객체 그래프 탐색

        for (Member member : members) {
            System.out.println("member.username = " + member.getName());
        }
    }
}