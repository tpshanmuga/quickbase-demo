package com.quickbase.devint;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopulationStatProvider {

	private static final Logger log = LoggerFactory.getLogger(PopulationStatProvider.class);
	private IStatService statService;
	private DBManager dbm;

	public PopulationStatProvider() {
		statService = new ConcreteStatService();
		dbm = new DBManagerImpl();
	}

	public List<Pair<String, Integer>> getPopulationByCountry() throws SQLException  {
		// Get population data from concrete source		
		List<Pair<String, Integer>> concreteStats = statService.getCountryPopulations();
		
		// Validate CountryNames for consistency, this should generally be done using some kind of mapper with country codes
		concreteStats.removeIf(s -> s.getLeft().equals("United States of America"));
		
		Map<String, Pair<String, Integer>> populationMap = concreteStats.stream().collect(Collectors.toMap(Pair::getLeft, p -> p));
		log.info("The ConcreteStatService contains {} population record(s) {}", populationMap.size(), populationMap);
		
		// Get population data from DB source	
		Map<String, Pair<String, Integer>> dbStats = dbm.retrieveCountryPopulations();		
		// Safe to add all population from DB source
		populationMap.putAll(dbStats);

		List<Pair<String, Integer>> populations = populationMap.values().stream().sorted().collect(Collectors.toList());
		log.info("Combined population data by country {}", populations);
		
		return populations;
	}
}
