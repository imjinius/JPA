package jpa.premium;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ParentId.class)
public class Parent {

	@Id
	@Column(name = "PARENT_ID1")
	private String id1;
	
	@Id
	@Column(name = "PARENT_ID2")
	private String id2; // 만약 복합키를 위한 테이블 설계를 하지 않는다면 영속성 컨텍스트에 보관되는 순간 에러가 발생!
	
	private String name;

	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
