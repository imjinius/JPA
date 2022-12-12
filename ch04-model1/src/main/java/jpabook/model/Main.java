package jpabook.model;

import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jpa.premium.Parent;
import jpa.premium.ParentId;
import jpabook.model.entity.Address;
import jpabook.model.entity.Member;
import jpabook.model.entity.Order;
import jpabook.model.entity.Team;

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
    	TypedQuery<Member> query = em.createQuery("select m from Member m where m.age > (select avg(m2.age) from Member m2)", Member.class);
    	TypedQuery<Member> query2 = em.createQuery("select m from Member m where (select count(o) from Order o where m = o.member) > 0)", Member.class);
    	TypedQuery<Member> query3 = em.createQuery("select m from Member m where m.orders.size > 0)", Member.class);
    	List<Member> result = query.getResultList();
    	
    }
    
    public static void subQuery2(EntityManager em) {
    	
    	// 서브 쿼리 함수
    	// 1. exists
    	TypedQuery<Member> query = em.createQuery("select m from Member m where exists (select t from m.team t where t.name = '팀 A')", Member.class);
    	
    	// 2. all, any, some
    	TypedQuery<Order> query2 = em.createQuery("select o from Order o where o.orderAmount > all (select p.stockAmount from Product p)", Order.class);
    	TypedQuery<Member> query3 = em.createQuery("select m from Member m where m.team = any (select t from Team t)", Member.class);
    	
    	// 3. in
    	TypedQuery<Team> query4 = em.createQuery("select t from Team t where t in (select t2 from Team t2 join t2.members m2 where m2.age >= 20)", Team.class);
    }
    
    // 컬렉션 식
    public static void collectionGrammar(EntityManager em) {
    	
    	// 빈 컬렉션 비교 식
    	// 주문이 하나라도 있는 회원 조회
    	TypedQuery<Member> query = em.createQuery("select m from Member m where m.orders is not empty", Member.class);
    	
    	// 컬렉션의 멤버 식
    	// 엔티티나 값이 컬렉션에 포함되어 있으면 참
    	TypedQuery<Team> query2 = em.createQuery("select t from Team t where :memberParam member of t.members", Team.class);
    }
    
    public static void namedQuery(EntityManager em) {
    	List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
    			.setParameter("username", "회원1")
    			.getResultList();
    }
    
    public static void criteriaQuery(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Member> cq = cb.createQuery(Member.class);
    	Root<Member> m = cq.from(Member.class);
    	
    	Predicate usernameEqual = cb.equal(m.get("username"), "회원1");
    	javax.persistence.criteria.Order ageDesc = cb.desc(m.get("age"));
    	
    	cq.select(m).where(usernameEqual).orderBy(ageDesc);
    	
    	List<Member> members = em.createQuery(cq).getResultList();
    }
    
    public static void criteriaQueryMemberAge(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Member> cq = cb.createQuery(Member.class);
    	Root<Member> m = cq.from(Member.class);
    	
    	Predicate age = cb.greaterThan(m.<Integer>get("age"), 10);
    	
    	cq.select(m);
    	cq.where(age);
    	cq.orderBy(cb.desc(m.get("age")));
    	
    }
    
    public static void groupBy(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
    	Root<Member> m = cq.from(Member.class);
    	
    	Expression maxAge = cb.max(m.<Integer>get("age"));
    	Expression minAge = cb.min(m.<Integer>get("age"));
    	
    	cq.multiselect(m.get("team").get("name"), maxAge, minAge);
    	cq.groupBy(m.get("team").get("name")); //GROUP BY
    	
    	TypedQuery<Object[]> query = em.createQuery(cq);
    	List<Object[]> resultList = query.getResultList();
    	
    	
    }
    
}