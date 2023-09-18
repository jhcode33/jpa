package jpabook.model;

import jpabook.model.entity.*;

import javax.persistence.*;
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
            Long[] ids = saveStub(em);
            tx.commit();//트랜잭션 커밋
            em.clear();

            //== use TypeQuery ==//
            useTypeQuery(em);

            //== use Query ==//
            useQuery(em);

            //== use Parameters [ :"parameterName" ] ==//
            useParameters(em);

            //== use Entity Projection ==//
            useEntityProjection(em);

            //== use Embedded ==//
            useEmbedded(em);

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }
    public static Long[] saveStub(EntityManager em) {
        System.out.println("============================== save stub ==========================");
        Team team = new Team();
        team.setName("team");
        em.persist(team);

        Member member1 = new Member();
        member1.setName("member1");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member();
        member2.setName("member2");
        member2.setTeam(team);
        em.persist(member2);

        Product productA = new Product();
        productA.setName("productA");
        em.persist(productA);

        Address address1 = new Address();
        address1.setCity("Dague");
        address1.setStreet("Sangin");
        address1.setZipcode("41281");
        //em.persist(address1); // Entity가 아니므로 Embedded를  사용하는 쪽에서 저장해야함.

        Order order = new Order();
        order.setMember(member1);
        order.setProduct(productA);
        order.setAddress(address1);
        em.persist(order);

        Long[] ids = new Long[10];
        ids[0] = order.getId();
        return ids;
    }

    public static void useTypeQuery(EntityManager em) {


        System.out.println("============================== use TypeQuery ==========================");
        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
        List<Member> resultList = query.getResultList();
        for (Member member : resultList) {
            System.out.println("member: " + member);
        }
    }

    public static void useQuery(EntityManager em) {
        System.out.println("=============================== use Query ============================");
        Query query
                = em.createQuery("SELECT m.id, m.name FROM Member m");
        List<Object> resultList = query.getResultList();
        for (Object o : resultList) {
            Object[] result = (Object[]) o;  //결과가 둘 이상이면 Object[] 반환
            System.out.println("id: " + result[0]);
            System.out.println("name: " + result[1]);
        }
    }

    public static void useParameters(EntityManager em) {
        System.out.println("=============================== use Parameters ============================");
        em.clear();
        String memberName = "member1";

        TypedQuery<Member> query =
                em.createQuery("SELECT m From Member m WHERE m.name = :memberName", Member.class);

        //== Methode 체인 방식으로도 가능 ==//
//        List<Member> resultList =
//                em.createQuery("SELECT m From Member m WHERE m.name = :memberName", Member.class)
//                        .setParameter("memberName", memberName)
//                                .getResultList();


        query.setParameter("memberName", memberName);
        List<Member> resultList = query.getResultList();

        for (Member member : resultList) {
            System.out.println("id: " + member.getId());
            System.out.println("name: " + member.getName());
        }
    }

    public static void useEntityProjection(EntityManager em) {
        System.out.println("=============================== use EntityProjection ============================");
        em.clear();

        System.out.println("=============================== result Team =============================");
        TypedQuery<Team> queryTeam =
                em.createQuery("SELECT m.team From Member m", Team.class);

        // m.team이 무엇인지 이해하기
        List<Team> resultTeamList = queryTeam.getResultList();
        for (Team team : resultTeamList) {
            System.out.println("name: " + team.getName());
            for (Member member : team.getMembers()) {
                System.out.println("name: " + member.getName());
            }
        }
    }


    public static void useEmbedded(EntityManager em) {
        em.clear();
        System.out.println("=============================== use Embedded ============================");

        List<Address> addresses =
                em.createQuery("SELECT o.address FROM Order o", Address.class).getResultList();
    }
}
