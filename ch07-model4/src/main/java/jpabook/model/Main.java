package jpabook.model;

import jpabook.model.entity.Member;
import jpabook.model.entity.item.Album;
import jpabook.model.entity.item.Book;
import jpabook.model.entity.item.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Date;

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
            saveBook(em);
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void saveBook(EntityManager em) {
        Book book = new Book();
        book.setAuthor("jh");
        book.setIsbn("1234");
        em.persist(book);

        Album album = new Album();
        album.setArtist("new");
        album.setEtc("known");
        em.persist(album);

        Movie movie = new Movie();
        movie.setActor("unknown");
        movie.setDirector("sad");
        em.persist(movie);

        em.flush();
        em.clear();

        Book findBook = em.find(Book.class, book.getId());
        System.out.println("Book Author: " + findBook.getAuthor());
        System.out.println("Book ISBN: " + findBook.getIsbn());
    }

    public static void saveOrderAndMember(EntityManager em) {
        Member member = new Member();
        member.setName("jh");
        member.setCity("Dague");
        member.setStreet("sagin");
    }
}
