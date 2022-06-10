package com.SpringBoot.contact.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.persistence.PreUpdate;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.multi.MultiButtonUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.contact.Dao.ContactRepo;
import com.SpringBoot.contact.Dao.UserRepo;
import com.SpringBoot.contact.Model.Contact;
import com.SpringBoot.contact.Model.User;
import com.SpringBoot.contact.helper.message;

@Controller
@RequestMapping("/user")
public class LoginController {

	@Autowired
	private UserRepo userrepo;
	@Autowired
	private ContactRepo contactrepo;

	
	
	@ModelAttribute
	public void CommonData(Model m, Principal principal) {

		String name = principal.getName();
		System.out.println("User Name " + name);
		User userName = userrepo.getUserName(name);
		System.out.println("user " + userName);

		m.addAttribute("user", userName);
	}
	
	
	
	
	
	
	

	@RequestMapping("/index")
	public String log(Model m, Principal principal) {
		m.addAttribute("title", "Home-User");

		return "normal/index";
	}

	@RequestMapping("/add-contact")
	public String AddContact(Model m, Principal principal) {

		m.addAttribute("title", "Add-Contact Manager");
		m.addAttribute("contact", new Contact());
		return "normal/add_contact";
	}

	
	
	
	
	
	
	
	
	@PostMapping("/process-contact")
	public String processForm(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal, HttpSession session) {

		try {

			String name = principal.getName();
			User user = userrepo.getUserName(name);
			// user details
			System.out.println("Usser Details : " + user);

			if (file.isEmpty()) {

				contact.setImage("contact.png");

			} else {
				contact.setImage(file.getOriginalFilename());
				File file2 = new ClassPathResource("static/images").getFile();

				Path path = Paths.get(file2.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			}

			contact.setUser(user);

			// adding contact in user list
			user.getContacts().add(contact);
			this.userrepo.save(user);

			System.out.println("Contact " + contact);
			session.setAttribute("message", new message("Contact successfully addes...!!,Add new Contact.", "success"));

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("message", new message("Something went Wrong..!!", "danger"));
		}

		return "normal/add_contact";
	}

	@RequestMapping("/show-contact/{page}")

	public String showContact(@PathVariable("page") Integer page, Model m, Principal principal) {

		m.addAttribute("title", "show- Contact Manager");
		String name = principal.getName();

		User user = userrepo.getUserName(name);
		PageRequest pageRequest = PageRequest.of(page, 5);

		Page<Contact> contactByUser = contactrepo.findContactByUser(user.getId(), pageRequest);

		m.addAttribute("contacts", contactByUser);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", contactByUser.getTotalPages());

		return "normal/show_contact";
	}

	@RequestMapping("/contact/{cId}")
	public String showContactDetails(@PathVariable("cId") Integer cId, Model m, Principal principal) {

		String name = principal.getName();

		User user = userrepo.getUserName(name);

		System.out.println(cId);
		Optional<Contact> findById = contactrepo.findById(cId);
		Contact contact = findById.get();

		if (user.getId() == contact.getUser().getId())
			m.addAttribute("contact", contact);
		m.addAttribute("title", "Details- Contact Manager");
		return "normal/ContactDetail";
	}

	@RequestMapping("/delete/{cid}")
	public String deleteSingleContact(@PathVariable("cid") Integer cid, Principal principal, Model m,
			HttpSession session) {

		Optional<Contact> contactoptional = this.contactrepo.findById(cid);
		Contact contact = contactoptional.get();
		String name = principal.getName();

		User user = userrepo.getUserName(name);

		user.getContacts().remove(contact);

		if (user.getId() == contact.getUser().getId()) {
			contact.setUser(null);
			this.contactrepo.delete(contact);
			session.setAttribute("message", new message("Contact Sucessfully deleted...!!", "success"));
		} else {
			session.setAttribute("message", new message("Something went wrong(Contact cant be deleted...!!", "danger"));
		}

		System.out.println("delted");

		return "redirect:/user/show-contact/0";
	}

	@PostMapping("/updateContact/{cid}")
	public String UpdateContact(@PathVariable("cid") Integer cid, Model m) {

		Optional<Contact> contactoptional = this.contactrepo.findById(cid);
		Contact contact = contactoptional.get();

		m.addAttribute("contact", contact);
		m.addAttribute("title", "Update-contact Manager");
		return "normal/Update_contact";

	}

	@PostMapping("/process-Contact")
	public String processCon(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal, Model m, HttpSession session) {

		try {

			Contact oldContact = contactrepo.findById(contact.getCid()).get();

			if (file.isEmpty()) {

				//
				contact.setImage(oldContact.getImage());

			} else {
				File file2 = new ClassPathResource("static/images").getFile();

				Path path = Paths.get(file2.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				contact.setImage(file.getOriginalFilename());

			}

			String name = principal.getName();
			User userName = userrepo.getUserName(name);
			contact.setUser(userName);

			this.contactrepo.save(contact);
			session.setAttribute("message", new message("Sucessfully Updated...!!", "success"));

		} catch (Exception e) {
			session.setAttribute("message", new message("fail to Updated...!!", "danger"));

			e.printStackTrace();
		}

		m.addAttribute("contact", contact);

		return "normal/Update_contact";

	}
	
	
	
	
	
	@RequestMapping("/profile")
	public String profile(Model m) {
		
		
		
		m.addAttribute("title", "Profile-contact Manager");
		return "normal/profile";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
