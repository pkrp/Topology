package toik_calculationmodule;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.toik.common.Properties;
import pl.edu.agh.toik.topology.TopologyCreator;
import pl.edu.agh.toik.workplace.IWorkplace;
import toik_calculationalgorithmsmodule.SimulationSubstep;
import toik_calculationalgorithmsmodule.SimulationSubstepsManager;
import toik_calculationmodule.population.Individual;
import toik_calculationmodule.population.Population;
import toik_calculationmodule.population.SimplePopulation;

public class CalculationAgentImpl implements CalculationAgent{

	private SimplePopulation simplePopulation;
	private List<Population> populationToInject;
	
	private IWorkplace workplace;
	private Properties configuration;
	private TopologyCreator topology;
	private Integer id;
	private SimulationSubstepsManager simulationSubstepManager;
	
	public CalculationAgentImpl() {
		simplePopulation = new SimplePopulation();
		populationToInject = new ArrayList<Population>();
		simulationSubstepManager = new SimulationSubstepsManager();
	}
	
	@Override
	public boolean initializeAgent(IWorkplace workplace,
			Properties configuration, TopologyCreator topology, Integer id) {
		this.workplace = workplace;
		this.configuration = configuration;
		this.topology = topology;
		this.id = id;
		return true;
	}
	
	@Override
	public void step() {
		//dodanie populacji wstrzyknietej
		List<Individual> pop = simplePopulation.getPopulation();
		for(Population p : populationToInject){
			pop.addAll(p.getPopulation());
		}
		populationToInject.clear();
		simplePopulation.setPopulation(pop);
		//wykonanie symulacji
		for(String step : configuration.getSimulationSubsteps()){
			SimulationSubstep st = simulationSubstepManager.getSimulationSubstep(step);
			st.invoke(simplePopulation);
		}
		//przeslanie danych
		workplace.sendPopulationToAgent(simplePopulation, "", id);
	}

	@Override
	public void injectPopulation(Population population) {
		populationToInject.add(population);
	}

}
