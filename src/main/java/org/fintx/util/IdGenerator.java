package org.fintx.util;


public class IdGenerator {
	private IdGenerator() {
	}

	/**
	 * 
	 * @Title: getUID
	 * @Description: (获取UUID)
	 * @return
	 */
	public static String getUID() {
		return ObjectIdUtil.base64ObjectId();
	}

	/**
	 * 
	 * @Title: getUID
	 * @Description: (获取UID数组)
	 * @param number 数组大小
	 * @return
	 */
	public static String[] getUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = ObjectIdUtil.base64ObjectId();
		}
		return ss;
	}

}
