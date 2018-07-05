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

import cn.fintecher.sms.entity.SysSmsRecordDetailEntity;
import cn.fintecher.sms.service.SysSmsRecordDetailService;
import cn.fintecher.sms.utils.PageUtils;
import cn.fintecher.sms.utils.Query;
import cn.fintecher.sms.utils.R;


/**
 * 
 * 
 * @author 
 * @email 
 * @date 2017-06-05 16:10:34
 */
@RestController
@RequestMapping("syssmsrecorddetail")
public class SysSmsRecordDetailController {
	@Autowired
	private SysSmsRecordDetailService sysSmsRecordDetailService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("syssmsrecorddetail:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysSmsRecordDetailEntity> sysSmsRecordDetailList = sysSmsRecordDetailService.queryList(query);
		int total = sysSmsRecordDetailService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysSmsRecordDetailList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("syssmsrecorddetail:info")
	public R info(@PathVariable("id") Long id){
		SysSmsRecordDetailEntity sysSmsRecordDetail = sysSmsRecordDetailService.queryObject(id);
		
		return R.ok().put("sysSmsRecordDetail", sysSmsRecordDetail);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("syssmsrecorddetail:save")
	public R save(@RequestBody SysSmsRecordDetailEntity sysSmsRecordDetail){
		sysSmsRecordDetailService.save(sysSmsRecordDetail);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("syssmsrecorddetail:update")
	public R update(@RequestBody SysSmsRecordDetailEntity sysSmsRecordDetail){
		sysSmsRecordDetailService.update(sysSmsRecordDetail);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("syssmsrecorddetail:delete")
	public R delete(@RequestBody Long[] ids){
		sysSmsRecordDetailService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
