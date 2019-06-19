package com.quickbase.devint;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by ckeswani on 9/16/15.
 */
public interface DBManager {
	
    public Connection getConnection();
    
    Map<String, Pair<String, Integer>> retrieveCountryPopulations() throws SQLException;
    
} 
