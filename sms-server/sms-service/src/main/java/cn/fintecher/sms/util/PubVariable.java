package cn.fintecher.sms.util;

/**
 * 
 * PubVariable  全局变量
 * Version	  1.2
 * @author panye
 * 2014-6-25
 * Copyright notice
 */
public class PubVariable {
	
	public static final Integer PAGE = 1;			//默认
	
	public static final Integer PAGESIZE = 10;
	
	public static final Integer LIMIT_MINTUE = 1;		//限定的分钟数
	
	public static final Integer LIMIT_NUM = 5;			//短时间内限定的次数
	
//	public static final Long DAY_LIMIT_NUM = 14l;		//一天内短信限定的次数(每个服务号)
	public static final Long DAY_LIMIT_NUM = 5l;		//一天内短信限定的次数(每个服务号)

	
}
