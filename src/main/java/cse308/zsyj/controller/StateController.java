package cse308.zsyj.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Objects.CongressionalDistrict;
import Objects.Coordinate;
import Objects.Precinct;
import Objects.RawCDData;
import Objects.State;
import cse308.zsyj.repository.CDRepository;
import cse308.zsyj.repository.CoordinateRepository;
import cse308.zsyj.repository.PrecinctRepository;
import cse308.zsyj.repository.StateRepository;
import cse308.zsyj.service.StateService;
@Controller
public class StateController {
	@Autowired
	StateService stateService;
	
	public String getState(State state,int year) {
		state = stateService.getState(state.getName(), 2008);
		System.out.println(state.getId());
		return "";
	}
}
