package com.quickbase.devint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This DBManager implementation provides a connection to the database containing population data.
 *
 * Created by ckeswani on 9/16/15.
 */
public class DBManagerImpl implements DBManager {
	
	private static final Logger log = LoggerFactory.getLogger(DBManagerImpl.class);
			
	private static final String RETRIEVE_COUNTRY_AGGREGATE_POPULATION = "SELECT Country.CountryName, SUM(City.Population) as Population FROM Country\n" + 
			"INNER JOIN State ON Country.CountryId = State.CountryId\n" + 
			"INNER JOIN City ON City.StateId = State.StateId\n" + 
			"GROUP BY Country.CountryName"; 
			
			
	/*
     * @see com.quickbase.devint.DBManager#getConnection()
     */
    public Connection getConnection() {
    	Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:resources/data/citystatecountry.db");
            log.info("Opened database successfully");

        } catch (ClassNotFoundException cnf) {
            log.info("could not load driver");
        } catch (SQLException sqle) {
            log.info("sql exception:" + sqle.getStackTrace());
        }
        return c;
    }
    
    //TODO: Add a method (signature of your choosing) to query the db for population data by country
	public Map<String, Pair<String, Integer>> retrieveCountryPopulations() throws SQLException  {
		Map<String, Pair<String, Integer>> populationMap = new HashMap<>();
		try (Connection connection = getConnection();
				Statement stmt = connection.createStatement();
				ResultSet resultSet = stmt.executeQuery(RETRIEVE_COUNTRY_AGGREGATE_POPULATION)) {
			while (resultSet.next()) {
				String countryName = resultSet.getString("COUNTRYNAME");
				int population = resultSet.getInt("POPULATION");
				populationMap.put(countryName, new ImmutablePair<String, Integer>(countryName, population));
			}
		} catch (SQLException e) {
			log.error("Retrieve population by country failed with an error");
			throw e;
		}
		log.info("Country Population from DB Source=> {}", populationMap);
		return populationMap;
	}	
}
