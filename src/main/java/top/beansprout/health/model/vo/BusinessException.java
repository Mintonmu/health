package top.beansprout.health.model.vo;

import lombok.Getter;

/**
 * <p>Title: BusinessException</p>
 * <p>Description: 业务异常</p>
 * @author cyy
 * @date 2020年4月23日
 */
@Getter
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -5846808029174051527L;

	private final R r;

	public BusinessException() {
		r = R.budil().result(false).message("系统异常");
	}

	public BusinessException(String message) {
		r = R.budil().result(false).message(message);
	}

	public BusinessException(String path, String message) {
		r = R.budil().result(false).message(message).path(path);
	}

	public BusinessException(String message, Object data) {
		r = R.budil().result(false).message(message).data(data);
	}

}