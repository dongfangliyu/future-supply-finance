package cn.fintecher.common.utils;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
public class FunctionSet implements Serializable {
	private Set<String> functions;
	
	public FunctionSet(){
		
	}
	
	public FunctionSet(Set<String> functions) {
		this.functions = functions;
	}

	public Set<String> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<String> functions) {
		this.functions = functions;
	}
	
}
