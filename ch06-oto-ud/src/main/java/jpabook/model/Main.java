package jpabook.model;

import jpabook.model.entity.Locker;
import jpabook.model.entity.Member;

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

            //조회
            oneToManyReadOnly(em);

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
        Locker locker1 = new Locker("locker1");
        em.persist(locker1);

        member1.setLocker(locker1);
        em.persist(member1);
    }

    public static void oneToManyReadOnly(EntityManager em) {
        //== 앞에서 member1의 team1과 매핑한 후 team2로 바뀔 수 있는지 체크 ==//
        System.out.println("=====================================================");
        TypedQuery<Member> query = em.createQuery(
                "SELECT m FROM Member m WHERE m.name = :name", Member.class
        );
        query.setParameter("name", "member1");
        Member findMember = query.getSingleResult();

        System.out.println("Locker ID: " + findMember.getLocker().getId());
        System.out.println("Locker Name: " + findMember.getLocker().getName());
    }

}