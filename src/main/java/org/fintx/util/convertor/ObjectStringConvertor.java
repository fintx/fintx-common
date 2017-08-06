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
package org.fintx.util.convertor;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public interface ObjectStringConvertor {
    public <T> String toString(T obj) throws Exception;

    public <T> T toObject(String text, Class<T> clazz) throws Exception;

    // public <T> String toString(T obj, Properties prop) throws Exception;

    // public <T> T toObject(String text, Class<T> clazz, Properties prop) throws
    // Exception;
}
