package cn.fintecher.sms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.service.SysConfigService;
import cn.fintecher.sms.service.SysSmsRecordService;
import cn.fintecher.sms.util.SignatureUtil;
import cn.fintecher.sms.utils.Constant;
import cn.fintecher.sms.vo.RecordBodyVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class RecordController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendMsgController.class);
	
	private final static String SIGN_SWITCH = "signSwitch";
	
	@Autowired
	private  SysSmsRecordService sysSmsRecordService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 
	 * @param shortMessageEntity
	 * @param authorization
	 * @throws IOException
	 */
	@RequestMapping(value="querySmsRecord.html",method=RequestMethod.POST)
	public String querySmsRecord(@RequestBody RecordBodyVO recordEntity, @RequestHeader("authorization") String authorization) throws IOException { 
		
		LOGGER.info("查询短信开始.");
		LOGGER.debug("查询短信输入参数：{}", recordEntity.toString());
		//数据验证
		boolean signSwtich = Boolean.parseBoolean(sysConfigService.getValue(SIGN_SWITCH, "true"));
		if(signSwtich && !SignatureUtil.chkSignature(authorization)) {
			LOGGER.error("验签失败");
			return Constant.STATUS_SIGN_ERROR;
		}
		
		// 接受分页信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", recordEntity.getTelephone());
		map.put("beginTime", recordEntity.getBeginTime());
		map.put("endTime", recordEntity.getEndTime());
		int startRow = (recordEntity.getPageNo() - 1) * recordEntity.getPageSize();
		map.put("offset", startRow);
		map.put("limit", recordEntity.getPageSize());
		map.put("remark", recordEntity.getRemark());
		map.put("status", recordEntity.getStatus());
		map.put("sys_number", recordEntity.getSysNumber());
		List<SysSmsRecordEntity> sysSmsRecordEntityList = null;
		try {
			sysSmsRecordEntityList = sysSmsRecordService.queryList(map);
			int total = sysSmsRecordService.queryTotal(map);
			JSONObject  jsonObj= new JSONObject();
			jsonObj.put("total", total);
			jsonObj.put("data", JSONArray.fromObject(sysSmsRecordEntityList));
			return jsonObj.toString();
		} catch (Exception e) {
			LOGGER.error("查询短信失败", e);
			return Constant.STATUS_SYSTEM_ERROR;
		}
	}
}
