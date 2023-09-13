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

            tx.commit();//트랜잭션 커밋
            em.clear();
            //조회
            oneToManyReadToLocker(em);

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

        member1.setLocker(locker1);

        // 객체 간에 관계도 설정해줘야 한다
        // 설정하지 않는다면 JPQL을 사용해서 조회하더라도 우선적으로 1차 캐시에서 조회하기 때문에
        // locker1.setMember()을 통해 연관관계를 설정하지 않았다면, 설정하지 않은 객체만 조회되서 오류가 난다
        // em.clear()을 통해 1차 캐쉬를 비울 경우, 쿼리를 발생시켜서 조회해서 member가 조회될 수 있다
        locker1.setMember(member1);

        em.persist(locker1);
        em.persist(member1);
    }

    public static void oneToManyReadToLocker(EntityManager em) {
        //== 앞에서 member1의 team1과 매핑한 후 team2로 바뀔 수 있는지 체크 ==//
        System.out.println("=====================================================");
        TypedQuery<Locker> query = em.createQuery(
                "SELECT l FROM Locker l WHERE l.name = :name", Locker.class
        );
        query.setParameter("name", "locker1");
        Locker findLocker = em.find(Locker.class, 1l);

        System.out.println("Locker ID: " + findLocker.getId());
        System.out.println("Locker Name: " + findLocker.getName());
        System.out.println("Locker Member.id: " + findLocker.getMember().getId());
        System.out.println("Locker Member.name: " + findLocker.getMember().getName());
    }

}