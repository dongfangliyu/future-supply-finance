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

import cn.fintecher.sms.entity.SysSmsAccountInfoEntity;
import cn.fintecher.sms.service.SysSmsAccountInfoService;
import cn.fintecher.sms.utils.PageUtils;
import cn.fintecher.sms.utils.Query;
import cn.fintecher.sms.utils.R;
import cn.fintecher.sms.utils.RRException;
import cn.fintecher.sms.utils.validator.ValidatorUtils;
import cn.fintecher.sms.utils.validator.group.AddGroup;
import cn.fintecher.sms.utils.validator.group.UpdateGroup;


/**
 * 
 * 
 * @author
 * @email
 * @date
 */
@RestController
@RequestMapping("syssmsaccountinfo")
public class SysSmsAccountInfoController {
	@Autowired
	private SysSmsAccountInfoService sysSmsAccountInfoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("syssmsaccountinfo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysSmsAccountInfoEntity> sysSmsAccountInfoList = sysSmsAccountInfoService.queryList(query);
		int total = sysSmsAccountInfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysSmsAccountInfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("syssmsaccountinfo:info")
	public R info(@PathVariable("id") Integer id){
		SysSmsAccountInfoEntity sysSmsAccountInfo = sysSmsAccountInfoService.queryObject(id);
		
		return R.ok().put("sysSmsAccountInfo", sysSmsAccountInfo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("syssmsaccountinfo:save")
	public R save(@RequestBody SysSmsAccountInfoEntity sysSmsAccountInfo){
		
		ValidatorUtils.validateEntity(sysSmsAccountInfo, AddGroup.class);
		Map<String, SysSmsAccountInfoEntity> map = sysSmsAccountInfoService.findAccountInfo(sysSmsAccountInfo.getChannelCode());
		SysSmsAccountInfoEntity sysSmsAccountInfoEntity = map.get(sysSmsAccountInfo.getChannelCode() + sysSmsAccountInfo.getFlat());
		if(sysSmsAccountInfoEntity != null){
			throw new RRException("通道编码和短信平台不能重复！");
		}
		sysSmsAccountInfoService.save(sysSmsAccountInfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("syssmsaccountinfo:update")
	public R update(@RequestBody SysSmsAccountInfoEntity sysSmsAccountInfo){
		
		ValidatorUtils.validateEntity(sysSmsAccountInfo, UpdateGroup.class);
		
		Map<String, SysSmsAccountInfoEntity> map = sysSmsAccountInfoService.findAccountInfo(sysSmsAccountInfo.getChannelCode());
		SysSmsAccountInfoEntity sysSmsAccountInfoEntity = map.get(sysSmsAccountInfo.getChannelCode() + sysSmsAccountInfo.getFlat());
		if(sysSmsAccountInfoEntity != null && sysSmsAccountInfo.getId() != sysSmsAccountInfoEntity.getId()){
			throw new RRException("通道编码和短信平台不能重复！");
		}
		
		sysSmsAccountInfoService.update(sysSmsAccountInfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("syssmsaccountinfo:delete")
	public R delete(@RequestBody Integer[] ids){
		sysSmsAccountInfoService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
