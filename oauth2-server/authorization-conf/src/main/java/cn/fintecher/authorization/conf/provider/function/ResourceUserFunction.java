package cn.fintecher.authorization.conf.provider.function;


import cn.fintecher.authorization.common.dto.functionDetails.FunctionDetailInfo;

import java.util.List;

public interface ResourceUserFunction {
	List<FunctionDetailInfo> getFunctions();

    void setFunctions(List<FunctionDetailInfo> functions);
}	
