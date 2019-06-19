package com.quickbase;

import com.quickbase.devint.DBManager;
import com.quickbase.devint.DBManagerImpl;
import com.quickbase.devint.PopulationStatProvider;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
    public static void main( String args[] ) {
    	
        log.info("Starting.");
        log.info("Getting DB Connection...");

        DBManager dbm = new DBManagerImpl();
        Connection c = dbm.getConnection();
        if (null == c ) {
            log.info("failed.");
            System.exit(1);
        }
        
		try {
			PopulationStatProvider statProvider = new PopulationStatProvider();
			statProvider.getPopulationByCountry();
		} catch (Exception e) {
			log.error("PopulationStatProvider failed", e);
		}        
    }
}