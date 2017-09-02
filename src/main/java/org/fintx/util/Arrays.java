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

import java.lang.reflect.Array;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class Arrays {

    /**
     * The index value when an element is not found in a list or array: {@code -1}. This value is returned by methods in this class and can also be used in
     * comparisons with values returned by various method from {@link java.util.List}.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * An empty immutable {@code Object} array.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    /**
     * An empty immutable {@code Class} array.
     */
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    /**
     * An empty immutable {@code String} array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    /**
     * An empty immutable {@code long} array.
     */
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    /**
     * An empty immutable {@code Long} array.
     */
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
    /**
     * An empty immutable {@code int} array.
     */
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    /**
     * An empty immutable {@code Integer} array.
     */
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
    /**
     * An empty immutable {@code short} array.
     */
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    /**
     * An empty immutable {@code Short} array.
     */
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
    /**
     * An empty immutable {@code byte} array.
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    /**
     * An empty immutable {@code Byte} array.
     */
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
    /**
     * An empty immutable {@code double} array.
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    /**
     * An empty immutable {@code Double} array.
     */
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
    /**
     * An empty immutable {@code float} array.
     */
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    /**
     * An empty immutable {@code Float} array.
     */
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
    /**
     * An empty immutable {@code boolean} array.
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    /**
     * An empty immutable {@code Boolean} array.
     */
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
    /**
     * An empty immutable {@code char} array.
     */
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];

    private Arrays() {
    }

    // To map
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Converts the given array into a {@link java.util.Map}. Each element of the array must be either a {@link java.util.Map.Entry} or an Array, containing at
     * least two elements, where the first element is used as key and the second as value.
     *
     * <p>
     * This method can be used to initialize:
     * 
     * <pre>
     * // Create a Map mapping colors.
     * Map colorMap = org.fintx.Arrays.toMap(new String[][] { { "RED", "#FF0000" }, { "GREEN", "#00FF00" }, { "BLUE", "#0000FF" } });
     * </pre>
     *
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     *
     * @param array an array whose elements are either a {@link java.util.Map.Entry} or an Array containing at least two elements, may be {@code null}
     * @return a {@code Map} that was created from the array
     * @throws IllegalArgumentException if one element of this Array is itself an Array containing less then two elements
     * @throws IllegalArgumentException if the array contains elements other than {@link java.util.Map.Entry} and an Array
     */
    public static Map<Object, Object> toMap(final Object[] array) {
        if (array == null) {
            return null;
        }
        final Map<Object, Object> map = new HashMap<>((int) (array.length * 1.5));
        for (int i = 0; i < array.length; i++) {
            final Object object = array[i];
            if (object instanceof Map.Entry<?, ?>) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>) object;
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                final Object[] entry = (Object[]) object;
                if (entry.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
                }
                map.put(entry[0], entry[1]);
            } else {
                throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
            }
        }
        return map;
    }

    // Generic array
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Create a type-safe generic array.
     *
     * <p>
     * The Java language does not allow an array to be created from a generic type:
     *
     * <pre>
     * public static &lt;T&gt; T[] createAnArray(int size) {
     *     return new T[size]; // compiler error here
     * }
     * 
     * public static &lt;T&gt; T[] createAnArray(int size) {
     *     return (T[]) new Object[size]; // ClassCastException at runtime
     * }
     * </pre>
     *
     * <p>
     * Therefore new arrays of generic types can be created with this method. For example, an array of Strings can be created:
     *
     * <pre>
     * String[] array = org.fintx.Arrays.toArray("1", "2");
     * String[] emptyArray = org.fintx.Arrays.&lt;String&gt; toArray();
     * </pre>
     *
     * <p>
     * The method is typically used in scenarios, where the caller itself uses generic types that have to be combined into an array.
     *
     * <p>
     * Note, this method makes only sense to provide arguments of the same type so that the compiler can deduce the type of the array itself. While it is
     * possible to select the type explicitly like in
     * <code>Number[] array = org.fintx.Arrays.&lt;Number&gt;toArray(Integer.valueOf(42), Double.valueOf(Math.PI))</code>, there is no real advantage when
     * compared to <code>new Number[] {Integer.valueOf(42), Double.valueOf(Math.PI)}</code>.
     *
     * @param <T> the array's element type
     * @param items the varargs array items, null allowed
     * @return the array, not null unless a null array is passed in
     */
    public static <T> T[] toArray(final T...items) {
        return items;
    }

    // Subarrays
    // -----------------------------------------------------------------------

    /**
     * <p>
     * Produces a new {@code long} array containing the elements between the start and end indices.
     *
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     *
     * @param <T> the type of array eg:String[]
     * @param array the array
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     */
    public static <T> T subarray(final T array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array is not a Array!");
        }
        int length = getLength(array);
        if (0 == length) {
            return array;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive < 0) {
            endIndexExclusive = 0;
        }

        if (endIndexExclusive > length) {
            endIndexExclusive = length;
        }
        if (startIndexInclusive > endIndexExclusive) {
            startIndexInclusive = endIndexExclusive;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize == 0) {
            String className = array.getClass().getComponentType().getName();
            if (className.equals("int")) {
                return (T) EMPTY_INT_ARRAY;
            } else if (className.equals("short")) {
                return (T) EMPTY_SHORT_ARRAY;
            } else if (className.equals("long")) {
                return (T) EMPTY_LONG_ARRAY;
            } else if (className.equals("float")) {
                return (T) EMPTY_FLOAT_ARRAY;
            } else if (className.equals("double")) {
                return (T) EMPTY_DOUBLE_ARRAY;
            } else if (className.equals("boolean")) {
                return (T) EMPTY_BOOLEAN_ARRAY;
            } else if (className.equals("char")) {
                return (T) EMPTY_CHAR_ARRAY;
            } else if (className.equals("byte")) {
                return (T) EMPTY_BYTE_ARRAY;
            } else {
                return (T) Array.newInstance(array.getClass().getComponentType(), 0);
            }
        } else {
            T subarray = (T) Array.newInstance(array.getClass().getComponentType(), newSize);
            System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
            return subarray;
        }

    }

    // Is same length
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Checks whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * <p>
     * Any multi-dimensional aspects of the arrays are ignored.
     *
     * @param <E> E the type of array eg:String[] F the type of array2 eg:Object[]
     * @param <F> F the type of array2 eg:Object[]
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array
     */
    public static <E, F> boolean isSameLength(final E array1, final F array2) {
        return getLength(array1) == getLength(array2);

    }

    // -----------------------------------------------------------------------
    /**
     * <p>
     * Returns the length of the specified array. This method can deal with {@code Object} arrays and with primitive arrays.
     *
     * <p>
     * If the input array is {@code null}, {@code 0} is returned.
     *
     * <pre>
     * org.fintx.Arrays.getLength(null)            = 0
     * org.fintx.Arrays.getLength([])              = 0
     * org.fintx.Arrays.getLength([null])          = 1
     * org.fintx.Arrays.getLength([true, false])   = 2
     * org.fintx.Arrays.getLength([1, 2, 3])       = 3
     * org.fintx.Arrays.getLength(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param <T> the type of array eg:String[]
     * @param array the array to retrieve the length from, may be null
     * @return The length of the array, or {@code 0} if the array is {@code null}
     */
    public static <T> int getLength(final T array) {
        if (array == null) {
            return 0;
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array is not a Array!");
        }
        return Array.getLength(array);
    }

    /**
     * <p>
     * Checks whether two arrays are the same type taking into account multi-dimensional arrays.
     * 
     * @param <E> E the type of array eg:String[] F the type of array2 eg:Object[]
     * @param <F> F the type of array2 eg:Object[]
     * @param array1 the first array, must not be {@code null}
     * @param array2 the second array, must not be {@code null}
     * @return {@code true} if type of arrays matches
     * @throws IllegalArgumentException if either array is {@code null}
     */
    public static <E, F> boolean isSameType(final E array1, final F array2) {
        if (array1 == null || array2 == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (!array1.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array1 is not a Array!");
        }
        if (!array2.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array2 is not a Array!");
        }
        return array1.getClass().getName().equals(array2.getClass().getName());
    }

    // Reverse

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to reverse, may be {@code null}
     */
    public static <T> void reverse(final T array) {
        reverse(array, 0, getLength(array));
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to reverse, may be {@code null}
     * @param startIndexInclusive the starting index. Under value (&lt;0) is promoted to 0, over value (&gt;array.length) results in no change.
     * @param endIndexExclusive elements up to endIndex-1 are reversed in the array. Under value (&lt; start index) results in no change. Over value
     *            (&gt;array.length) is demoted to array length.
     */
    public static <T> void reverse(final T array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array1 is not a Array!");
        }
        int length = Array.getLength(array);
        if (length == 0) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(Array.getLength(array), endIndexExclusive) - 1;
        Object tmp;
        while (j > i) {
            tmp = Array.get(array, j);
            Array.set(array, j, Array.get(array, i));
            Array.set(array, i, tmp);
            j--;
            i++;
        }
    }

    // Swap
    // -----------------------------------------------------------------------
    /**
     * Swaps two elements in the given array.
     *
     * <p>
     * There is no special handling for multi-dimensional arrays. This method does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).
     * </p>
     *
     * Examples:
     * <ul>
     * <li>Arrays.swap(["1", "2", "3"], 0, 2) -&gt; ["3", "2", "1"]</li>
     * <li>Arrays.swap(["1", "2", "3"], 0, 0) -&gt; ["1", "2", "3"]</li>
     * <li>Arrays.swap(["1", "2", "3"], 1, 0) -&gt; ["2", "1", "3"]</li>
     * <li>Arrays.swap(["1", "2", "3"], 0, 5) -&gt; ["1", "2", "3"]</li>
     * <li>Arrays.swap(["1", "2", "3"], -1, 1) -&gt; ["2", "1", "3"]</li>
     * </ul>
     *
     * @param <T> the type of the array eg:String[]
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     */
    public static <T> void swap(final T array, final int offset1, final int offset2) {
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps a series of elements in the given array.
     *
     * <p>
     * This method does nothing for a {@code null} or empty input array or for overflow indices. Negative indices are promoted to 0(zero). If any of the
     * sub-arrays to swap falls outside of the given array, then the swap is stopped at the end of the array and as many as possible elements are swapped.
     * </p>
     *
     * Examples:
     * <ul>
     * <li>org.fintx.Arrays.swap(["1", "2", "3", "4"], 0, 2, 1) -&gt; ["3", "2", "1", "4"]</li>
     * <li>org.fintx.Arrays.swap(["1", "2", "3", "4"], 0, 0, 1) -&gt; ["1", "2", "3", "4"]</li>
     * <li>org.fintx.Arrays.swap(["1", "2", "3", "4"], 2, 0, 2) -&gt; ["3", "4", "1", "2"]</li>
     * <li>org.fintx.Arrays.swap(["1", "2", "3", "4"], -3, 2, 2) -&gt; ["3", "4", "1", "2"]</li>
     * <li>org.fintx.Arrays.swap(["1", "2", "3", "4"], 0, 3, 3) -&gt; ["4", "2", "3", "1"]</li>
     * </ul>
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     */
    public static <T> void swap(final T array, int offset1, int offset2, int len) {
        if (array == null) {
            return;
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array is not a Array!");
        }
        int length = getLength(array);
        if (array == null || length == 0 || offset1 >= length || offset2 >= length) {
            return;
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array is not a Array!");
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, length - offset1), length - offset2);
        Object aux = null;
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            aux = Array.get(array, offset1);
            Array.set(array, offset1, Array.get(array, offset2));
            Array.set(array, offset2, aux);
        }
    }

    // Shift
    // -----------------------------------------------------------------------
    /**
     * Shifts the order of the given array.
     *
     * <p>
     * There is no special handling for multi-dimensional arrays. This method does nothing for {@code null} or empty input arrays.
     * </p>
     *
     * @param <T> the type of the array eg:String[]
     * @param array the array to shift, may be {@code null}
     * @param offset The number of positions to rotate the elements. If the offset is larger than the number of elements to rotate, than the effective offset is
     *            modulo the number of elements to rotate.
     */
    public static <T> void shift(final T array, final int offset) {
        shift(array, 0, Integer.MAX_VALUE, offset);
    }

    /**
     * Shifts the order of a series of elements in the given array.
     *
     * <p>
     * There is no special handling for multi-dimensional arrays. This method does nothing for {@code null} or empty input arrays.
     * </p>
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to shift, may be {@code null}
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no change.
     * @param endIndexExclusive elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no change. Overvalue
     *            (&gt;array.length) is demoted to array length.
     * @param offset The number of positions to rotate the elements. If the offset is larger than the number of elements to rotate, than the effective offset is
     *            modulo the number of elements to rotate.
     */
    public static <T> void shift(final T array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null) {
            return;
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array is not a Array!");
        }
        int length = getLength(array);
        if (length == 0 || startIndexInclusive >= length - 1 || length <= 0) {
            return;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive >= length) {
            endIndexExclusive = length;
        }
        int n = endIndexExclusive - startIndexInclusive;
        if (n <= 1) {
            return;
        }
        offset %= n;
        if (offset < 0) {
            offset += n;
        }
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while (n > 1 && offset > 0) {
            final int n_offset = n - offset;

            if (offset > n_offset) {
                swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
                n = offset;
                offset -= n_offset;
            } else if (offset < n_offset) {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                startIndexInclusive += offset;
                n = n_offset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                break;
            }
        }
    }

    // IndexOf search
    // ----------------------------------------------------------------------
    /**
     * <p>
     * Finds the index of the given object in the array.
     *
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param <T> the type of array eg:String[]
     * @param array the array to search through for the object, may be {@code null}
     * @param objectToFind the object to find, may be {@code null}
     * @return the index of the object within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static <T> int indexOf(final T array, final Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * <p>
     * Finds the index of the given object in the array starting at the given index.
     *
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to search through for the object, may be {@code null}
     * @param objectToFind the object to find, may be {@code null}
     * @param startIndex the index to start searching at
     * @return the index of the object within the array starting at the index, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static <T> int indexOf(final T array, final Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array is not a Array!");
        }
        int length = getLength(array);
        if (length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i < length; i++) {
                if (Array.get(array, i) == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < length; i++) {
                if (objectToFind.equals(Array.get(array, i))) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>
     * Finds the last index of the given object within the array.
     *
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param <T> the type of the array eg:String[]
     * @param array the array to traverse backwards looking for the object, may be {@code null}
     * @param objectToFind the object to find, may be {@code null}
     * @return the last index of the object within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static <T> int lastIndexOf(final T array, final Object objectToFind) {
        return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>
     * Finds the last index of the given object in the array starting at the given index.
     *
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>
     * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the array length will search from the end of the array.
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to traverse for looking for the object, may be {@code null}
     * @param objectToFind the object to find, may be {@code null}
     * @param startIndex the start index to traverse backwards from
     * @return the last index of the object within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static <T> int lastIndexOf(final T array, final Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array is not a Array!");
        }
        int length = getLength(array);
        if (length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= length) {
            startIndex = length - 1;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i >= 0; i--) {
                if (Array.get(array, i) == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i >= 0; i--) {
                if (objectToFind.equals(Array.get(array, i))) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>
     * Checks if the object is in the given array.
     *
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to search through
     * @param objectToFind the object to find
     * @return {@code true} if the array contains the object
     */
    public static <T> boolean contains(final T array, final Object objectToFind) {
        return indexOf(array, objectToFind, 0) != INDEX_NOT_FOUND;
    }

    // Primitive/Object array converters
    // ----------------------------------------------------------------------

    // ----------------------------------------------------------------------
    /**
     * <p>
     * Checks if an array of Objects is empty or {@code null}.
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static <T> boolean isEmpty(final T array) {
        return getLength(array) == 0;
    }

    // ----------------------------------------------------------------------
    /**
     * <p>
     * Checks if an array of Objects is not empty and not {@code null}.
     *
     * @param <T> the component type of the array
     * @param array the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     */
    public static <T> boolean isNotEmpty(final T array) {
        return !isEmpty(array);
    }

    /**
     * <p>
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When an array is returned, it is always a new
     * array.
     *
     * <pre>
     * org.fintx.Arrays.addAll(null, null)     = null
     * org.fintx.Arrays.addAll(array1, null)   = cloned copy of array1
     * org.fintx.Arrays.addAll(null, array2)   = cloned copy of array2
     * org.fintx.Arrays.addAll([], [])         = []
     * org.fintx.Arrays.addAll([null], [null]) = [null, null]
     * org.fintx.Arrays.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array1 the first array whose elements are added to the new array, may be {@code null}
     * @param array2 the second array whose elements are added to the new array, may be {@code null}
     * @return The new array, {@code null} if both arrays are {@code null}. The type of the new array is the type of the first array, unless the first array is
     *         null, in which case the type is the same as the second array.
     * @throws IllegalArgumentException if the array types are incompatible
     * @throws NullPointerException if array is null
     */
    // TODO different from commons-lang3
    public static <T> T add(final T array1, final Object...array2) {

        if (null == array1) {
            throw new NullPointerException("Argument array1 should not be both null!");
        }
        int length1 = getLength(array1);
        int length2 = getLength(array2);
        if (array2 == null || length2 == 0) {
            return Objects.deepClone(array1);
        }

        final Class<?> type1 = array1.getClass().getComponentType();
        @SuppressWarnings("unchecked") // OK, because array is of type T
        final T joinedArray = (T) Array.newInstance(type1, length1 + length2);
        System.arraycopy(array1, 0, joinedArray, 0, length1);
        try {
            // System.arraycopy did not use autoboxing but Array use autoboxing for primitive type
            if (type1.isPrimitive()) {
                for (int i = 0; i < length2; i++) {
                    Array.set(joinedArray, length1 + i, Array.get(array2, i));
                }
            } else {
                System.arraycopy(array2, 0, joinedArray, length1, length2);
            }
        } catch (final ArrayStoreException ase) {
            // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because: - it would be a wasted check most of the time - safer, in case check turns out to be too
             * strict
             */
            final Class<?> type2 = array2[0].getClass();
            if (!type1.isAssignableFrom(type2)) {
                throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
            }
            throw ase; // No, so rethrow original
        }
        return joinedArray;
    }

    // /**
    // * Returns a copy of the given array of size 1 greater than the argument. The last value of the array is left to the default value.
    // *
    // * @param array The array to copy, must not be {@code null}.
    // * @param newArrayComponentType If {@code array} is {@code null}, create a size 1 array of this type.
    // * @return A new copy of the array of size 1 greater than the input.
    // */
    // private static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
    // if (array != null) {
    // final int arrayLength = Array.getLength(array);
    // final Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
    // System.arraycopy(array, 0, newArray, 0, arrayLength);
    // return newArray;
    // }
    // return Array.newInstance(newArrayComponentType, 1);
    // }

    // /**
    // * Underlying implementation of add(array, index, element) methods. The last parameter is the class, which may not equal element.getClass for primitives.
    // *
    // * @param array the array to add the element to, may be {@code null}
    // * @param index the position of the new object
    // * @param element the object to add
    // * @param clss the type of the element being added
    // * @return A new array containing the existing elements and the new element
    // */
    // private static Object add(final Object array, final int index, final Object element, final Class<?> clss) {
    // if (array == null) {
    // if (index != 0) {
    // throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
    // }
    // final Object joinedArray = Array.newInstance(clss, 1);
    // Array.set(joinedArray, 0, element);
    // return joinedArray;
    // }
    //
    // final int length = Array.getLength(array);
    // if (index > length || index < 0) {
    // throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
    // }
    // final Object result = Array.newInstance(clss, length + 1);
    // System.arraycopy(array, 0, result, 0, index);
    // Array.set(result, index, element);
    // if (index < length) {
    // System.arraycopy(array, index, result, index + 1, length - index);
    // }
    // return result;
    // }

    /**
     * <p>
     * Removes the first occurrence of the specified element from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices). If the array doesn't contains such an element, no elements are removed from the array.
     *
     * <p>
     * This method returns a new array with the same elements of the input array except the first occurrence of the specified element. The component type of the
     * returned array is always the same as that of the input array.
     *
     * <pre>
     * org.fintx.Arrays.removeElement(null, 1)      = null
     * org.fintx.Arrays.removeElement([], 1)        = []
     * org.fintx.Arrays.removeElement([1], 2)       = [1]
     * org.fintx.Arrays.removeElement([1, 3], 1)    = [3]
     * org.fintx.Arrays.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to remove the element from, may be {@code null}
     * @param element the element to be removed
     * @return A new array containing the existing elements except the first occurrence of the specified element.
     */
    public static <T> T removeElement(final T array, final Object element) {
        final int index = indexOf(array, element, 0);
        if (index == INDEX_NOT_FOUND) {
            return Objects.deepClone(array);
        }
        return remove(array, index);
    }

    /**
     * Removes multiple array elements specified by index.
     * 
     * @param <T> the type of array eg:String[]
     * @param array source
     * @param indices to remove
     * @return new array of same type minus elements specified by unique values of {@code indices}
     */
    // package protected for access by unit tests
    static <T> T remove(final T array, final int...indices) {
        final int length = getLength(array);
        int diff = 0; // number of distinct indexes, i.e. number of entries that will be removed
        final int[] clonedIndices = Objects.deepClone(indices);
        java.util.Arrays.sort(clonedIndices);

        // identify length of result array
        if (isNotEmpty(clonedIndices)) {
            int i = clonedIndices.length;
            int prevIndex = length;
            while (--i >= 0) {
                final int index = clonedIndices[i];
                if (index < 0 || index >= length) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index >= prevIndex) {
                    continue;
                }
                diff++;
                prevIndex = index;
            }
        }

        // create result array
        final T result = (T) Array.newInstance(array.getClass().getComponentType(), length - diff);
        if (diff < length) {
            int end = length; // index just after last copy
            int dest = length - diff; // number of entries so far not copied
            for (int i = clonedIndices.length - 1; i >= 0; i--) {
                final int index = clonedIndices[i];
                if (end - index > 1) { // same as (cp > 0)
                    final int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                    // Afer this copy, we still have room for dest items.
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * Removes multiple array elements specified by indices.
     * 
     * @param <T> the type of array eg:String[]
     * @param array source
     * @param indices to remove
     * @return new array of same type minus elements specified by the set bits in {@code indices}
     */
    // package protected for access by unit tests
    static <T> T removeAll(final T array, final BitSet indices) {
        final int srcLength = Array.getLength(array);
        // No need to check maxIndex here, because method only currently called from removeElements()
        // which guarantee to generate on;y valid bit entries.
        // final int maxIndex = indices.length();
        // if (maxIndex > srcLength) {
        // throw new IndexOutOfBoundsException("Index: " + (maxIndex-1) + ", Length: " + srcLength);
        // }
        final int removals = indices.cardinality(); // true bits are items to remove
        final T result = (T) Array.newInstance(array.getClass().getComponentType(), srcLength - removals);
        int srcIndex = 0;
        int destIndex = 0;
        int count;
        int set;
        while ((set = indices.nextSetBit(srcIndex)) != -1) {
            count = set - srcIndex;
            if (count > 0) {
                System.arraycopy(array, srcIndex, result, destIndex, count);
                destIndex += count;
            }
            srcIndex = indices.nextClearBit(set);
        }
        count = srcLength - srcIndex;
        if (count > 0) {
            System.arraycopy(array, srcIndex, result, destIndex, count);
        }
        return result;
    }

    /**
     * <p>
     * This method checks whether the provided array is sorted according to natural ordering.
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     */
    public static <T> boolean isSorted(final T array) {
        if (array == null || !array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array should not be null or not a array.");
        }
        int length = getLength(array);
        if (length < 2) {
            return true;
        }
        if (Objects.isWrapperType(array.getClass().getComponentType())) {
            return isSorted(array, new Comparator<Comparable>() {
                @Override
                public int compare(final Comparable o1, final Comparable o2) {
                    return o1.compareTo(o2);
                }
            });
        } else if (array.getClass().getComponentType().isPrimitive()) {

            // String className = array.getClass().getComponentType().getName();
            // if (className.equals("int")) {
            // return isSorted(array, new Comparator<Integer>() {
            // @Override
            // public int compare(final Integer o1, final Integer o2) {
            // return o1.compareTo(o2);
            // }
            // });
            // } else if (className.equals("short")) {
            // return isSorted(array, new Comparator<Short>() {
            // @Override
            // public int compare(final Short o1, final Short o2) {
            // return o1.compareTo(o2);
            // }
            // });
            // } else if (className.equals("long")) {
            // return isSorted(array, new Comparator<Long>() {
            // @Override
            // public int compare(final Long o1, final Long o2) {
            // return o1.compareTo(o2);
            // }
            // });
            // } else if (className.equals("float")) {
            // return isSorted(array, new Comparator<Float>() {
            // @Override
            // public int compare(final Float o1, final Float o2) {
            // return o1.compareTo(o2);
            // }
            // });
            // } else if (className.equals("double")) {
            // return isSorted(array, new Comparator<Double>() {
            // @Override
            // public int compare(final Double o1, final Double o2) {
            // return o1.compareTo(o2);
            // }
            // });
            // } else if (className.equals("boolean")) {
            // return isSorted(array, new Comparator<Boolean>() {
            // @Override
            // public int compare(final Boolean o1, final Boolean o2) {
            // return o1.compareTo(o2);
            // }
            // });
            // } else if (className.equals("char")) {
            // return isSorted(array, new Comparator<Character>() {
            // @Override
            // public int compare(final Character o1, final Character o2) {
            // return o1.compareTo(o2);
            // }
            // });
            // } else {//className.equals("byte")
            // return isSorted(array, new Comparator<Byte>() {
            // @Override
            // public int compare(final Byte o1, final Byte o2) {
            // return o1.compareTo(o2);
            // }
            // });
            // }
            return isSorted(array, new Comparator() {
                @Override
                public int compare(final Object o1, final Object o2) {
                    return ((Comparable) o1).compareTo(o2);
                }
            });

        } else {
            throw new IllegalArgumentException("Araument array can not use default comparator.");
        }

    }

    /**
     * <p>
     * This method checks whether the provided array is sorted according to the provided {@code Comparator}.
     *
     * @param <T> the type of the array eg:String[]
     * @param array the array to check
     * @param comparator the {@code Comparator} to compare over
     * @param <T> the type of array eg:String[]
     * @return whether the array is sorted
     */
    public static <T> boolean isSorted(final T array, final Comparator comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator should not be null.");
        }

        if (array == null || !array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array should not be null and must be Array.");
        }
        int length = getLength(array);
        if (length < 2) {
            return true;
        }

        Object previous = Array.get(array, 0);
        // final int n = array.length;
        for (int i = 1; i < length; i++) {
            final Object current = Array.get(array, i);
            if (comparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    /**
     * Removes the occurrences of the specified element from the specified array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices). If the array doesn't contains such an element, no elements are
     * removed from the array. <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param <T> the type of object in the array
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     */
    public static <T> T[] removeAllOccurences(final T[] array, final T element) {
        int index = indexOf(array, element, 0);
        if (index == INDEX_NOT_FOUND) {
            return Objects.deepClone(array);
        }

        final int[] indices = new int[array.length - index];
        indices[0] = index;
        int count = 1;

        while ((index = indexOf(array, element, indices[count - 1] + 1)) != INDEX_NOT_FOUND) {
            indices[count++] = index;
        }

        return (T[]) remove(array, java.util.Arrays.copyOf(indices, count));
    }

    /**
     * Removes the occurrences of the specified element from the specified array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices). If the array doesn't contains such an element, no elements are
     * removed from the array. <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param <T> the type of object in the array
     * @param element the element to remove
     * @param array the input array
     * @return A new array containing the existing elements except the occurrences of the specified element.
     */
    public static <T> T removeAllOccurences(final T array, final Object element) {
        int index = indexOf(array, element, 0);
        if (index == INDEX_NOT_FOUND) {
            return Objects.deepClone(array);
        }
        int length = getLength(array);
        final int[] indices = new int[length - index];
        indices[0] = index;
        int count = 1;

        while ((index = indexOf(array, element, indices[count - 1] + 1)) != INDEX_NOT_FOUND) {
            indices[count++] = index;
        }

        return remove(array, java.util.Arrays.copyOf(indices, count));
    }

    /**
     * <p>
     * Returns an array containing the string representation of each element in the argument array.
     * </p>
     *
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param <T> the type of the array eg:String[]
     * @param array the {@code Object[]} to be processed, may be null
     * @return {@code String[]} of the same size as the source with its element's string representation, {@code null} if null array input
     * @throws NullPointerException if array contains {@code null}
     */
    // TODO different from commons-lang3
    public static <T> String[] toStringArray(final T array) {
        return toStringArray(array, null);
    }

    /**
     * <p>
     * Returns an array containing the string representation of each element in the argument array handling {@code null} elements.
     * </p>
     *
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     * 
     * @param <T> the type of array eg:String[]
     * @param array the Object[] to be processed, may be null
     * @param valueForNullElements the value to insert if {@code null} is found
     * @return a {@code String} array, {@code null} if null array input
     */
    public static <T> String[] toStringArray(final T array, final String valueForNullElements) {
        int length = getLength(array);
        if (null == array) {
            return null;
        } else if (length == 0) {
            return EMPTY_STRING_ARRAY;
        }

        final String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            final Object object = Array.get(array, i);
            result[i] = (object == null ? valueForNullElements : object.toString());
        }

        return result;
    }

    /**
     * <p>
     * Inserts elements into an array at the given index (starting from zero).
     * </p>
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * org.fintx.Arrays.insert(index, null, null)      = null
     * org.fintx.Arrays.insert(index, array, null)     = cloned copy of 'array'
     * org.fintx.Arrays.insert(index, null, values)    = null
     * </pre>
     * 
     * @param <T> the type of array eg:String[]
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}
     * @throws NullPointerException when the array is null
     */
    // TODO different from commons-lang3
    public static <T> T insert(final T array, final int index, final Object...values) {
        if (null == array) {
            throw new NullPointerException("Argument array should not be both null!");
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument array should not be null and must be Array.");
        }
        if (values == null || values.length == 0) {
            return Objects.deepClone(array);
        }
        int length = getLength(array);
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        T result = (T) Array.newInstance(array.getClass().getComponentType(), length + values.length);
        if (array.getClass().getComponentType().isPrimitive()) {
            // System.arraycopy(values, 0, result, index, values.length);
            for (int i = 0; i < values.length; i++) {
                Array.set(result, index + i, Array.get(values, i));
            }
            if (index > 0) {
                // System.arraycopy(array, 0, result, 0, index);
                for (int i = 0; i < index; i++) {
                    Array.set(result, i, Array.get(array, i));
                }
            }
            if (index < length) {
                int size = length - index;
                int resultStartPosition = index + values.length;
                // System.arraycopy(array, index, result,resultStartPosition , size);
                for (int i = 0; i < size; i++) {
                    Array.set(result, resultStartPosition + i, Array.get(array, index + i));
                }
            }
        } else {
            System.arraycopy(values, 0, result, index, values.length);
            if (index > 0) {
                System.arraycopy(array, 0, result, 0, index);
            }
            if (index < length) {
                System.arraycopy(array, index, result, index + values.length, length - index);
            }
        }

        return result;
    }

    // /**
    // * <p>
    // * Inserts elements into an array at the given index (starting from zero).
    // * </p>
    // *
    // * <p>
    // * When an array is returned, it is always a new array.
    // * </p>
    // *
    // * <pre>
    // * org.fintx.Arrays.insert(index, null, null) = null
    // * org.fintx.Arrays.insert(index, array, null) = cloned copy of 'array'
    // * org.fintx.Arrays.insert(index, null, values) = null
    // * </pre>
    // *
    // * @param <T> The type of elements in {@code array} and {@code values}
    // * @param index the position within {@code array} to insert the new values
    // * @param array the array to insert the values into, may be {@code null}
    // * @param values the new values to insert, may be {@code null}
    // * @return The new array.
    // * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}
    // */
    // @SafeVarargs
    // public static <T> T[] insert(final T[] array, final int index, final T...values) {
    // /*
    // * Note on use of @SafeVarargs:
    // *
    // * By returning null when 'array' is null, we avoid returning the vararg array to the caller. We also avoid relying on the type of the vararg array, by
    // * inspecting the component type of 'array'.
    // */
    //
    // if (array == null) {
    // return null;
    // }
    // if (values == null || values.length == 0) {
    // return Objects.deepClone(array);
    // }
    // if (index < 0 || index > array.length) {
    // throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
    // }
    //
    // final Class<?> type = array.getClass().getComponentType();
    // @SuppressWarnings("unchecked") // OK, because array and values are of type T
    // T[] result = (T[]) Array.newInstance(type, array.length + values.length);
    //
    // System.arraycopy(values, 0, result, index, values.length);
    // if (index > 0) {
    // System.arraycopy(array, 0, result, 0, index);
    // }
    // if (index < array.length) {
    // System.arraycopy(array, index, result, index + values.length, array.length - index);
    // }
    // return result;
    // }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     */
    public static <T> void shuffle(T array) {
        shuffle(array, new Random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     * 
     * @param <T> the type of array eg:String[]
     * @param array the array to shuffle
     * @param random the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     */
    public static <T> void shuffle(T array, Random random) {
        for (int i = getLength(array); i > 1; i--) {
            swap(array, i - 1, random.nextInt(i), 1);
        }
    }

}