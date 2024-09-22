package com.tokioshool.filmotokio.controller;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tokioshool.filmotokio.domain.Role;
import com.tokioshool.filmotokio.domain.User;
import com.tokioshool.filmotokio.service.RoleService;
import com.tokioshool.filmotokio.service.UserService;

@Controller
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);
			
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/registration")
	public String registerUser(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@GetMapping("/login")
	public String login(@ModelAttribute User user) {
		return "login";
	}
	
	@PostMapping("/new-user")
	public String addUser(@ModelAttribute User user, Model model, RedirectAttributes rAttributes)throws Exception {
		Pattern patUsername = Pattern.compile("^[0-9a-zA-Z]+");
		Matcher username = patUsername.matcher(user.getUsername());
		Pattern patEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher email = patEmail.matcher(user.getEmail());
		Pattern patName = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+");
		Matcher name = patName.matcher(user.getName());
		Matcher surname = patName.matcher(user.getSurname());
		if(username.matches() && email.matches() && name.matches() && surname.matches()) {
			boolean userAdded = userService.add(user);
			if(!userAdded) {
				throw new Exception();
			}
			logger.info(user.getUsername() + " adicionado");
			model.addAttribute("user", user);
			rAttributes.addFlashAttribute("messagem", "Usuario adicionado");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-success");
		}else if(!username.matches()){
			rAttributes.addFlashAttribute("messagem", "Usuario incorreto");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-danger");
		}else if(!email.matches()){
			rAttributes.addFlashAttribute("messagem", "E-mail incorreto");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-danger");
		}else if(!name.matches()){
			rAttributes.addFlashAttribute("messagem", "nome incorreto");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-danger");
		}else if(!surname.matches()){
			rAttributes.addFlashAttribute("messagem", "Apelido incorreto");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-danger");
		}
		return "redirect:/registration";
	}
	
	@GetMapping("/create-user")
	public String createUser(Model model) {
		Set<Role> roles= roleService.findAll();
		model.addAttribute("user", new User());
		model.addAttribute("roles", roles);
		return "create-user";
	}
	
	@PostMapping("/new-user-admin")
	public String addUserAdmin(@ModelAttribute User user, Model model, RedirectAttributes rAttributes)throws Exception {
		Pattern patUsername = Pattern.compile("^[0-9a-zA-Z]+");
		Matcher username = patUsername.matcher(user.getUsername());
		Pattern patEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher email = patEmail.matcher(user.getEmail());
		Pattern patName = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+");
		Matcher name = patName.matcher(user.getName());
		Matcher surname = patName.matcher(user.getSurname());
		if(username.matches() && email.matches() && name.matches() && surname.matches()) {
			boolean userAdded = userService.addAdmin(user);
			if(!userAdded) {
				throw new Exception();
			}
			logger.info(user.getUsername() + " adicionado");
			rAttributes.addFlashAttribute("messagem", "Usuario adicionado");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-success");
		}else if(!username.matches()){
			rAttributes.addFlashAttribute("messagem", "Usuario incorreto");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-danger");
		}else if(!email.matches()){
			rAttributes.addFlashAttribute("messagem", "E-mail incorreto");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-danger");
		}else if(!name.matches()){
			rAttributes.addFlashAttribute("messagem", "nome incorreto");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-danger");
		}else if(!surname.matches()){
			rAttributes.addFlashAttribute("messagem", "Apelido incorreto");
			rAttributes.addFlashAttribute("messagemclass", "alert alert-danger");
		}
		return "redirect:/create-user";
	}

	@ExceptionHandler()
	public ModelAndView handleException(HttpServletRequest request, Exception exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", "Não foi possível registar o utilizador");
		mav.addObject("exception", exception);
		mav.addObject("url", request.getRequestURL());
		mav.setViewName("error");
		return mav;
	}
}

