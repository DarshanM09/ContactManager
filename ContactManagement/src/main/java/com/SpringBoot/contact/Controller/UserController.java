package com.SpringBoot.contact.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.SpringBoot.contact.Dao.UserRepo;
import com.SpringBoot.contact.Model.User;
import com.SpringBoot.contact.helper.message;

@Controller
public class UserController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo userRepo;

	@RequestMapping("/")
	public String home(Model m) {

		m.addAttribute("title", "Home-Contact Management");
		return "home";
	}

	@RequestMapping("/about")
	public String About(Model m) {

		m.addAttribute("title", "About-Contact Management");
		return "about";
	}

	@RequestMapping("/signup")
	public String signUp(Model m) {

		m.addAttribute("title", "Registration-Contact Management");
		m.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping("/registerProcess")
	public String RegisterProcess(@Valid @ModelAttribute("user") User user,BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model m,
			HttpSession session ) {

		try {

			if (!agreement ) {
				
				
				System.out.println("please select terms and conditions");
				throw new Exception("please select terms and conditions..");
			}

			
			if(result.hasErrors()) {
				System.out.println("Errors"+result.toString());
				return"signup";
			}
			user.setActive(true);
			user.setRole("ROLE_USER");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("agrement" + agreement);
			System.out.println(user);

			User save = userRepo.save(user);

			m.addAttribute("user", new User());

			session.setAttribute("message", new message("Sucessfully Registered..!!", "alert-success"));
			return "signup";

		} catch (Exception e) {

			e.printStackTrace();
			m.addAttribute("user", user);
			session.setAttribute("message", new message("Something Went Wrong..!!" + e.getMessage(), "alert-error"));
			return "signup";
		}

	}
	
	@RequestMapping("/signin")
	public String signin(Model m) {
		m.addAttribute("title", "Login-Contact Management");
		return "login";
	}

}
