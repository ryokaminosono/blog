package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="Comments_Info")
public class Comments_Info {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq_no;
	
	@Column
	private Integer posts_id;
	
	@Column
	private String comments;
	
	@Column
	private String reg_user_id;
	
	@Column
	private LocalDateTime reg_dt;
	
	
	
	//コンストラクタ
	public Comments_Info() {}
	
	public Comments_Info(int seqNo, int postsId, String comments, String regUserId, LocalDateTime regDt) {
		this.seq_no = seqNo;
		this.posts_id = postsId;
		this.comments = comments;
		this.reg_user_id = regUserId;
		this.reg_dt = regDt;
	}
	
	
	// getter/setter
	public Integer getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(Integer seq_no) {
		this.seq_no = seq_no;
	}

	public Integer getPosts_id() {
		return posts_id;
	}

	public void setPosts_id(Integer posts_id) {
		this.posts_id = posts_id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getReg_user_id() {
		return reg_user_id;
	}

	public void setReg_user_id(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}

	public LocalDateTime getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(LocalDateTime reg_dt) {
		this.reg_dt = reg_dt;
	}
	
	

}
