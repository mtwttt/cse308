package cse308.zsyj.controller;

import Objects.Account;
import Objects.CongressionalDistrict;
import Objects.Precinct;
import Objects.RawCDData;
import Objects.State;
import cse308.zsyj.service.StateService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@Controller
@RequestMapping("demo")
public class controller {
	@Autowired
	StateService stateService;
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
		//state = stateService.getState(state.getName(), 2008);
		//System.out.println(state.getCongressionalDistrict().get(0).getPrecincts().get(0).getCoordinate().get(0).get(0).get(0));
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
		String fileUrl = "./src/main/resources/static/json/kansasCD2010.geojson";
		try {
			RawCDData cdBoundary = new Gson().fromJson(new FileReader(fileUrl), RawCDData.class);
			state = stateService.getState(state.getName(), 2008);
			for(int i=0;i<cdBoundary.features.size();i++) {
				List<List<List<Double>>> coordinates = cdBoundary.features.get(i).geometry.coordinates;
				state.generateBorder(coordinates);
			}
			int t =0;
			for(int i=0;i<state.getCongressionalDistrict().size();i++) {
				CongressionalDistrict cd = state.getCongressionalDistrict().get(i);
				for(int j=0;j<cd.getPrecincts().size();j++) {
					Precinct p = cd.getPrecincts().get(j);
					if(p.isBorder==1)
						t++;
				}
			}
			System.out.println(t);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		model.addAttribute("state", state);
		return "/demo/congressional_districts.html";
		
	}
	

}