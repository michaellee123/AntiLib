package dog.abcd.lib.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <b>数据校验类</b><br>
 * 一些数据的校验
 * 
 * @Company RZQC
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 上午10:56:01
 * @Mender Michael Lee<br>
 *         <b> change at </b>2017-01-17 上午11:56:01
 * 
 */
public class AntiDataUtils {

	private AntiDataUtils() {

	}

	/**
	 * 判断是不是手机号
	 * 
	 * @param number
	 *            号码
	 * @return 是手机号返回true
	 */
	public static boolean isPhoneNumber(String number) {
		boolean flag;
		try {
			Pattern regex = Pattern.compile("^(((1[3-9][0-9]))\\d{8})");
			Matcher matcher = regex.matcher(number);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断是否是只有小数点后两位的金额
	 * 
	 * @param money
	 * @return
	 */
	public static boolean isMoney(String money) {
		boolean flag;
		try {
			Pattern regex = Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$");
			Matcher matcher = regex.matcher(money);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static String formatMoney(double value) {
		try {
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			return bd.toString();
		} catch (Exception e) {
			return String.valueOf(value);
		}
	}

	/**
	 * 保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static String formatMoney(String value) {
		try {
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			return bd.toString();
		} catch (Exception e) {
			return value;
		}
	}

	/**
	 * 判断字符串是否为空
	 * @param str
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 是否为身份证
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean isIdCard(String idcard) {
		if (AntiDataUtils.isEmpty(idcard)) {
			return false;
		}
		idcard.toLowerCase(Locale.getDefault());
		try {
			if (idcard.length() < 18) {
				return false;
			}
			/*身份证校验*/
			int[] i_card_number = new int[idcard.length() - 1];
			char[] c_card_number = idcard.toCharArray();

			for (int i = 0; i < idcard.length() - 1; i++) {
				i_card_number[i] = Integer.parseInt(Character.toString(c_card_number[i]));
			}

			int[] key = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
			int num = 0;
			char nTail = '0';

			for (int i = 0; i < i_card_number.length; i++) {
				num += i_card_number[i] * key[i];
			}

			num %= 11;

			switch (num) {
			case 0: {
				nTail = '1';
				break;
			}
			case 1: {
				nTail = '0';
				break;
			}
			case 2: {
				nTail = 'x';
				break;
			}
			case 3: {
				nTail = '9';
				break;
			}
			case 4: {
				nTail = '8';
				break;
			}
			case 5: {
				nTail = '7';
				break;
			}
			case 6: {
				nTail = '6';
				break;
			}
			case 7: {
				nTail = '5';
				break;
			}
			case 8: {
				nTail = '4';
				break;
			}
			case 9: {
				nTail = '3';
				break;
			}
			case 10: {
				nTail = '2';
				break;
			}
			}

			if (!Character.toString(nTail).equals(idcard.substring(17))) {
				//身份证验证失败
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
