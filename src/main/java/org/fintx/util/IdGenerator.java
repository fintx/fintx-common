/**
 *  Copyright 2017 FinTx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
