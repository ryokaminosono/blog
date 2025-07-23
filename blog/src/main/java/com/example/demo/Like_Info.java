package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Like_Info")
public class Like_Info {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seqNo;

	@Column(name = "posts_id")
	private Integer posts_id;

	@Column(name = "reg_user_id")
	private String reg_user_id;

	@Column(name = "reg_dt")
	private LocalDateTime reg_dt;

	// コンストラクタ
	public Like_Info() {}

	public Like_Info(int seqNo, int postsId, String userId, LocalDateTime regDt) {
		this.seqNo = seqNo;
		this.posts_id = postsId;
		this.reg_user_id = userId;
		this.reg_dt = regDt;
	}

	// getter/setter
	public Integer getSeqNo() {
		return seqNo; 
	}
	public void setSeqNo(Integer seqNo) { 
		this.seqNo = seqNo; 
	}

	public Integer getPosts_Id() { 
		return posts_id; 
	}
	public void setPosts_Id(Integer postsId) { 
		this.posts_id = postsId; 
	}

	public String getReg_User_Id() { 
		return reg_user_id; 
	}
	public void setReg_User_Id(String userId) { 
		this.reg_user_id = userId; 
	}

	public LocalDateTime getReg_Dt() { 
		return reg_dt; 
	}
	public void setReg_Dt(LocalDateTime regDt) { 
		this.reg_dt = regDt; 
	}
	
	
}