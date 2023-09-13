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

            //조회
            mtoFindTest(em);

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
        em.persist(team);

        member1.setTeam(team);          // 연관관계 설정
        member2.setTeam(team);          // 연관관계 설정
    }

    public static void mtoFindTest(EntityManager em) {
        System.out.println("=====================================================");
        TypedQuery<Member> query = em.createQuery(
                "SELECT m FROM Member m WHERE m.name = :name", Member.class
        );
        query.setParameter("name", "member1");
        Member findMember = query.getSingleResult();
        Team findTeam = findMember.getTeam();

        System.out.println("Member Name: " + findMember.getName());
        System.out.println("Team Id: " + findTeam.getId());
        System.out.println("Team Name: " + findTeam.getName());
    }
}