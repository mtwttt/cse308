package cse308.zsyj.controller;

import Objects.State;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("demo")
public class controller {
	@GetMapping("home")
	public String home() {
		return "demo/homepage_sample.html";
	}
	@GetMapping("aboutus")
	public String aboutus() {
		return "demo/aboutus.html";
	}
	@RequestMapping(value="congressional_districts", method=RequestMethod.POST)
	public String congressionaldistricts(State state, Model model) {
		System.out.println("---------------------------------");
		model.addAttribute("state",state);
		System.out.println(state.getName());
		System.out.println("---------------------------------");
		return "demo/congressional_districts.html";
	}
	@GetMapping("credit")
	public String index() {
		return "demo/credit.html";
	}
	@GetMapping("login")
	public String login() {
		return "demo/login.html";
	}
	@GetMapping("register")
	public String register() {
		return "demo/register.html";
	}
	@GetMapping("resetp")
	public String resetp() {
		return "demo/resetp.html";
	}

}