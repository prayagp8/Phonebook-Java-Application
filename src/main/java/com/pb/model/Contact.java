package com.pb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
@Entity
@Data
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cId;
	private String name;
	private String secondname;
	private String work;
	private String email;
	private String phone;
	private String image;
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

	@Override
	public String toString() {
		return "Contact [cId=" + cId + ", name=" + name + ", secondname=" + secondname + ", work=" + work + ", email="
				+ email + ", phone=" + phone + ", image=" + image + ", description=" + description + "]";
	}
	
	
}
