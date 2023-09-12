package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {


            tx.begin(); //트랜잭션 시작
            logic2(em);  //비즈니스 로직
            tx.commit();//트랜잭션 커밋
            System.out.println("============ after commit ===========");

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }
    public static void logic2(EntityManager em) {
        Board board1 = new Board();
        em.persist(board1);
        System.out.println("============ after board1 flush ============");
        // IDENTITY 전략은 commit을 수행하기 전에 영속성 컨텍스트에 저장하기 위해서 id 값이 필요하기 때문에
        // flush()가 먼저 수행된다, 즉 지연 쓰기가 적용되지 않는다
        System.out.println("board.id: " + board1.getId());

        Board board2 = new Board();
        em.persist(board2);
        System.out.println("============ after board2 flush ============");
        System.out.println("board2.id: " + board2.getId());
    }

    public static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        //등록
        em.persist(member);

        //수정
        member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        //삭제
        em.remove(member);

    }
}
