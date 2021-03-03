package top.beansprout.health.config;

import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import top.beansprout.health.model.vo.R;

/**
 * <p>Title: ExceptionManager</p>
 * <p>Description: </p>
 * @author cyy
 * @date 2020年4月28日
 */
@ControllerAdvice
public class ExceptionManager {

	@ResponseBody
	@ExceptionHandler({ BindException.class, MethodArgumentNotValidException.class })
	public R handleException(Exception e) {
		List<FieldError> fieldErrs = null;
		if (e instanceof BindException) {
			fieldErrs = ((BindException) e).getBindingResult().getFieldErrors();
		}
		if (e instanceof MethodArgumentNotValidException) {
			fieldErrs = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
		}
		Object r = e.getMessage();
		if (fieldErrs != null) {
			for (final FieldError err : fieldErrs) {
				r = err.getDefaultMessage();
				break;
			}
		}
		return R.budil().result(false).message(r.toString()).data(r);
	}

}