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

        Team team = new Team("team1");
        team.getMembers().add(member1);     // OneToMany 연관관계 설정
        team.getMembers().add(member2);     // OneToMany 연관관계 설정

        em.persist(member1);    //INSERT-member1
        em.persist(member2);    //INSERT-member2
        em.persist(team);       //INSERT-team, UPDATE-member1.fk, UPDATE-member2.fk
    }
}