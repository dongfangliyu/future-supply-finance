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

import cn.fintecher.sms.entity.SysSmsTaskEntity;
import cn.fintecher.sms.service.SysSmsTaskService;
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
@RequestMapping("syssmstask")
public class SysSmsTaskController {
	@Autowired
	private SysSmsTaskService sysSmsTaskService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("syssmstask:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysSmsTaskEntity> sysSmsTaskList = sysSmsTaskService.queryList(query);
		int total = sysSmsTaskService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysSmsTaskList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("syssmstask:info")
	public R info(@PathVariable("id") Long id){
		SysSmsTaskEntity sysSmsTask = sysSmsTaskService.queryObject(id);
		
		return R.ok().put("sysSmsTask", sysSmsTask);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("syssmstask:save")
	public R save(@RequestBody SysSmsTaskEntity sysSmsTask){
		sysSmsTaskService.save(sysSmsTask);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("syssmstask:update")
	public R update(@RequestBody SysSmsTaskEntity sysSmsTask){
		sysSmsTaskService.update(sysSmsTask);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("syssmstask:delete")
	public R delete(@RequestBody Long[] ids){
		sysSmsTaskService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
