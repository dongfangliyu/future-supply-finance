package cn.fintecher.common.utils;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
public class PowerSet implements Serializable {
	private Set<String> powers;

	public PowerSet(){
		
	}
	
	public PowerSet(Set<String> powers){
		this.powers = powers;
	}
	
	public Set<String> getPowers() {
		return powers;
	}

	public void setPowers(Set<String> powers) {
		this.powers = powers;
	}
	
}
