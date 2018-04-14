package cse308.zsyj.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Objects.CongressionalDistrict;
import Objects.RawCDData;
import Objects.State;
import cse308.zsyj.repository.CDRepository;
import cse308.zsyj.repository.PrecinctRepository;
import cse308.zsyj.repository.StateRepository;

public class StateController {
	@Autowired
	PrecinctRepository precinctRepository;
	@Autowired
	StateRepository stateRepository;
	@Autowired
	CDRepository cdRepository;
	
	public void saveState(RawCDData cd) {
		for(int i =0;i<cd.features.size();i++) {
			precinctRepository.save(cd.features.get(i).properties);
		}
	}
	public State getState(String stateName, int year) {
		int stateID = stateRepository.findByNameAndYear(stateName, year);
		List <CongressionalDistrict> cds = cdRepository.findById(stateID);
		for (int i=0;i<cds.size();i++) {
			int cdID = cds.get(i).getId();
			precinctRepository.findAllById(cdID);
		}
		return null;
		
	}
}
