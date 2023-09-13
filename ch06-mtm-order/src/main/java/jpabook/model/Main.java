package jpabook.model;


import jpabook.model.entity.Member;
import jpabook.model.entity.Order;
import jpabook.model.entity.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

            tx.commit(); //트랜잭션 커밋
            em.clear();

            //조회
            find(em);

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void testSave(EntityManager em) {
        Member member1 = new Member("member1", "회원1");
        em.persist(member1);

        Product productA = new Product("productA", "상품A");
        em.persist(productA);

        Order order = new Order();
        order.setMember(member1);
        order.setProduct(productA);
        order.setOrderAmount(2);
        em.persist(order);

    }

    //== 조회 ==//
    public static void find(EntityManager em) {
        System.out.println("=======================================================");
        Long orderId = 1L;
        Order order = em.find(Order.class, orderId);

        Member member = order.getMember();
        Product product = order.getProduct();

        System.out.println("Member Id: " + member.getId());
        System.out.println("Member Name: " + member.getName());
        System.out.println("Product Id: " + product.getId());
        System.out.println("Product Name: " + product.getName());
    }
}