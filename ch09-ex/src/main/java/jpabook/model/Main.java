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
public class Main {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작
            //== save stub ==//
            saveStub(em);

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

            //== use Scalar ==//
            useScalarType(em);

            //== new UserDto ==//
            useUserDto(em);

            //== Inner Join ==//
            innerJoin(em);

            //== Outer Join ==//
            outerJoin(em);

            //== Collection Join ==//
            collectionJoin(em);

            //== Fetch Join ==//
            fetchJoin(em);

            //== Collection Fetch Join ==//
            collectionFetchJoin(em);

            //== DISTINCT Fetch Join ==//
            distinctFetchJoin(em);


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
        Team team1 = new Team();
        team1.setName("team1");
        em.persist(team1);

        Team team2 = new Team();
        team2.setName("team2");
        em.persist(team2);

        Member member1 = new Member();
        member1.setName("member1");
        member1.setAge(20);
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member();
        member2.setName("member2");
        member2.setAge(30);
        member2.setTeam(team1);
        em.persist(member2);

        Member member3 = new Member();
        member3.setName("member3");
        member3.setAge(40);
        member3.setTeam(team2);
        em.persist(member3);

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
            System.out.print("name: " + team.getName());
            for (Member member : team.getMembers()) {
                System.out.println("name: " + member.getName());
            }
        }
    }

    public static void useEmbedded(EntityManager em) {
        em.clear();
        System.out.println("=============================== use Embedded ============================");

        List<Address> addresses =
                em.createQuery("SELECT o.address FROM Order o", Address.class)
                        .getResultList();

        for (Address address : addresses) {
            System.out.print("City: " + address.getCity() + ", ");
            System.out.print("Street: " + address.getStreet() + ", ");
            System.out.println("Zipcode: " + address.getZipcode());
        }
    }

    public static void useScalarType(EntityManager em) {
        em.clear();
        System.out.println("=============================== use Scalar ============================");
        List<String> names =
                // 중복 제거 : SELECT DISTINCT
                em.createQuery("SELECT name FROM Member m", String.class).getResultList();

        for (String name : names) {
            System.out.println("Member name: " + name);
        }


        List<Object> resultList =
                em.createQuery("SELECT m.id, m.name FROM Member m")
                        .getResultList();

        Iterator iterator = resultList.iterator();
        while (iterator.hasNext()) {
            Object[] row = (Object[]) iterator.next();
            System.out.print("id: " + (Long) row[0] + ", ");
            System.out.println("name: " + (String) row[1]);
        }

        List<Object[]> resultObjectList =
                em.createQuery("SELECT o.id, o.member, o.product FROM Order o")
                    .getResultList();
        for (Object[] row : resultObjectList) {
            System.out.println("id: " + (Long) row[0]);         // 스칼라
            System.out.println("Member: " + (Member) row[1]);   // 엔티티
            System.out.println("Product: " + (Product) row[2]); // 엔티티
        }
    }

    public static void useUserDto(EntityManager em) {
        em.clear();
        System.out.println("=============================== use new UserDto ============================");
        //== new 사용 전 ==//
        List<Object[]> resultList =
                em.createQuery("SELECT m.name, m.age FROM Member m")
                        .getResultList();

        //객체 변환 작업
        List<UserDto> userDTOs = new ArrayList<>();
        for (Object[] row : resultList) {
            UserDto userDto = new UserDto((String)row[0], (Integer)row[1]);
            userDTOs.add(userDto);
        }

        //== new 사용 후 ==//
        TypedQuery<UserDto> query =
                em.createQuery("SELECT new jpabook.model.entity.UserDto(m.name, m.age) FROM Member m", UserDto.class);
        List<UserDto> userDtoList = query.getResultList();
        for (UserDto userDto : userDtoList) {
            System.out.print("UserDto name: " + userDto.getName() + ", ");
            System.out.println("UserDto age: " + userDto.getAge());
        }
    }

    public static void innerJoin(EntityManager em) {
        em.clear();
        System.out.println("=============================== use Inner Join ============================");
        String query = "SELECT m, t FROM Member m JOIN m.team t";
        List<Object[]> result = em.createQuery(query).getResultList();

        for (Object[] row : result) {
            Member member = (Member) row[0];
            Team team = (Team) row[1];
            System.out.print("Member: " + member + ", ");
            System.out.println("Team: " + team);
        }
    }

    public static void outerJoin(EntityManager em) {
        em.clear();
        System.out.println("=============================== use Outer Join ============================");
        String query = "SELECT m FROM Member m LEFT JOIN m.team t";
        List<Member> result = em.createQuery(query).getResultList();

        for (Member member : result) {
            System.out.print("Member: " + member + ", ");
            System.out.println("Team: " + member.getTeam());
        }
    }

    public static void collectionJoin(EntityManager em) {
        em.clear();
        System.out.println("=============================== use Collection Join ============================");
        String query = " SELECT t, m FROM Team t LEFT JOIN t.members m";
        List<Object[]> result = em.createQuery(query).getResultList();

        for (Object[] o : result) {
            Team team = (Team) o[0];
            Member member = (Member) o[1];

            System.out.print("Team: " + team + ", ");
            System.out.println("Member1: " + member + ", ");
        }
    }

    public static void fetchJoin(EntityManager em) {
        em.clear();
        System.out.println("=============================== use Fetch Join ============================");
        //String query = "SELECT m FROM Member m";
        String fetchQuery = "SELECT m FROM Member m JOIN FETCH m.team";

        List<Member> result = em.createQuery(fetchQuery).getResultList();

        for (Member member : result) {
            System.out.println("Member name: " + member.getName());
            System.out.println("Member team: " + member.getTeam());
        }
    }

    public static void collectionFetchJoin(EntityManager em) {
        em.clear();
        System.out.println("=============================== use Collection Fetch Join ============================");
        //String query = "SELECT t FROM Team t WHERE t.name = :name";
        String fetchQuery = "SELECT t FROM Team t JOIN FETCH t.members WHERE t.name = :name";

        List<Team> result = em.createQuery(fetchQuery)
                                .setParameter("name", "team1")
                                .getResultList();

        for (Team team : result) {
            System.out.println("Team name: " + team.getName());
            System.out.println("Team members: " + team.getMembers());
        }
    }

    public static void distinctFetchJoin(EntityManager em) {
        em.clear();
        System.out.println("=============================== use Collection Fetch Join ============================");
        String fetchQuery = "SELECT DISTINCT t FROM Team t JOIN FETCH t.members WHERE t.name = :name";

        List<Team> result = em.createQuery(fetchQuery)
                .setParameter("name", "team1")
                .getResultList();

        for (Team team : result) {
            System.out.println("Team name: " + team.getName());
            System.out.println("Team members: " + team.getMembers());
        }
    }
}
