package top.beansprout.health.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.beansprout.health.model.dto.BodyInfoQuery;
import top.beansprout.health.model.dto.BodyInfoSaveDto;
import top.beansprout.health.model.vo.R;
import top.beansprout.health.service.HealthService;

/**
 * <p>Title: HealthController</p>
 * <p>Description: 健康管理接口</p>
 * 
 * @author cyy
 * @date 2020年4月27日
 */
@Controller
@RequestMapping("/health")
public class HealthController extends BaseController {

	@Autowired
	private HealthService healthService;

	// 保存身体信息
	@ResponseBody
	@PostMapping("/saveBodyInfo")
	public R saveBodyInfo(@RequestBody @Valid BodyInfoSaveDto bodyInfoSaveDto) {
		healthService.saveBodyInfo(getUserId(), bodyInfoSaveDto);
		return R.okAsAjax();
	}

	// 身体信息列表
	@ResponseBody
	@GetMapping("/bodyInfoList")
	public R bodyInfoList(@Valid BodyInfoQuery bodyInfoQuery) {
		return R.okAsAjax(healthService.bodyInfoList(getUserId(), bodyInfoQuery));
	}

	// 删除身体信息
	@ResponseBody
	@DeleteMapping("/{id}")
	public R saveBodyInfo(@PathVariable int id) {
		healthService.deleteBodyInfo(getUserId(), id);
		return R.okAsAjax();
	}

	// 获取身体信息
	@ResponseBody
	@GetMapping("/{id}")
	public R getBodyInfo(@PathVariable int id) {
		return R.okAsAjax(healthService.getBodyInfo(getUserId(), id));
	}

	// 身体信息统计
	@ResponseBody
	@GetMapping("/bodyInfoStatistics")
	public R bodyInfoStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		return R.okAsAjax(healthService.getBodyStatistics(getUserId(), date));
	}

}