package jpabook.model;

import jpabook.model.entity.Address;
import jpabook.model.entity.Member;
import jpabook.model.entity.Period;
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

            findParent(em);

            //useTREAT(em);

            useEntity(em);
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
}
