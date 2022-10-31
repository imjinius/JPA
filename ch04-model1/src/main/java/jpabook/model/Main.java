package jpabook.model;

import javax.persistence.*;

import jpa.premium.Parent;
import jpa.premium.ParentId;

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
    
    public void find(EntityManager em) {
    	ParentId parentId = new ParentId("myId1","myId2");
    	Parent parent = em.find(Parent.class, parentId);
    	
    }

}
