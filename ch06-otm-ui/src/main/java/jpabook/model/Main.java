package jpabook.model;

import jpabook.model.entity.Member;
import jpabook.model.entity.Team;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {
            tx.begin(); //트랜잭션 시작
            //저장
            testSave(em);

            tx.commit();//트랜잭션 커밋

            //조회 Member -> Team
            //findTeamToMember(em);

            // Member을 통해서 Team 연관관계를 변경할 수 있는지 체크
            oneToManyReadOnly(em);

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void testSave(EntityManager em) {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        em.persist(member1);
        em.persist(member2);

        Team team = new Team("team1");
        team.getMembers().add(member1);     // OneToMany 연관관계 설정
        team.getMembers().add(member2);     // OneToMany 연관관계 설정
        em.persist(team);
    }

    public static void findTeamToMember(EntityManager em) {
        System.out.println("=====================================================");
        TypedQuery<Member> query = em.createQuery(
                "SELECT m FROM Member m WHERE m.name = :name", Member.class
        );
        query.setParameter("name", "member1");
        Member findMember = query.getSingleResult();
        Team team = findMember.getTeam();

        System.out.println("Team ID: " + team.getId());
        System.out.println("Team Name: " + team.getName());
    }

    public static void oneToManyReadOnly(EntityManager em) {
        //== 앞에서 member1의 team1과 매핑한 후 team2로 바뀔 수 있는지 체크 ==//
        System.out.println("=====================================================");
        TypedQuery<Member> query = em.createQuery(
                "SELECT m FROM Member m WHERE m.name = :name", Member.class
        );
        query.setParameter("name", "member1");
        Member findMember = query.getSingleResult();

        Team team2 = new Team("team2");
        findMember.setTeam(team2);
        em.persist(team2);
    }
}