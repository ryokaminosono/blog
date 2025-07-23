package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="M_User")
public class M_User {
	
	@Id
	private String user_id;
	private String user_password;
	private String user_name;
	private LocalDateTime reg_dt;
	/*private String reg_user;*/
	
	public M_User() {};
	
	public M_User(String id, String pass, String name, LocalDateTime regdt) {
		this.user_id = id;
		this.user_password = pass;
		this.user_name = name;
		this.reg_dt = regdt;
		
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public LocalDateTime getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(LocalDateTime reg_dt) {
		this.reg_dt = reg_dt;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

/*	public String getReg_user() {
		return reg_user;
	}

	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
*/
	

}
