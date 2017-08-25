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

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class ArraysTest {

    // -----------------------------------------------------------------------
    @Test
    public void testHashCode() {
        final long[][] array1 = new long[][] { { 2, 5 }, { 4, 5 } };
        final long[][] array2 = new long[][] { { 2, 5 }, { 4, 6 } };
        assertTrue(Objects.hashCode(array1) == Objects.hashCode(array1));
        assertFalse(Objects.hashCode(array1) == Objects.hashCode(array2));

        final Object[] array3 = new Object[] { new String(new char[] { 'A', 'B' }) };
        final Object[] array4 = new Object[] { "AB" };
        assertTrue(Objects.hashCode(array3) == Objects.hashCode(array3));
        assertTrue(Objects.hashCode(array3) == Objects.hashCode(array4));

        final Object[] arrayA = new Object[] { new boolean[] { true, false }, new int[] { 6, 7 } };
        final Object[] arrayB = new Object[] { new boolean[] { true, false }, new int[] { 6, 7 } };
        assertTrue(Objects.hashCode(arrayB) == Objects.hashCode(arrayA));
    }

    // -----------------------------------------------------------------------
    private void assertIsEquals(final Object array1, final Object array2, final Object array3) {
        assertTrue(Objects.deepEquals(array1, array1));
        assertTrue(Objects.deepEquals(array2, array2));
        assertTrue(Objects.deepEquals(array3, array3));
        assertFalse(Objects.deepEquals(array1, array2));
        assertFalse(Objects.deepEquals(array2, array1));
        assertFalse(Objects.deepEquals(array1, array3));
        assertFalse(Objects.deepEquals(array3, array1));
        assertFalse(Objects.deepEquals(array1, array2));
        assertFalse(Objects.deepEquals(array2, array1));
    }

    @Test
    public void testIsEquals() {
        final long[][] larray1 = new long[][] { { 2, 5 }, { 4, 5 } };
        final long[][] larray2 = new long[][] { { 2, 5 }, { 4, 6 } };
        final long[] larray3 = new long[] { 2, 5 };
        this.assertIsEquals(larray1, larray2, larray3);

        final int[][] iarray1 = new int[][] { { 2, 5 }, { 4, 5 } };
        final int[][] iarray2 = new int[][] { { 2, 5 }, { 4, 6 } };
        final int[] iarray3 = new int[] { 2, 5 };
        this.assertIsEquals(iarray1, iarray2, iarray3);

        final short[][] sarray1 = new short[][] { { 2, 5 }, { 4, 5 } };
        final short[][] sarray2 = new short[][] { { 2, 5 }, { 4, 6 } };
        final short[] sarray3 = new short[] { 2, 5 };
        this.assertIsEquals(sarray1, sarray2, sarray3);

        final float[][] farray1 = new float[][] { { 2, 5 }, { 4, 5 } };
        final float[][] farray2 = new float[][] { { 2, 5 }, { 4, 6 } };
        final float[] farray3 = new float[] { 2, 5 };
        this.assertIsEquals(farray1, farray2, farray3);

        final double[][] darray1 = new double[][] { { 2, 5 }, { 4, 5 } };
        final double[][] darray2 = new double[][] { { 2, 5 }, { 4, 6 } };
        final double[] darray3 = new double[] { 2, 5 };
        this.assertIsEquals(darray1, darray2, darray3);

        final byte[][] byteArray1 = new byte[][] { { 2, 5 }, { 4, 5 } };
        final byte[][] byteArray2 = new byte[][] { { 2, 5 }, { 4, 6 } };
        final byte[] byteArray3 = new byte[] { 2, 5 };
        this.assertIsEquals(byteArray1, byteArray2, byteArray3);

        final char[][] charArray1 = new char[][] { { 2, 5 }, { 4, 5 } };
        final char[][] charArray2 = new char[][] { { 2, 5 }, { 4, 6 } };
        final char[] charArray3 = new char[] { 2, 5 };
        this.assertIsEquals(charArray1, charArray2, charArray3);

        final boolean[][] barray1 = new boolean[][] { { true, false }, { true, true } };
        final boolean[][] barray2 = new boolean[][] { { true, false }, { true, false } };
        final boolean[] barray3 = new boolean[] { false, true };
        this.assertIsEquals(barray1, barray2, barray3);

        final Object[] array3 = new Object[] { new String(new char[] { 'A', 'B' }) };
        final Object[] array4 = new Object[] { "AB" };
        assertTrue(Objects.deepEquals(array3, array3));
        assertTrue(Objects.deepEquals(array3, array4));

        assertTrue(Objects.deepEquals(null, null));
        assertFalse(Objects.deepEquals(null, array4));
    }

    // -----------------------------------------------------------------------
    /**
     * Tests generic array creation with parameters of same type.
     */
    @Test
    public void testArrayCreation() {
        final String[] array = Arrays.toArray("foo", "bar");
        assertEquals(2, array.length);
        assertEquals("foo", array[0]);
        assertEquals("bar", array[1]);
    }

    /**
     * Tests generic array creation with general return type.
     */
    @Test
    public void testArrayCreationWithGeneralReturnType() {
        final Object obj = Arrays.toArray("foo", "bar");
        assertTrue(obj instanceof String[]);
    }

    /**
     * Tests generic array creation with parameters of common base type.
     */
    @Test
    public void testArrayCreationWithDifferentTypes() {
        final Number[] array = Arrays.<Number> toArray(Integer.valueOf(42), Double.valueOf(Math.PI));
        assertEquals(2, array.length);
        assertEquals(Integer.valueOf(42), array[0]);
        assertEquals(Double.valueOf(Math.PI), array[1]);
    }

    /**
     * Tests generic array creation with generic type.
     */
    @Test
    public void testIndirectArrayCreation() {
        final String[] array = toArrayPropagatingType("foo", "bar");
        assertEquals(2, array.length);
        assertEquals("foo", array[0]);
        assertEquals("bar", array[1]);
    }

    /**
     * Tests generic empty array creation with generic type.
     */
    @Test
    public void testEmptyArrayCreation() {
        final String[] array = Arrays.<String> toArray();
        assertEquals(0, array.length);
    }

    /**
     * Tests indirect generic empty array creation with generic type.
     */
    @Test
    public void testIndirectEmptyArrayCreation() {
        final String[] array = ArraysTest.<String> toArrayPropagatingType();
        assertEquals(0, array.length);
    }

    @SafeVarargs
    private static <T> T[] toArrayPropagatingType(final T...items) {
        return Arrays.toArray(items);
    }

    // -----------------------------------------------------------------------
    @Test
    public void testToMap() {
        Map<?, ?> map = Arrays.toMap(new String[][] { { "foo", "bar" }, { "hello", "world" } });

        assertEquals("bar", map.get("foo"));
        assertEquals("world", map.get("hello"));

        assertEquals(null, Arrays.toMap(null));
        try {
            Arrays.toMap(new String[][] { { "foo", "bar" }, { "short" } });
            fail("exception expected");
        } catch (final IllegalArgumentException ex) {
        }
        try {
            Arrays.toMap(new Object[] { new Object[] { "foo", "bar" }, "illegal type" });
            fail("exception expected");
        } catch (final IllegalArgumentException ex) {
        }
        try {
            Arrays.toMap(new Object[] { new Object[] { "foo", "bar" }, null });
            fail("exception expected");
        } catch (final IllegalArgumentException ex) {
        }

        map = Arrays.toMap(new Object[] { new Map.Entry<Object, Object>() {
            @Override
            public Object getKey() {
                return "foo";
            }

            @Override
            public Object getValue() {
                return "bar";
            }

            @Override
            public Object setValue(final Object value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean equals(final Object o) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int hashCode() {
                throw new UnsupportedOperationException();
            }
        } });
        assertEquals("bar", map.get("foo"));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testClone() {
        assertArrayEquals(null, Objects.deepClone((Object[]) null));
        Object[] original1 = new Object[0];
        Object[] cloned1 = Objects.deepClone(original1);
        assertTrue(Objects.deepEquals(original1, cloned1));
        assertTrue(original1 != cloned1);

        final StringBuilder builder = new StringBuilder("pick");
        original1 = new Object[] { builder, "a", new String[] { "stick" } };
        cloned1 = Objects.deepClone(original1);

        assertTrue(original1 != cloned1);
        /* !!! */
        // assertSame(original1[0], cloned1[0]);
        // assertSame(original1[1], cloned1[1]);
        // assertSame(original1[2], cloned1[2]);
        assertTrue(Objects.deepEquals(original1[0], cloned1[0]));
        assertTrue(Objects.deepEquals(original1[1], cloned1[1]));
        assertTrue(Objects.deepEquals(original1[2], cloned1[2]));

        assertTrue(Objects.deepEquals(original1, cloned1));

    }

    @Test
    public void testCloneBoolean() {
        assertEquals(null, Objects.deepClone((boolean[]) null));
        final boolean[] original = new boolean[] { true, false };
        final boolean[] cloned = Objects.deepClone(original);
        assertTrue(Objects.deepEquals(original, cloned));
        assertTrue(original != cloned);
    }

    @Test
    public void testCloneLong() {
        assertEquals(null, Objects.deepClone((long[]) null));
        final long[] original = new long[] { 0L, 1L };
        final long[] cloned = Objects.deepClone(original);
        assertTrue(Objects.deepEquals(original, cloned));
        assertTrue(original != cloned);
    }

    @Test
    public void testCloneInt() {
        assertEquals(null, Objects.deepClone((int[]) null));
        final int[] original = new int[] { 5, 8 };
        final int[] cloned = Objects.deepClone(original);
        assertTrue(Objects.deepEquals(original, cloned));
        assertTrue(original != cloned);
    }

    @Test
    public void testCloneShort() {
        assertEquals(null, Objects.deepClone((short[]) null));
        final short[] original = new short[] { 1, 4 };
        final short[] cloned = Objects.deepClone(original);
        assertTrue(Objects.deepEquals(original, cloned));
        assertTrue(original != cloned);
    }

    @Test
    public void testCloneChar() {
        assertEquals(null, Objects.deepClone((char[]) null));
        final char[] original = new char[] { 'a', '4' };
        final char[] cloned = Objects.deepClone(original);
        assertTrue(Objects.deepEquals(original, cloned));
        assertTrue(original != cloned);
    }

    @Test
    public void testCloneByte() {
        assertEquals(null, Objects.deepClone((byte[]) null));
        final byte[] original = new byte[] { 1, 6 };
        final byte[] cloned = Objects.deepClone(original);
        assertTrue(Objects.deepEquals(original, cloned));
        assertTrue(original != cloned);
    }

    @Test
    public void testCloneDouble() {
        assertEquals(null, Objects.deepClone((double[]) null));
        final double[] original = new double[] { 2.4d, 5.7d };
        final double[] cloned = Objects.deepClone(original);
        assertTrue(Objects.deepEquals(original, cloned));
        assertTrue(original != cloned);
    }

    @Test
    public void testCloneFloat() {
        assertEquals(null, Objects.deepClone((float[]) null));
        final float[] original = new float[] { 2.6f, 6.4f };
        final float[] cloned = Objects.deepClone(original);
        assertTrue(Objects.deepEquals(original, cloned));
        assertTrue(original != cloned);
    }

    // -----------------------------------------------------------------------

    private class TestClass {
    }

    // -----------------------------------------------------------------------

    @Test
    public void testSubarrayObject() {
        final Object[] nullArray = null;
        final Object[] objectArray = { "a", "b", "c", "d", "e", "f" };
        assertEquals("0 start, mid end", "abcd", Strings.join(Arrays.subarray(objectArray, 0, 4)));
        assertEquals("0 start, length end", "abcdef", Strings.join(Arrays.subarray(objectArray, 0, objectArray.length)));
        assertEquals("mid start, mid end", "bcd", Strings.join(Arrays.subarray(objectArray, 1, 4)));
        assertEquals("mid start, length end", "bcdef", Strings.join(Arrays.subarray(objectArray, 1, objectArray.length)));

        assertNull("null input", Arrays.subarray(nullArray, 0, 3));
        assertEquals("empty array", "", Strings.join(Arrays.subarray(Arrays.EMPTY_OBJECT_ARRAY, 1, 2)));
        assertEquals("start > end", "", Strings.join(Arrays.subarray(objectArray, 4, 2)));
        assertEquals("start == end", "", Strings.join(Arrays.subarray(objectArray, 3, 3)));
        assertEquals("start undershoot, normal end", "abcd", Strings.join(Arrays.subarray(objectArray, -2, 4)));
        assertEquals("start overshoot, any end", "", Strings.join(Arrays.subarray(objectArray, 33, 4)));
        assertEquals("normal start, end overshoot", "cdef", Strings.join(Arrays.subarray(objectArray, 2, 33)));
        assertEquals("start undershoot, end overshoot", "abcdef", Strings.join(Arrays.subarray(objectArray, -2, 12)));

        // array type tests
        final Date[] dateArray = { new java.sql.Date(new Date().getTime()), new Date(), new Date(), new Date(), new Date() };

        assertSame("Object type", Object.class, Arrays.subarray(objectArray, 2, 4).getClass().getComponentType());
        assertSame("java.util.Date type", java.util.Date.class, Arrays.subarray(dateArray, 1, 4).getClass().getComponentType());
        assertNotSame("java.sql.Date type", java.sql.Date.class, Arrays.subarray(dateArray, 1, 4).getClass().getComponentType());
        try {
            @SuppressWarnings("unused")
            final java.sql.Date[] dummy = (java.sql.Date[]) Arrays.subarray(dateArray, 1, 3);
            fail("Invalid downcast");
        } catch (final ClassCastException e) {
        }
    }

    @Test
    public void testSubarrayLong() {
        final long[] nullArray = null;
        final long[] array = { 999910, 999911, 999912, 999913, 999914, 999915 };
        final long[] leftSubarray = { 999910, 999911, 999912, 999913 };
        final long[] midSubarray = { 999911, 999912, 999913, 999914 };
        final long[] rightSubarray = { 999912, 999913, 999914, 999915 };

        assertTrue("0 start, mid end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, 0, 4)));

        assertTrue("0 start, length end", Objects.deepEquals(array, Arrays.subarray(array, 0, array.length)));

        assertTrue("mid start, mid end", Objects.deepEquals(midSubarray, Arrays.subarray(array, 1, 5)));

        assertTrue("mid start, length end", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, array.length)));

        assertNull("null input", Arrays.subarray(nullArray, 0, 3));

        assertEquals("empty array", Arrays.EMPTY_LONG_ARRAY, Arrays.subarray(Arrays.EMPTY_LONG_ARRAY, 1, 2));

        assertEquals("start > end", Arrays.EMPTY_LONG_ARRAY, Arrays.subarray(array, 4, 2));

        assertEquals("start == end", Arrays.EMPTY_LONG_ARRAY, Arrays.subarray(array, 3, 3));

        assertTrue("start undershoot, normal end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, -2, 4)));

        assertEquals("start overshoot, any end", Arrays.EMPTY_LONG_ARRAY, Arrays.subarray(array, 33, 4));

        assertTrue("normal start, end overshoot", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, 33)));

        assertTrue("start undershoot, end overshoot", Objects.deepEquals(array, Arrays.subarray(array, -2, 12)));

        // empty-return tests

        assertSame("empty array, object test", Arrays.EMPTY_LONG_ARRAY, Arrays.subarray(Arrays.EMPTY_LONG_ARRAY, 1, 2));

        assertSame("start > end, object test", Arrays.EMPTY_LONG_ARRAY, Arrays.subarray(array, 4, 1));

        assertSame("start == end, object test", Arrays.EMPTY_LONG_ARRAY, Arrays.subarray(array, 3, 3));

        assertSame("start overshoot, any end, object test", Arrays.EMPTY_LONG_ARRAY, Arrays.subarray(array, 8733, 4));

        // array type tests

        assertSame("long type", long.class, Arrays.subarray(array, 2, 4).getClass().getComponentType());

    }

    @Test
    public void testSubarrayInt() {
        final int[] nullArray = null;
        final int[] array = { 10, 11, 12, 13, 14, 15 };
        final int[] leftSubarray = { 10, 11, 12, 13 };
        final int[] midSubarray = { 11, 12, 13, 14 };
        final int[] rightSubarray = { 12, 13, 14, 15 };

        assertTrue("0 start, mid end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, 0, 4)));

        assertTrue("0 start, length end", Objects.deepEquals(array, Arrays.subarray(array, 0, array.length)));

        assertTrue("mid start, mid end", Objects.deepEquals(midSubarray, Arrays.subarray(array, 1, 5)));

        assertTrue("mid start, length end", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, array.length)));

        assertNull("null input", Arrays.subarray(nullArray, 0, 3));

        assertEquals("empty array", Arrays.EMPTY_INT_ARRAY, Arrays.subarray(Arrays.EMPTY_INT_ARRAY, 1, 2));

        assertEquals("start > end", Arrays.EMPTY_INT_ARRAY, Arrays.subarray(array, 4, 2));

        assertEquals("start == end", Arrays.EMPTY_INT_ARRAY, Arrays.subarray(array, 3, 3));

        assertTrue("start undershoot, normal end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, -2, 4)));

        assertEquals("start overshoot, any end", Arrays.EMPTY_INT_ARRAY, Arrays.subarray(array, 33, 4));

        assertTrue("normal start, end overshoot", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, 33)));

        assertTrue("start undershoot, end overshoot", Objects.deepEquals(array, Arrays.subarray(array, -2, 12)));

        // empty-return tests

        assertSame("empty array, object test", Arrays.EMPTY_INT_ARRAY, Arrays.subarray(Arrays.EMPTY_INT_ARRAY, 1, 2));

        assertSame("start > end, object test", Arrays.EMPTY_INT_ARRAY, Arrays.subarray(array, 4, 1));

        assertSame("start == end, object test", Arrays.EMPTY_INT_ARRAY, Arrays.subarray(array, 3, 3));

        assertSame("start overshoot, any end, object test", Arrays.EMPTY_INT_ARRAY, Arrays.subarray(array, 8733, 4));

        // array type tests

        assertSame("int type", int.class, Arrays.subarray(array, 2, 4).getClass().getComponentType());

    }

    @Test
    public void testSubarrayShort() {
        final short[] nullArray = null;
        final short[] array = { 10, 11, 12, 13, 14, 15 };
        final short[] leftSubarray = { 10, 11, 12, 13 };
        final short[] midSubarray = { 11, 12, 13, 14 };
        final short[] rightSubarray = { 12, 13, 14, 15 };

        assertTrue("0 start, mid end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, 0, 4)));

        assertTrue("0 start, length end", Objects.deepEquals(array, Arrays.subarray(array, 0, array.length)));

        assertTrue("mid start, mid end", Objects.deepEquals(midSubarray, Arrays.subarray(array, 1, 5)));

        assertTrue("mid start, length end", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, array.length)));

        assertNull("null input", Arrays.subarray(nullArray, 0, 3));

        assertEquals("empty array", Arrays.EMPTY_SHORT_ARRAY, Arrays.subarray(Arrays.EMPTY_SHORT_ARRAY, 1, 2));

        assertEquals("start > end", Arrays.EMPTY_SHORT_ARRAY, Arrays.subarray(array, 4, 2));

        assertEquals("start == end", Arrays.EMPTY_SHORT_ARRAY, Arrays.subarray(array, 3, 3));

        assertTrue("start undershoot, normal end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, -2, 4)));

        assertEquals("start overshoot, any end", Arrays.EMPTY_SHORT_ARRAY, Arrays.subarray(array, 33, 4));

        assertTrue("normal start, end overshoot", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, 33)));

        assertTrue("start undershoot, end overshoot", Objects.deepEquals(array, Arrays.subarray(array, -2, 12)));

        // empty-return tests

        assertSame("empty array, object test", Arrays.EMPTY_SHORT_ARRAY, Arrays.subarray(Arrays.EMPTY_SHORT_ARRAY, 1, 2));

        assertSame("start > end, object test", Arrays.EMPTY_SHORT_ARRAY, Arrays.subarray(array, 4, 1));

        assertSame("start == end, object test", Arrays.EMPTY_SHORT_ARRAY, Arrays.subarray(array, 3, 3));

        assertSame("start overshoot, any end, object test", Arrays.EMPTY_SHORT_ARRAY, Arrays.subarray(array, 8733, 4));

        // array type tests

        assertSame("short type", short.class, Arrays.subarray(array, 2, 4).getClass().getComponentType());

    }

    @Test
    public void testSubarrChar() {
        final char[] nullArray = null;
        final char[] array = { 'a', 'b', 'c', 'd', 'e', 'f' };
        final char[] leftSubarray = { 'a', 'b', 'c', 'd', };
        final char[] midSubarray = { 'b', 'c', 'd', 'e', };
        final char[] rightSubarray = { 'c', 'd', 'e', 'f', };

        assertTrue("0 start, mid end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, 0, 4)));

        assertTrue("0 start, length end", Objects.deepEquals(array, Arrays.subarray(array, 0, array.length)));

        assertTrue("mid start, mid end", Objects.deepEquals(midSubarray, Arrays.subarray(array, 1, 5)));

        assertTrue("mid start, length end", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, array.length)));

        assertNull("null input", Arrays.subarray(nullArray, 0, 3));

        assertEquals("empty array", Arrays.EMPTY_CHAR_ARRAY, Arrays.subarray(Arrays.EMPTY_CHAR_ARRAY, 1, 2));

        assertEquals("start > end", Arrays.EMPTY_CHAR_ARRAY, Arrays.subarray(array, 4, 2));

        assertEquals("start == end", Arrays.EMPTY_CHAR_ARRAY, Arrays.subarray(array, 3, 3));

        assertTrue("start undershoot, normal end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, -2, 4)));

        assertEquals("start overshoot, any end", Arrays.EMPTY_CHAR_ARRAY, Arrays.subarray(array, 33, 4));

        assertTrue("normal start, end overshoot", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, 33)));

        assertTrue("start undershoot, end overshoot", Objects.deepEquals(array, Arrays.subarray(array, -2, 12)));

        // empty-return tests

        assertSame("empty array, object test", Arrays.EMPTY_CHAR_ARRAY, Arrays.subarray(Arrays.EMPTY_CHAR_ARRAY, 1, 2));

        assertSame("start > end, object test", Arrays.EMPTY_CHAR_ARRAY, Arrays.subarray(array, 4, 1));

        assertSame("start == end, object test", Arrays.EMPTY_CHAR_ARRAY, Arrays.subarray(array, 3, 3));

        assertSame("start overshoot, any end, object test", Arrays.EMPTY_CHAR_ARRAY, Arrays.subarray(array, 8733, 4));

        // array type tests

        assertSame("char type", char.class, Arrays.subarray(array, 2, 4).getClass().getComponentType());

    }

    @Test
    public void testSubarrayByte() {
        final byte[] nullArray = null;
        final byte[] array = { 10, 11, 12, 13, 14, 15 };
        final byte[] leftSubarray = { 10, 11, 12, 13 };
        final byte[] midSubarray = { 11, 12, 13, 14 };
        final byte[] rightSubarray = { 12, 13, 14, 15 };

        assertTrue("0 start, mid end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, 0, 4)));

        assertTrue("0 start, length end", Objects.deepEquals(array, Arrays.subarray(array, 0, array.length)));

        assertTrue("mid start, mid end", Objects.deepEquals(midSubarray, Arrays.subarray(array, 1, 5)));

        assertTrue("mid start, length end", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, array.length)));

        assertNull("null input", Arrays.subarray(nullArray, 0, 3));

        assertEquals("empty array", Arrays.EMPTY_BYTE_ARRAY, Arrays.subarray(Arrays.EMPTY_BYTE_ARRAY, 1, 2));

        assertEquals("start > end", Arrays.EMPTY_BYTE_ARRAY, Arrays.subarray(array, 4, 2));

        assertEquals("start == end", Arrays.EMPTY_BYTE_ARRAY, Arrays.subarray(array, 3, 3));

        assertTrue("start undershoot, normal end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, -2, 4)));

        assertEquals("start overshoot, any end", Arrays.EMPTY_BYTE_ARRAY, Arrays.subarray(array, 33, 4));

        assertTrue("normal start, end overshoot", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, 33)));

        assertTrue("start undershoot, end overshoot", Objects.deepEquals(array, Arrays.subarray(array, -2, 12)));

        // empty-return tests

        assertSame("empty array, object test", Arrays.EMPTY_BYTE_ARRAY, Arrays.subarray(Arrays.EMPTY_BYTE_ARRAY, 1, 2));

        assertSame("start > end, object test", Arrays.EMPTY_BYTE_ARRAY, Arrays.subarray(array, 4, 1));

        assertSame("start == end, object test", Arrays.EMPTY_BYTE_ARRAY, Arrays.subarray(array, 3, 3));

        assertSame("start overshoot, any end, object test", Arrays.EMPTY_BYTE_ARRAY, Arrays.subarray(array, 8733, 4));

        // array type tests

        assertSame("byte type", byte.class, Arrays.subarray(array, 2, 4).getClass().getComponentType());

    }

    @Test
    public void testSubarrayDouble() {
        final double[] nullArray = null;
        final double[] array = { 10.123, 11.234, 12.345, 13.456, 14.567, 15.678 };
        final double[] leftSubarray = { 10.123, 11.234, 12.345, 13.456, };
        final double[] midSubarray = { 11.234, 12.345, 13.456, 14.567, };
        final double[] rightSubarray = { 12.345, 13.456, 14.567, 15.678 };

        assertTrue("0 start, mid end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, 0, 4)));

        assertTrue("0 start, length end", Objects.deepEquals(array, Arrays.subarray(array, 0, array.length)));

        assertTrue("mid start, mid end", Objects.deepEquals(midSubarray, Arrays.subarray(array, 1, 5)));

        assertTrue("mid start, length end", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, array.length)));

        assertNull("null input", Arrays.subarray(nullArray, 0, 3));

        assertEquals("empty array", Arrays.EMPTY_DOUBLE_ARRAY, Arrays.subarray(Arrays.EMPTY_DOUBLE_ARRAY, 1, 2));

        assertEquals("start > end", Arrays.EMPTY_DOUBLE_ARRAY, Arrays.subarray(array, 4, 2));

        assertEquals("start == end", Arrays.EMPTY_DOUBLE_ARRAY, Arrays.subarray(array, 3, 3));

        assertTrue("start undershoot, normal end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, -2, 4)));

        assertEquals("start overshoot, any end", Arrays.EMPTY_DOUBLE_ARRAY, Arrays.subarray(array, 33, 4));

        assertTrue("normal start, end overshoot", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, 33)));

        assertTrue("start undershoot, end overshoot", Objects.deepEquals(array, Arrays.subarray(array, -2, 12)));

        // empty-return tests

        assertSame("empty array, object test", Arrays.EMPTY_DOUBLE_ARRAY, Arrays.subarray(Arrays.EMPTY_DOUBLE_ARRAY, 1, 2));

        assertSame("start > end, object test", Arrays.EMPTY_DOUBLE_ARRAY, Arrays.subarray(array, 4, 1));

        assertSame("start == end, object test", Arrays.EMPTY_DOUBLE_ARRAY, Arrays.subarray(array, 3, 3));

        assertSame("start overshoot, any end, object test", Arrays.EMPTY_DOUBLE_ARRAY, Arrays.subarray(array, 8733, 4));

        // array type tests

        assertSame("double type", double.class, Arrays.subarray(array, 2, 4).getClass().getComponentType());

    }

    @Test
    public void testSubarrayFloat() {
        final float[] nullArray = null;
        final float[] array = { 10, 11, 12, 13, 14, 15 };
        final float[] leftSubarray = { 10, 11, 12, 13 };
        final float[] midSubarray = { 11, 12, 13, 14 };
        final float[] rightSubarray = { 12, 13, 14, 15 };

        assertTrue("0 start, mid end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, 0, 4)));

        assertTrue("0 start, length end", Objects.deepEquals(array, Arrays.subarray(array, 0, array.length)));

        assertTrue("mid start, mid end", Objects.deepEquals(midSubarray, Arrays.subarray(array, 1, 5)));

        assertTrue("mid start, length end", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, array.length)));

        assertNull("null input", Arrays.subarray(nullArray, 0, 3));

        assertEquals("empty array", Arrays.EMPTY_FLOAT_ARRAY, Arrays.subarray(Arrays.EMPTY_FLOAT_ARRAY, 1, 2));

        assertEquals("start > end", Arrays.EMPTY_FLOAT_ARRAY, Arrays.subarray(array, 4, 2));

        assertEquals("start == end", Arrays.EMPTY_FLOAT_ARRAY, Arrays.subarray(array, 3, 3));

        assertTrue("start undershoot, normal end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, -2, 4)));

        assertEquals("start overshoot, any end", Arrays.EMPTY_FLOAT_ARRAY, Arrays.subarray(array, 33, 4));

        assertTrue("normal start, end overshoot", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, 33)));

        assertTrue("start undershoot, end overshoot", Objects.deepEquals(array, Arrays.subarray(array, -2, 12)));

        // empty-return tests

        assertSame("empty array, object test", Arrays.EMPTY_FLOAT_ARRAY, Arrays.subarray(Arrays.EMPTY_FLOAT_ARRAY, 1, 2));

        assertSame("start > end, object test", Arrays.EMPTY_FLOAT_ARRAY, Arrays.subarray(array, 4, 1));

        assertSame("start == end, object test", Arrays.EMPTY_FLOAT_ARRAY, Arrays.subarray(array, 3, 3));

        assertSame("start overshoot, any end, object test", Arrays.EMPTY_FLOAT_ARRAY, Arrays.subarray(array, 8733, 4));

        // array type tests

        assertSame("float type", float.class, Arrays.subarray(array, 2, 4).getClass().getComponentType());

    }

    @Test
    public void testSubarrayBoolean() {
        final boolean[] nullArray = null;
        final boolean[] array = { true, true, false, true, false, true };
        final boolean[] leftSubarray = { true, true, false, true };
        final boolean[] midSubarray = { true, false, true, false };
        final boolean[] rightSubarray = { false, true, false, true };

        assertTrue("0 start, mid end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, 0, 4)));

        assertTrue("0 start, length end", Objects.deepEquals(array, Arrays.subarray(array, 0, array.length)));

        assertTrue("mid start, mid end", Objects.deepEquals(midSubarray, Arrays.subarray(array, 1, 5)));

        assertTrue("mid start, length end", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, array.length)));

        assertNull("null input", Arrays.subarray(nullArray, 0, 3));

        assertEquals("empty array", Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.subarray(Arrays.EMPTY_BOOLEAN_ARRAY, 1, 2));

        assertEquals("start > end", Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.subarray(array, 4, 2));

        assertEquals("start == end", Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.subarray(array, 3, 3));

        assertTrue("start undershoot, normal end", Objects.deepEquals(leftSubarray, Arrays.subarray(array, -2, 4)));

        assertEquals("start overshoot, any end", Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.subarray(array, 33, 4));

        assertTrue("normal start, end overshoot", Objects.deepEquals(rightSubarray, Arrays.subarray(array, 2, 33)));

        assertTrue("start undershoot, end overshoot", Objects.deepEquals(array, Arrays.subarray(array, -2, 12)));

        // empty-return tests

        assertSame("empty array, object test", Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.subarray(Arrays.EMPTY_BOOLEAN_ARRAY, 1, 2));

        assertSame("start > end, object test", Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.subarray(array, 4, 1));

        assertSame("start == end, object test", Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.subarray(array, 3, 3));

        assertSame("start overshoot, any end, object test", Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.subarray(array, 8733, 4));

        // array type tests

        assertSame("boolean type", boolean.class, Arrays.subarray(array, 2, 4).getClass().getComponentType());

    }

    // -----------------------------------------------------------------------
    @Test
    public void testSameLength() {
        final Object[] nullArray = null;
        final Object[] emptyArray = new Object[0];
        final Object[] oneArray = new Object[] { "pick" };
        final Object[] twoArray = new Object[] { "pick", "stick" };

        assertTrue(Arrays.isSameLength(nullArray, nullArray));
        assertTrue(Arrays.isSameLength(nullArray, emptyArray));
        assertFalse(Arrays.isSameLength(nullArray, oneArray));
        assertFalse(Arrays.isSameLength(nullArray, twoArray));

        assertTrue(Arrays.isSameLength(emptyArray, nullArray));
        assertTrue(Arrays.isSameLength(emptyArray, emptyArray));
        assertFalse(Arrays.isSameLength(emptyArray, oneArray));
        assertFalse(Arrays.isSameLength(emptyArray, twoArray));

        assertFalse(Arrays.isSameLength(oneArray, nullArray));
        assertFalse(Arrays.isSameLength(oneArray, emptyArray));
        assertTrue(Arrays.isSameLength(oneArray, oneArray));
        assertFalse(Arrays.isSameLength(oneArray, twoArray));

        assertFalse(Arrays.isSameLength(twoArray, nullArray));
        assertFalse(Arrays.isSameLength(twoArray, emptyArray));
        assertFalse(Arrays.isSameLength(twoArray, oneArray));
        assertTrue(Arrays.isSameLength(twoArray, twoArray));
    }

    @Test
    public void testSameLengthBoolean() {
        final boolean[] nullArray = null;
        final boolean[] emptyArray = new boolean[0];
        final boolean[] oneArray = new boolean[] { true };
        final boolean[] twoArray = new boolean[] { true, false };

        assertTrue(Arrays.isSameLength(nullArray, nullArray));
        assertTrue(Arrays.isSameLength(nullArray, emptyArray));
        assertFalse(Arrays.isSameLength(nullArray, oneArray));
        assertFalse(Arrays.isSameLength(nullArray, twoArray));

        assertTrue(Arrays.isSameLength(emptyArray, nullArray));
        assertTrue(Arrays.isSameLength(emptyArray, emptyArray));
        assertFalse(Arrays.isSameLength(emptyArray, oneArray));
        assertFalse(Arrays.isSameLength(emptyArray, twoArray));

        assertFalse(Arrays.isSameLength(oneArray, nullArray));
        assertFalse(Arrays.isSameLength(oneArray, emptyArray));
        assertTrue(Arrays.isSameLength(oneArray, oneArray));
        assertFalse(Arrays.isSameLength(oneArray, twoArray));

        assertFalse(Arrays.isSameLength(twoArray, nullArray));
        assertFalse(Arrays.isSameLength(twoArray, emptyArray));
        assertFalse(Arrays.isSameLength(twoArray, oneArray));
        assertTrue(Arrays.isSameLength(twoArray, twoArray));
    }

    @Test
    public void testSameLengthLong() {
        final long[] nullArray = null;
        final long[] emptyArray = new long[0];
        final long[] oneArray = new long[] { 0L };
        final long[] twoArray = new long[] { 0L, 76L };

        assertTrue(Arrays.isSameLength(nullArray, nullArray));
        assertTrue(Arrays.isSameLength(nullArray, emptyArray));
        assertFalse(Arrays.isSameLength(nullArray, oneArray));
        assertFalse(Arrays.isSameLength(nullArray, twoArray));

        assertTrue(Arrays.isSameLength(emptyArray, nullArray));
        assertTrue(Arrays.isSameLength(emptyArray, emptyArray));
        assertFalse(Arrays.isSameLength(emptyArray, oneArray));
        assertFalse(Arrays.isSameLength(emptyArray, twoArray));

        assertFalse(Arrays.isSameLength(oneArray, nullArray));
        assertFalse(Arrays.isSameLength(oneArray, emptyArray));
        assertTrue(Arrays.isSameLength(oneArray, oneArray));
        assertFalse(Arrays.isSameLength(oneArray, twoArray));

        assertFalse(Arrays.isSameLength(twoArray, nullArray));
        assertFalse(Arrays.isSameLength(twoArray, emptyArray));
        assertFalse(Arrays.isSameLength(twoArray, oneArray));
        assertTrue(Arrays.isSameLength(twoArray, twoArray));
    }

    @Test
    public void testSameLengthInt() {
        final int[] nullArray = null;
        final int[] emptyArray = new int[0];
        final int[] oneArray = new int[] { 4 };
        final int[] twoArray = new int[] { 5, 7 };

        assertTrue(Arrays.isSameLength(nullArray, nullArray));
        assertTrue(Arrays.isSameLength(nullArray, emptyArray));
        assertFalse(Arrays.isSameLength(nullArray, oneArray));
        assertFalse(Arrays.isSameLength(nullArray, twoArray));

        assertTrue(Arrays.isSameLength(emptyArray, nullArray));
        assertTrue(Arrays.isSameLength(emptyArray, emptyArray));
        assertFalse(Arrays.isSameLength(emptyArray, oneArray));
        assertFalse(Arrays.isSameLength(emptyArray, twoArray));

        assertFalse(Arrays.isSameLength(oneArray, nullArray));
        assertFalse(Arrays.isSameLength(oneArray, emptyArray));
        assertTrue(Arrays.isSameLength(oneArray, oneArray));
        assertFalse(Arrays.isSameLength(oneArray, twoArray));

        assertFalse(Arrays.isSameLength(twoArray, nullArray));
        assertFalse(Arrays.isSameLength(twoArray, emptyArray));
        assertFalse(Arrays.isSameLength(twoArray, oneArray));
        assertTrue(Arrays.isSameLength(twoArray, twoArray));
    }

    @Test
    public void testSameLengthShort() {
        final short[] nullArray = null;
        final short[] emptyArray = new short[0];
        final short[] oneArray = new short[] { 4 };
        final short[] twoArray = new short[] { 6, 8 };

        assertTrue(Arrays.isSameLength(nullArray, nullArray));
        assertTrue(Arrays.isSameLength(nullArray, emptyArray));
        assertFalse(Arrays.isSameLength(nullArray, oneArray));
        assertFalse(Arrays.isSameLength(nullArray, twoArray));

        assertTrue(Arrays.isSameLength(emptyArray, nullArray));
        assertTrue(Arrays.isSameLength(emptyArray, emptyArray));
        assertFalse(Arrays.isSameLength(emptyArray, oneArray));
        assertFalse(Arrays.isSameLength(emptyArray, twoArray));

        assertFalse(Arrays.isSameLength(oneArray, nullArray));
        assertFalse(Arrays.isSameLength(oneArray, emptyArray));
        assertTrue(Arrays.isSameLength(oneArray, oneArray));
        assertFalse(Arrays.isSameLength(oneArray, twoArray));

        assertFalse(Arrays.isSameLength(twoArray, nullArray));
        assertFalse(Arrays.isSameLength(twoArray, emptyArray));
        assertFalse(Arrays.isSameLength(twoArray, oneArray));
        assertTrue(Arrays.isSameLength(twoArray, twoArray));
    }

    @Test
    public void testSameLengthChar() {
        final char[] nullArray = null;
        final char[] emptyArray = new char[0];
        final char[] oneArray = new char[] { 'f' };
        final char[] twoArray = new char[] { 'd', 't' };

        assertTrue(Arrays.isSameLength(nullArray, nullArray));
        assertTrue(Arrays.isSameLength(nullArray, emptyArray));
        assertFalse(Arrays.isSameLength(nullArray, oneArray));
        assertFalse(Arrays.isSameLength(nullArray, twoArray));

        assertTrue(Arrays.isSameLength(emptyArray, nullArray));
        assertTrue(Arrays.isSameLength(emptyArray, emptyArray));
        assertFalse(Arrays.isSameLength(emptyArray, oneArray));
        assertFalse(Arrays.isSameLength(emptyArray, twoArray));

        assertFalse(Arrays.isSameLength(oneArray, nullArray));
        assertFalse(Arrays.isSameLength(oneArray, emptyArray));
        assertTrue(Arrays.isSameLength(oneArray, oneArray));
        assertFalse(Arrays.isSameLength(oneArray, twoArray));

        assertFalse(Arrays.isSameLength(twoArray, nullArray));
        assertFalse(Arrays.isSameLength(twoArray, emptyArray));
        assertFalse(Arrays.isSameLength(twoArray, oneArray));
        assertTrue(Arrays.isSameLength(twoArray, twoArray));
    }

    @Test
    public void testSameLengthByte() {
        final byte[] nullArray = null;
        final byte[] emptyArray = new byte[0];
        final byte[] oneArray = new byte[] { 3 };
        final byte[] twoArray = new byte[] { 4, 6 };

        assertTrue(Arrays.isSameLength(nullArray, nullArray));
        assertTrue(Arrays.isSameLength(nullArray, emptyArray));
        assertFalse(Arrays.isSameLength(nullArray, oneArray));
        assertFalse(Arrays.isSameLength(nullArray, twoArray));

        assertTrue(Arrays.isSameLength(emptyArray, nullArray));
        assertTrue(Arrays.isSameLength(emptyArray, emptyArray));
        assertFalse(Arrays.isSameLength(emptyArray, oneArray));
        assertFalse(Arrays.isSameLength(emptyArray, twoArray));

        assertFalse(Arrays.isSameLength(oneArray, nullArray));
        assertFalse(Arrays.isSameLength(oneArray, emptyArray));
        assertTrue(Arrays.isSameLength(oneArray, oneArray));
        assertFalse(Arrays.isSameLength(oneArray, twoArray));

        assertFalse(Arrays.isSameLength(twoArray, nullArray));
        assertFalse(Arrays.isSameLength(twoArray, emptyArray));
        assertFalse(Arrays.isSameLength(twoArray, oneArray));
        assertTrue(Arrays.isSameLength(twoArray, twoArray));
    }

    @Test
    public void testSameLengthDouble() {
        final double[] nullArray = null;
        final double[] emptyArray = new double[0];
        final double[] oneArray = new double[] { 1.3d };
        final double[] twoArray = new double[] { 4.5d, 6.3d };

        assertTrue(Arrays.isSameLength(nullArray, nullArray));
        assertTrue(Arrays.isSameLength(nullArray, emptyArray));
        assertFalse(Arrays.isSameLength(nullArray, oneArray));
        assertFalse(Arrays.isSameLength(nullArray, twoArray));

        assertTrue(Arrays.isSameLength(emptyArray, nullArray));
        assertTrue(Arrays.isSameLength(emptyArray, emptyArray));
        assertFalse(Arrays.isSameLength(emptyArray, oneArray));
        assertFalse(Arrays.isSameLength(emptyArray, twoArray));

        assertFalse(Arrays.isSameLength(oneArray, nullArray));
        assertFalse(Arrays.isSameLength(oneArray, emptyArray));
        assertTrue(Arrays.isSameLength(oneArray, oneArray));
        assertFalse(Arrays.isSameLength(oneArray, twoArray));

        assertFalse(Arrays.isSameLength(twoArray, nullArray));
        assertFalse(Arrays.isSameLength(twoArray, emptyArray));
        assertFalse(Arrays.isSameLength(twoArray, oneArray));
        assertTrue(Arrays.isSameLength(twoArray, twoArray));
    }

    @Test
    public void testSameLengthFloat() {
        final float[] nullArray = null;
        final float[] emptyArray = new float[0];
        final float[] oneArray = new float[] { 2.5f };
        final float[] twoArray = new float[] { 6.4f, 5.8f };

        assertTrue(Arrays.isSameLength(nullArray, nullArray));
        assertTrue(Arrays.isSameLength(nullArray, emptyArray));
        assertFalse(Arrays.isSameLength(nullArray, oneArray));
        assertFalse(Arrays.isSameLength(nullArray, twoArray));

        assertTrue(Arrays.isSameLength(emptyArray, nullArray));
        assertTrue(Arrays.isSameLength(emptyArray, emptyArray));
        assertFalse(Arrays.isSameLength(emptyArray, oneArray));
        assertFalse(Arrays.isSameLength(emptyArray, twoArray));

        assertFalse(Arrays.isSameLength(oneArray, nullArray));
        assertFalse(Arrays.isSameLength(oneArray, emptyArray));
        assertTrue(Arrays.isSameLength(oneArray, oneArray));
        assertFalse(Arrays.isSameLength(oneArray, twoArray));

        assertFalse(Arrays.isSameLength(twoArray, nullArray));
        assertFalse(Arrays.isSameLength(twoArray, emptyArray));
        assertFalse(Arrays.isSameLength(twoArray, oneArray));
        assertTrue(Arrays.isSameLength(twoArray, twoArray));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testSameType() {
        try {
            Arrays.isSameType(null, null);
            fail();
        } catch (final IllegalArgumentException ex) {
        }
        try {
            Arrays.isSameType(null, new Object[0]);
            fail();
        } catch (final IllegalArgumentException ex) {
        }
        try {
            Arrays.isSameType(new Object[0], null);
            fail();
        } catch (final IllegalArgumentException ex) {
        }

        assertTrue(Arrays.isSameType(new Object[0], new Object[0]));
        assertFalse(Arrays.isSameType(new String[0], new Object[0]));
        assertTrue(Arrays.isSameType(new String[0][0], new String[0][0]));
        assertFalse(Arrays.isSameType(new String[0], new String[0][0]));
        assertFalse(Arrays.isSameType(new String[0][0], new String[0]));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testReverse() {
        final StringBuffer str1 = new StringBuffer("pick");
        final String str2 = "a";
        final String[] str3 = new String[] { "stick" };
        final String str4 = "up";

        Object[] array = new Object[] { str1, str2, str3 };
        Arrays.reverse(array);
        assertEquals(array[0], str3);
        assertEquals(array[1], str2);
        assertEquals(array[2], str1);

        array = new Object[] { str1, str2, str3, str4 };
        Arrays.reverse(array);
        assertEquals(array[0], str4);
        assertEquals(array[1], str3);
        assertEquals(array[2], str2);
        assertEquals(array[3], str1);

        array = null;
        Arrays.reverse(array);
        assertArrayEquals(null, array);
    }

    @Test
    public void testReverseLong() {
        long[] array = new long[] { 1L, 2L, 3L };
        Arrays.reverse(array);
        assertEquals(array[0], 3L);
        assertEquals(array[1], 2L);
        assertEquals(array[2], 1L);

        array = null;
        Arrays.reverse(array);
        assertEquals(null, array);
    }

    @Test
    public void testReverseInt() {
        int[] array = new int[] { 1, 2, 3 };
        Arrays.reverse(array);
        assertEquals(array[0], 3);
        assertEquals(array[1], 2);
        assertEquals(array[2], 1);

        array = null;
        Arrays.reverse(array);
        assertEquals(null, array);
    }

    @Test
    public void testReverseShort() {
        short[] array = new short[] { 1, 2, 3 };
        Arrays.reverse(array);
        assertEquals(array[0], 3);
        assertEquals(array[1], 2);
        assertEquals(array[2], 1);

        array = null;
        Arrays.reverse(array);
        assertEquals(null, array);
    }

    @Test
    public void testReverseChar() {
        char[] array = new char[] { 'a', 'f', 'C' };
        Arrays.reverse(array);
        assertEquals(array[0], 'C');
        assertEquals(array[1], 'f');
        assertEquals(array[2], 'a');

        array = null;
        Arrays.reverse(array);
        assertEquals(null, array);
    }

    @Test
    public void testReverseByte() {
        byte[] array = new byte[] { 2, 3, 4 };
        Arrays.reverse(array);
        assertEquals(array[0], 4);
        assertEquals(array[1], 3);
        assertEquals(array[2], 2);

        array = null;
        Arrays.reverse(array);
        assertEquals(null, array);
    }

    @Test
    public void testReverseDouble() {
        double[] array = new double[] { 0.3d, 0.4d, 0.5d };
        Arrays.reverse(array);
        assertEquals(array[0], 0.5d, 0.0d);
        assertEquals(array[1], 0.4d, 0.0d);
        assertEquals(array[2], 0.3d, 0.0d);

        array = null;
        Arrays.reverse(array);
        assertEquals(null, array);
    }

    @Test
    public void testReverseFloat() {
        float[] array = new float[] { 0.3f, 0.4f, 0.5f };
        Arrays.reverse(array);
        assertEquals(array[0], 0.5f, 0.0f);
        assertEquals(array[1], 0.4f, 0.0f);
        assertEquals(array[2], 0.3f, 0.0f);

        array = null;
        Arrays.reverse(array);
        assertEquals(null, array);
    }

    @Test
    public void testReverseBoolean() {
        boolean[] array = new boolean[] { false, false, true };
        Arrays.reverse(array);
        assertTrue(array[0]);
        assertFalse(array[1]);
        assertFalse(array[2]);

        array = null;
        Arrays.reverse(array);
        assertEquals(null, array);
    }

    @Test
    public void testReverseBooleanRange() {
        boolean[] array = new boolean[] { false, false, true };
        // The whole array
        Arrays.reverse(array, 0, 3);
        assertTrue(array[0]);
        assertFalse(array[1]);
        assertFalse(array[2]);
        // a range
        array = new boolean[] { false, false, true };
        Arrays.reverse(array, 0, 2);
        assertFalse(array[0]);
        assertFalse(array[1]);
        assertTrue(array[2]);
        // a range with a negative start
        array = new boolean[] { false, false, true };
        Arrays.reverse(array, -1, 3);
        assertTrue(array[0]);
        assertFalse(array[1]);
        assertFalse(array[2]);
        // a range with a large stop index
        array = new boolean[] { false, false, true };
        Arrays.reverse(array, -1, array.length + 1000);
        assertTrue(array[0]);
        assertFalse(array[1]);
        assertFalse(array[2]);
        // null
        array = null;
        Arrays.reverse(array, 0, 3);
        assertEquals(null, array);
    }

    @Test
    public void testReverseByteRange() {
        byte[] array = new byte[] { 1, 2, 3 };
        // The whole array
        Arrays.reverse(array, 0, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range
        array = new byte[] { 1, 2, 3 };
        Arrays.reverse(array, 0, 2);
        assertEquals(2, array[0]);
        assertEquals(1, array[1]);
        assertEquals(3, array[2]);
        // a range with a negative start
        array = new byte[] { 1, 2, 3 };
        Arrays.reverse(array, -1, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range with a large stop index
        array = new byte[] { 1, 2, 3 };
        Arrays.reverse(array, -1, array.length + 1000);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // null
        array = null;
        Arrays.reverse(array, 0, 3);
        assertEquals(null, array);
    }

    @Test
    public void testReverseCharRange() {
        char[] array = new char[] { 1, 2, 3 };
        // The whole array
        Arrays.reverse(array, 0, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range
        array = new char[] { 1, 2, 3 };
        Arrays.reverse(array, 0, 2);
        assertEquals(2, array[0]);
        assertEquals(1, array[1]);
        assertEquals(3, array[2]);
        // a range with a negative start
        array = new char[] { 1, 2, 3 };
        Arrays.reverse(array, -1, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range with a large stop index
        array = new char[] { 1, 2, 3 };
        Arrays.reverse(array, -1, array.length + 1000);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // null
        array = null;
        Arrays.reverse(array, 0, 3);
        assertEquals(null, array);
    }

    @Test
    public void testReverseDoubleRange() {
        double[] array = new double[] { 1, 2, 3 };
        // The whole array
        Arrays.reverse(array, 0, 3);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);
        // a range
        array = new double[] { 1, 2, 3 };
        Arrays.reverse(array, 0, 2);
        assertEquals(2, array[0], 0);
        assertEquals(1, array[1], 0);
        assertEquals(3, array[2], 0);
        // a range with a negative start
        array = new double[] { 1, 2, 3 };
        Arrays.reverse(array, -1, 3);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);
        // a range with a large stop index
        array = new double[] { 1, 2, 3 };
        Arrays.reverse(array, -1, array.length + 1000);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);
        // null
        array = null;
        Arrays.reverse(array, 0, 3);
        assertEquals(null, array);
    }

    @Test
    public void testReverseFloatRange() {
        float[] array = new float[] { 1, 2, 3 };
        // The whole array
        Arrays.reverse(array, 0, 3);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);
        // a range
        array = new float[] { 1, 2, 3 };
        Arrays.reverse(array, 0, 2);
        assertEquals(2, array[0], 0);
        assertEquals(1, array[1], 0);
        assertEquals(3, array[2], 0);
        // a range with a negative start
        array = new float[] { 1, 2, 3 };
        Arrays.reverse(array, -1, 3);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);
        // a range with a large stop index
        array = new float[] { 1, 2, 3 };
        Arrays.reverse(array, -1, array.length + 1000);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);
        // null
        array = null;
        Arrays.reverse(array, 0, 3);
        assertEquals(null, array);
    }

    @Test
    public void testReverseIntRange() {
        int[] array = new int[] { 1, 2, 3 };
        // The whole array
        Arrays.reverse(array, 0, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range
        array = new int[] { 1, 2, 3 };
        Arrays.reverse(array, 0, 2);
        assertEquals(2, array[0]);
        assertEquals(1, array[1]);
        assertEquals(3, array[2]);
        // a range with a negative start
        array = new int[] { 1, 2, 3 };
        Arrays.reverse(array, -1, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range with a large stop index
        array = new int[] { 1, 2, 3 };
        Arrays.reverse(array, -1, array.length + 1000);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // null
        array = null;
        Arrays.reverse(array, 0, 3);
        assertEquals(null, array);
    }

    @Test
    public void testReverseLongRange() {
        long[] array = new long[] { 1, 2, 3 };
        // The whole array
        Arrays.reverse(array, 0, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range
        array = new long[] { 1, 2, 3 };
        Arrays.reverse(array, 0, 2);
        assertEquals(2, array[0]);
        assertEquals(1, array[1]);
        assertEquals(3, array[2]);
        // a range with a negative start
        array = new long[] { 1, 2, 3 };
        Arrays.reverse(array, -1, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range with a large stop index
        array = new long[] { 1, 2, 3 };
        Arrays.reverse(array, -1, array.length + 1000);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // null
        array = null;
        Arrays.reverse(array, 0, 3);
        assertEquals(null, array);
    }

    @Test
    public void testReverseShortRange() {
        short[] array = new short[] { 1, 2, 3 };
        // The whole array
        Arrays.reverse(array, 0, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range
        array = new short[] { 1, 2, 3 };
        Arrays.reverse(array, 0, 2);
        assertEquals(2, array[0]);
        assertEquals(1, array[1]);
        assertEquals(3, array[2]);
        // a range with a negative start
        array = new short[] { 1, 2, 3 };
        Arrays.reverse(array, -1, 3);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // a range with a large stop index
        array = new short[] { 1, 2, 3 };
        Arrays.reverse(array, -1, array.length + 1000);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
        // null
        array = null;
        Arrays.reverse(array, 0, 3);
        assertEquals(null, array);
    }

    @Test
    public void testReverseObjectRange() {
        String[] array = new String[] { "1", "2", "3" };
        // The whole array
        Arrays.reverse(array, 0, 3);
        assertEquals("3", array[0]);
        assertEquals("2", array[1]);
        assertEquals("1", array[2]);
        // a range
        array = new String[] { "1", "2", "3" };
        Arrays.reverse(array, 0, 2);
        assertEquals("2", array[0]);
        assertEquals("1", array[1]);
        assertEquals("3", array[2]);
        // a range with a negative start
        array = new String[] { "1", "2", "3" };
        Arrays.reverse(array, -1, 3);
        assertEquals("3", array[0]);
        assertEquals("2", array[1]);
        assertEquals("1", array[2]);
        // a range with a large stop index
        array = new String[] { "1", "2", "3" };
        Arrays.reverse(array, -1, array.length + 1000);
        assertEquals("3", array[0]);
        assertEquals("2", array[1]);
        assertEquals("1", array[2]);
        // null
        array = null;
        Arrays.reverse(array, 0, 3);
        assertEquals(null, array);
    }

    // -----------------------------------------------------------------------
    @Test
    public void testSwapChar() {
        char[] array = new char[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2);
        assertArrayEquals(new char[] { 3, 2, 1 }, array);

        array = new char[] { 1, 2, 3 };
        Arrays.swap(array, 0, 0);
        assertArrayEquals(new char[] { 1, 2, 3 }, array);

        array = new char[] { 1, 2, 3 };
        Arrays.swap(array, 1, 0);
        assertArrayEquals(new char[] { 2, 1, 3 }, array);
    }

    @Test
    public void testSwapCharRange() {
        char[] array = new char[] { 1, 2, 3, 4 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);

        array = new char[] { 1, 2, 3 };
        Arrays.swap(array, 0, 3);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new char[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new char[] { 1, 2, 3 };
        Arrays.swap(array, -1, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new char[] { 1, 2, 3 };
        Arrays.swap(array, 0, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new char[] { 1, 2, 3 };
        Arrays.swap(array, -1, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    public void testSwapByte() {
        final byte[] array = new byte[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
    }

    @Test
    public void testSwapNullByteArray() {
        final byte[] array = null;
        Arrays.swap(array, 0, 2);
        assertNull(array);
    }

    @Test
    public void testSwapEmptyByteArray() {
        final byte[] array = new byte[0];
        Arrays.swap(array, 0, 2);
        assertEquals(0, array.length);
    }

    @Test
    public void testSwapByteRange() {
        byte[] array = new byte[] { 1, 2, 3, 4 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);

        array = new byte[] { 1, 2, 3 };
        Arrays.swap(array, 0, 3);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new byte[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new byte[] { 1, 2, 3 };
        Arrays.swap(array, -1, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new byte[] { 1, 2, 3 };
        Arrays.swap(array, 0, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new byte[] { 1, 2, 3 };
        Arrays.swap(array, -1, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    public void testSwapFloat() {
        final float[] array = new float[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);
    }

    @Test
    public void testSwapNullFloatArray() {
        final float[] array = null;
        Arrays.swap(array, 0, 2);
        assertNull(array);
    }

    @Test
    public void testSwapEmptyFloatArray() {
        final float[] array = new float[0];
        Arrays.swap(array, 0, 2);
        assertEquals(0, array.length);
    }

    @Test
    public void testSwapFloatRange() {
        float[] array = new float[] { 1, 2, 3, 4 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0], 0);
        assertEquals(4, array[1], 0);
        assertEquals(1, array[2], 0);
        assertEquals(2, array[3], 0);

        array = new float[] { 1, 2, 3 };
        Arrays.swap(array, 0, 3);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);

        array = new float[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);

        array = new float[] { 1, 2, 3 };
        Arrays.swap(array, -1, 2, 2);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);

        array = new float[] { 1, 2, 3 };
        Arrays.swap(array, 0, -1, 2);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);

        array = new float[] { 1, 2, 3 };
        Arrays.swap(array, -1, -1, 2);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
    }

    @Test
    public void testSwapDouble() {
        final double[] array = new double[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);
    }

    @Test
    public void testSwapNullDoubleArray() {
        final double[] array = null;
        Arrays.swap(array, 0, 2);
        assertNull(array);
    }

    @Test
    public void testSwapEmptyDoubleArray() {
        final double[] array = new double[0];
        Arrays.swap(array, 0, 2);
        assertEquals(0, array.length);
    }

    @Test
    public void testSwapDoubleRange() {
        double[] array = new double[] { 1, 2, 3, 4 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0], 0);
        assertEquals(4, array[1], 0);
        assertEquals(1, array[2], 0);
        assertEquals(2, array[3], 0);

        array = new double[] { 1, 2, 3 };
        Arrays.swap(array, 0, 3);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);

        array = new double[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);

        array = new double[] { 1, 2, 3 };
        Arrays.swap(array, -1, 2, 2);
        assertEquals(3, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(1, array[2], 0);

        array = new double[] { 1, 2, 3 };
        Arrays.swap(array, 0, -1, 2);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);

        array = new double[] { 1, 2, 3 };
        Arrays.swap(array, -1, -1, 2);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
    }

    @Test
    public void testSwapInt() {
        final int[] array = new int[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
    }

    @Test
    public void testSwapNullIntArray() {
        final int[] array = null;
        Arrays.swap(array, 0, 2);
        assertNull(array);
    }

    @Test
    public void testSwapEmptyIntArray() {
        final int[] array = new int[0];
        Arrays.swap(array, 0, 2);
        assertEquals(0, array.length);
    }

    @Test
    public void testSwapIntRange() {
        int[] array = new int[] { 1, 2, 3, 4 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);

        array = new int[] { 1, 2, 3 };
        Arrays.swap(array, 3, 0);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new int[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new int[] { 1, 2, 3 };
        Arrays.swap(array, -1, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new int[] { 1, 2, 3 };
        Arrays.swap(array, 0, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new int[] { 1, 2, 3 };
        Arrays.swap(array, -1, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    public void testSwapIntExchangedOffsets() {
        int[] array;
        array = new int[] { 1, 2, 3 };
        Arrays.swap(array, 0, 1, 2);
        assertArrayEquals(new int[] { 2, 3, 1 }, array);

        array = new int[] { 1, 2, 3 };
        Arrays.swap(array, 1, 0, 2);
        assertArrayEquals(new int[] { 2, 3, 1 }, array);
    }

    @Test
    public void testSwapShort() {
        final short[] array = new short[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
    }

    @Test
    public void testSwapNullShortArray() {
        final short[] array = null;
        Arrays.swap(array, 0, 2);
        assertNull(array);
    }

    @Test
    public void testSwapEmptyShortArray() {
        final short[] array = new short[0];
        Arrays.swap(array, 0, 2);
        assertEquals(0, array.length);
    }

    @Test
    public void testSwapShortRange() {
        short[] array = new short[] { 1, 2, 3, 4 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);

        array = new short[] { 1, 2, 3 };
        Arrays.swap(array, 3, 0);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new short[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new short[] { 1, 2, 3 };
        Arrays.swap(array, -1, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new short[] { 1, 2, 3 };
        Arrays.swap(array, 0, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new short[] { 1, 2, 3 };
        Arrays.swap(array, -1, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    public void testSwapNullCharArray() {
        final char[] array = null;
        Arrays.swap(array, 0, 2);
        assertNull(array);
    }

    @Test
    public void testSwapEmptyCharArray() {
        final char[] array = new char[0];
        Arrays.swap(array, 0, 2);
        assertEquals(0, array.length);
    }

    @Test
    public void testSwapLong() {
        final long[] array = new long[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
    }

    @Test
    public void testSwapNullLongArray() {
        final long[] array = null;
        Arrays.swap(array, 0, 2);
        assertNull(array);
    }

    @Test
    public void testSwapEmptyLongArray() {
        final long[] array = new long[0];
        Arrays.swap(array, 0, 2);
        assertEquals(0, array.length);
    }

    @Test
    public void testSwapLongRange() {
        long[] array = new long[] { 1, 2, 3, 4 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);

        array = new long[] { 1, 2, 3 };
        Arrays.swap(array, 0, 3);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new long[] { 1, 2, 3 };
        Arrays.swap(array, 0, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new long[] { 1, 2, 3 };
        Arrays.swap(array, -1, 2, 2);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);

        array = new long[] { 1, 2, 3 };
        Arrays.swap(array, 0, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);

        array = new long[] { 1, 2, 3 };
        Arrays.swap(array, -1, -1, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    public void testSwapBoolean() {
        final boolean[] array = new boolean[] { true, false, false };
        Arrays.swap(array, 0, 2);
        assertFalse(array[0]);
        assertFalse(array[1]);
        assertTrue(array[2]);
    }

    @Test
    public void testSwapNullBooleanArray() {
        final boolean[] array = null;
        Arrays.swap(array, 0, 2);
        assertNull(array);
    }

    @Test
    public void testSwapEmptyBooleanArray() {
        final boolean[] array = new boolean[0];
        Arrays.swap(array, 0, 2);
        assertEquals(0, array.length);
    }

    @Test
    public void testSwapBooleanRange() {
        boolean[] array = new boolean[] { false, false, true, true };
        Arrays.swap(array, 0, 2, 2);
        assertTrue(array[0]);
        assertTrue(array[1]);
        assertFalse(array[2]);
        assertFalse(array[3]);

        array = new boolean[] { false, true, false };
        Arrays.swap(array, 0, 3);
        assertFalse(array[0]);
        assertTrue(array[1]);
        assertFalse(array[2]);

        array = new boolean[] { true, true, false };
        Arrays.swap(array, 0, 2, 2);
        assertFalse(array[0]);
        assertTrue(array[1]);
        assertTrue(array[2]);

        array = new boolean[] { true, true, false };
        Arrays.swap(array, -1, 2, 2);
        assertFalse(array[0]);
        assertTrue(array[1]);
        assertTrue(array[2]);

        array = new boolean[] { true, true, false };
        Arrays.swap(array, 0, -1, 2);
        assertTrue(array[0]);
        assertTrue(array[1]);
        assertFalse(array[2]);

        array = new boolean[] { true, true, false };
        Arrays.swap(array, -1, -1, 2);
        assertTrue(array[0]);
        assertTrue(array[1]);
        assertFalse(array[2]);
    }

    @Test
    public void testSwapObject() {
        final String[] array = new String[] { "1", "2", "3" };
        Arrays.swap(array, 0, 2);
        assertEquals("3", array[0]);
        assertEquals("2", array[1]);
        assertEquals("1", array[2]);
    }

    @Test
    public void testSwapNullObjectArray() {
        final String[] array = null;
        Arrays.swap(array, 0, 2);
        assertNull(array);
    }

    @Test
    public void testSwapEmptyObjectArray() {
        final String[] array = new String[0];
        Arrays.swap(array, 0, 2);
        assertEquals(0, array.length);
    }

    @Test
    public void testSwapObjectRange() {
        String[] array = new String[] { "1", "2", "3", "4" };
        Arrays.swap(array, 0, 2, 2);
        assertEquals("3", array[0]);
        assertEquals("4", array[1]);
        assertEquals("1", array[2]);
        assertEquals("2", array[3]);

        array = new String[] { "1", "2", "3", "4" };
        Arrays.swap(array, -1, 2, 3);
        assertEquals("3", array[0]);
        assertEquals("4", array[1]);
        assertEquals("1", array[2]);
        assertEquals("2", array[3]);

        array = new String[] { "1", "2", "3", "4", "5" };
        Arrays.swap(array, -3, 2, 3);
        assertEquals("3", array[0]);
        assertEquals("4", array[1]);
        assertEquals("5", array[2]);
        assertEquals("2", array[3]);
        assertEquals("1", array[4]);

        array = new String[] { "1", "2", "3", "4", "5" };
        Arrays.swap(array, 2, -2, 3);
        assertEquals("3", array[0]);
        assertEquals("4", array[1]);
        assertEquals("5", array[2]);
        assertEquals("2", array[3]);
        assertEquals("1", array[4]);

        array = new String[0];
        Arrays.swap(array, 0, 2, 2);
        assertEquals(0, array.length);

        array = null;
        Arrays.swap(array, 0, 2, 2);
        assertNull(array);
    }

    // -----------------------------------------------------------------------
    @Test
    public void testShiftDouble() {
        final double[] array = new double[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1);
        assertEquals(4, array[0], 0);
        assertEquals(1, array[1], 0);
        assertEquals(2, array[2], 0);
        assertEquals(3, array[3], 0);
        Arrays.shift(array, -1);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
        assertEquals(4, array[3], 0);
        Arrays.shift(array, 5);
        assertEquals(4, array[0], 0);
        assertEquals(1, array[1], 0);
        assertEquals(2, array[2], 0);
        assertEquals(3, array[3], 0);
        Arrays.shift(array, -3);
        assertEquals(3, array[0], 0);
        assertEquals(4, array[1], 0);
        assertEquals(1, array[2], 0);
        assertEquals(2, array[3], 0);
    }

    @Test
    public void testShiftRangeDouble() {
        final double[] array = new double[] { 1, 2, 3, 4, 5 };
        Arrays.shift(array, 1, 3, 1);
        assertEquals(1, array[0], 0);
        assertEquals(3, array[1], 0);
        assertEquals(2, array[2], 0);
        assertEquals(4, array[3], 0);
        assertEquals(5, array[4], 0);
        Arrays.shift(array, 1, 4, 2);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(4, array[2], 0);
        assertEquals(3, array[3], 0);
        assertEquals(5, array[4], 0);
    }

    @Test
    public void testShiftRangeNoElemDouble() {
        final double[] array = new double[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1, 1, 1);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
        assertEquals(4, array[3], 0);
    }

    @Test
    public void testShiftRangeNullDouble() {
        final double[] array = null;
        Arrays.shift(array, 1, 1, 1);
        assertNull(array);
    }

    @Test
    public void testShiftNullDouble() {
        final double[] array = null;

        Arrays.shift(array, 1);
        assertNull(array);
    }

    @Test
    public void testShiftAllDouble() {
        final double[] array = new double[] { 1, 2, 3, 4 };
        Arrays.shift(array, 4);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
        assertEquals(4, array[3], 0);
        Arrays.shift(array, -4);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
        assertEquals(4, array[3], 0);
    }

    @Test
    public void testShiftFloat() {
        final float[] array = new float[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1);
        assertEquals(4, array[0], 0);
        assertEquals(1, array[1], 0);
        assertEquals(2, array[2], 0);
        assertEquals(3, array[3], 0);
        Arrays.shift(array, -1);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
        assertEquals(4, array[3], 0);
        Arrays.shift(array, 5);
        assertEquals(4, array[0], 0);
        assertEquals(1, array[1], 0);
        assertEquals(2, array[2], 0);
        assertEquals(3, array[3], 0);
        Arrays.shift(array, -3);
        assertEquals(3, array[0], 0);
        assertEquals(4, array[1], 0);
        assertEquals(1, array[2], 0);
        assertEquals(2, array[3], 0);
    }

    @Test
    public void testShiftRangeFloat() {
        final float[] array = new float[] { 1, 2, 3, 4, 5 };
        Arrays.shift(array, 1, 3, 1);
        assertEquals(1, array[0], 0);
        assertEquals(3, array[1], 0);
        assertEquals(2, array[2], 0);
        assertEquals(4, array[3], 0);
        assertEquals(5, array[4], 0);
        Arrays.shift(array, 1, 4, 2);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(4, array[2], 0);
        assertEquals(3, array[3], 0);
        assertEquals(5, array[4], 0);
    }

    @Test
    public void testShiftRangeNoElemFloat() {
        final float[] array = new float[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1, 1, 1);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
        assertEquals(4, array[3], 0);
    }

    @Test
    public void testShiftRangeNullFloat() {
        final float[] array = null;
        Arrays.shift(array, 1, 1, 1);
        assertNull(array);
    }

    @Test
    public void testShiftNullFloat() {
        final float[] array = null;

        Arrays.shift(array, 1);
        assertNull(array);
    }

    @Test
    public void testShiftAllFloat() {
        final float[] array = new float[] { 1, 2, 3, 4 };
        Arrays.shift(array, 4);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
        assertEquals(4, array[3], 0);
        Arrays.shift(array, -4);
        assertEquals(1, array[0], 0);
        assertEquals(2, array[1], 0);
        assertEquals(3, array[2], 0);
        assertEquals(4, array[3], 0);
    }

    @Test
    public void testShiftShort() {
        short[] array = new short[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, 5);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -3);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);
        array = new short[] { 1, 2, 3, 4, 5 };
        Arrays.shift(array, 2);
        assertEquals(4, array[0]);
        assertEquals(5, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);
        assertEquals(3, array[4]);
    }

    @Test
    public void testShiftNullShort() {
        final short[] array = null;

        Arrays.shift(array, 1);
        assertNull(array);
    }

    @Test
    public void testShiftRangeShort() {
        final short[] array = new short[] { 1, 2, 3, 4, 5 };
        Arrays.shift(array, 1, 3, 1);
        assertEquals(1, array[0]);
        assertEquals(3, array[1]);
        assertEquals(2, array[2]);
        assertEquals(4, array[3]);
        assertEquals(5, array[4]);
        Arrays.shift(array, 1, 4, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(4, array[2]);
        assertEquals(3, array[3]);
        assertEquals(5, array[4]);
    }

    @Test
    public void testShiftRangeNoElemShort() {
        final short[] array = new short[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1, 1, 1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftRangeNullShort() {
        final short[] array = null;

        Arrays.shift(array, 1, 1, 1);
        assertNull(array);
    }

    @Test
    public void testShiftAllShort() {
        final short[] array = new short[] { 1, 2, 3, 4 };
        Arrays.shift(array, 4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, -4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftByte() {
        final byte[] array = new byte[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, 5);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -3);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);
    }

    @Test
    public void testShiftRangeByte() {
        final byte[] array = new byte[] { 1, 2, 3, 4, 5 };
        Arrays.shift(array, 1, 3, 1);
        assertEquals(1, array[0]);
        assertEquals(3, array[1]);
        assertEquals(2, array[2]);
        assertEquals(4, array[3]);
        assertEquals(5, array[4]);
        Arrays.shift(array, 1, 4, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(4, array[2]);
        assertEquals(3, array[3]);
        assertEquals(5, array[4]);
    }

    @Test
    public void testShiftRangeNoElemByte() {
        final byte[] array = new byte[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1, 1, 1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftRangeNullByte() {
        final byte[] array = null;
        Arrays.shift(array, 1, 1, 1);
        assertNull(array);
    }

    @Test
    public void testShiftAllByte() {
        final byte[] array = new byte[] { 1, 2, 3, 4 };
        Arrays.shift(array, 4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, -4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftChar() {
        final char[] array = new char[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, 5);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -3);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);
    }

    @Test
    public void testShiftRangeChar() {
        final char[] array = new char[] { 1, 2, 3, 4, 5 };
        Arrays.shift(array, 1, 3, 1);
        assertEquals(1, array[0]);
        assertEquals(3, array[1]);
        assertEquals(2, array[2]);
        assertEquals(4, array[3]);
        assertEquals(5, array[4]);
        Arrays.shift(array, 1, 4, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(4, array[2]);
        assertEquals(3, array[3]);
        assertEquals(5, array[4]);
    }

    @Test
    public void testShiftRangeNoElemChar() {
        final char[] array = new char[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1, 1, 1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftRangeNullChar() {
        final char[] array = null;
        Arrays.shift(array, 1, 1, 1);
        assertNull(array);
    }

    @Test
    public void testShiftAllChar() {
        final char[] array = new char[] { 1, 2, 3, 4 };
        Arrays.shift(array, 4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, -4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftLong() {
        final long[] array = new long[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, 5);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -3);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);
    }

    @Test
    public void testShiftRangeLong() {
        final long[] array = new long[] { 1, 2, 3, 4, 5 };
        Arrays.shift(array, 1, 3, 1);
        assertEquals(1, array[0]);
        assertEquals(3, array[1]);
        assertEquals(2, array[2]);
        assertEquals(4, array[3]);
        assertEquals(5, array[4]);
        Arrays.shift(array, 1, 4, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(4, array[2]);
        assertEquals(3, array[3]);
        assertEquals(5, array[4]);
    }

    @Test
    public void testShiftRangeNoElemLong() {
        final long[] array = new long[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1, 1, 1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftRangeNullLong() {
        final long[] array = null;
        Arrays.shift(array, 1, 1, 1);
        assertNull(array);
    }

    @Test
    public void testShiftNullLong() {
        final long[] array = null;

        Arrays.shift(array, 1);
        assertNull(array);
    }

    @Test
    public void testShiftAllLong() {
        final long[] array = new long[] { 1, 2, 3, 4 };
        Arrays.shift(array, 4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, -4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftInt() {
        final int[] array = new int[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, 5);
        assertEquals(4, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
        Arrays.shift(array, -3);
        assertEquals(3, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, array[2]);
        assertEquals(2, array[3]);
    }

    @Test
    public void testShiftNullInt() {
        final int[] array = null;

        Arrays.shift(array, 1);
        assertNull(array);
    }

    @Test
    public void testShiftRangeInt() {
        final int[] array = new int[] { 1, 2, 3, 4, 5 };
        Arrays.shift(array, 1, 3, 1);
        assertEquals(1, array[0]);
        assertEquals(3, array[1]);
        assertEquals(2, array[2]);
        assertEquals(4, array[3]);
        assertEquals(5, array[4]);
        Arrays.shift(array, 1, 4, 2);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(4, array[2]);
        assertEquals(3, array[3]);
        assertEquals(5, array[4]);
    }

    @Test
    public void testShiftRangeNoElemInt() {
        final int[] array = new int[] { 1, 2, 3, 4 };
        Arrays.shift(array, 1, 1, 1);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftRangeNullInt() {
        final int[] array = null;
        Arrays.shift(array, 1, 1, 1);
        assertNull(array);
    }

    @Test
    public void testShiftAllInt() {
        final int[] array = new int[] { 1, 2, 3, 4 };
        Arrays.shift(array, 4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        Arrays.shift(array, -4);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testShiftObject() {
        final String[] array = new String[] { "1", "2", "3", "4" };
        Arrays.shift(array, 1);
        assertEquals("4", array[0]);
        assertEquals("1", array[1]);
        assertEquals("2", array[2]);
        assertEquals("3", array[3]);
        Arrays.shift(array, -1);
        assertEquals("1", array[0]);
        assertEquals("2", array[1]);
        assertEquals("3", array[2]);
        assertEquals("4", array[3]);
        Arrays.shift(array, 5);
        assertEquals("4", array[0]);
        assertEquals("1", array[1]);
        assertEquals("2", array[2]);
        assertEquals("3", array[3]);
        Arrays.shift(array, -3);
        assertEquals("3", array[0]);
        assertEquals("4", array[1]);
        assertEquals("1", array[2]);
        assertEquals("2", array[3]);
    }

    @Test
    public void testShiftNullObject() {
        final String[] array = null;

        Arrays.shift(array, 1);
        assertNull(array);
    }

    @Test
    public void testShiftRangeObject() {
        final String[] array = new String[] { "1", "2", "3", "4", "5" };
        Arrays.shift(array, 1, 3, 1);
        assertEquals("1", array[0]);
        assertEquals("3", array[1]);
        assertEquals("2", array[2]);
        assertEquals("4", array[3]);
        assertEquals("5", array[4]);
        Arrays.shift(array, 1, 4, 2);
        assertEquals("1", array[0]);
        assertEquals("2", array[1]);
        assertEquals("4", array[2]);
        assertEquals("3", array[3]);
        assertEquals("5", array[4]);
    }

    @Test
    public void testShiftRangeNoElemObject() {
        final String[] array = new String[] { "1", "2", "3", "4" };
        Arrays.shift(array, 1, 1, 1);
        assertEquals("1", array[0]);
        assertEquals("2", array[1]);
        assertEquals("3", array[2]);
        assertEquals("4", array[3]);
    }

    @Test
    public void testShiftRangeNullObject() {
        final String[] array = null;
        Arrays.shift(array, 1, 1, 1);
        assertNull(array);
    }

    @Test
    public void testShiftAllObject() {
        final String[] array = new String[] { "1", "2", "3", "4" };
        Arrays.shift(array, 4);
        assertEquals("1", array[0]);
        assertEquals("2", array[1]);
        assertEquals("3", array[2]);
        assertEquals("4", array[3]);
        Arrays.shift(array, -4);
        assertEquals("1", array[0]);
        assertEquals("2", array[1]);
        assertEquals("3", array[2]);
        assertEquals("4", array[3]);
    }

    @Test
    public void testShiftBoolean() {
        final boolean[] array = new boolean[] { true, true, false, false };

        Arrays.shift(array, 1);
        assertFalse(array[0]);
        assertTrue(array[1]);
        assertTrue(array[2]);
        assertFalse(array[3]);

        Arrays.shift(array, -1);
        assertTrue(array[0]);
        assertTrue(array[1]);
        assertFalse(array[2]);
        assertFalse(array[3]);

        Arrays.shift(array, 5);
        assertFalse(array[0]);
        assertTrue(array[1]);
        assertTrue(array[2]);
        assertFalse(array[3]);

        Arrays.shift(array, -3);
        assertFalse(array[0]);
        assertFalse(array[1]);
        assertTrue(array[2]);
        assertTrue(array[3]);
    }

    @Test
    public void testShiftNullBoolean() {
        final boolean[] array = null;

        Arrays.shift(array, 1);
        assertNull(array);
    }

    // -----------------------------------------------------------------------
    @Test
    public void testIndexOf() {
        final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
        assertEquals(-1, Arrays.indexOf(null, null));
        assertEquals(-1, Arrays.indexOf(null, "0"));
        assertEquals(-1, Arrays.indexOf(new Object[0], "0"));
        assertEquals(0, Arrays.indexOf(array, "0"));
        assertEquals(1, Arrays.indexOf(array, "1"));
        assertEquals(2, Arrays.indexOf(array, "2"));
        assertEquals(3, Arrays.indexOf(array, "3"));
        assertEquals(4, Arrays.indexOf(array, null));
        assertEquals(-1, Arrays.indexOf(array, "notInArray"));
    }

    @Test
    public void testIndexOfWithStartIndex() {
        final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
        assertEquals(-1, Arrays.indexOf(null, null, 2));
        assertEquals(-1, Arrays.indexOf(new Object[0], "0", 0));
        assertEquals(-1, Arrays.indexOf(null, "0", 2));
        assertEquals(5, Arrays.indexOf(array, "0", 2));
        assertEquals(-1, Arrays.indexOf(array, "1", 2));
        assertEquals(2, Arrays.indexOf(array, "2", 2));
        assertEquals(3, Arrays.indexOf(array, "3", 2));
        assertEquals(4, Arrays.indexOf(array, null, 2));
        assertEquals(-1, Arrays.indexOf(array, "notInArray", 2));

        assertEquals(4, Arrays.indexOf(array, null, -1));
        assertEquals(-1, Arrays.indexOf(array, null, 8));
        assertEquals(-1, Arrays.indexOf(array, "0", 8));
    }

    @Test
    public void testLastIndexOf() {
        final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
        assertEquals(-1, Arrays.lastIndexOf(null, null));
        assertEquals(-1, Arrays.lastIndexOf(null, "0"));
        assertEquals(5, Arrays.lastIndexOf(array, "0"));
        assertEquals(1, Arrays.lastIndexOf(array, "1"));
        assertEquals(2, Arrays.lastIndexOf(array, "2"));
        assertEquals(3, Arrays.lastIndexOf(array, "3"));
        assertEquals(4, Arrays.lastIndexOf(array, null));
        assertEquals(-1, Arrays.lastIndexOf(array, "notInArray"));
    }

    @Test
    public void testLastIndexOfWithStartIndex() {
        final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
        assertEquals(-1, Arrays.lastIndexOf(null, null, 2));
        assertEquals(-1, Arrays.lastIndexOf(null, "0", 2));
        assertEquals(0, Arrays.lastIndexOf(array, "0", 2));
        assertEquals(1, Arrays.lastIndexOf(array, "1", 2));
        assertEquals(2, Arrays.lastIndexOf(array, "2", 2));
        assertEquals(-1, Arrays.lastIndexOf(array, "3", 2));
        assertEquals(-1, Arrays.lastIndexOf(array, "3", -1));
        assertEquals(4, Arrays.lastIndexOf(array, null, 5));
        assertEquals(-1, Arrays.lastIndexOf(array, null, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, "notInArray", 5));

        assertEquals(-1, Arrays.lastIndexOf(array, null, -1));
        assertEquals(5, Arrays.lastIndexOf(array, "0", 88));
    }

    @Test
    public void testContains() {
        final Object[] array = new Object[] { "0", "1", "2", "3", null, "0" };
        assertFalse(Arrays.contains(null, null));
        assertFalse(Arrays.contains(null, "1"));
        assertTrue(Arrays.contains(array, "0"));
        assertTrue(Arrays.contains(array, "1"));
        assertTrue(Arrays.contains(array, "2"));
        assertTrue(Arrays.contains(array, "3"));
        assertTrue(Arrays.contains(array, null));
        assertFalse(Arrays.contains(array, "notInArray"));
    }

    @Test
    public void testContains_LANG_1261() {
        class LANG1261ParentObject {
            @Override
            public boolean equals(final Object o) {
                return true;
            }
        }
        class LANG1261ChildObject extends LANG1261ParentObject {
        }

        final Object[] array = new LANG1261ChildObject[] { new LANG1261ChildObject() };

        assertTrue(Arrays.contains(array, new LANG1261ParentObject()));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testIndexOfLong() {
        long[] array = null;
        assertEquals(-1, Arrays.indexOf(array, 0));
        array = new long[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.indexOf(array, 0L));
        assertEquals(1, Arrays.indexOf(array, 1L));
        assertEquals(2, Arrays.indexOf(array, 2L));
        assertEquals(3, Arrays.indexOf(array, 3L));
        assertEquals(-1, Arrays.indexOf(array, 99L));
    }

    @Test
    public void testIndexOfLongWithStartIndex() {
        long[] array = null;
        assertEquals(-1, Arrays.indexOf(array, 0, 2));
        array = new long[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.indexOf(array, 0L/* !!! */, 2));
        assertEquals(-1, Arrays.indexOf(array, 1L, 2));
        assertEquals(2, Arrays.indexOf(array, 2L, 2));
        assertEquals(3, Arrays.indexOf(array, 3L, 2));
        assertEquals(3, Arrays.indexOf(array, 3L, -1));
        assertEquals(-1, Arrays.indexOf(array, 99L, 0));
        assertEquals(-1, Arrays.indexOf(array, 0L, 6));
    }

    @Test

    public void testLastIndexOfLong() {
        long[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, 0));
        array = new long[] { 0, 1, 2, 3, 0 };
        assertEquals(-1, Arrays.lastIndexOf(array, 0));
        assertEquals(4, Arrays.lastIndexOf(array, 0L/* !!!! */));
        assertEquals(-1, Arrays.lastIndexOf(array, 1));
        assertEquals(1, Arrays.lastIndexOf(array, 1L));
        assertEquals(-1, Arrays.lastIndexOf(array, 2));
        assertEquals(2, Arrays.lastIndexOf(array, 2L));
        assertEquals(-1, Arrays.lastIndexOf(array, 3));
        assertEquals(3, Arrays.lastIndexOf(array, 3L));
        assertEquals(-1, Arrays.lastIndexOf(array, 99));
    }

    @Test
    public void testLastIndexOfLongWithStartIndex() {
        long[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, 0, 2));
        array = new long[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.lastIndexOf(array, 0L/* !!! */, 2));
        assertEquals(1, Arrays.lastIndexOf(array, 1L, 2));
        assertEquals(2, Arrays.lastIndexOf(array, 2L, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, 3L, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, 3L, -1));
        assertEquals(-1, Arrays.lastIndexOf(array, 99L, 4));
        assertEquals(4, Arrays.lastIndexOf(array, 0L, 88));
    }

    @Test
    public void testContainsLong() {
        long[] array = null;
        assertFalse(Arrays.contains(array, 1));
        array = new long[] { 0, 1, 2, 3, 0 };
        assertFalse(Arrays.contains(array, 0/* !!! */));
        assertFalse(Arrays.contains(array, 1));
        assertFalse(Arrays.contains(array, 2));
        assertFalse(Arrays.contains(array, 3));
        assertTrue(Arrays.contains(array, 0L/* !!! */));
        assertTrue(Arrays.contains(array, 1L));
        assertTrue(Arrays.contains(array, 2L));
        assertTrue(Arrays.contains(array, 3L));
        assertFalse(Arrays.contains(array, 99));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testIndexOfInt() {
        int[] array = null;
        assertEquals(-1, Arrays.indexOf(array, 0));
        array = new int[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.indexOf(array, 0));
        assertEquals(1, Arrays.indexOf(array, 1));
        assertEquals(2, Arrays.indexOf(array, 2));
        assertEquals(3, Arrays.indexOf(array, 3));
        assertEquals(-1, Arrays.indexOf(array, 99));
    }

    @Test
    public void testIndexOfIntWithStartIndex() {
        int[] array = null;
        assertEquals(-1, Arrays.indexOf(array, 0, 2));
        array = new int[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.indexOf(array, 0, 2));
        assertEquals(-1, Arrays.indexOf(array, 1, 2));
        assertEquals(2, Arrays.indexOf(array, 2, 2));
        assertEquals(3, Arrays.indexOf(array, 3, 2));
        assertEquals(3, Arrays.indexOf(array, 3, -1));
        assertEquals(-1, Arrays.indexOf(array, 99, 0));
        assertEquals(-1, Arrays.indexOf(array, 0, 6));
    }

    @Test
    public void testLastIndexOfInt() {
        int[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, 0));
        array = new int[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.lastIndexOf(array, 0));
        assertEquals(1, Arrays.lastIndexOf(array, 1));
        assertEquals(2, Arrays.lastIndexOf(array, 2));
        assertEquals(3, Arrays.lastIndexOf(array, 3));
        assertEquals(-1, Arrays.lastIndexOf(array, 99));
    }

    @Test
    public void testLastIndexOfIntWithStartIndex() {
        int[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, 0, 2));
        array = new int[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.lastIndexOf(array, 0, 2));
        assertEquals(1, Arrays.lastIndexOf(array, 1, 2));
        assertEquals(2, Arrays.lastIndexOf(array, 2, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, 3, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, 3, -1));
        assertEquals(-1, Arrays.lastIndexOf(array, 99));
        assertEquals(4, Arrays.lastIndexOf(array, 0, 88));
    }

    @Test
    public void testContainsInt() {
        int[] array = null;
        assertFalse(Arrays.contains(array, 1));
        array = new int[] { 0, 1, 2, 3, 0 };
        assertTrue(Arrays.contains(array, 0));
        assertTrue(Arrays.contains(array, 1));
        assertTrue(Arrays.contains(array, 2));
        assertTrue(Arrays.contains(array, 3));
        assertFalse(Arrays.contains(array, 99));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testIndexOfShort() {
        short[] array = null;
        assertEquals(-1, Arrays.indexOf(array, (short) 0));
        array = new short[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.indexOf(array, (short) 0));
        assertEquals(1, Arrays.indexOf(array, (short) 1));
        assertEquals(2, Arrays.indexOf(array, (short) 2));
        assertEquals(3, Arrays.indexOf(array, (short) 3));
        assertEquals(-1, Arrays.indexOf(array, (short) 99));
    }

    @Test
    public void testIndexOfShortWithStartIndex() {
        short[] array = null;
        assertEquals(-1, Arrays.indexOf(array, (short) 0, 2));
        array = new short[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.indexOf(array, (short) 0, 2));
        assertEquals(-1, Arrays.indexOf(array, (short) 1, 2));
        assertEquals(2, Arrays.indexOf(array, (short) 2, 2));
        assertEquals(3, Arrays.indexOf(array, (short) 3, 2));
        assertEquals(3, Arrays.indexOf(array, (short) 3, -1));
        assertEquals(-1, Arrays.indexOf(array, (short) 99, 0));
        assertEquals(-1, Arrays.indexOf(array, (short) 0, 6));
    }

    @Test
    public void testLastIndexOfShort() {
        short[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, (short) 0));
        array = new short[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.lastIndexOf(array, (short) 0));
        assertEquals(1, Arrays.lastIndexOf(array, (short) 1));
        assertEquals(2, Arrays.lastIndexOf(array, (short) 2));
        assertEquals(3, Arrays.lastIndexOf(array, (short) 3));
        assertEquals(-1, Arrays.lastIndexOf(array, (short) 99));
    }

    @Test
    public void testLastIndexOfShortWithStartIndex() {
        short[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, (short) 0, 2));
        array = new short[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.lastIndexOf(array, (short) 0, 2));
        assertEquals(1, Arrays.lastIndexOf(array, (short) 1, 2));
        assertEquals(2, Arrays.lastIndexOf(array, (short) 2, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, (short) 3, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, (short) 3, -1));
        assertEquals(-1, Arrays.lastIndexOf(array, (short) 99));
        assertEquals(4, Arrays.lastIndexOf(array, (short) 0, 88));
    }

    @Test
    public void testContainsShort() {
        short[] array = null;
        assertFalse(Arrays.contains(array, (short) 1));
        array = new short[] { 0, 1, 2, 3, 0 };
        assertTrue(Arrays.contains(array, (short) 0));
        assertTrue(Arrays.contains(array, (short) 1));
        assertTrue(Arrays.contains(array, (short) 2));
        assertTrue(Arrays.contains(array, (short) 3));
        assertFalse(Arrays.contains(array, (short) 99));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testIndexOfChar() {
        char[] array = null;
        assertEquals(-1, Arrays.indexOf(array, 'a'));
        array = new char[] { 'a', 'b', 'c', 'd', 'a' };
        assertEquals(0, Arrays.indexOf(array, 'a'));
        assertEquals(1, Arrays.indexOf(array, 'b'));
        assertEquals(2, Arrays.indexOf(array, 'c'));
        assertEquals(3, Arrays.indexOf(array, 'd'));
        assertEquals(-1, Arrays.indexOf(array, 'e'));
    }

    @Test
    public void testIndexOfCharWithStartIndex() {
        char[] array = null;
        assertEquals(-1, Arrays.indexOf(array, 'a', 2));
        array = new char[] { 'a', 'b', 'c', 'd', 'a' };
        assertEquals(4, Arrays.indexOf(array, 'a', 2));
        assertEquals(-1, Arrays.indexOf(array, 'b', 2));
        assertEquals(2, Arrays.indexOf(array, 'c', 2));
        assertEquals(3, Arrays.indexOf(array, 'd', 2));
        assertEquals(3, Arrays.indexOf(array, 'd', -1));
        assertEquals(-1, Arrays.indexOf(array, 'e', 0));
        assertEquals(-1, Arrays.indexOf(array, 'a', 6));
    }

    @Test
    public void testLastIndexOfChar() {
        char[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, 'a'));
        array = new char[] { 'a', 'b', 'c', 'd', 'a' };
        assertEquals(4, Arrays.lastIndexOf(array, 'a'));
        assertEquals(1, Arrays.lastIndexOf(array, 'b'));
        assertEquals(2, Arrays.lastIndexOf(array, 'c'));
        assertEquals(3, Arrays.lastIndexOf(array, 'd'));
        assertEquals(-1, Arrays.lastIndexOf(array, 'e'));
    }

    @Test
    public void testLastIndexOfCharWithStartIndex() {
        char[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, 'a', 2));
        array = new char[] { 'a', 'b', 'c', 'd', 'a' };
        assertEquals(0, Arrays.lastIndexOf(array, 'a', 2));
        assertEquals(1, Arrays.lastIndexOf(array, 'b', 2));
        assertEquals(2, Arrays.lastIndexOf(array, 'c', 2));
        assertEquals(-1, Arrays.lastIndexOf(array, 'd', 2));
        assertEquals(-1, Arrays.lastIndexOf(array, 'd', -1));
        assertEquals(-1, Arrays.lastIndexOf(array, 'e'));
        assertEquals(4, Arrays.lastIndexOf(array, 'a', 88));
    }

    @Test
    public void testContainsChar() {
        char[] array = null;
        assertFalse(Arrays.contains(array, 'b'));
        array = new char[] { 'a', 'b', 'c', 'd', 'a' };
        assertTrue(Arrays.contains(array, 'a'));
        assertTrue(Arrays.contains(array, 'b'));
        assertTrue(Arrays.contains(array, 'c'));
        assertTrue(Arrays.contains(array, 'd'));
        assertFalse(Arrays.contains(array, 'e'));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testIndexOfByte() {
        byte[] array = null;
        assertEquals(-1, Arrays.indexOf(array, (byte) 0));
        array = new byte[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.indexOf(array, (byte) 0));
        assertEquals(1, Arrays.indexOf(array, (byte) 1));
        assertEquals(2, Arrays.indexOf(array, (byte) 2));
        assertEquals(3, Arrays.indexOf(array, (byte) 3));
        assertEquals(-1, Arrays.indexOf(array, (byte) 99));
    }

    @Test
    public void testIndexOfByteWithStartIndex() {
        byte[] array = null;
        assertEquals(-1, Arrays.indexOf(array, (byte) 0, 2));
        array = new byte[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.indexOf(array, (byte) 0, 2));
        assertEquals(-1, Arrays.indexOf(array, (byte) 1, 2));
        assertEquals(2, Arrays.indexOf(array, (byte) 2, 2));
        assertEquals(3, Arrays.indexOf(array, (byte) 3, 2));
        assertEquals(3, Arrays.indexOf(array, (byte) 3, -1));
        assertEquals(-1, Arrays.indexOf(array, (byte) 99, 0));
        assertEquals(-1, Arrays.indexOf(array, (byte) 0, 6));
    }

    @Test
    public void testLastIndexOfByte() {
        byte[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, (byte) 0));
        array = new byte[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.lastIndexOf(array, (byte) 0));
        assertEquals(1, Arrays.lastIndexOf(array, (byte) 1));
        assertEquals(2, Arrays.lastIndexOf(array, (byte) 2));
        assertEquals(3, Arrays.lastIndexOf(array, (byte) 3));
        assertEquals(-1, Arrays.lastIndexOf(array, (byte) 99));
    }

    @Test
    public void testLastIndexOfByteWithStartIndex() {
        byte[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, (byte) 0, 2));
        array = new byte[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.lastIndexOf(array, (byte) 0, 2));
        assertEquals(1, Arrays.lastIndexOf(array, (byte) 1, 2));
        assertEquals(2, Arrays.lastIndexOf(array, (byte) 2, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, (byte) 3, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, (byte) 3, -1));
        assertEquals(-1, Arrays.lastIndexOf(array, (byte) 99));
        assertEquals(4, Arrays.lastIndexOf(array, (byte) 0, 88));
    }

    @Test
    public void testContainsByte() {
        byte[] array = null;
        assertFalse(Arrays.contains(array, (byte) 1));
        array = new byte[] { 0, 1, 2, 3, 0 };
        assertTrue(Arrays.contains(array, (byte) 0));
        assertTrue(Arrays.contains(array, (byte) 1));
        assertTrue(Arrays.contains(array, (byte) 2));
        assertTrue(Arrays.contains(array, (byte) 3));
        assertFalse(Arrays.contains(array, (byte) 99));
    }

    // -----------------------------------------------------------------------
    @SuppressWarnings("cast")
    @Test
    public void testIndexOfDouble() {
        double[] array = null;
        assertEquals(-1, Arrays.indexOf(array, (double) 0));
        array = new double[0];
        assertEquals(-1, Arrays.indexOf(array, (double) 0));
        array = new double[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.indexOf(array, (double) 0));
        assertEquals(1, Arrays.indexOf(array, (double) 1));
        assertEquals(2, Arrays.indexOf(array, (double) 2));
        assertEquals(3, Arrays.indexOf(array, (double) 3));
        assertEquals(3, Arrays.indexOf(array, (double) 3, -1));
        assertEquals(-1, Arrays.indexOf(array, (double) 99));
    }

    @SuppressWarnings("cast")
    @Test
    public void testIndexOfDoubleWithStartIndex() {
        double[] array = null;
        assertEquals(-1, Arrays.indexOf(array, (double) 0, 2));
        array = new double[0];
        assertEquals(-1, Arrays.indexOf(array, (double) 0, 2));
        array = new double[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.indexOf(array, (double) 0, 2));
        assertEquals(-1, Arrays.indexOf(array, (double) 1, 2));
        assertEquals(2, Arrays.indexOf(array, (double) 2, 2));
        assertEquals(3, Arrays.indexOf(array, (double) 3, 2));
        assertEquals(-1, Arrays.indexOf(array, (double) 99, 0));
        assertEquals(-1, Arrays.indexOf(array, (double) 0, 6));
    }

    @SuppressWarnings("cast")
    @Test
    public void testLastIndexOfDouble() {
        double[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, (double) 0));
        array = new double[0];
        assertEquals(-1, Arrays.lastIndexOf(array, (double) 0));
        array = new double[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.lastIndexOf(array, (double) 0));
        assertEquals(1, Arrays.lastIndexOf(array, (double) 1));
        assertEquals(2, Arrays.lastIndexOf(array, (double) 2));
        assertEquals(3, Arrays.lastIndexOf(array, (double) 3));
        assertEquals(-1, Arrays.lastIndexOf(array, (double) 99));
    }

    @SuppressWarnings("cast")
    @Test
    public void testLastIndexOfDoubleWithStartIndex() {
        double[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, (double) 0, 2));
        array = new double[0];
        assertEquals(-1, Arrays.lastIndexOf(array, (double) 0, 2));
        array = new double[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.lastIndexOf(array, (double) 0, 2));
        assertEquals(1, Arrays.lastIndexOf(array, (double) 1, 2));
        assertEquals(2, Arrays.lastIndexOf(array, (double) 2, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, (double) 3, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, (double) 3, -1));
        assertEquals(-1, Arrays.lastIndexOf(array, (double) 99));
        assertEquals(4, Arrays.lastIndexOf(array, (double) 0, 88));
    }

    @SuppressWarnings("cast")
    @Test
    public void testContainsDouble() {
        double[] array = null;
        assertFalse(Arrays.contains(array, (double) 1));
        array = new double[] { 0, 1, 2, 3, 0 };
        assertTrue(Arrays.contains(array, (double) 0));
        assertTrue(Arrays.contains(array, (double) 1));
        assertTrue(Arrays.contains(array, (double) 2));
        assertTrue(Arrays.contains(array, (double) 3));
        assertFalse(Arrays.contains(array, (double) 99));
    }

    // -----------------------------------------------------------------------
    @SuppressWarnings("cast")
    @Test
    public void testIndexOfFloat() {
        float[] array = null;
        assertEquals(-1, Arrays.indexOf(array, (float) 0));
        array = new float[0];
        assertEquals(-1, Arrays.indexOf(array, (float) 0));
        array = new float[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.indexOf(array, (float) 0));
        assertEquals(1, Arrays.indexOf(array, (float) 1));
        assertEquals(2, Arrays.indexOf(array, (float) 2));
        assertEquals(3, Arrays.indexOf(array, (float) 3));
        assertEquals(-1, Arrays.indexOf(array, (float) 99));
    }

    @SuppressWarnings("cast")
    @Test
    public void testIndexOfFloatWithStartIndex() {
        float[] array = null;
        assertEquals(-1, Arrays.indexOf(array, (float) 0, 2));
        array = new float[0];
        assertEquals(-1, Arrays.indexOf(array, (float) 0, 2));
        array = new float[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.indexOf(array, (float) 0, 2));
        assertEquals(-1, Arrays.indexOf(array, (float) 1, 2));
        assertEquals(2, Arrays.indexOf(array, (float) 2, 2));
        assertEquals(3, Arrays.indexOf(array, (float) 3, 2));
        assertEquals(3, Arrays.indexOf(array, (float) 3, -1));
        assertEquals(-1, Arrays.indexOf(array, (float) 99, 0));
        assertEquals(-1, Arrays.indexOf(array, (float) 0, 6));
    }

    @SuppressWarnings("cast")
    @Test
    public void testLastIndexOfFloat() {
        float[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, (float) 0));
        array = new float[0];
        assertEquals(-1, Arrays.lastIndexOf(array, (float) 0));
        array = new float[] { 0, 1, 2, 3, 0 };
        assertEquals(4, Arrays.lastIndexOf(array, (float) 0));
        assertEquals(1, Arrays.lastIndexOf(array, (float) 1));
        assertEquals(2, Arrays.lastIndexOf(array, (float) 2));
        assertEquals(3, Arrays.lastIndexOf(array, (float) 3));
        assertEquals(-1, Arrays.lastIndexOf(array, (float) 99));
    }

    @SuppressWarnings("cast")
    @Test
    public void testLastIndexOfFloatWithStartIndex() {
        float[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, (float) 0, 2));
        array = new float[0];
        assertEquals(-1, Arrays.lastIndexOf(array, (float) 0, 2));
        array = new float[] { 0, 1, 2, 3, 0 };
        assertEquals(0, Arrays.lastIndexOf(array, (float) 0, 2));
        assertEquals(1, Arrays.lastIndexOf(array, (float) 1, 2));
        assertEquals(2, Arrays.lastIndexOf(array, (float) 2, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, (float) 3, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, (float) 3, -1));
        assertEquals(-1, Arrays.lastIndexOf(array, (float) 99));
        assertEquals(4, Arrays.lastIndexOf(array, (float) 0, 88));
    }

    @SuppressWarnings("cast")
    @Test
    public void testContainsFloat() {
        float[] array = null;
        assertFalse(Arrays.contains(array, (float) 1));
        array = new float[] { 0, 1, 2, 3, 0 };
        assertTrue(Arrays.contains(array, (float) 0));
        assertTrue(Arrays.contains(array, (float) 1));
        assertTrue(Arrays.contains(array, (float) 2));
        assertTrue(Arrays.contains(array, (float) 3));
        assertFalse(Arrays.contains(array, (float) 99));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testIndexOfBoolean() {
        boolean[] array = null;
        assertEquals(-1, Arrays.indexOf(array, true));
        array = new boolean[0];
        assertEquals(-1, Arrays.indexOf(array, true));
        array = new boolean[] { true, false, true };
        assertEquals(0, Arrays.indexOf(array, true));
        assertEquals(1, Arrays.indexOf(array, false));
        array = new boolean[] { true, true };
        assertEquals(-1, Arrays.indexOf(array, false));
    }

    @Test
    public void testIndexOfBooleanWithStartIndex() {
        boolean[] array = null;
        assertEquals(-1, Arrays.indexOf(array, true, 2));
        array = new boolean[0];
        assertEquals(-1, Arrays.indexOf(array, true, 2));
        array = new boolean[] { true, false, true };
        assertEquals(2, Arrays.indexOf(array, true, 1));
        assertEquals(-1, Arrays.indexOf(array, false, 2));
        assertEquals(1, Arrays.indexOf(array, false, 0));
        assertEquals(1, Arrays.indexOf(array, false, -1));
        array = new boolean[] { true, true };
        assertEquals(-1, Arrays.indexOf(array, false, 0));
        assertEquals(-1, Arrays.indexOf(array, false, -1));
    }

    @Test
    public void testLastIndexOfBoolean() {
        boolean[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, true));
        array = new boolean[0];
        assertEquals(-1, Arrays.lastIndexOf(array, true));
        array = new boolean[] { true, false, true };
        assertEquals(2, Arrays.lastIndexOf(array, true));
        assertEquals(1, Arrays.lastIndexOf(array, false));
        array = new boolean[] { true, true };
        assertEquals(-1, Arrays.lastIndexOf(array, false));
    }

    @Test
    public void testLastIndexOfBooleanWithStartIndex() {
        boolean[] array = null;
        assertEquals(-1, Arrays.lastIndexOf(array, true, 2));
        array = new boolean[0];
        assertEquals(-1, Arrays.lastIndexOf(array, true, 2));
        array = new boolean[] { true, false, true };
        assertEquals(2, Arrays.lastIndexOf(array, true, 2));
        assertEquals(0, Arrays.lastIndexOf(array, true, 1));
        assertEquals(1, Arrays.lastIndexOf(array, false, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, true, -1));
        array = new boolean[] { true, true };
        assertEquals(-1, Arrays.lastIndexOf(array, false, 2));
        assertEquals(-1, Arrays.lastIndexOf(array, true, -1));
    }

    @Test
    public void testContainsBoolean() {
        boolean[] array = null;
        assertFalse(Arrays.contains(array, true));
        array = new boolean[] { true, false, true };
        assertTrue(Arrays.contains(array, true));
        assertTrue(Arrays.contains(array, false));
        array = new boolean[] { true, true };
        assertTrue(Arrays.contains(array, true));
        assertFalse(Arrays.contains(array, false));
    }

    // -----------------------------------------------------------------------

    /**
     * Test for {@link Arrays#isEmpty(java.lang.Object[])}.
     */
    @Test
    public void testIsEmptyObject() {
        final Object[] emptyArray = new Object[] {};
        final Object[] notEmptyArray = new Object[] { new String("Value") };
        assertTrue(Arrays.isEmpty((Object[]) null));
        assertTrue(Arrays.isEmpty(emptyArray));
        assertFalse(Arrays.isEmpty(notEmptyArray));
    }

    /**
     * Tests for {@link Arrays#isEmpty(long[])}, {@link Arrays#isEmpty(int[])}, {@link Arrays#isEmpty(short[])}, {@link Arrays#isEmpty(char[])},
     * {@link Arrays#isEmpty(byte[])}, {@link Arrays#isEmpty(double[])}, {@link Arrays#isEmpty(float[])} and {@link Arrays#isEmpty(boolean[])}.
     */
    @Test
    public void testIsEmptyPrimitives() {
        final long[] emptyLongArray = new long[] {};
        final long[] notEmptyLongArray = new long[] { 1L };
        assertTrue(Arrays.isEmpty((long[]) null));
        assertTrue(Arrays.isEmpty(emptyLongArray));
        assertFalse(Arrays.isEmpty(notEmptyLongArray));

        final int[] emptyIntArray = new int[] {};
        final int[] notEmptyIntArray = new int[] { 1 };
        assertTrue(Arrays.isEmpty((int[]) null));
        assertTrue(Arrays.isEmpty(emptyIntArray));
        assertFalse(Arrays.isEmpty(notEmptyIntArray));

        final short[] emptyShortArray = new short[] {};
        final short[] notEmptyShortArray = new short[] { 1 };
        assertTrue(Arrays.isEmpty((short[]) null));
        assertTrue(Arrays.isEmpty(emptyShortArray));
        assertFalse(Arrays.isEmpty(notEmptyShortArray));

        final char[] emptyCharArray = new char[] {};
        final char[] notEmptyCharArray = new char[] { 1 };
        assertTrue(Arrays.isEmpty((char[]) null));
        assertTrue(Arrays.isEmpty(emptyCharArray));
        assertFalse(Arrays.isEmpty(notEmptyCharArray));

        final byte[] emptyByteArray = new byte[] {};
        final byte[] notEmptyByteArray = new byte[] { 1 };
        assertTrue(Arrays.isEmpty((byte[]) null));
        assertTrue(Arrays.isEmpty(emptyByteArray));
        assertFalse(Arrays.isEmpty(notEmptyByteArray));

        final double[] emptyDoubleArray = new double[] {};
        final double[] notEmptyDoubleArray = new double[] { 1.0 };
        assertTrue(Arrays.isEmpty((double[]) null));
        assertTrue(Arrays.isEmpty(emptyDoubleArray));
        assertFalse(Arrays.isEmpty(notEmptyDoubleArray));

        final float[] emptyFloatArray = new float[] {};
        final float[] notEmptyFloatArray = new float[] { 1.0F };
        assertTrue(Arrays.isEmpty((float[]) null));
        assertTrue(Arrays.isEmpty(emptyFloatArray));
        assertFalse(Arrays.isEmpty(notEmptyFloatArray));

        final boolean[] emptyBooleanArray = new boolean[] {};
        final boolean[] notEmptyBooleanArray = new boolean[] { true };
        assertTrue(Arrays.isEmpty((boolean[]) null));
        assertTrue(Arrays.isEmpty(emptyBooleanArray));
        assertFalse(Arrays.isEmpty(notEmptyBooleanArray));
    }

    /**
     * Test for {@link Arrays#isNotEmpty(java.lang.Object[])}.
     */
    @Test
    public void testIsNotEmptyObject() {
        final Object[] emptyArray = new Object[] {};
        final Object[] notEmptyArray = new Object[] { new String("Value") };
        assertFalse(Arrays.isNotEmpty((Object[]) null));
        assertFalse(Arrays.isNotEmpty(emptyArray));
        assertTrue(Arrays.isNotEmpty(notEmptyArray));
    }

    /**
     * Tests for {@link Arrays#isNotEmpty(long[])}, {@link Arrays#isNotEmpty(int[])}, {@link Arrays#isNotEmpty(short[])}, {@link Arrays#isNotEmpty(char[])},
     * {@link Arrays#isNotEmpty(byte[])}, {@link Arrays#isNotEmpty(double[])}, {@link Arrays#isNotEmpty(float[])} and {@link Arrays#isNotEmpty(boolean[])}.
     */
    @Test
    public void testIsNotEmptyPrimitives() {
        final long[] emptyLongArray = new long[] {};
        final long[] notEmptyLongArray = new long[] { 1L };
        assertFalse(Arrays.isNotEmpty((long[]) null));
        assertFalse(Arrays.isNotEmpty(emptyLongArray));
        assertTrue(Arrays.isNotEmpty(notEmptyLongArray));

        final int[] emptyIntArray = new int[] {};
        final int[] notEmptyIntArray = new int[] { 1 };
        assertFalse(Arrays.isNotEmpty((int[]) null));
        assertFalse(Arrays.isNotEmpty(emptyIntArray));
        assertTrue(Arrays.isNotEmpty(notEmptyIntArray));

        final short[] emptyShortArray = new short[] {};
        final short[] notEmptyShortArray = new short[] { 1 };
        assertFalse(Arrays.isNotEmpty((short[]) null));
        assertFalse(Arrays.isNotEmpty(emptyShortArray));
        assertTrue(Arrays.isNotEmpty(notEmptyShortArray));

        final char[] emptyCharArray = new char[] {};
        final char[] notEmptyCharArray = new char[] { 1 };
        assertFalse(Arrays.isNotEmpty((char[]) null));
        assertFalse(Arrays.isNotEmpty(emptyCharArray));
        assertTrue(Arrays.isNotEmpty(notEmptyCharArray));

        final byte[] emptyByteArray = new byte[] {};
        final byte[] notEmptyByteArray = new byte[] { 1 };
        assertFalse(Arrays.isNotEmpty((byte[]) null));
        assertFalse(Arrays.isNotEmpty(emptyByteArray));
        assertTrue(Arrays.isNotEmpty(notEmptyByteArray));

        final double[] emptyDoubleArray = new double[] {};
        final double[] notEmptyDoubleArray = new double[] { 1.0 };
        assertFalse(Arrays.isNotEmpty((double[]) null));
        assertFalse(Arrays.isNotEmpty(emptyDoubleArray));
        assertTrue(Arrays.isNotEmpty(notEmptyDoubleArray));

        final float[] emptyFloatArray = new float[] {};
        final float[] notEmptyFloatArray = new float[] { 1.0F };
        assertFalse(Arrays.isNotEmpty((float[]) null));
        assertFalse(Arrays.isNotEmpty(emptyFloatArray));
        assertTrue(Arrays.isNotEmpty(notEmptyFloatArray));

        final boolean[] emptyBooleanArray = new boolean[] {};
        final boolean[] notEmptyBooleanArray = new boolean[] { true };
        assertFalse(Arrays.isNotEmpty((boolean[]) null));
        assertFalse(Arrays.isNotEmpty(emptyBooleanArray));
        assertTrue(Arrays.isNotEmpty(notEmptyBooleanArray));
    }

    // ------------------------------------------------------------------------
    @Test
    public void testGetLength() {
        assertEquals(0, Arrays.getLength(null));

        final Object[] emptyObjectArray = new Object[0];
        final Object[] notEmptyObjectArray = new Object[] { "aValue" };
        assertEquals(0, Arrays.getLength(null));
        assertEquals(0, Arrays.getLength(emptyObjectArray));
        assertEquals(1, Arrays.getLength(notEmptyObjectArray));

        final int[] emptyIntArray = new int[] {};
        final int[] notEmptyIntArray = new int[] { 1 };
        assertEquals(0, Arrays.getLength(null));
        assertEquals(0, Arrays.getLength(emptyIntArray));
        assertEquals(1, Arrays.getLength(notEmptyIntArray));

        final short[] emptyShortArray = new short[] {};
        final short[] notEmptyShortArray = new short[] { 1 };
        assertEquals(0, Arrays.getLength(null));
        assertEquals(0, Arrays.getLength(emptyShortArray));
        assertEquals(1, Arrays.getLength(notEmptyShortArray));

        final char[] emptyCharArray = new char[] {};
        final char[] notEmptyCharArray = new char[] { 1 };
        assertEquals(0, Arrays.getLength(null));
        assertEquals(0, Arrays.getLength(emptyCharArray));
        assertEquals(1, Arrays.getLength(notEmptyCharArray));

        final byte[] emptyByteArray = new byte[] {};
        final byte[] notEmptyByteArray = new byte[] { 1 };
        assertEquals(0, Arrays.getLength(null));
        assertEquals(0, Arrays.getLength(emptyByteArray));
        assertEquals(1, Arrays.getLength(notEmptyByteArray));

        final double[] emptyDoubleArray = new double[] {};
        final double[] notEmptyDoubleArray = new double[] { 1.0 };
        assertEquals(0, Arrays.getLength(null));
        assertEquals(0, Arrays.getLength(emptyDoubleArray));
        assertEquals(1, Arrays.getLength(notEmptyDoubleArray));

        final float[] emptyFloatArray = new float[] {};
        final float[] notEmptyFloatArray = new float[] { 1.0F };
        assertEquals(0, Arrays.getLength(null));
        assertEquals(0, Arrays.getLength(emptyFloatArray));
        assertEquals(1, Arrays.getLength(notEmptyFloatArray));

        final boolean[] emptyBooleanArray = new boolean[] {};
        final boolean[] notEmptyBooleanArray = new boolean[] { true };
        assertEquals(0, Arrays.getLength(null));
        assertEquals(0, Arrays.getLength(emptyBooleanArray));
        assertEquals(1, Arrays.getLength(notEmptyBooleanArray));

        try {
            Arrays.getLength("notAnArray");
            fail("IllegalArgumentException should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    @Test
    public void testIsSorted() {
        Integer[] array = null;

        array = new Integer[] { 1 };
        assertTrue(Arrays.isSorted(array));

        array = new Integer[] { 1, 2, 3 };
        assertTrue(Arrays.isSorted(array));

        array = new Integer[] { 1, 3, 2 };
        assertFalse(Arrays.isSorted(array));
    }

    @Test
    public void testIsSortedComparator() {
        final Comparator<Integer> c = new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                return o2.compareTo(o1);
            }
        };

        Integer[] array = null;

        array = new Integer[] { 1 };
        assertTrue(Arrays.isSorted(array, c));

        array = new Integer[] { 3, 2, 1 };
        assertTrue(Arrays.isSorted(array, c));

        array = new Integer[] { 1, 3, 2 };
        assertFalse(Arrays.isSorted(array, c));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsSortedNullComparator() throws Exception {
        Arrays.isSorted(null, null);
    }

    @Test
    public void testIsSortedInt() {
        int[] array = null;

        array = new int[] { 1 };
        assertTrue(Arrays.isSorted(array));

        array = new int[] { 1, 2, 3 };
        assertTrue(Arrays.isSorted(array));

        array = new int[] { 1, 3, 2 };
        assertFalse(Arrays.isSorted(array));
    }

    @Test
    public void testIsSortedFloat() {
        float[] array = null;

        array = new float[] { 0f };
        assertTrue(Arrays.isSorted(array));

        array = new float[] { -1f, 0f, 0.1f, 0.2f };
        assertTrue(Arrays.isSorted(array));

        array = new float[] { -1f, 0.2f, 0.1f, 0f };
        assertFalse(Arrays.isSorted(array));
    }

    @Test
    public void testIsSortedLong() {
        long[] array = null;

        array = new long[] { 0L };
        assertTrue(Arrays.isSorted(array));

        array = new long[] { -1L, 0L, 1L };
        assertTrue(Arrays.isSorted(array));

        array = new long[] { -1L, 1L, 0L };
        assertFalse(Arrays.isSorted(array));
    }

    @Test
    public void testIsSortedDouble() {
        double[] array = null;

        array = new double[] { 0.0 };
        assertTrue(Arrays.isSorted(array));

        array = new double[] { -1.0, 0.0, 0.1, 0.2 };
        assertTrue(Arrays.isSorted(array));

        array = new double[] { -1.0, 0.2, 0.1, 0.0 };
        assertFalse(Arrays.isSorted(array));
    }

    @Test
    public void testIsSortedChar() {
        char[] array = null;

        array = new char[] { 'a' };
        assertTrue(Arrays.isSorted(array));

        array = new char[] { 'a', 'b', 'c' };
        assertTrue(Arrays.isSorted(array));

        array = new char[] { 'a', 'c', 'b' };
        assertFalse(Arrays.isSorted(array));
    }

    @Test
    public void testIsSortedByte() {
        byte[] array = null;

        array = new byte[] { 0x10 };
        assertTrue(Arrays.isSorted(array));

        array = new byte[] { 0x10, 0x20, 0x30 };
        assertTrue(Arrays.isSorted(array));

        array = new byte[] { 0x10, 0x30, 0x20 };
        assertFalse(Arrays.isSorted(array));
    }

    @Test
    public void testIsSortedShort() {
        short[] array = null;

        array = new short[] { 0 };
        assertTrue(Arrays.isSorted(array));

        array = new short[] { -1, 0, 1 };
        assertTrue(Arrays.isSorted(array));

        array = new short[] { -1, 1, 0 };
        assertFalse(Arrays.isSorted(array));
    }

    @Test
    public void testIsSortedBool() {
        boolean[] array = null;

        array = new boolean[] { true };
        assertTrue(Arrays.isSorted(array));

        array = new boolean[] { false, true };
        assertTrue(Arrays.isSorted(array));

        array = new boolean[] { true, false };
        assertFalse(Arrays.isSorted(array));
    }

    @Test
    public void testToStringArray_array() {
        assertNull(Arrays.toStringArray(null));

        assertArrayEquals(new String[0], Arrays.toStringArray(new Object[0]));

        final Object[] array = new Object[] { 1, 2, 3, "array", "test" };
        assertArrayEquals(new String[] { "1", "2", "3", "array", "test" }, Arrays.toStringArray(array));
        Arrays.toStringArray(new Object[] { null });
    }

    @Test
    public void testToStringArray_array_string() {
        assertNull(Arrays.toStringArray(null, ""));

        assertArrayEquals(new String[0], Arrays.toStringArray(new Object[0], ""));

        final Object[] array = new Object[] { 1, null, "test" };
        assertArrayEquals(new String[] { "1", "valueForNullElements", "test" }, Arrays.toStringArray(array, "valueForNullElements"));
    }

    @Test
    public void testShuffle() {
        String[] array1 = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        String[] array2 = Objects.deepClone(array1);

        Arrays.shuffle(array1);
        Assert.assertFalse(Objects.deepEquals(array1, array2));
        for (String element : array2) {
            Assert.assertTrue("Element " + element + " not found", Arrays.contains(array1, element));
        }
    }

    @Test
    public void testShuffleBoolean() {
        boolean[] array1 = new boolean[] { true, false, true, true, false, false, true, false, false, true };
        boolean[] array2 = Objects.deepClone(array1);

        Arrays.shuffle(array1);
        Assert.assertFalse(Objects.deepEquals(array1, array2));
        Assert.assertEquals(5, Arrays.removeAllOccurences(array1, true).length);
    }

    @Test
    public void testShuffleByte() {
        byte[] array1 = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        byte[] array2 = Objects.deepClone(array1);

        Arrays.shuffle(array1);
        Assert.assertFalse(Objects.deepEquals(array1, array2));
        for (byte element : array2) {
            Assert.assertTrue("Element " + element + " not found", Arrays.contains(array1, element));
        }
    }

    @Test
    public void testShuffleChar() {
        char[] array1 = new char[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        char[] array2 = Objects.deepClone(array1);

        Arrays.shuffle(array1);
        Assert.assertFalse(Objects.deepEquals(array1, array2));
        for (char element : array2) {
            Assert.assertTrue("Element " + element + " not found", Arrays.contains(array1, element));
        }
    }

    @Test
    public void testShuffleShort() {
        short[] array1 = new short[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        short[] array2 = Objects.deepClone(array1);

        Arrays.shuffle(array1);
        Assert.assertFalse(Objects.deepEquals(array1, array2));
        for (short element : array2) {
            Assert.assertTrue("Element " + element + " not found", Arrays.contains(array1, element));
        }
    }

    @Test
    public void testShuffleInt() {
        int[] array1 = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int[] array2 = Objects.deepClone(array1);

        Arrays.shuffle(array1);
        Assert.assertFalse(Objects.deepEquals(array1, array2));
        for (int element : array2) {
            Assert.assertTrue("Element " + element + " not found", Arrays.contains(array1, element));
        }
    }

    @Test
    public void testShuffleLong() {
        long[] array1 = new long[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        long[] array2 = Objects.deepClone(array1);

        Arrays.shuffle(array1);
        Assert.assertFalse(Objects.deepEquals(array1, array2));
        for (long element : array2) {
            Assert.assertTrue("Element " + element + " not found", Arrays.contains(array1, element));
        }
    }

    @Test
    public void testShuffleFloat() {
        float[] array1 = new float[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        float[] array2 = Objects.deepClone(array1);

        Arrays.shuffle(array1);
        Assert.assertFalse(Objects.deepEquals(array1, array2));
        for (float element : array2) {
            Assert.assertTrue("Element " + element + " not found", Arrays.contains(array1, element));
        }
    }

    @Test
    public void testShuffleDouble() {
        double[] array1 = new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double[] array2 = Objects.deepClone(array1);

        Arrays.shuffle(array1);
        Assert.assertFalse(Objects.deepEquals(array1, array2));
        for (double element : array2) {
            Assert.assertTrue("Element " + element + " not found", Arrays.contains(array1, element));
        }
    }

}
