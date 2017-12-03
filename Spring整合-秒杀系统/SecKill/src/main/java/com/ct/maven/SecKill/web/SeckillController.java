package com.ct.maven.SecKill.web;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ct.maven.SecKill.dto.Exposer;
import com.ct.maven.SecKill.dto.SeckillExecution;
import com.ct.maven.SecKill.dto.SeckillResult;
import com.ct.maven.SecKill.entity.Seckill;
import com.ct.maven.SecKill.enums.SeckillStateEnum;
import com.ct.maven.SecKill.exception.RepeatKillException;
import com.ct.maven.SecKill.exception.SeckillCloseException;
import com.ct.maven.SecKill.service.SeckillService;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		// list.jsp + model = Model and View
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		return "list";

	}

	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}

	// ajax接口：
	// 输出json
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exposer> exposer(
			@PathVariable("seckillId") Long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}

	// 执行秒杀
	@RequestMapping(value = "/{seckillId}/{md5}/execute", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(
			@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5,
			@CookieValue(value = "phone", required = false) Long phone) {
		//spring mvc valid
		if(phone == null){
			return new SeckillResult<SeckillExecution>(false,"未注册");
		}
		
		
		SeckillResult<SeckillExecution> result;
		try{
			//存储过程调用
			SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch(RepeatKillException e){
			SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch(SeckillCloseException e1){
			SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch(Exception e2){
			logger.error(e2.getMessage(),e2);
			SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true,execution);
		}
	}
	
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time(){
		Date nowTime = new Date();
		return new SeckillResult<Long>(true,nowTime.getTime());
	}

}
