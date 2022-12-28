package com.pb.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pb.helper.Message;
import com.pb.model.Contact;
import com.pb.model.User;
import com.pb.repository.UserRepo;



@Controller
public class HomeController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		
		User u = new User();
		u.setName("raju");
		u.setEmail("raju@gmail.com");
		
		userRepo.save(u);
		
		return "working";
	}
	
	
	@RequestMapping("/home")
	public String home() {
		
		return "home";
	}
	

	
///signup handeller	
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title","Register here !!");
		model.addAttribute("user",new User());
		return "signup";
	}
	
//	register handeller
	
	@RequestMapping(value="/do_register",method = RequestMethod.POST)
	public String registeruser(@ModelAttribute("user") User user,Model model,HttpSession session){

		try {
			user.setRole("ROLE_USER");
			user.setImageurl("demp.png");
			user.setPassword(encoder.encode(user.getPassword()));
			 User result = userRepo.save(user);
			 model.addAttribute("user",new User());
			System.out.println("user" + result);
			session.setAttribute("message",new Message("Successfully registered!!", "alert-success"));
			return "signup";
		} catch (Exception e) {
			model.addAttribute("user",user);
			session.setAttribute("message",new Message("something went wrong!!"+e.getMessage(), "alert-danger"));
			return "signup";
		}
	}
	
	

	
//	login handeller

	
	@RequestMapping("/signin")
	public String customlogin(Model model) {
		
		return "login";
	}
	
//	login handeller

	
	@RequestMapping("/failed")
	public String failed(Model model) {
		
		return "failed";
	}
	

	
	



}
