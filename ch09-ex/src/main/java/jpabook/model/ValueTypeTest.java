package jpabook.model;

import jpabook.model.entity.Address;
import jpabook.model.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ValueTypeTest {
    public static void main(String[] args) {
        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작
            valueTypeCopy(em);
            tx.commit();//트랜잭션 커밋


        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void valueTypeCopyTest(EntityManager em) {
        // 주소1
        Address address = new Address("city", "zip", "Street");

        // 회원1
        Member member1 = new Member();
        member1.setName("member1");
        member1.setHomeAddress(address);
        em.persist(member1);

        // 회원2
        Member member2 = new Member();
        member2.setName("member2");
        member2.setHomeAddress(address);
        em.persist(member2);

        // 회원1의 주소만 변경하고 싶어서 getter, setter을 사용
        member1.getHomeAddress().setCity("homeCity");
    }

    public static void valueTypeCopy(EntityManager em) {
        // 주소1
        Address address = new Address("city", "zip", "Street");

        // 회원1
        Member member1 = new Member();
        member1.setName("member1");
        member1.setHomeAddress(address);
        em.persist(member1);

        // 생성자를 통한 주소1 복사
        Address newAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

        // 회원2
        Member member2 = new Member();
        member2.setName("member2");
        member2.setHomeAddress(newAddress);
        em.persist(member2);

        member1.getHomeAddress().setCity("homeCity");
    }
}
