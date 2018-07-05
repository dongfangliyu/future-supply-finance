package cn.fintecher.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.fintecher.sms.admin.AbstractController;
import cn.fintecher.sms.entity.SysSmsContentEntity;
import cn.fintecher.sms.service.SysSmsContentService;
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
@RequestMapping("syssmscontent")
public class SysSmsContentController extends AbstractController{
	
	@Autowired
	private SysSmsContentService sysSmsContentService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("syssmscontent:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysSmsContentEntity> sysSmsContentList = sysSmsContentService.queryList(query);
		int total = sysSmsContentService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysSmsContentList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("syssmscontent:info")
	public R info(@PathVariable("id") Long id){
		SysSmsContentEntity sysSmsContent = sysSmsContentService.queryObject(id);
		
		return R.ok().put("sysSmsContent", sysSmsContent);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("syssmscontent:save")
	public R save(@RequestBody SysSmsContentEntity sysSmsContent){
		ValidatorUtils.validateEntity(sysSmsContent, AddGroup.class);
		SysSmsContentEntity sysSmsContentEntity = sysSmsContentService.findByNumber(sysSmsContent.getNumber());
		if(sysSmsContentEntity != null){
			throw new RRException("编号不能重复！");
		}
		sysSmsContentService.save(sysSmsContent);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("syssmscontent:update")
	public R update(@RequestBody SysSmsContentEntity sysSmsContent){
		ValidatorUtils.validateEntity(sysSmsContent, UpdateGroup.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", sysSmsContent.getId());
		map.put("number", sysSmsContent.getNumber());
		List<SysSmsContentEntity> list = sysSmsContentService.queryList(map);
		if(list != null && !list.isEmpty()){
			throw new RRException("编号不能重复！");
		}
		sysSmsContentService.update(sysSmsContent);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("syssmscontent:delete")
	public R delete(@RequestBody Long[] ids){
		sysSmsContentService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
