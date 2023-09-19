package jpabook.model;

import jpabook.model.entity.*;
import jpabook.model.entity.item.Book;
import jpabook.model.entity.item.Item;
import jpabook.model.entity.item.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by 1001218 on 15. 4. 5..
 */
public class Main {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작
            //TODO 비즈니스 로직
            save(em);
            tx.commit();//트랜잭션 커밋

            //findParent(em);

            //useTREAT(em);

            //== use Entity 직접 입력 ==//
            //useEntity(em);

            //== named query ==//
            //namedQuery(em);

            //== Bulk ==//
            //tx.begin();
            //bulk(em);
            //tx.commit();

            emFind(em);

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void save(EntityManager em) {
        Book book = new Book();
        book.setName("hello");
        book.setIsbn("1234");
        book.setAuthor("jhcode");
        book.setPrice(2000);
        book.setStockQuantity(20);
        em.persist(book);

        Movie movie = new Movie();
        movie.setName("world");
        movie.setActor("me");
        movie.setDirector("korea");
        movie.setPrice(3000);
        movie.setStockQuantity(20);
        em.persist(movie);

        System.out.println("============================== save stub ==========================");
        Member member1 = new Member();
        member1.setName("member1");
        em.persist(member1);

        Order order = new Order();
        order.setMember(member1);

//        for (int i = 0; i < 1000; i++) {
//            Product product = new Product();
//            product.setName("product" + i);
//            product.setPrice(2000);
//            product.setStockAmount(10);
//            em.persist(product);
//            order.setProduct(product);
//        }

        em.persist(order);
    }

    public static void findParent(EntityManager em) {
        List<Item> resultList = em.createQuery("SELECT i FROM Item i")
                                    .getResultList();
    }

    public static void useTREAT(EntityManager em) {
        List<Item> items =
                em.createQuery("SELECT i FROM Item i WHERE TREAT(i as Book).author = 'me' ", Item.class)
                        .getResultList();

        for (Item i : items) {
            System.out.println("Item name: " + i.getName());
        }
    }

    public static void useEntity(EntityManager em) {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("Dague", "sangin", "51"));
        member.setWorkPeriod(new Period(new Date(), new Date()));
        em.persist(member);

        String jpql = "SELECT m FROM Member m WHERE m = :member";
        List resultList = em.createQuery(jpql)
                .setParameter("member", member)
                .getResultList();
    }

    public static void namedQuery(EntityManager em) {
        List<Member> members = em.createNamedQuery("Member.findByName", Member.class)
                                .setParameter("name", "member1")
                                .getResultList();

        for (Member member : members) {
            System.out.print("Member Name: " + member.getName());
        }
    }

    public static void bulk(EntityManager em) {
        String jpqlString = "UPDATE Product p " +
                               "SET p.price = p.price * 2.0 " +
                             "WHERE p.stockAmount < :stockAmount";

        int resultCount = em.createQuery(jpqlString)
                .setParameter("stockAmount", 20)
                .executeUpdate();

        System.out.println(resultCount);
    }

    public static void emFind(EntityManager em) {
        Member member1 = em.createNamedQuery("Member.findByName", Member.class)
                            .setParameter("name", "member1")
                            .getSingleResult();

        Member member2 = em.createQuery("SELECT m FROM Member m", Member.class).getSingleResult();

        if (member1 == member2) {
            System.out.println("true");
            System.out.println(member1.hashCode());
            System.out.println(member2.hashCode());
        }

        if (member1.equals(member2)) {
            System.out.println("true");
            System.out.println(member1.hashCode());
            System.out.println(member2.hashCode());
        }

    }
}
