package cn.fintecher.authorization.conf.provider.function;


import cn.fintecher.authorization.common.dto.functionDetails.FunctionDetailInfo;

import java.util.List;

public class ResourceDefaultUserFunction implements ResourceUserFunction {
	
	private List<FunctionDetailInfo> functions;
	
	public List<FunctionDetailInfo> getFunctions() {
		return this.functions;
	}

	public void setFunctions(List<FunctionDetailInfo> functions) {
		this.functions = functions;
	}

}
