package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Posts_Info")
public class Posts_Info {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	/*GenerationType.AUTOだと初期値が0だった。
	 * IDENTITYだと初期値がNULLになった。その違いかな。*/
	private Integer posts_id;
	@Column
	private String user_id;
	@Column
	private String title;
	@Column
	private String contents;
	@Column
	private LocalDateTime reg_dt;
	/*private String reg_user;*/
	
	public Posts_Info() {}
	
	public Posts_Info(String userid, String title, String contents, LocalDateTime regdt) {
		this.user_id = userid;
		this.title = title;
		this.contents = contents;
		this.reg_dt = regdt;
		
		/*this.reg_user = reguser;*/
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public LocalDateTime getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(LocalDateTime reg_dt) {
		this.reg_dt = reg_dt;
	}

	public int getPosts_id() {
		return posts_id;
	}

	public void setPosts_id(int posts_id) {
		this.posts_id = posts_id;
	}
	

/*	public String getReg_user() {
		return reg_user;
	}

	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
*/

}
