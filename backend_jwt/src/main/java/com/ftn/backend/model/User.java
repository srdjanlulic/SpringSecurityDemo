package com.ftn.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name="User")
@Table(name="tblUsers")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserID", columnDefinition = "serial")
	private Integer id;	
	
	@Column(name="Name")
	private String name;
	
	@Column(name="LastName")
	private String lastname;
	
	@Column(name="Username")
	private String username;
	
	@Column(name="Email")
	private String email;
	
	@Transient
	private String password;
	
	@Column(name="Password")
    private String encryptedPassword;

	
}
