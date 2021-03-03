package top.beansprout.health.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <p>Title: CollectionUtils</p>
 * <p>Description: 集合工具类</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/19 22:37
 */
public class CollectionUtils {

    /** 判断集合是否为null或者size=0 **/
	public static <T> boolean isEmpty(Collection<T> coll) {
        return CollectionUtils.isEmpty(coll);
    }

	public static <T> boolean isNotEmpty(Collection<T> coll) {
        return !isEmpty(coll);
    }

    /**
     * 将Iterable映射成列表
     *
     * @param src
     * @param mapper
     * @return
     */
    public static <T, R> List<R> map(final Iterable<T> src, final Function<? super T, ? extends R> mapper) {
        final List<R> r = new ArrayList<>();
        for (final T t : src) {
			r.add(mapper.apply(t));
		}
        return r;
    }

    /**
     * 数组转集合
     * @param key
     * @return
     */
	public static <T extends Object> List<T> arrayToList(T... key) {
		return Arrays.asList(key);
    }

    /**
     * element 转换成指定集合
     *
     * @param sourceCollection  源集合
     * @param collectionFactory 目标集合
     * @return DEST
     */
    public static <T, SOURCETYPE extends Collection<T>, DEST extends Collection<T>> DEST
    transformElements(SOURCETYPE sourceCollection, Supplier<DEST> collectionFactory) {
        final DEST dest = collectionFactory.get();
        sourceCollection.forEach(t -> {
            dest.add(t);
        });
        return dest;
    }

}