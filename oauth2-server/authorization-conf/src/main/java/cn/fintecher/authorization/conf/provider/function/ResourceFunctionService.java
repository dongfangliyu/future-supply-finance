package cn.fintecher.authorization.conf.provider.function;



public interface ResourceFunctionService {
	ResourceUserFunction loadFunctionByUsername(String userName) throws ResourceUserFunctionException;
}
