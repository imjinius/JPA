package jpa.premium;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ParentId implements Serializable{
	
	@Column(name = "PARENT_ID1")
	private String id1; //Parent.id1 매핑
	
	@Column(name = "PARENT_ID2")
	private String id2; //Parent.id2 매핑
	
	
	public ParentId() {}

	public ParentId(String id1, String id2) {
		this.id1 = id1;
		this.id2 = id2;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
}
