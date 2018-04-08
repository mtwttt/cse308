package cse308.zsyj.controller;

import Objects.State;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("demo")
public class controller {
	@GetMapping("home")
	public String home() {
		System.out.println("---------------------------------");
		return "demo/homepage_sample.html";
	}
	
	@GetMapping("aboutus")
	public String aboutus() {
		System.out.println("---------------------------------");
		return "demo/aboutus.html";
	}
	@RequestMapping(value="congressional_districts", method=RequestMethod.POST)
	public String congressionaldistricts() {
		return "demo/congressional_districts.html";
	}
	@GetMapping("credit")
	public String index() {
		System.out.println("---------------------------------");
		return "demo/credit.html";
	}
	@GetMapping("login")
	public String login() {
		System.out.println("---------------------------------");
		return "demo/login.html";
	}
	@GetMapping("register")
	public String register() {
		System.out.println("---------------------------------");
		return "demo/register.html";
	}
	@GetMapping("resetp")
	public String resetp() {
		System.out.println("---------------------------------");
		return "demo/resetp.html";
	}

}