package com.pb.controller;


import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pb.helper.Message;
import com.pb.model.Contact;
import com.pb.model.User;
import com.pb.repository.ContactRepo;
import com.pb.repository.UserRepo;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ContactRepo contactRepo;
	
	
	@ModelAttribute
	public void addCommonData(Model model ,Principal p ) {
		 User u = userRepo.findByEmail(p.getName());
	
		 model.addAttribute("user",u);
	}
	

	
//	user-dashboard
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		
		List<Contact> ls = contactRepo.findAll();
		List<User> ls1 = userRepo.findAll();
		model.addAttribute("count",ls.size());
		model.addAttribute("users",ls1.size());
		return "dashboard";
	}
	
	
//	add contact 
	

	@RequestMapping("/addcontact")
	public String addcontact(Model model) {
		
		model.addAttribute("title","Add contact");
		model.addAttribute("contact",new Contact());
		
		return "addcontact";
	}
	
//	azbout
	
	@RequestMapping("/about")
	public String about(Model model,Principal p) {
		
		User u =  userRepo.findByEmail(p.getName());
		model.addAttribute("title","this is my about page !!");
		model.addAttribute("user",u);
		return "about";
	}
	
//	process contact 
	

	@PostMapping("/processcontact")
	public String processContact(@ModelAttribute Contact contact,Principal p ,HttpSession session) {
		
		try {
			 User u = userRepo.findByEmail(p.getName());
			 contact.setUser(u);
			 if(contact.getPhone().length()!=10) throw new Exception("invalid mobile number!!");
			 u.getContacts().add(contact);
			 userRepo.save(u);
			System.out.println(contact);
			System.out.println("added !!!!!!");
			session.setAttribute("message",new Message("Successfully registered!!", "success"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("message",new Message("something went wrong!!"+e.getMessage(), "danger"));
			
		}
		
	
		return "addcontact";
	}
	
	
//	show contacts
	

	@RequestMapping("/showcontacts/{page}")
	public String showContacts( @PathVariable("page") Integer page, Model model,Principal p ) {
		
		model.addAttribute("title","Show contact list");
		User u = userRepo.findByEmail(p.getName());
//		List<Contact> contacts = u.getContacts();
		
		Pageable pageable = PageRequest.of(page, 2);
		
		   Page<Contact> contacts= contactRepo.findContactByUser(u.getId(),pageable);
		
		for(Contact c : contacts) {
			System.out.println(c.getImage());
		}
		
		model.addAttribute("contacts",contacts);
		
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPages",contacts.getTotalPages());
		
		
		return "showcontacts";
	}
	
}
