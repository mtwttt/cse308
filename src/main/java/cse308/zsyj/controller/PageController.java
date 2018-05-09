package cse308.zsyj.controller;

import Objects.Account;
import Objects.Algorithm;
import Objects.CongressionalDistrict;
import Objects.Precinct;
import Objects.RawCDData;
import Objects.State;
import Objects.StateManager;
import Objects.StateStat;
import cse308.zsyj.repository.StateStatRepository;
import cse308.zsyj.repository.UserRepository;
import cse308.zsyj.service.StateService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpSession;

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
@Scope("session")
@RequestMapping("demo")
public class PageController {
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	@Autowired
	StateService stateService;
	@Autowired
	UserRepository userRepo;
	@Autowired
	StateStatRepository statRepository;
	
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
	@RequestMapping(value="addUser", method=RequestMethod.POST)
	public String addUser(HttpSession httpSession) {
		return "demo/addUser.html";
	}
	@RequestMapping(value="editUser", method=RequestMethod.POST)
	public String editUser(@RequestParam(name ="username") String username,HttpSession httpSession) {
		System.out.println(username);
		httpSession.setAttribute("editUsername", username.substring(0, username.length()-1));
		return "demo/editUser.html";
	}
	@RequestMapping(value="deleteUser", method=RequestMethod.POST)
	public String deleteUser(Model model,@RequestParam(name ="deleteUsername") String username,HttpSession httpSession) {
		//Account account = new Account();
		//account.setUsername(username);
		userRepo.deleteById(username.substring(0, username.length()-1));
		ArrayList<Account> accounts = (ArrayList<Account>) userRepo.getUsers();
		model.addAttribute("accounts", accounts);
		return "demo/manageUser.html";
	}
	@RequestMapping(value="edit", method=RequestMethod.POST)
	public String edit(Model model, Account account,@RequestParam(name ="verified") String v,HttpSession httpSession) {
		Account original = userRepo.getAccount((String)httpSession.getAttribute("editUsername"));
		if(!account.getUsername().equals("")) {
			original.setUsername(account.getUsername());
		}
		if(!account.getEmail().equals("")) {
			original.setEmail(account.getEmail());
		}
		if(!account.getVkey().equals("")) {
			original.setVkey(account.getVkey());
		}
		if(!account.getPassword().equals("")) {
			original.setPassword(account.getPassword());
		}
		if(v.equals("True")) {
			original.setIsVerified(true);
		}
		else if (v.equals("False")) {
			original.setIsVerified(false);
		}
		userRepo.save(original);
		ArrayList<Account> accounts = (ArrayList<Account>) userRepo.getUsers();
		model.addAttribute("accounts", accounts);
		return "demo/manageUser.html";
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String add(Model model, Account account,@RequestParam(name ="verified") String v,@RequestParam(name ="IsAdmin") String a) {
		Account original = new Account();
		if(!account.getUsername().equals("")) {
			original.setUsername(account.getUsername());
		}
		if(!account.getEmail().equals("")) {
			original.setEmail(account.getEmail());
		}
		if(!account.getVkey().equals("")) {
			original.setVkey(account.getVkey());
		}
		if(!account.getPassword().equals("")) {
			original.setPassword(account.getPassword());
		}
		if(v.equals("True")) {
			original.setIsVerified(true);
		}
		else if (v.equals("False")) {
			original.setIsVerified(false);
		}
		if(a.equals("True")) {
			original.setIsAdmin(true);
		}
		else if (a.equals("False")) {
			original.setIsAdmin(false);
		}
		userRepo.save(original);
		ArrayList<Account> accounts = (ArrayList<Account>) userRepo.getUsers();
		model.addAttribute("accounts", accounts);
		return "demo/manageUser.html";
	}
	
	@GetMapping("changeExternal")
	public String changeExternal(Model model) {
		return "demo/changeExternal.html";
	}
	
	@GetMapping("statistics")
	public String statistics(Model model, HttpSession httpSession) {
		List<StateStat> s =  (List<StateStat>) statRepository.findAll();
		httpSession.setAttribute("ksCount", s.get(0).getCount());
		httpSession.setAttribute("idCount", s.get(1).getCount());
		httpSession.setAttribute("coCount", s.get(2).getCount());
		return "demo/stat.html";
	}
	
	@RequestMapping(value="CD", method=RequestMethod.POST)
	public String congressionaldistricts(State state, Model model) {
		StateManager.state = stateService.getState(state.getName(), 2008);
		Algorithm.improvedTimes = 0;
		Algorithm.failedTimes = 0;
		Algorithm.stop =0;
		int id = 1;
		if(state.getName().equals("colorado"))
			id = 3;
		else if(state.getName().equals("idaho"))
			id = 2;
		StateStat stat= statRepository.findById(id).get();
		stat.setCount(stat.getCount()+1);
		statRepository.save(stat);
		return "demo/congressionalD.html";
	}
	
	@RequestMapping(value="loading", method=RequestMethod.POST)
	public String loading(State state, Model model) {
		model.addAttribute("state",state);
		return "demo/loading.html";
	}
	

	@RequestMapping(value="stop", method=RequestMethod.POST)
	public @ResponseBody String stop(boolean stop) {
		Algorithm.running = stop;
		if(Algorithm.running == false) {
			System.out.println("1231241");
		}
		return "got it";
	}
	
	@RequestMapping(value="resetMap", method=RequestMethod.POST)
	public @ResponseBody String reset(String name) {
		Algorithm.improvedTimes = 0;
		Algorithm.failedTimes = 0;
		Algorithm.stop =0;
		StateManager.state = stateService.getState(name, 2008);
		return "got it";
	}
	
	@RequestMapping(value="moveP", method=RequestMethod.POST)
	public @ResponseBody int moveP(@RequestParam("moveP") int moveP) {
		Algorithm temp = new Algorithm();
		int movedCD = temp.manualMove(StateManager.state, moveP);
		return movedCD;
	}
	
	@RequestMapping(value="redraw", method=RequestMethod.POST)
	public @ResponseBody
	Hashtable<Integer,Integer> startAlgo(@RequestParam("name") String name,@RequestParam("year") int year, 
			@RequestParam("populationW") int populationW,@RequestParam("racialW") int racialW,
			@RequestParam("partisanW") int partisanW,@RequestParam("compactnessW") int compactnessW,
			@RequestParam("selectpid") String selectpid, Model model) {
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
		State state = StateManager.state;
		state.setSeletedPids(pids);
		for(int x : pids) {
			System.out.println(x);
		}
		System.out.println(Algorithm.failedTimes);
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
		state = weight.startAlgorithm(state);
		if(Algorithm.stop == 1) {
			return new Hashtable<Integer,Integer>();
		}
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println(Algorithm.failedTimes);

	    return state.getBorderDict();
	}
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
	
	@GetMapping("admin")
	public String admin(HttpSession httpSession) {
		if(httpSession.getAttribute("isAdmin")!=null&&(boolean)(httpSession.getAttribute("isAdmin"))==true) {
			return "demo/admin.html";
		}
		return "demo/login.html";
	}
	
	@RequestMapping(value = "login", method=RequestMethod.POST)
	public String login(Account account, Model model, HttpSession httpSession) {
			String username = account.getUsername();
			String password = account.getPassword();
			int check = userRepo.verified(username, password);
			System.out.print("asdasd+"+check);
			if ( check == 1) {
				account = userRepo.getAccount(username);
				if(account.isAdmin()) {
					httpSession.setAttribute("isAdmin", true);
					return "demo/admin.html";
				}
				httpSession.setAttribute("username", username);
				return "demo/home.html";
			}
			else {
				
			}
		return "demo/login.html";
	}
	
	
	@RequestMapping(value = "generateBorder", method=RequestMethod.POST)
	public String generateBorder(State state, Model model) {
		String filename = state.getName().toLowerCase();
		String fileUrl = "./src/main/resources/static/json/"+filename+"CD.geojson";
		//state = stateService.getState(state.getName(), 2008);
		//state.generateBorder2();
		
		try {
			RawCDData cdBoundary = new Gson().fromJson(new FileReader(fileUrl), 
					RawCDData.class);
			state = stateService.getState(state.getName(), 2008);
			System.out.println(state.getCongressionalDistrict().size());
			System.out.println(cdBoundary.features.size());
			for(int i=0;i<cdBoundary.features.size();i++) {
				List<List<List<Double>>> coordinates = 
						cdBoundary.features.get(i).geometry.coordinates;
				state.generateBorder(coordinates);
			}
			
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		//stateService.updateBorder(state, true);
		System.out.println(state.getBorderPrecinctIDs());
		model.addAttribute("state", state);
		model.addAttribute("pids",state.getBorderPrecinctIDs());
		return "demo/generateBorder.html";	
	}
}