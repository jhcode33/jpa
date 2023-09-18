package jpabook.model;

import jpabook.model.entity.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 1001218 on 15. 4. 5..
 */
public class MainPagingTest {
    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작

            //== with use Paging API ==//
            testPagingApi(em);
            tx.commit();//트랜잭션 커밋
            em.clear();

            //== useP Paging API ==//
            usePaging(em);

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void testPagingApi(EntityManager em) {
        System.out.println("============================== save testPagingApi ==========================");
        Team team = new Team();
        team.setName("team");
        em.persist(team);

        Product productA = new Product();
        productA.setName("productA");
        productA.setPrice(2000);
        em.persist(productA);

        Address address1 = new Address();
        address1.setCity("Daegu");
        address1.setStreet("Sangin");
        address1.setZipcode("41281");
        //em.persist(address1); // Entity가 아니므로 Embedded를  사용하는 쪽에서 저장해야함.

        Order order = new Order();
        order.setProduct(productA);
        order.setAddress(address1);

        for (int i = 0; i < 100; i++) {
            Member member = new Member();
            member.setName("member" + i);
            member.setAge(i);
            member.setTeam(team);
            order.setMember(member);
            em.persist(member);
        }

        em.persist(order);
    }

    public static void usePaging(EntityManager em) {
        TypedQuery<Member> query =
                em.createQuery("SELECT m FROM Member m ORDER BY m.id DESC", Member.class);

        query.setFirstResult(10);
        query.setMaxResults(20);
        List<Member> members = query.getResultList();

        // Team, Order, Product에 ID 시퀀스가 1씩 할당되어 이름과 3의 차이가 발생했음
        System.out.println("Paging Size: " + members.size());
        for (Member member : members) {
            System.out.print("Member id: " + member.getId() + ", ");
            System.out.println("Member name: " + member.getName());
        }
    }
}
