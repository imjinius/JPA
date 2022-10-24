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
            //testSave(em);  //비즈니스 로직
            //queryLogicJoin(em);  //비즈니스 로직
            //updateRelation(em);  //비즈니스 로직
            deleteRelation(em);  //비즈니스 로직
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {

    }
    
    public static void testSave(EntityManager em) {
    	
    	// 팀1 저장
    	Team team1 = new Team("team1", "팀1");
    	em.persist(team1);
    	
    	// 회원1 저장
    	Member member1 = new Member("member1", "회원1");
    	member1.setTeam(team1); // 연관관계 설정 member1 -> team1
    	em.persist(member1);
    	
    	// 회원2 저장
    	Member member2 = new Member("member2", "회원2");
    	member2.setTeam(team1); // 연관관계 설정 member2 -> team1
    	em.persist(member2);

    	
    	Member member = em.find(Member.class, "member1");
    	Team team = member.getTeam(); // 객체 그래프 탐색
    	System.out.println("팀 이름 : "+team.getName());
    }
    
    private static void queryLogicJoin(EntityManager em) {
    	String jpql = "select m from Member m join m.team t where " + "t.name=:teamName";
    	
    	List<Member> resultList = em.createQuery(jpql, Member.class)
    			.setParameter("teamName", "팀1")
    			.getResultList();
    	
    	for(Member member:resultList) {
    		System.out.println("[query member.userName="+member.getUsername());
    	}
    	
    	
    }
    
    private static void updateRelation(EntityManager em) {
    	
    	// 새로운 팀2
    	Team team2 = new Team("team2", "팀2");
    	em.persist(team2);
    	
    	// 회원1에 새로운 팀2 설정
    	Member member = em.find(Member.class, "member1");
    	member.setTeam(team2);
    }
    
    private static void deleteRelation(EntityManager em) {
    	Member member1 = em.find(Member.class, "member1");
    	member1.setTeam(null); // 연관관계 제거
    }
}
