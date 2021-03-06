package cse308.zsyj.controller;

import Objects.Account;
import Objects.Algorithm;
import Objects.CongressionalDistrict;
import Objects.Precinct;
import Objects.RawCDData;
import Objects.State;
import Objects.StateManager;
import Objects.StateStat;
import cse308.zsyj.repository.CDRepository;
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
import com.rits.cloning.Cloner;

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
	@Autowired
	CDRepository cdRepository;
	
	@GetMapping("home")
	public String home(HttpSession httpSession) {
		
		if(httpSession.getAttribute("user")!=null&& !((boolean)(((String) httpSession.getAttribute("user")).equals("admin"))) 
				&& !((boolean)(((String) httpSession.getAttribute("user")).equals(""))) ) {
			return "demo/home.html";
		}
		return "demo/login.html";
	}
	
	@GetMapping("welcome")
	public String welcome() {
		
		return "demo/welcome.html";
	}
	@GetMapping("logout")
	public String logout(HttpSession httpSession) {
		httpSession.setAttribute("user", "");
		return "demo/welcome.html";
	}
	
	@GetMapping("aboutus")
	public String aboutus(HttpSession httpSession) {
		return "demo/aboutus.html";
	}
	@GetMapping("repKS")
	public String repKS(HttpSession httpSession) {
		return "demo/kansasRepresent.html";
	}
	@GetMapping("repID")
	public String repID(HttpSession httpSession) {
		return "demo/idahoRepresent.html";
	}
	@GetMapping("repCO")
	public String repCO(HttpSession httpSession) {
		return "demo/coloradoRepresent.html";
	}
	
	@GetMapping("manageUser")
	public String manageUser(Model model,HttpSession httpSession) {
		if(httpSession.getAttribute("user")!=null&&(boolean)(((String) httpSession.getAttribute("user")).equals("admin"))) {
			
		ArrayList<Account> accounts = (ArrayList<Account>) userRepo.getUsers();
		model.addAttribute("accounts", accounts);
		return "demo/manageUser.html";
		}
		return "demo/login.html";
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
		if(httpSession.getAttribute("user")!=null&&(boolean)(((String) httpSession.getAttribute("user")).equals("admin"))) {
			
		List<StateStat> s =  (List<StateStat>) statRepository.findAll();
		httpSession.setAttribute("ksCount", s.get(0).getCount());
		httpSession.setAttribute("idCount", s.get(1).getCount());
		httpSession.setAttribute("coCount", s.get(2).getCount());
		return "demo/stat.html";
		}
		return "demo/login.html";
	}
	
	@RequestMapping(value="CD", method=RequestMethod.POST)
	public String congressionaldistricts(State state, Model model, HttpSession httpSession) {
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
		List<CongressionalDistrict> cdlist = cdRepository.findAllById(id, 2008);
		for(int i =1; i<cdlist.size()+1; i++) {
			String attr = "cd"+i;
			httpSession.setAttribute(attr, cdlist.get(i-1).toString());
		}
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
		}else {
			System.out.println("Yes");
		}
		return "got it";
	}
	
	@RequestMapping(value="resetMap", method=RequestMethod.POST)
	public @ResponseBody String resetMap(@RequestParam("name") String name, HttpSession httpSession) {
		StateManager.state = stateService.getState(name, 2008);
		Algorithm.improvedTimes = 0;
		Algorithm.failedTimes = 0;
		Algorithm.stop =0;
		Algorithm.running = true;
		int id = 1;
		if(name.equals("colorado"))
			id = 3;
		else if(name.equals("idaho"))
			id = 2;
		List<CongressionalDistrict> cdlist = cdRepository.findAllById(id, 2008);
		for(int i =1; i<cdlist.size()+1; i++) {
			String attr = "cd"+i;
			httpSession.setAttribute(attr, cdlist.get(i-1).toString());
		}
		return "got it";
	}
	
	@RequestMapping(value="moveP", method=RequestMethod.POST)
	public @ResponseBody int moveP(@RequestParam("moveP") int moveP, HttpSession httpSession) {
		Algorithm temp = new Algorithm();
		int movedCD = temp.manualMove(StateManager.state, moveP);
		for (int i = 1; i<StateManager.state.getCongressionalDistrict().size()+1 ;i++) {
			String attr = "cd"+i;
			httpSession.setAttribute(attr, StateManager.state.getCongressionalDistrict().get(i-1).toString());
		}
		return movedCD;
	}
	
	@RequestMapping(value="redraw", method=RequestMethod.POST)
	public @ResponseBody
	Hashtable<Integer,Integer> startAlgo(@RequestParam("name") String name,@RequestParam("year") int year, 
			@RequestParam("populationW") int populationW,@RequestParam("racialW") int racialW,
			@RequestParam("partisanW") int partisanW,@RequestParam("compactnessW") int compactnessW,
			@RequestParam("selectpid") String selectpid,
			@RequestParam("contiguity") boolean contiguity,
			@RequestParam("representative") boolean representative,
			Model model,
			HttpSession httpSession) {

		Algorithm weight = new Algorithm();
		if (contiguity)
			Algorithm.contigConstraint = 1;
		else
			Algorithm.contigConstraint = 0;
		if (representative)
			Algorithm.repConstraint = 1;
		else
			Algorithm.repConstraint = 0;
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
			System.out.println("herexxxxxxxxxxx");
			return new Hashtable<Integer,Integer>();
		}
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println(Algorithm.failedTimes);
		for (int i = 1; i<state.getCongressionalDistrict().size()+1 ;i++) {
			String attr = "cd"+i;
			httpSession.setAttribute(attr, state.getCongressionalDistrict().get(i-1).toString());
		}
	    return state.getBorderDict();
	}
	@GetMapping("credit")
	public String index(HttpSession httpSession) {
		return "demo/credit.html";
	}
	
	@GetMapping("loginpage")
	public String loginPage(HttpSession httpSession) {
		httpSession.setAttribute("loginError", "False");
		return "demo/login.html";
	}
	
	@GetMapping("editUser")
	public String editUser() {
		
		return "demo/editUser.html";
	}
	
	@GetMapping("register")
	public String register(HttpSession httpSession) {
		httpSession.setAttribute("registError", "False");
		return "demo/register.html";	
	}
	
	@GetMapping("verify")
	public String verify(HttpSession httpSession) {
		httpSession.setAttribute("verifyError", "False");
		return "demo/verify.html";	
	}
	
	@GetMapping("verifyAccount")
	public String verifyAccount(Account account, HttpSession httpSession) {
		String vkey = account.getVkey();
		String username = account.getUsername();
		System.out.println("vkey:"+vkey+" "+username);
		Account a = userRepo.getAccount(username);
		if(a==null) {
			httpSession.setAttribute("verifyError", "True");
			return "demo/verify.html";
			}
		if(	a.setVerified(vkey)) {
			httpSession.setAttribute("verifyError", "False");
			userRepo.save(a);
			return "demo/login.html";
		}
		else {
			httpSession.setAttribute("verifyError", "True");
			return "demo/verify.html";	
		}
	}
	
	@GetMapping("regist")
	public String regist(Account account, Model model, HttpSession httpSession) {
		boolean check = false;
		check = account.checkPsw();
		if(check) {
			try {
				String vkey = "";
				for (int i = 0; i<8; i++) {
					vkey += AB.charAt((int)(Math.random()*62));
				}
				account.setIsAdmin(false);
				account.setVkey(vkey);
				account.sendEmail();
				userRepo.save(account);
				httpSession.setAttribute("registError", "False");
				return "demo/login.html";
			}
			catch(Exception e) {}
		}
		httpSession.setAttribute("registError", "True");
		return "demo/register.html";	
	}
	
	
	@GetMapping("resetp")
	public String resetp() {
		return "demo/resetp.html";
	}
	
	@GetMapping("admin")
	public String admin(HttpSession httpSession) {
		
		if(httpSession.getAttribute("user")!=null&&(boolean)(((String) httpSession.getAttribute("user")).equals("admin"))) {
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
					httpSession.setAttribute("user", "admin");
					return "demo/admin.html";
				}
				httpSession.setAttribute("user", username);
				return "demo/home.html";
			}
			else {
				
			}
			httpSession.setAttribute("loginError", "True");
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
			//System.out.println(state.getCongressionalDistrict().size());
			//System.out.println(cdBoundary.features.size());
			for(int i=0;i<cdBoundary.features.size();i++) {
				List<List<List<Double>>> coordinates = 
						cdBoundary.features.get(i).geometry.coordinates;
				state.generateBorder(coordinates);
			}
			
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		//stateService.updateBorder(state, true);
		//System.out.println(state.getBorderPrecinctIDs());
		model.addAttribute("state", state);
		model.addAttribute("pids",state.getBorderPrecinctIDs());
		return "demo/generateBorder.html";	
	}
	
	@RequestMapping(value = "compareState", method=RequestMethod.POST)
	public String compareState(String state,Model model) {
		Cloner clone = new Cloner();	
		State original = stateService.getState(state, 2008);
		original = original.clearCoor(original);
		original.setCDsize();
		System.out.println(original.getCongressionalDistrict().size());
		State current = clone.deepClone(StateManager.state);
		//get pids
		model.addAttribute("pids",current.getBorderDict());
		String oString = "";
		for(CongressionalDistrict c: original.getCongressionalDistrict()) {
			oString += "CD ID: "+ c.getId()+"<br>"
					  +"Year: "+c.getYear()+"<br>"
					  +"totalVote: "+c.getTotalVote()+"<br>"
					  + "Party: " +c.getParty()+"<br>"
					  +"Republican Vote: "+c.getRepublicanVote()+"<br>"
					  +"Democratic Vote: "+c.getDemocratVote()+"<br>"
					  +"Total Population: "+c.getTotalPopulation()+"<br>";
		}
		current = current.clearCoor(current);
		current.setCDsize();
		String cString = "";
		for(CongressionalDistrict c: current.getCongressionalDistrict()) {
			  cString += "CD ID: "+ c.getId()+"<br>"
					  +"Year: "+c.getYear()+"<br>"
					  +"totalVote: "+c.getTotalVote()+"<br>"
					  + "Party: " +c.getParty()+"<br>"
					  +"Republican Vote: "+c.getRepublicanVote()+"<br>"
					  +"Democratic Vote: "+c.getDemocratVote()+"<br>"
					  +"Total Population: "+c.getTotalPopulation()+"<br>";
		}
		System.out.println(current.getCongressionalDistrict().size());
		model.addAttribute("name",state);
		model.addAttribute("original",original);
		model.addAttribute("current",current);
		model.addAttribute("oString",oString);
		model.addAttribute("cString",cString);
		return "demo/compareState.html";	
	}
	
	@RequestMapping(value = "threeState", method=RequestMethod.POST)
	public String threeState(Model model) {
		State kansas = stateService.getState("kansas", 2008);
		
		kansas = kansas.clearCoor(kansas);
		kansas.setCDsize();
		State colorado = stateService.getState("colorado", 2008);
		colorado = colorado.clearCoor(colorado);
		colorado.setCDsize();
		State idaho = stateService.getState("idaho", 2008);
		idaho = idaho.clearCoor(idaho);
		idaho.setCDsize();
		
		model.addAttribute("kansas",kansas);
		model.addAttribute("colorado",colorado);
		model.addAttribute("idaho",idaho);
		return "demo/threeState.html";	
	}
}



