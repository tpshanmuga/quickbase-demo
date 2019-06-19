package com.quickbase.devint; 

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PopulationStatProviderTest {

	@Mock
	private IStatService statService;

	@Mock
	private DBManager dbm;

	@InjectMocks
	private PopulationStatProvider provider;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);		
	}

	@Test
	public void getConnection() {
		try {
			provider.getPopulationByCountry();
		} catch (SQLException e) {
			assertTrue(e instanceof SQLException);
		}
		Mockito.verify(dbm, Mockito.times(0)).getConnection();
	}

	@Test
	public void getPopulationByCountry() {
		Mockito.when(statService.getCountryPopulations()).thenReturn(new ArrayList<Pair<String, Integer>>());
		try {
			Map<String, Pair<String, Integer>> popMap = new HashMap<>();
			popMap.put("Ukraine", new ImmutablePair<String, Integer>("Ukraine", 1234567));
			Mockito.when(dbm.retrieveCountryPopulations()).thenReturn(popMap);
			provider.getPopulationByCountry();
		} catch (SQLException e) {
			assertTrue(e instanceof SQLException);
		}
	}
	
	@Test
	public void getPopulationByCountryWithDuplicate() {
		List<Pair<String, Integer>> concreteStats =  new ArrayList<Pair<String, Integer>>();
		concreteStats.add(new ImmutablePair<String, Integer>("Ukraine", 2345678));
		concreteStats.add(new ImmutablePair<String, Integer>("Spain", 234567));
		Mockito.when(statService.getCountryPopulations()).thenReturn(concreteStats);
		try {
			Map<String, Pair<String, Integer>> popMap = new HashMap<>();
			popMap.put("Ukraine", new ImmutablePair<String, Integer>("Ukraine", 1234567));
			popMap.put("Netherlands", new ImmutablePair<String, Integer>("Netherlands", 12345670));
			Mockito.when(dbm.retrieveCountryPopulations()).thenReturn(popMap);
			provider.getPopulationByCountry();
		} catch (SQLException e) {
			assertTrue(e instanceof SQLException);
		}
	}
}
