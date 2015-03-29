package demo.model;

import java.util.HashSet;
import java.util.Set;

public class User {
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<String> getHistorySet() {
		return historySet;
	}
	
	public void setHistorySet(Set<String> historySet) {
		this.historySet = historySet;
	}
	
	private String id;
	private String name;
	private Set<String> historySet = new HashSet<String>();
	
}