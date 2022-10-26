package jpabook.start;

import java.util.List;

import javax.persistence.*;

@Entity
public class Team {

	@Id
	@Column(name = "TEAM_ID")
	private String id;
	
	private String name;
	
	// 추가
	@OneToMany(mappedBy = "team")
	private List<Member> members;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team() {}
	
	public Team(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	
	
}
