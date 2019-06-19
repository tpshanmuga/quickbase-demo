package com.quickbase.model;

public class City {

	private Long id;
	private String name;
	private Long stateId;
	private int population;

	public City(Long id, String name, Long stateId, int population) {
		this.id = id;
		this.name = name;
		this.stateId = stateId;
		this.population = population;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
}
