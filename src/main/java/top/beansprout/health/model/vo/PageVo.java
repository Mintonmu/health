package top.beansprout.health.model.vo;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: PageVo</p>
 * <p>Description: 列表返回</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/22 22:15
 */
@Setter
@Getter
public class PageVo<T extends Object> implements Serializable {

	private static final long serialVersionUID = 7039024431017840683L;

	// 当前页
	private long page;
	// 每页展示数量
	private long pageSize;
	// 是否是第一页true第一
	private boolean first = false;
	// 是否是最后一页true最后
	private boolean last = false;
	// 总页数
	private int totalPages;
	// 总数据条数
	private int totalElements;
	// 当前页数据条数
	private int numberOfElements;
	// 数据集
	private List<?> content;

	public PageVo(int page, int pageSize, int totalElements, List<T> content) {
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.page = page <= 0 ? 1 : page;
		this.totalPages = totalElements == 0 ? 0
				: (totalElements % pageSize) == 0 ? totalElements / pageSize : (totalElements / pageSize) + 1;
		this.first = page == 1 ? true : false;
		this.last = ((page == this.totalPages) || (content.size() < 1)) ? true : false;
		this.content = content;
		this.numberOfElements = this.content.size();
	}

	public PageVo(PageInfo<T> pageInfo) {
		this.page = pageInfo.getPageNum();
		this.pageSize = pageInfo.getPageSize();
		this.totalElements = (int) pageInfo.getTotal();
		this.totalPages = pageInfo.getPages();
		this.first = page == 1 ? true : false;
		this.content = pageInfo.getList();
		this.last = ((page == this.totalPages) || (content.size() < 1)) ? true : false;
		this.numberOfElements = this.content.size();
	}

	public static <T> PageVo<T> of(int page, int pageSize, int totalElements, List<T> content) {
		return new PageVo<T>(page, pageSize, totalElements, content);
	}

	public static <T> PageVo<T> of(PageInfo<T> pageInfo) {
		return new PageVo<T>(pageInfo);
	}

}