package toik_calculationalgorithmsmodule;

import java.util.ArrayList;
import java.util.List;

import toik_calculationmodule.population.Individual;
import toik_calculationmodule.population.Population;

public class SimpleSelectionSimulationSubstep implements SimulationSubstep {

	private static final double exactPI = Math.PI;
	private static final double maxErrorRate = 0.1;
	
	@Override
	public void invoke(Population population) {
		List<Individual> pop = population.getPopulation();
		List<Individual> newPop = new ArrayList<Individual>();
		
		for(Individual ind : pop){
			if(Math.abs(ind.getValue() - exactPI) <= maxErrorRate){
				newPop.add(ind);
			}
		}	
		population.setPopulation(newPop);
	}
	
	@Override
	public String toString(){
		return "SimpleSelectionSimulationSubstep";
	}
}
