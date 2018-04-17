package Objects;

import java.util.ArrayList;
import java.util.List;

import com.rits.cloning.Cloner;

public class Algorithm {
	private double populationW;
	private double compactnessW;
	private double racialW;
	private double partisanW;
	private int year;
	
	public void setPopulationW(double weight) {
		this.populationW = weight;
	}
	
	public void setcompactnessW(double weight) {
		this.compactnessW = weight;
	}
	
	
	public void setracialW(double weight) {
		this.racialW = weight;
	}
	
	public void setpartisanW(double weight) {
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
		goodness = CD.getPopulationScore() * populationW + CD.getCompactnessScore() * compactnessW + 
				CD.getPartisanFairnessScore() * partisanW + CD.getRacialFairnessScore() * racialW;
		return goodness;
	}
	
	public State startAlgorithm(State state) {
		for (CongressionalDistrict CD : state.getCongressionalDistrict()) {
			List<Precinct> borderPrecincts = CD.getBorderPrecinct();
			int size = borderPrecincts.size();
			Precinct p;
			List<Precinct> neighbor;
			while (true) {
				p = borderPrecincts.get((int)Math.random()*size);
				neighbor = getNeighborInOtherCD(p,state.getCongressionalDistrict());
				if (neighbor.size()==0) {
					continue;
				}
				else
					break;				
			}
			if (movePrecinct(p,CD,neighbor,state)) {
				updateSourceCDBorder(p,CD);
				updateTargetCDBorder(neighbor,state);
			}
		}
		return state;
	}
	
	public void updateSourceCDBorder(Precinct p, CongressionalDistrict CD) {
		for (Precinct pr : CD.getPrecincts()) {
			List<ArrayList<Double>> listOfPoints = p.getCoordinate().get(0);
			for (List<Double> l1 : listOfPoints) {
				List<ArrayList<Double>> listOfNeighborP = pr.getCoordinate().get(0);
				for (List<Double> l2 : listOfNeighborP) {
					if (l1.get(0)==l2.get(0) && l1.get(1)==l2.get(1) && CD.getId()==pr.getcdNumber()) {
						pr.setBorder(1);
					}
				}
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
		Cloner cloner = new Cloner();
		for (Precinct targetP: neighbor) {
			CongressionalDistrict targetC = state.getCongressionalDistrict().get(targetP.getcdNumber());
			CongressionalDistrict cloneTargetC = cloner.deepClone(targetC);
			CongressionalDistrict cloneSourceC = cloner.deepClone(CD);
			updateCD(cloneTargetC, cloneSourceC, moveP);
			double originalScore = calculateCDGoodness(targetC) + calculateCDGoodness(CD);
			double newScore = calculateCDGoodness(cloneTargetC) + calculateCDGoodness(cloneSourceC);
			if(newScore>originalScore) {
				updateCD(targetC, CD, moveP);
				return(true);
			}
		}
		return(false);
	}

	public void updateCD(CongressionalDistrict targetC, CongressionalDistrict CD, Precinct moveP) {
		List<Precinct> addedList = targetC.getPrecincts();
		addedList.add(moveP);
		List<Precinct> removeList= CD.getPrecincts();
		removeList.remove(moveP);
		targetC.setPrecincts(addedList);
		CD.setPrecincts(removeList);
		targetC.updateCDInfo();
		CD.updateCDInfo();
		moveP.setcdNumber(targetC.getId());
		targetC.updateCDInfo();
		CD.updateCDInfo();
	}
	
	public List<Precinct> getNeighborInOtherCD(Precinct p, List<CongressionalDistrict> CDList) {
		List<Precinct> neighbor = new ArrayList<Precinct>();
		for (CongressionalDistrict CD : CDList) {
			if (CD.getId()!=p.getcdNumber()) {
				for (Precinct pr : CD.getBorderPrecinct()) {
					List<ArrayList<Double>> listOfPoints = p.getCoordinate().get(0);
					for (List<Double> l1 : listOfPoints) {
						List<ArrayList<Double>> listOfNeighborP = pr.getCoordinate().get(0);
						for (List<Double> l2 : listOfNeighborP) {
							if (l1.get(0)==l2.get(0) && l1.get(1)==l2.get(1)) {
								neighbor.add(pr);
								break;
							}
						}
					}
				}
			}
		}
		return(neighbor);
	}
	
}
