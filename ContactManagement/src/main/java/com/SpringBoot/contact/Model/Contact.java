package com.SpringBoot.contact.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	private String name;
	private String secondName;
	private String email;
	private String work;
	private String phone;
	private String image;
	@Column(length = 1000)
	private String description;
	
	@ManyToOne
	private User user;
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * @Override public String toString() { return "Contact [cid=" + cid + ", name="
	 * + name + ", secondName=" + secondName + ", email=" + email + ", work=" + work
	 * + ", phone=" + phone + ", image=" + image + ", description=" + description +
	 * ", user=" + user + "]"; }
	 * 
	 */

	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.cid==((Contact)obj).getCid();
	}
}
