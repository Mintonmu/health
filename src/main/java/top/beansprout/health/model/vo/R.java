package top.beansprout.health.model.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.Setter;
import top.beansprout.health.constant.SysConstant;

/**
 * <p>Title: R</p>
 * <p>Description: 返回结果</p>
 * @author cyy
 * @date 2020年4月23日
 */
@Setter
@Getter
public class R extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = -7261945562236369523L;

	public static final String FIELD_RESULT = "result";
	public static final String FIELD_MESSAGE = "message";
	public static final String FIELD_DATA = "data";
	public static final String FIELD_PATH = "path";

	public static final String FAILED_FIELD_REQUEST_ID = SysConstant.INIT_FIELD_REQUEST_REQUEST_ID;
	public static final String FAILED_FIELD_TIME_STAMP = "timestamp";
	public static final String FAILED_FIELD_PATH = "path";
	public static final String FAILED_FIELD_ERROR = "error";
	public static final String FAILED_FIELD_ERRORS = "errors";

	// 结果
	private boolean result;
	// 描述
	private String message;
	// 数据
	private Object data;
	// 页面
	private String path;

	public static R budil() {
		return new R();
	}

	public R result(boolean result) {
		this.put(FIELD_RESULT, result);
		this.result = result;
		return this;
	}

	public R message(String message) {
		this.put(FIELD_MESSAGE, message);
		this.message = message;
		return this;
	}

	public R data(Object data) {
		this.put(FIELD_DATA, data);
		this.data = data;
		return this;
	}

	public R path(String path) {
		this.put(FIELD_PATH, path);
		this.path = path;
		return this;
	}

	public static ModelAndView ok() {
		return of("/", null);
	}

	public static R okAsAjax() {
		return okAsAjax(null);
	}

	public static ModelAndView ok(Map<String, Object> data) {
		return of("/", data);
	}

	public static R okAsAjax(Object data) {
		return ofAsAjax(true, "成功", data);
	}

	public static ModelAndView failed() {
		return of("/500", null);
	}

	public static ModelAndView failed(Map<String, Object> data) {
		return of("/500", data);
	}

	public static ModelAndView failed(String path, Map<String, Object> data) {
		return of(path, data);
	}

	public static ModelAndView of(String path, Map<String, Object> data) {
		final ModelAndView modelAndView = new ModelAndView(path, data);
		return modelAndView;
	}

	public static R ofAsAjax(boolean result, String message, Object data) {
		return budil().result(result).message(message).data(data);
	}

}