package jpabook.model;

import jpabook.model.entity.Member;
import jpabook.model.entity.Team;
import org.hibernate.Hibernate;

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
            Long[] ids = testSave(em);
            Long memberId = ids[0];
            Long team1Id = ids[1];
            Long team2Id = ids[2];

            tx.commit();//트랜잭션 커밋
            em.clear();

            // Eager & Lazy Test
            printUserAndTeam(em, memberId);
//            printUser(em, memberId);

            // Proxy Test
            //proxyTest(em, memberId);

            // Mapping Test
            //referenceMapping(em, memberId, team2Id);

            // Check Proxy
            //checkProxy(em, ids[0]);

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static Long[] testSave(EntityManager em) {
        Team team1 = new Team("team1");
        em.persist(team1);
        // .persist()하면 영속성 컨텍스트로 관리되기 위해 식별자 ID인 시퀀스 값을 할당 받음
        // DB에 저장은 트랜잭션이 commit될 때임
        Team team2 = new Team("team1");
        em.persist(team2);

        Member member1 = new Member("member1");
        em.persist(member1);

        member1.setTeam(team1);          // 연관관계 설정
        Long[] result = new Long[5];
        result[0] = member1.getId();
        result[1] = team1.getId();
        result[2] = team2.getId();
        return result;
    }

    public static void printUserAndTeam(EntityManager em, Long id) {
        System.out.println("====================== print Member & Team ==========================");
        Member findMember = em.find(Member.class, id);

        Team findTeam = findMember.getTeam();
        System.out.println("Team 객체의 초기화 여부: " + Hibernate.isInitialized(findTeam));
        System.out.println("Member Name: " + findMember.getName());
        System.out.println("Team Name: " + findTeam.getName());
        System.out.println("Team 객체의 초기화 여부: " + Hibernate.isInitialized(findTeam));
    }

    public static void printUser(EntityManager em, Long id) {
        System.out.println("======================== print Member ==============================");
        Member findMember = em.find(Member.class, id);
        System.out.println("Member Name: " + findMember.getName());
    }

    public static void proxyTest(EntityManager em, Long id) {
        System.out.println("======================== Proxy Test ================================");
        Member proxyMember = em.getReference(Member.class, id);
        System.out.println("객체 이름: " + proxyMember.getClass().getName());
        System.out.println("Proxy 객체의 초기화 여부: " + Hibernate.isInitialized(proxyMember));

        proxyMember.getName();
        System.out.println("Proxy 객체의 초기화 여부: " + Hibernate.isInitialized(proxyMember));
    }

    public static void referenceMapping(EntityManager em, Long memberId, Long team2Id) {
        System.out.println("======================== Mapping Test ================================");
        Member getMember = em.find(Member.class, memberId);
        Team team = em.getReference(Team.class, team2Id); // SQL을 실행하지 않는다
        getMember.setTeam(team);
    }

    public static void checkProxy(EntityManager em, Long memberId) {
        System.out.println("========================== check Test ================================");
        Member memberProxy = em.getReference(Member.class, memberId);

        boolean isLoad = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(memberProxy);
        System.out.println("memberProxy: " + memberProxy.getClass().getName());
        System.out.println("isLoad: " + isLoad);
    }
}