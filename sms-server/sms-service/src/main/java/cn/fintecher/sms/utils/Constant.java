package cn.fintecher.sms.utils;

/**
 * 常量
 * 
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

	/**
	 * 菜单类型
	 * 
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 保持数据类型
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum RecordType {
        /**
         * 乾景通
         */
    	QJT("QJT"),
        /**
         * 创蓝
         */
    	CL("CL");

        private String value;

        private RecordType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 发送成功
    public static final String STATUS_SUCCESS = "200";
    // 系统异常，发送失败
    public static final String STATUS_SYSTEM_ERROR = "2001";
    // 短时间发送过于频繁，发送失败
    public static final String STATUS_FREQUENCY_ERROR = "2002";
    // 短时间发送过于频繁，加入到定时任务列表，一定时间内再次发送
    public static final String STATUS_TASK_ERROR = "2003";
    // 短时间内短信内容有重复，发送失败
    public static final String STATUS_DUPLICATED_CONTENT_ERROR = "2004";
    // 短时间内短信内容有重复，加入定时列表，一定时间内再次发送
    public static final String STATUS_DUPLICATED_CONTENT_TASK_ERROR = "2005";
    // 发送失败（短信通道未能发送成功）
    public static final String STATUS_SEND_ERROR = "2006";
    // 该手机号码超过每天短信的限制次数
    public static final String STATUS_EXCEED_LIMITPERDAY_ERROR = "2007";
    // 语音短信发送失败
    public static final String STATUS_VOICE_SEND_ERROR = "2008";
    
    // 发送短信开关未开启
    public static final String STATUS_SENT_SWITCH_ERROR = "2009";
    
    // 发送乾景通不支持催收类模板
    public static final String STATUS_QJT_NONSUPPORT_ERROR = "2010";
    
    // 验签失败
    public static final String STATUS_SIGN_ERROR = "4003";
    
    
    //表单状态0无效
    public final static Integer STATUS_NO = 0;
    //表单状态1正常
	public final static Integer STATUS_YES = 1;
	
	 //点集
    public final static String SEND = "DJ";
    //钱景通
	public final static String QJT = "QJT";
    public final static String CL = "CL";
	//账号区分 创蓝
    public final static String FLAT_CL = "1";
    //账号区分 乾景通
	public final static String FLAT_QJT = "2";
	
	//乾景通发短信
	public final static String QJT_PRODUCT_TEXT = "23643246";
	
	//乾景通发语言
	public final static String QJT_PRODUCT_VOICE = "37705238";
	
	 //点集
	public final static String DJ = "1";
	
}
