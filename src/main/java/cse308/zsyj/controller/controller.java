package cse308.zsyj.controller;

import Objects.Account;
import Objects.State;
import cse308.zsyj.service.StateService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;

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

import com.google.gson.Gson;

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
	
	@GetMapping("loginpage")
	public String loginPage() {
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
	
	@RequestMapping(value = "login", method=RequestMethod.POST)
	public String login(Account account, Model model) {
		
			if(account.validate()) {
				if(account.isAdmin())
				return "demo/admin.html";
			}
		
		return "demo/login.html";
	}
	
	@RequestMapping(value = "generateBorder", method=RequestMethod.POST)
	public String generateBorder(State state, Model model) {
		String fileUrl = "/json/kansasCD.geojson";
		JSONParser parser = new JSONParser();
		try {
			JSONObject j = (JSONObject) parser.parse(new FileReader(fileUrl));
			System.out.println(j);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	

}