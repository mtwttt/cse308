package cse308.zsyj.controller;

import Objects.Account;
import Objects.Algorithm;
import Objects.CongressionalDistrict;
import Objects.Precinct;
import Objects.RawCDData;
import Objects.State;
import cse308.zsyj.repository.UserRepository;
import cse308.zsyj.service.StateService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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
public class PageController {
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	@Autowired
	StateService stateService;
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("home")
	public String home() {
		return "demo/home.html";
	}
	
	@GetMapping("aboutus")
	public String aboutus() {
		return "demo/aboutus.html";
	}
	
	@GetMapping("manageUser")
	public String manageUser(Model model) {
		ArrayList<Account> accounts = (ArrayList<Account>) userRepo.getUsers();
		model.addAttribute("accounts", accounts);
		return "demo/manageUser.html";
	}
	
	@RequestMapping(value="CD", method=RequestMethod.POST)
	public String congressionaldistricts(State state, Model model) {
		model.addAttribute("state",state);
		return "demo/congressionalD.html";
	}
	
	@RequestMapping(value="loading", method=RequestMethod.POST)
	public String loading(State state, Model model) {
		model.addAttribute("state",state);
		return "demo/loading.html";
	}
	@RequestMapping(value="redraw", method=RequestMethod.POST)
	public @ResponseBody
	State startAlgo(@RequestParam("name") String name,@RequestParam("year") int year, 
			@RequestParam("populationW") int populationW,@RequestParam("racialW") int racialW,
			@RequestParam("partisanW") int partisanW,@RequestParam("compactnessW") int compactnessW,
			@RequestParam("selectpid") String selectpid) {
		System.out.println(name);
		System.out.println(populationW);
		System.out.println(partisanW);
		System.out.println(racialW);
		System.out.println(year);
		System.out.println(compactnessW);
		System.out.println(selectpid);

		Algorithm weight = new Algorithm();
		weight.setcompactnessW(compactnessW);
		weight.setpartisanW(partisanW);
		weight.setPopulationW(populationW);
		weight.setracialW(racialW);
		weight.setYear(year);
		List<Integer> pids = new ArrayList<Integer>();
		if(!selectpid.equals("")) {
			String strarray[] = selectpid.split(",");
			int intarray[] = new int[strarray.length];
			for (int i = 0; i < intarray.length ; i++) {
			    pids.add(Integer.parseInt(strarray[i]));
			}
		}
		State state = stateService.getState(name, weight.getYear()); 
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
		state = weight.startAlgorithm(state);
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
	    return state;
	}
	/*
	 * 
	 * 	
	@RequestMapping(value="redraw", method=RequestMethod.POST)
	public ResponseEntity<?> startAlgo( @RequestBody Algorithm weight, String name,String selectpid,Model model) {
			System.out.println(selectpid);
			System.out.println("11111111111111111111");
			List<Integer> pids = new ArrayList<Integer>();
			if(!selectpid.equals("")) {
				String strarray[] = selectpid.split(",");
				int intarray[] = new int[strarray.length];
				for (int i = 0; i < intarray.length ; i++) {
				    pids.add(Integer.parseInt(strarray[i]));
				}
			}
			State state = stateService.getState(name, weight.getYear()); 
			state.setSeletedPids(pids);
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
			state = weight.startAlgorithm(state);
			model.addAttribute("state",state);
			model.addAttribute("pids",state.getBorderDict());
			return ResponseEntity.ok(state);
	}
	*/
	@GetMapping("credit")
	public String index() {
		return "demo/credit.html";
	}
	
	@GetMapping("loginpage")
	public String loginPage() {
		return "demo/login.html";
	}
	
	@GetMapping("editUser")
	public String editUser() {
		
		return "demo/editUser.html";
	}
	
	@GetMapping("register")
	public String register() {
		
		return "demo/register.html";	
	}
	
	@GetMapping("verify")
	public String verify() {
		
		return "demo/verify.html";	
	}
	
	@GetMapping("verifyAccount")
	public String verifyAccount(Account account) {
		String vkey = account.getVkey();
		String username = account.getUsername();
		System.out.println("vkey:"+vkey+" "+username);
		Account a = userRepo.getAccount(username);
		if(a==null) 
			return "demo/verify.html";
		if(	a.setVerified(vkey)) {
			userRepo.save(a);
			return "demo/login.html";
		}
		else {
			return "demo/verify.html";	
		}
	}
	
	@GetMapping("regist")
	public String regist(Account account, Model model) {
		boolean check = false;
		check = account.checkPsw();
		if(check) {
			String vkey = "";
			for (int i = 0; i<8; i++) {
				vkey += AB.charAt((int)(Math.random()*62));
			}
			account.setIsAdmin(false);
			account.setVkey(vkey);
			account.sendEmail();
			userRepo.save(account);
			return "demo/login.html";
		}
		return "demo/register.html";	
	}
	
	
	@GetMapping("resetp")
	public String resetp() {
		return "demo/resetp.html";
	}
	
	@RequestMapping(value = "login", method=RequestMethod.POST)
	public String login(Account account, Model model) {
			String username = account.getUsername();
			String password = account.getPassword();
			int check = userRepo.verified(username, password);
			System.out.print("asdasd+"+check);
			if ( check == 1) {
				account = userRepo.getAccount(username);
				if(account.isAdmin())
					return "demo/admin.html";
				return "demo/home.html";
			}
			else {
				
			}
		return "demo/login.html";
	}
	
	@RequestMapping(value = "generateBorder", method=RequestMethod.POST)
	public String generateBorder(State state, Model model) {
		String fileUrl = "./src/main/resources/static/json/kansasCD2010.geojson";
		try {
			RawCDData cdBoundary = new Gson().fromJson(new FileReader(fileUrl), 
					RawCDData.class);
			state = stateService.getState(state.getName(), 2008);
			for(int i=0;i<cdBoundary.features.size();i++) {
				List<List<List<Double>>> coordinates = 
						cdBoundary.features.get(i).geometry.coordinates;
				state.generateBorder(coordinates);
			}
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("state", state);
		model.addAttribute("pids",state.getBorderPrecinctIDs());
		return "demo/generateBorder.html";	
	}
}