package cse308.zsyj.service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import Objects.CongressionalDistrict;
import Objects.Coordinate;
import Objects.Precinct;
import Objects.RawCDData;
import Objects.State;
import Objects.StateManager;
import cse308.zsyj.repository.CDRepository;
import cse308.zsyj.repository.CoordinateRepository;
import cse308.zsyj.repository.PrecinctRepository;
import cse308.zsyj.repository.StateRepository;

@Service
public class StateService {
		@Autowired
		PrecinctRepository precinctRepository;
		@Autowired
		StateRepository stateRepository;
		@Autowired
		CDRepository cdRepository;
		@Autowired
		CoordinateRepository coordinateRepository;
		/*
		public void saveState(RawCDData cd) {
			for(int i =0;i<cd.features.size();i++) {
				precinctRepository.save(cd.features.get(i).properties);
				List<List<Double>> coordinates = cd.features.get(i).geometry.coordinates.get(0);
				for(int j =0; j<coordinates.size();j++) {
					Coordinate c = new Coordinate(coordinates.get(j).get(0),coordinates.get(j).get(1),cd.features.get(i).properties.getID());
					coordinateRepository.save(c);
				}
			}
		}
		public void saveState(State state) {
			stateRepository.save(state);
			List<CongressionalDistrict> cds = state.getCongressionalDistrict();
			for(int i=0;i<cds.size();i++) {
				cdRepository.save(cds.get(0));
				List<Precinct> precincts = cds.get(i).getPrecincts();
				for(int j =0 ; j<precincts.size();j++) {
					precinctRepository.save(precincts.get(j)); 
				}
			}
		}
		*/
		public State getState(String stateName, int year) {
			int stateID = stateRepository.findByNameAndYear(stateName, year);
			State state = stateRepository.findById(stateID).get();
			List <CongressionalDistrict> cds = cdRepository.findAllById(stateID,year);
			for (int i=0;i<cds.size();i++) {
				int cdID = cds.get(i).getId();
				List<Precinct> precincts = precinctRepository.findAllByCD(cdID,stateID,year);
				cds.get(i).setPrecincts(precincts);
				cds.get(i).setState(state);
			}
			state.setCongressionalDistrict(cds);
			String fileUrl = "./src/main/resources/static/json/"+stateName+".json";
			try {
				RawCDData cdBoundary = new Gson().fromJson(new FileReader(fileUrl), 
						RawCDData.class);
			for(CongressionalDistrict c : state.getCongressionalDistrict()) {
				for(Precinct p: c.getPrecincts()) {
					for(int i=0;i<cdBoundary.features.size();i++) {
						if(cdBoundary.features.get(i).pid == p.getID()) {
							p.setCoordinate(cdBoundary.features.get(i).geometry.coordinates);
							break;
						}
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return state;
		}
		
		public void updateBorder(State state, boolean updateSQL) {
			if (updateSQL) {
				ArrayList<Precinct> ps = state.getBorderPrecincts();
				for(int i = 0; i < ps.size(); i++) {
					precinctRepository.save(ps.get(i));
				}
			}
		}
}
