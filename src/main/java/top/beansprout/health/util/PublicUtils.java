package top.beansprout.health.util;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;

/**
 * <p>Title: PublicUtils</p>
 * <p>Description: 公共方法</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/22 17:41
 */
public class PublicUtils {

    /**
     * 为空
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof String)
            return ((String) obj).trim().equals("");
        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;
        if (obj instanceof Object[])
            return ((Object[]) obj).length == 0;
        if (obj instanceof Collection)
            return ((Collection<?>) obj).isEmpty();
        if (obj instanceof Map)
            return ((Map<?, ?>) obj).isEmpty();
        return false;
    }

    /**
     * 不为空
     * @param obj
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 随机指定长度的数字+字母字符串
     * @param leng
     * @return
     */
    public static String randomString(int leng) {
        return RandomStringUtils.randomAlphanumeric(leng);
    }

    /**
     * 拼接字符串
     * @param values
     * @return
     */
    public static String join(Object ...values) {
        if (values.length <= 0) return "";
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Object value : values) {
            stringBuilder.append(value);
        }
        return stringBuilder.toString();
    }

    /**
     * Base64加密
     * @param value   原文
     * @return
     */
    public static String encryptBase64(String value) {
        if (isBlank(value)) return null;
        final byte[] encode = Base64.getEncoder().encode(value.getBytes());
        return new String(encode);
    }

    /**
     * Base64解密
     * @param encrypt   密文
     * @return
     */
    public static String decryptBase64(String encrypt) {
        if (isBlank(encrypt)) return null;
        final byte[] decode = Base64.getDecoder().decode(encrypt);
        return new String(decode);
    }

    /**
     * 获取请求域参数
     * @param request
     * @param key
     * @return
     */
    public static String getAttribute(HttpServletRequest request, String key) {
        return getAttribute(request, key, String.class);
    }

    public static <T> T getAttribute(HttpServletRequest request, String key, Class<T> requiredType) {
        if ((request == null) || isBlank(key)) return null;
        final Object value = request.getAttribute(key);
        return isBlank(value) ? null : castValue(value, requiredType);
    }

    /** Object类型转换 **/
    private static <T> T castValue(Object value, Class<T> requiredType) {
        if ((requiredType == Date.class) && (value instanceof Long)) {
            value = new Date((Long)value);
        }

        if (value instanceof Integer) {
            final int intValue = (Integer)value;
            if (requiredType == Long.class) {
                value = (long)intValue;
            } else if ((requiredType == Short.class) && (-32768 <= intValue) && (intValue <= 32767)) {
                value = (short)intValue;
            } else if ((requiredType == Byte.class) && (-128 <= intValue) && (intValue <= 127)) {
                value = (byte)intValue;
            }
        }

        if (!requiredType.isInstance(value))
			throw new RuntimeException(
					"Expected value to be of type: " + requiredType + ", but was " + value.getClass());
		else
			return requiredType.cast(value);
    }

    /**
     * Bean字段值复制
     * @param source    源
     * @param target    目标
     * @return  目标
     */
    public static <T extends Object>T copyBean(T source, T target) {
        if (isBlank(source) && isBlank(target)) return null;
        BeanUtils.copyProperties(source, target);
        return target;
    }

}