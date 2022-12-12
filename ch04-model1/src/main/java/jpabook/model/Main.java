package jpabook.model;

import javax.persistence.*;

import jpa.premium.Parent;
import jpa.premium.ParentId;
import jpabook.model.entity.Address;
import jpabook.model.entity.Member;
import jpabook.model.entity.Order;

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
            find(em);
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }
    
    public void save(EntityManager em) {
    	Parent parent = new Parent();
    	ParentId parentId = new ParentId("myId1","myId2");
    	parent.setId(parentId);
    	parent.setName("parentName");
    	
    	em.persist(parent);
    }
    
    public static void find(EntityManager em) {
    	Member member = new Member();
    	
    	member.setHomeAddress(new Address("통영","몽돌해수욕장","660-123"));
    	member.getFavoriteFoods().add("짬뽕");
    	member.getFavoriteFoods().add("짜장");
    	member.getFavoriteFoods().add("탕수육");
    	
    	member.getAddressHistory().add(new Address("서울","강남","123-123"));
    	member.getAddressHistory().add(new Address("서울","강북","000-000"));
    	
    	em.persist(member);
    }
    
    public static void subQuery(EntityManager em) {
    	
    	// 서브쿼리에 결과가 존재하면 참
    	TypedQuery<Member> query = em.createQuery("select m from Member m where exists (select t "
    			+ "from m.team t where t.name='팀A')",Member.class);
    	
    	// 서브쿼리에 결과가 존재하면 참
    	TypedQuery<Order> query2 = em.createQuery("select o from Order o where o.orderAmount > ALL(select p.stockAmount "
    			+ "from Product p)",Order.class);
    	
    }
    
}
