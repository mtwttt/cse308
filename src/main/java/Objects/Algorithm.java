package Objects;

import java.util.ArrayList;
import java.util.List;

import com.rits.cloning.Cloner;

public class Algorithm {
	private double populationW;
	private double compactnessW;
	private double racialW;
	private double partisanW;
	
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
	
	public double calculateCDGoodness(CongressionalDistrict CD) {
		double goodness = 0.0;
		goodness = CD.getPopulationScore() * populationW + CD.getCompactnessScore() * compactnessW + 
				CD.getPartisanFairnessScore() * partisanW + CD.getRacialFairnessScore() * racialW;
		return goodness;
	}
	
	public void startAlgorithm(State state) {
		for (CongressionalDistrict CD : state.getCongressionalDistrict()) {
			List<Precinct> borderPrecincts = CD.getBorderPrecinct();
			int size = borderPrecincts.size();
			Precinct p;
			List<Precinct> neighbor;
			while (true) {
				p = borderPrecincts.get((int)Math.random()*size);
				neighbor = getNeighborPrecincts(p,state.getCongressionalDistrict());
				if (neighbor.size()==0) {
					continue;
				}
				else
					break;				
			}
			movePrecinct(p,CD,neighbor, state);
		}
	}
	
	public Precinct movePrecinct(Precinct moveP, CongressionalDistrict CD, List<Precinct> neighbor, State state) {
		Cloner cloner = new Cloner();
		for (Precinct targetP: neighbor) {
			CongressionalDistrict targetC = state.getCongressionalDistrict().get(targetP.getcdNumber());
			CongressionalDistrict cloneTargetC = cloner.deepClone(targetC);
			CongressionalDistrict cloneSourceC = cloner.deepClone(CD);
			List<Precinct> addedList = cloneTargetC.getPrecincts();
			addedList.add(moveP);
			cloneTargetC.setPrecincts(addedList);
			List<Precinct> removeList= cloneSourceC.getPrecincts();
			removeList.remove(moveP);
			cloneSourceC.setPrecincts(removeList);
			cloneTargetC.updateCDInfo();
			cloneSourceC.updateCDInfo();
			double originalScore = calculateCDGoodness(targetC) + calculateCDGoodness(CD);
			double newScore = calculateCDGoodness(cloneTargetC) + calculateCDGoodness(cloneSourceC);
			if(newScore>originalScore) {
				addedList = targetC.getPrecincts();
				addedList.add(moveP);
				removeList= CD.getPrecincts();
				removeList.remove(moveP);
				targetC.setPrecincts(addedList);
				CD.setPrecincts(removeList);
				targetC.updateCDInfo();
				CD.updateCDInfo();
				return moveP;
			}
		}
		return null;
	}

	public List<Precinct> getNeighborPrecincts(Precinct p, List<CongressionalDistrict> CDList) {
		List<Precinct> neighbor = new ArrayList<Precinct>();
		for (CongressionalDistrict CD : CDList) {
			if (CD.getId()!=p.getcdNumber()) {
				for (Precinct pr : CD.getBorderPrecinct()) {
					List<List<Double>> listOfPoints = p.getCoordinate().get(0);
					for (List<Double> l1 : listOfPoints) {
						List<List<Double>> listOfNeighborP = pr.getCoordinate().get(0);
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
