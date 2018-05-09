package Objects;

import java.util.ArrayList;
import java.io.*;
import java.util.List;

import com.rits.cloning.Cloner;

public class Algorithm {
	private Integer populationW;
	private Integer compactnessW;
	private Integer racialW;
	private Integer partisanW;
	private Integer year;
	public static boolean running;
	public static int improvedTimes;
	public static int failedTimes;
	public static int repConstraint;
	public static int contigConstraint;
	
	
	
	public Algorithm() {
		improvedTimes = 0;
		failedTimes = 0;
	}
	
	public void setPopulationW(int weight) {
		this.populationW = weight;
	}
	
	public void setcompactnessW(int weight) {
		this.compactnessW = weight;
	}
	
	
	public void setracialW(int weight) {
		this.racialW = weight;
	}
	
	public void setpartisanW(int weight) {
		this.partisanW = weight;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getYear() {
		return year;
	}
	public double getPopulationW() {
		return populationW;
	}

	public double getCompactnessW() {
		return compactnessW;
	}

	public double getRacialW() {
		return racialW;
	}

	public double getPartisanW() {
		return partisanW;
	}

	public double calculateCDGoodness(CongressionalDistrict CD) {
		double goodness = 0.0;
		goodness = CD.getPopulationScore() * populationW  
				+ CD.getCompactnessScore() * compactnessW 
				+ CD.getPartisanFairnessScore() * partisanW  
				+ CD.getRacialFairnessScore() * racialW;
		return goodness;
	}
	
//	public boolean markTheP(Precinct p, CongressionalDistrict CD, int dep) {
//		System.out.println("Processing "+p.getID());
//		if (dep>=100)
//			return false;
//		for (Precinct pp: this.getNeighborInSameCD(p, CD)) {
//			if (pp.isChecked==0) {
//				pp.isChecked=1;
//				return markTheP(pp, CD, dep+1);
//			}
//			else if (pp.isChecked==1) {
//				return true;
//			}
//		}
//		return false;
//	}
	public static int conFlag;
	
	public void checked(int curr, int target, List<Precinct> pN, CongressionalDistrict CD) {
		System.out.println("curr = "+curr);
		if (curr==target) {
			conFlag = 1;
			return;
		}
		List<Precinct> lop = this.getNeighborInSameCD(pN.get(curr), CD);
		for (Precinct pi:lop) {
			for (int i=0; i<pN.size();i++) {
				if (pN.get(i).getID()==pi.getID() && pN.get(i).isChecked==0) {
					pN.get(i).isChecked=1;
					checked(i,target,pN,CD);
					pN.get(i).isChecked=0;
				}
			}
		}
	}
	
	public boolean checkContigConstraint(Precinct p, CongressionalDistrict targetCD, State state) {
		List<CongressionalDistrict> otherCD = new ArrayList<CongressionalDistrict>();
		CongressionalDistrict thisCD = this.getTargetCD(state, p);
		otherCD.add(targetCD);
		List<Precinct> pN = this.getNeighborInSameCD(p, thisCD);
		if (pN.size()==1) {
			System.out.println("Fail");
			return false;
		}
			
		for (int i=1;i<pN.size();i++) {
			conFlag = 0;
			for (Precinct kk:pN)
				kk.isChecked = 0;
			pN.get(0).isChecked = 1;
			checked(0,i,pN,thisCD);
			if (conFlag==0) {
				System.out.println("Fail");
				return false;
			}
				
		}
		System.out.println("Success");
		return true;
		
		
	}
	
	public State startAlgorithm(State state) {
		running = true;
		this.contigConstraint = 1;
		File logFile = new File("./log/log.txt");
		try {
		for (CongressionalDistrict CD : state.getCongressionalDistrict()) {
			FileWriter writer = new FileWriter(logFile,true);
			writer.append("Getting border precinct from congressional district "+CD.getId()+"\n");
			writer.close();
			List<Precinct> borderPrecincts = CD.getBorderPrecinct();
			List<Precinct> neighbor;
			for(Precinct p:borderPrecincts) {	
				boolean representative = true;
				boolean contiguity = true;
				if (!running) {
					StateManager.state = state;
					return state;
				}
				if (p.getIsUsed()==1) {
					continue;
				}
				List<Integer> selectedP = state.getSelectedPids();
				int selected = -1;
				for (Integer i: selectedP) {
					if (i==p.getID()) {
						selected = 1;
						break;
					}
				}
				if (selected==1) {
					continue;
				}
				neighbor = getNeighborInOtherCD(p,state.getCongressionalDistrict());
				if (repConstraint==1)
				{
					representative = checkRepConstraint(p, CD);
				}
				if (neighbor.size()!=0 && representative && movePrecinct(p,CD,neighbor,state)) {
					improvedTimes++;
					updateSourceCDBorder(p,CD);
					updateTargetCDBorder(neighbor,state);
				}
				else {
					failedTimes++;
				}
				if (improvedTimes>=4 || failedTimes>=50) {
					return state;
				}
					
			}
		
		}
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return state;
	}
	
	public boolean checkRepConstraint(Precinct p, CongressionalDistrict CD) {
		if (p.getID()==CD.getRepLocation())
			return true;
		else
			return false;
	}
	
	public void pauseHandler() {
		if (running)
			running = false;
	}
	
	public void updateSourceCDBorder(Precinct p, CongressionalDistrict CD) {
		List<List<Double>> listOfPoints = p.getCoordinate().get(0);
		for (Precinct pr : CD.getPrecincts()) {
			int flag = 0;
			for (List<Double> l1 : listOfPoints) {
				if(pr.getCoordinate()!= null&& pr.getCoordinate().size()!=0) {
				List<List<Double>> listOfNeighborP = pr.getCoordinate().get(0);
				for (List<Double> l2 : listOfNeighborP) {
					if (l1.get(0).doubleValue()==l2.get(0).doubleValue() && l1.get(1).doubleValue()==l2.get(1).doubleValue()
							&& CD.getId()==pr.getcdNumber()) {
						pr.setBorder(1);
						flag = 1;
						break;
					}
				}
			}
			if(flag == 1)
					break;
			}
		}
	}
	
	public void updateTargetCDBorder(List<Precinct> neighbor, State state) {
		for (Precinct pr : neighbor) {
			List<Precinct> prNeighbor = getNeighborInOtherCD(pr, state.getCongressionalDistrict());
			if (prNeighbor.size()==0)
				pr.setBorder(0);
		}
	}
	
	public boolean movePrecinct(Precinct moveP, CongressionalDistrict CD, List<Precinct> neighbor, State state) {
		File logFile = new File("./log/log.txt");
		try {
			FileWriter writer = new FileWriter(logFile,true);
			if(repConstraint == 1) {
				writer.append("Passed Constrain\n");
			}
			writer.append("Moving Precinct "+moveP.getID()+" From Congressional District " + moveP.getcdNumber()+"\n");
		moveP.setIsUsed(1);
		Cloner cloner = new Cloner();
		for (Precinct targetP: neighbor) {
			CongressionalDistrict targetC = getTargetCD(state,targetP);
			CongressionalDistrict cloneTargetC = cloner.deepClone(targetC);
			CongressionalDistrict cloneSourceC = cloner.deepClone(CD);
			updateCD(cloneTargetC, cloneSourceC, moveP);
			double originalScore = calculateCDGoodness(targetC) + calculateCDGoodness(CD);
			double newScore = calculateCDGoodness(cloneTargetC) + calculateCDGoodness(cloneSourceC);
			writer.append("Moving Precinct to neighbor with original score:" +originalScore+"\n");
			writer.append("The NewScore After moving= " +newScore+"\n");
			if(newScore>originalScore) {
				if (contigConstraint == 1)
				{
					 if (!checkContigConstraint(moveP,targetC, state)) {
						 writer.append("Contiguity Check Failed, moving precinct back" +"\n");
						 continue;
					 }
					 else {
						 writer.append("Contiguity Constraint check passed." +"\n");
					 }
					 
				}
				System.out.println("got it");
				writer.append("Score improved, moving precinct to Congressional District" +targetC.getId()+"\n");
				updateCD(targetC, CD, moveP);
				moveP.setcdNumber(targetC.getId());
				writer.close();
				return true;
			}
			else {
				writer.append("Score did not improved, moving precinct back" +"\n");
			}
		}
		writer.close();
		}catch(Exception e) {
			System.out.println("error "+e);
		}
		return(false);
	}

	public void updateCD(CongressionalDistrict targetC, CongressionalDistrict CD, Precinct moveP) {
		List<Precinct> addedList = targetC.getPrecincts();
		addedList.add(moveP);
		List<Precinct> removeList= CD.getPrecincts();
		for(int i=0;i<removeList.size();i++) {
			if(moveP.getID() == removeList.get(i).getID()) {
				removeList.remove(i);
				break;
			}	
		}
		targetC.setPrecincts(addedList);
		CD.setPrecincts(removeList);
		targetC.updateCDInfo();
		CD.updateCDInfo();
		targetC.updateCDInfo();
		CD.updateCDInfo();
	}
	
	public List<Precinct> getNeighborInSameCD(Precinct p, CongressionalDistrict CD) {
		List<Precinct> neighbor = new ArrayList<Precinct>();
		if (p.getCoordinate()==null)
			return neighbor;
		List<List<Double>> listOfPoints = p.getCoordinate().get(0);
		
		for (Precinct pr : CD.getPrecincts()) {
			if (pr.getCoordinate()==null)
				continue;
			List<List<Double>> listOfNeighborP = pr.getCoordinate().get(0);
			int flag = 0;
			for (List<Double> l1 : listOfPoints) {
				for (List<Double> l2 : listOfNeighborP) {
					if ((l1.get(0).doubleValue()==l2.get(0).doubleValue()) && (l1.get(1).doubleValue()==l2.get(1).doubleValue())) {
						neighbor.add(pr);
						flag = 1;
						break;
					}
				}
				if(flag == 1)
					break;
			}
		}
		return(neighbor);
	}
	
	public List<Precinct> getNeighborInOtherCD(Precinct p, List<CongressionalDistrict> CDList) {
		List<Precinct> neighbor = new ArrayList<Precinct>();
		List<List<Double>> listOfPoints = p.getCoordinate().get(0);
		for (CongressionalDistrict CD : CDList) {
			if (CD.getId()!=p.getcdNumber()) {
				for (Precinct pr : CD.getBorderPrecinct()) {
					List<List<Double>> listOfNeighborP = pr.getCoordinate().get(0);
					int flag = 0;
					for (List<Double> l1 : listOfPoints) {
						for (List<Double> l2 : listOfNeighborP) {
							if ((l1.get(0).doubleValue()==l2.get(0).doubleValue()) && (l1.get(1).doubleValue()==l2.get(1).doubleValue())) {
								neighbor.add(pr);
								flag = 1;
								break;
							}
						}
						if(flag == 1)
							break;
					}
				}
			}
		}
		return(neighbor);
	}
	public CongressionalDistrict getTargetCD(State state, Precinct p) {
		int cdID = p.getcdNumber();
		for(int i=0;i<state.getCongressionalDistrict().size();i++) {
			if(cdID == state.getCongressionalDistrict().get(i).getId()) {
				return state.getCongressionalDistrict().get(i);
			}
		}
		return null;
	}
}
