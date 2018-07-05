package cn.fintecher.sms.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.service.SysSmsRecordService;
import cn.fintecher.sms.utils.PageUtils;
import cn.fintecher.sms.utils.Query;
import cn.fintecher.sms.utils.R;


/**
 * 
 * 
 * @author 
 * @email 
 * @date 2017-05-31 14:31:05
 */
@RestController
@RequestMapping("syssmsrecord")
public class SysSmsRecordController {
	@Autowired
	private SysSmsRecordService sysSmsRecordService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("syssmsrecord:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysSmsRecordEntity> sysSmsRecordList = sysSmsRecordService.queryList(query);
		int total = sysSmsRecordService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysSmsRecordList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("syssmsrecord:info")
	public R info(@PathVariable("id") Long id){
		SysSmsRecordEntity sysSmsRecord = sysSmsRecordService.queryObject(id);
		
		return R.ok().put("sysSmsRecord", sysSmsRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("syssmsrecord:save")
	public R save(@RequestBody SysSmsRecordEntity sysSmsRecord){
		sysSmsRecordService.save(sysSmsRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("syssmsrecord:update")
	public R update(@RequestBody SysSmsRecordEntity sysSmsRecord){
		sysSmsRecordService.update(sysSmsRecord);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("syssmsrecord:delete")
	public R delete(@RequestBody Long[] ids){
		sysSmsRecordService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
