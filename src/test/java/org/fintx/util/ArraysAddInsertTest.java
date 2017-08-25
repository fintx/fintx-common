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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Array;
import java.util.UUID;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class ArraysAddInsertTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testJira567() {
        Number[] n;
        // Valid array construction
        n = Arrays.add(new Number[] { Integer.valueOf(1) }, new Long[] { Long.valueOf(2) });
        assertEquals(2, n.length);
        assertEquals(Number.class, n.getClass().getComponentType());
        try {
            // Invalid - can't store Long in Integer array
            n = Arrays.add(new Integer[] { Integer.valueOf(1) }, new Long[] { Long.valueOf(2) });
            fail("Should have generated IllegalArgumentException");
        } catch (final IllegalArgumentException expected) {
        }
    }

    @Test
    public void testAddObjectArrayBoolean() {
        boolean[] newArray;
        newArray = Arrays.add(new boolean[] { false }, null);
        assertTrue(Objects.deepEquals(new boolean[] { false }, newArray));
        assertEquals(Boolean.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(new boolean[] { true }, null);
        assertTrue(Objects.deepEquals(new boolean[] { true }, newArray));
        assertEquals(Boolean.TYPE, newArray.getClass().getComponentType());
        final boolean[] array1 = new boolean[] { true, false, true };
        newArray = Arrays.add(array1, false);
        assertTrue(Objects.deepEquals(new boolean[] { true, false, true, false }, newArray));
        assertEquals(Boolean.TYPE, newArray.getClass().getComponentType());
    }

    @Test
    public void testAddObjectArrayByte() {
        byte[] newArray;
        newArray = Arrays.add(new byte[] { (byte) 0 }, null);
        assertTrue(Objects.deepEquals(new byte[] { 0 }, newArray));
        assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(new byte[] { (byte) 1 }, null);
        assertTrue(Objects.deepEquals(new byte[] { 1 }, newArray));
        assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
        final byte[] array1 = new byte[] { 1, 2, 3 };
        newArray = Arrays.add(array1, (byte) 0);
        assertTrue(Objects.deepEquals(new byte[] { 1, 2, 3, 0 }, newArray));
        assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(array1, (byte) 4);
        assertTrue(Objects.deepEquals(new byte[] { 1, 2, 3, 4 }, newArray));
        assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
    }

    @Test
    public void testAddObjectArrayChar() {
        char[] newArray;
        newArray = Arrays.add(new char[] { (char) 0 }, null);
        assertTrue(Objects.deepEquals(new char[] { 0 }, newArray));
        assertEquals(Character.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(new char[] { (char) 1 }, null);
        assertTrue(Objects.deepEquals(new char[] { 1 }, newArray));
        assertEquals(Character.TYPE, newArray.getClass().getComponentType());
        final char[] array1 = new char[] { 1, 2, 3 };
        newArray = Arrays.add(array1, (char) 0);
        assertTrue(Objects.deepEquals(new char[] { 1, 2, 3, 0 }, newArray));
        assertEquals(Character.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(array1, (char) 4);
        assertTrue(Objects.deepEquals(new char[] { 1, 2, 3, 4 }, newArray));
        assertEquals(Character.TYPE, newArray.getClass().getComponentType());
    }

    @Test
    public void testAddObjectArrayDouble() {
        double[] newArray;
        newArray = Arrays.add(new double[] { 0 }, null);
        assertTrue(Objects.deepEquals(new double[] { 0 }, newArray));
        assertEquals(Double.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(new double[] { 1 }, null);
        assertTrue(Objects.deepEquals(new double[] { 1 }, newArray));
        assertEquals(Double.TYPE, newArray.getClass().getComponentType());
        final double[] array1 = new double[] { 1, 2, 3 };
        newArray = Arrays.add(array1, 0);
        assertTrue(Objects.deepEquals(new double[] { 1, 2, 3, 0 }, newArray));
        assertEquals(Double.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(array1, 4);
        assertTrue(Objects.deepEquals(new double[] { 1, 2, 3, 4 }, newArray));
        assertEquals(Double.TYPE, newArray.getClass().getComponentType());
    }

    @Test
    public void testAddObjectArrayFloat() {
        float[] newArray;
        newArray = Arrays.add((new float[] { 0 }));
        assertTrue(Objects.deepEquals(new float[] { 0 }, newArray));
        assertEquals(Float.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(new float[] { 1 });
        assertTrue(Objects.deepEquals(new float[] { 1 }, newArray));
        assertEquals(Float.TYPE, newArray.getClass().getComponentType());
        final float[] array1 = new float[] { 1, 2, 3 };
        newArray = Arrays.add(array1, 0);
        assertTrue(Objects.deepEquals(new float[] { 1, 2, 3, 0 }, newArray));
        assertEquals(Float.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(array1, 4);
        assertTrue(Objects.deepEquals(new float[] { 1, 2, 3, 4 }, newArray));
        assertEquals(Float.TYPE, newArray.getClass().getComponentType());
    }

    @Test
    public void testAddObjectArrayInt() {
        int[] newArray;
        int[] emptyArray = Arrays.add(new int[] {});
        newArray = Arrays.add(new int[] { 0 });
        assertTrue(Objects.deepEquals(new int[] { 0 }, newArray));
        assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(emptyArray, 1);
        assertTrue(Objects.deepEquals(new int[] { 1 }, newArray));
        assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
        final int[] array1 = new int[] { 1, 2, 3 };
        newArray = Arrays.add(array1, 0);
        assertTrue(Objects.deepEquals(new int[] { 1, 2, 3, 0 }, newArray));
        assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(array1, 4);
        assertTrue(Objects.deepEquals(new int[] { 1, 2, 3, 4 }, newArray));
        assertEquals(Integer.TYPE, newArray.getClass().getComponentType());

    }

    @Test
    public void testAddObjectArrayLong() {
        long[] newArray;
        newArray = Arrays.add(new long[] { 0 }, null);
        assertTrue(Objects.deepEquals(new long[] { 0 }, newArray));
        assertEquals(Long.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(new long[] { 1 }, null);
        assertTrue(Objects.deepEquals(new long[] { 1 }, newArray));
        assertEquals(Long.TYPE, newArray.getClass().getComponentType());
        final long[] array1 = new long[] { 1, 2, 3 };
        newArray = Arrays.add(array1, 0);
        assertTrue(Objects.deepEquals(new long[] { 1, 2, 3, 0 }, newArray));
        assertEquals(Long.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(array1, 4);
        assertTrue(Objects.deepEquals(new long[] { 1, 2, 3, 4 }, newArray));
        assertEquals(Long.TYPE, newArray.getClass().getComponentType());
    }

    @Test
    public void testAddObjectArrayShort() {
        short[] newArray;
        newArray = Arrays.add(new short[] { 0 });
        assertTrue(Objects.deepEquals(new short[] { 0 }, newArray));
        assertEquals(Short.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(new short[] { 1 });
        assertTrue(Objects.deepEquals(new short[] { 1 }, newArray));
        assertEquals(Short.TYPE, newArray.getClass().getComponentType());
        final short[] array1 = new short[] { 1, 2, 3 };
        newArray = Arrays.add(array1, (short) 0);
        assertTrue(Objects.deepEquals(new short[] { 1, 2, 3, 0 }, newArray));
        assertEquals(Short.TYPE, newArray.getClass().getComponentType());
        newArray = Arrays.add(array1, (short) 4);
        assertTrue(Objects.deepEquals(new short[] { 1, 2, 3, 4 }, newArray));
        assertEquals(Short.TYPE, newArray.getClass().getComponentType());
    }

    @Test
    public void testAddObjectArrayObject() {
        Object[] newArray;

        // show that not casting is okay
        newArray = Arrays.add(new Object[] { "a" });
        assertTrue(Objects.deepEquals(new String[] { "a" }, newArray));
        assertTrue(Objects.deepEquals(new Object[] { "a" }, newArray));
        /* !!! */
        // assertEquals(String.class, newArray.getClass().getComponentType());

        // show that not casting to Object[] is okay and will assume String based on "a"
        final String[] newStringArray = Arrays.add(new String[] { "a" });
        assertTrue(Objects.deepEquals(new String[] { "a" }, newStringArray));
        assertTrue(Objects.deepEquals(new Object[] { "a" }, newStringArray));
        assertEquals(String.class, newStringArray.getClass().getComponentType());

        final String[] stringArray1 = new String[] { "a", "b", "c" };
        newArray = Arrays.add(stringArray1, new String[] {null});
        assertTrue(Objects.deepEquals(new String[] { "a", "b", "c", null }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());

        newArray = Arrays.add(stringArray1, "d");
        assertTrue(Objects.deepEquals(new String[] { "a", "b", "c", "d" }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());

        Number[] numberArray1 = new Number[] { Integer.valueOf(1), Double.valueOf(2) };
        newArray = Arrays.add(numberArray1, Float.valueOf(3));
        assertTrue(Objects.deepEquals(new Number[] { Integer.valueOf(1), Double.valueOf(2), Float.valueOf(3) }, newArray));
        assertEquals(Number.class, newArray.getClass().getComponentType());

        numberArray1 = null;
        newArray = Arrays.add(new Number[] {Float.valueOf(3)},numberArray1 );
        assertTrue(Objects.deepEquals(new Float[] { Float.valueOf(3) }, newArray));
        /*!!!*/
        //assertEquals(Float.class, newArray.getClass().getComponentType());
    }

    @Test
    public void testLANG571() {
        final String[] stringArray = null;
        final String aString = null;
        try {
            @SuppressWarnings("unused")
            final String[] sa = Arrays.add(stringArray, aString);
            fail("Should have caused IllegalArgumentException");
        } catch (final NullPointerException iae) {
            // expected
        }
        try {
            @SuppressWarnings({ "unused", "deprecation" })
            final String[] sa = Arrays.insert(stringArray, 0, aString);
            fail("Should have caused IllegalArgumentException");
        } catch (final NullPointerException iae) {
            // expected
        }
    }

    @Test
    public void testAddObjectArrayToObjectArray() {

        Object[] newArray;
        final String[] stringArray1 = new String[] { "a", "b", "c" };
        final String[] stringArray2 = new String[] { "1", "2", "3" };
        newArray = Arrays.add(stringArray1, (String[]) null);
        assertNotSame(stringArray1, newArray);
        assertTrue(Objects.deepEquals(stringArray1, newArray));
        assertTrue(Objects.deepEquals(new String[] { "a", "b", "c" }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        newArray = Arrays.add(stringArray2, null);
        assertNotSame(stringArray2, newArray);
        assertTrue(Objects.deepEquals(stringArray2, newArray));
        assertTrue(Objects.deepEquals(new String[] { "1", "2", "3" }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        newArray = Arrays.add(stringArray1, stringArray2);
        assertTrue(Objects.deepEquals(new String[] { "a", "b", "c", "1", "2", "3" }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        newArray = Arrays.add(Arrays.EMPTY_STRING_ARRAY, (String[]) null);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_STRING_ARRAY, newArray));
        assertTrue(Objects.deepEquals(new String[] {}, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        newArray = Arrays.add(Arrays.EMPTY_STRING_ARRAY, null);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_STRING_ARRAY, newArray));
        assertTrue(Objects.deepEquals(new String[] {}, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        newArray = Arrays.add(Arrays.EMPTY_STRING_ARRAY, Arrays.EMPTY_STRING_ARRAY);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_STRING_ARRAY, newArray));
        assertTrue(Objects.deepEquals(new String[] {}, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        final String[] stringArrayNull = new String[] { null };
        newArray = Arrays.add(stringArrayNull, stringArrayNull);
        assertTrue(Objects.deepEquals(new String[] { null, null }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());

        // boolean
        assertTrue(Objects.deepEquals(new boolean[] { true, false, false, true }, Arrays.add(new boolean[] { true, false }, false, true)));

        assertTrue(Objects.deepEquals(new boolean[] { false, true }, Arrays.add(new boolean[] { false, true }, null)));

        // char
        assertTrue(Objects.deepEquals(new char[] { 'a', 'b', 'c', 'd' }, Arrays.add(new char[] { 'a', 'b' }, 'c', 'd')));

        assertTrue(Objects.deepEquals(new char[] { 'a', 'b' }, Arrays.add(new char[] { 'a', 'b' }, null)));

        // byte
        assertTrue(
                Objects.deepEquals(new byte[] { (byte) 0, (byte) 1, (byte) 2, (byte) 3 }, Arrays.add(new byte[] { (byte) 0, (byte) 1 }, (byte) 2, (byte) 3)));

        assertTrue(Objects.deepEquals(new byte[] { (byte) 0, (byte) 1 }, Arrays.add(new byte[] { (byte) 0, (byte) 1 }, null)));

        // short
        assertTrue(Objects.deepEquals(new short[] { (short) 10, (short) 20, (short) 30, (short) 40 },
                Arrays.add(new short[] { (short) 10, (short) 20 }, (short) 30, (short) 40)));

        assertTrue(Objects.deepEquals(new short[] { (short) 10, (short) 20 }, Arrays.add(new short[] { (short) 10, (short) 20 }, null)));

        // int
        assertTrue(Objects.deepEquals(new int[] { 1, 1000, -1000, -1 }, Arrays.add(new int[] { 1, 1000 }, -1000, -1)));

        assertTrue(Objects.deepEquals(new int[] { 1, 1000 }, Arrays.add(new int[] { 1, 1000 }, null)));

        // long
        assertTrue(Objects.deepEquals(new long[] { 1L, -1L, 1000L, -1000L }, Arrays.add(new long[] { 1L, -1L }, 1000L, -1000L)));

        assertTrue(Objects.deepEquals(new long[] { 1L, -1L }, Arrays.add(new long[] { 1L, -1L }, null)));

        // float
        assertTrue(Objects.deepEquals(new float[] { 10.5f, 10.1f, 1.6f, 0.01f }, Arrays.add(new float[] { 10.5f, 10.1f }, 1.6f, 0.01f)));

        assertTrue(Objects.deepEquals(new float[] { 10.5f, 10.1f }, Arrays.add(new float[] { 10.5f, 10.1f }, null)));

        // double
        assertTrue(Objects.deepEquals(new double[] { Math.PI, -Math.PI, 0, 9.99 }, Arrays.add(new double[] { Math.PI, -Math.PI }, 0, 9.99)));

        assertTrue(Objects.deepEquals(new double[] { Math.PI, -Math.PI }, Arrays.add(new double[] { Math.PI, -Math.PI }, null)));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInsert() {
        Object[] newArray;
        newArray = Arrays.insert(new Object[] { "a" }, 0, null);
        assertTrue(Objects.deepEquals(new String[] { "a" }, newArray));
        assertTrue(Objects.deepEquals(new Object[] { "a" }, newArray));
        /* !!! */
        // assertEquals(String.class, newArray.getClass().getComponentType());

        final String[] stringArray1 = new String[] { "a", "b", "c" };
        /* !!! */
        // newArray = Arrays.insert(stringArray1, 0, null);
        newArray = Arrays.insert(stringArray1, 0, new String[] { null });
        assertTrue(Objects.deepEquals(new String[] { null, "a", "b", "c" }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        /* !!! */
        newArray = Arrays.insert(stringArray1, 1, null);
        newArray = Arrays.insert(stringArray1, 1, new String[] { null });
        assertTrue(Objects.deepEquals(new String[] { "a", null, "b", "c" }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        /* !!! */
        //newArray = Arrays.insert(stringArray1, 3, null);
        newArray = Arrays.insert(stringArray1, 3,  new String[] { null });
        assertTrue(Objects.deepEquals(new String[] { "a", "b", "c", null }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        newArray = Arrays.insert(stringArray1, 3, "d");
        assertTrue(Objects.deepEquals(new String[] { "a", "b", "c", "d" }, newArray));
        assertEquals(String.class, newArray.getClass().getComponentType());
        assertEquals(String.class, newArray.getClass().getComponentType());

        final Object[] o = new Object[] { "1", "2", "4" };
        final Object[] result = Arrays.insert(o, 2, "3");
        final Object[] result2 = Arrays.insert(o, 3, "5");

        assertNotNull(result);
        assertEquals(4, result.length);
        assertEquals("1", result[0]);
        assertEquals("2", result[1]);
        assertEquals("3", result[2]);
        assertEquals("4", result[3]);
        assertNotNull(result2);
        assertEquals(4, result2.length);
        assertEquals("1", result2[0]);
        assertEquals("2", result2[1]);
        assertEquals("4", result2[2]);
        assertEquals("5", result2[3]);

        // boolean tests
        boolean[] booleanArray = Arrays.insert(new boolean[] { true }, 0, null);
        assertTrue(Objects.deepEquals(new boolean[] { true }, booleanArray));
        try {
            booleanArray = Arrays.insert(new boolean[0], -1, true);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 0", e.getMessage());
        }
        booleanArray = Arrays.insert(new boolean[] { true }, 0, false);
        assertTrue(Objects.deepEquals(new boolean[] { false, true }, booleanArray));
        booleanArray = Arrays.insert(new boolean[] { false }, 1, true);
        assertTrue(Objects.deepEquals(new boolean[] { false, true }, booleanArray));
        booleanArray = Arrays.insert(new boolean[] { true, false }, 1, true);
        assertTrue(Objects.deepEquals(new boolean[] { true, true, false }, booleanArray));
        try {
            booleanArray = Arrays.insert(new boolean[] { true, false }, 4, true);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: 4, Length: 2", e.getMessage());
        }
        try {
            booleanArray = Arrays.insert(new boolean[] { true, false }, -1, true);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 2", e.getMessage());
        }

        // char tests
        char[] charArray = Arrays.insert(new char[] { 'a' }, 0, null);
        assertTrue(Objects.deepEquals(new char[] { 'a' }, charArray));
        try {
            charArray = Arrays.insert(new char[0] , -1, 'a');
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 0", e.getMessage());
        }
        charArray = Arrays.insert(new char[] { 'a' }, 0, 'b');
        assertTrue(Objects.deepEquals(new char[] { 'b', 'a' }, charArray));
        charArray = Arrays.insert(new char[] { 'a', 'b' }, 0, 'c');
        assertTrue(Objects.deepEquals(new char[] { 'c', 'a', 'b' }, charArray));
        charArray = Arrays.insert(new char[] { 'a', 'b' }, 1, 'k');
        assertTrue(Objects.deepEquals(new char[] { 'a', 'k', 'b' }, charArray));
        charArray = Arrays.insert(new char[] { 'a', 'b', 'c' }, 1, 't');
        assertTrue(Objects.deepEquals(new char[] { 'a', 't', 'b', 'c' }, charArray));
        try {
            charArray = Arrays.insert(new char[] { 'a', 'b' }, 4, 'c');
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: 4, Length: 2", e.getMessage());
        }
        try {
            charArray = Arrays.insert(new char[] { 'a', 'b' }, -1, 'c');
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 2", e.getMessage());
        }

        // short tests
        short[] shortArray = Arrays.insert(new short[] { 1 }, 0, (short) 2);
        assertTrue(Objects.deepEquals(new short[] { 2, 1 }, shortArray));
        try {
            shortArray = Arrays.insert(new short[0], -1, (short) 2);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 0", e.getMessage());
        }
        shortArray = Arrays.insert(new short[] { 2, 6 }, 2, (short) 10);
        assertTrue(Objects.deepEquals(new short[] { 2, 6, 10 }, shortArray));
        shortArray = Arrays.insert(new short[] { 2, 6 }, 0, (short) -4);
        assertTrue(Objects.deepEquals(new short[] { -4, 2, 6 }, shortArray));
        shortArray = Arrays.insert(new short[] { 2, 6, 3 }, 2, (short) 1);
        assertTrue(Objects.deepEquals(new short[] { 2, 6, 1, 3 }, shortArray));
        try {
            shortArray = Arrays.insert(new short[] { 2, 6 }, 4, (short) 10);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: 4, Length: 2", e.getMessage());
        }
        try {
            shortArray = Arrays.insert(new short[] { 2, 6 }, -1, (short) 10);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 2", e.getMessage());
        }

        // byte tests
        byte[] byteArray = Arrays.insert(new byte[] { 1 }, 0, (byte) 2);
        assertTrue(Objects.deepEquals(new byte[] { 2, 1 }, byteArray));
        try {
            byteArray = Arrays.insert(new byte[0], -1, (byte) 2);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 0", e.getMessage());
        }
        byteArray = Arrays.insert(new byte[] { 2, 6 }, 2, (byte) 3);
        assertTrue(Objects.deepEquals(new byte[] { 2, 6, 3 }, byteArray));
        byteArray = Arrays.insert(new byte[] { 2, 6 }, 0, (byte) 1);
        assertTrue(Objects.deepEquals(new byte[] { 1, 2, 6 }, byteArray));
        byteArray = Arrays.insert(new byte[] { 2, 6, 3 }, 2, (byte) 1);
        assertTrue(Objects.deepEquals(new byte[] { 2, 6, 1, 3 }, byteArray));
        try {
            byteArray = Arrays.insert(new byte[] { 2, 6 }, 4, (byte) 3);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: 4, Length: 2", e.getMessage());
        }
        try {
            byteArray = Arrays.insert(new byte[] { 2, 6 }, -1, (byte) 3);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 2", e.getMessage());
        }

        // int tests
        int[] intArray = Arrays.insert(new int[] { 1 }, 0, 2);
        assertTrue(Objects.deepEquals(new int[] { 2, 1 }, intArray));
        try {
            intArray = Arrays.insert(new int[0], -1, 2);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 0", e.getMessage());
        }
        intArray = Arrays.insert(new int[] { 2, 6 }, 2, 10);
        assertTrue(Objects.deepEquals(new int[] { 2, 6, 10 }, intArray));
        intArray = Arrays.insert(new int[] { 2, 6 }, 0, -4);
        assertTrue(Objects.deepEquals(new int[] { -4, 2, 6 }, intArray));
        intArray = Arrays.insert(new int[] { 2, 6, 3 }, 2, 1);
        assertTrue(Objects.deepEquals(new int[] { 2, 6, 1, 3 }, intArray));
        try {
            intArray = Arrays.insert(new int[] { 2, 6 }, 4, 10);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: 4, Length: 2", e.getMessage());
        }
        try {
            intArray = Arrays.insert(new int[] { 2, 6 }, -1, 10);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 2", e.getMessage());
        }

        // long tests
        long[] longArray = Arrays.insert(new long[] { 1L }, 0, 2L);
        assertTrue(Objects.deepEquals(new long[] { 2L, 1L }, longArray));
        try {
            longArray = Arrays.insert(new long[0], -1, 2L);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 0", e.getMessage());
        }
        longArray = Arrays.insert(new long[] { 2L, 6L }, 2, 10L);
        assertTrue(Objects.deepEquals(new long[] { 2L, 6L, 10L }, longArray));
        longArray = Arrays.insert(new long[] { 2L, 6L }, 0, -4L);
        assertTrue(Objects.deepEquals(new long[] { -4L, 2L, 6L }, longArray));
        longArray = Arrays.insert(new long[] { 2L, 6L, 3L }, 2, 1L);
        assertTrue(Objects.deepEquals(new long[] { 2L, 6L, 1L, 3L }, longArray));
        try {
            longArray = Arrays.insert(new long[] { 2L, 6L }, 4, 10L);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: 4, Length: 2", e.getMessage());
        }
        try {
            longArray = Arrays.insert(new long[] { 2L, 6L }, -1, 10L);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 2", e.getMessage());
        }

        // float tests
        float[] floatArray = Arrays.insert(new float[] { 1.1f }, 0, 2.2f);
        assertTrue(Objects.deepEquals(new float[] { 2.2f, 1.1f }, floatArray));
        try {
            floatArray = Arrays.insert(new float[0], -1, 2.2f);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 0", e.getMessage());
        }
        floatArray = Arrays.insert(new float[] { 2.3f, 6.4f }, 2, 10.5f);
        assertTrue(Objects.deepEquals(new float[] { 2.3f, 6.4f, 10.5f }, floatArray));
        floatArray = Arrays.insert(new float[] { 2.6f, 6.7f }, 0, -4.8f);
        assertTrue(Objects.deepEquals(new float[] { -4.8f, 2.6f, 6.7f }, floatArray));
        floatArray = Arrays.insert(new float[] { 2.9f, 6.0f, 0.3f }, 2, 1.0f);
        assertTrue(Objects.deepEquals(new float[] { 2.9f, 6.0f, 1.0f, 0.3f }, floatArray));
        try {
            floatArray = Arrays.insert(new float[] { 2.3f, 6.4f }, 4, 10.5f);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: 4, Length: 2", e.getMessage());
        }
        try {
            floatArray = Arrays.insert(new float[] { 2.3f, 6.4f }, -1, 10.5f);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 2", e.getMessage());
        }

        // double tests
        double[] doubleArray = Arrays.insert(new double[] { 1.1 }, 0, 2.2);
        assertTrue(Objects.deepEquals(new double[] { 2.2, 1.1 }, doubleArray));
        try {
            doubleArray = Arrays.insert(new double[0], -1, 2.2);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 0", e.getMessage());
        }
        doubleArray = Arrays.insert(new double[] { 2.3, 6.4 }, 2, 10.5);
        assertTrue(Objects.deepEquals(new double[] { 2.3, 6.4, 10.5 }, doubleArray));
        doubleArray = Arrays.insert(new double[] { 2.6, 6.7 }, 0, -4.8);
        assertTrue(Objects.deepEquals(new double[] { -4.8, 2.6, 6.7 }, doubleArray));
        doubleArray = Arrays.insert(new double[] { 2.9, 6.0, 0.3 }, 2, 1.0);
        assertTrue(Objects.deepEquals(new double[] { 2.9, 6.0, 1.0, 0.3 }, doubleArray));
        try {
            doubleArray = Arrays.insert(new double[] { 2.3, 6.4 }, 4, 10.5);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: 4, Length: 2", e.getMessage());
        }
        try {
            doubleArray = Arrays.insert(new double[] { 2.3, 6.4 }, -1, 10.5);
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("Index: -1, Length: 2", e.getMessage());
        }
    }

    @Test
    public void testException1() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Argument array1 should not be both null!");
        Arrays.add(null);
        Arrays.add((int[]) null, 1);

    }

    @Test
    public void testException2() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Argument should not be null!");
        UniqueId.fromHexString(null);

    }

    @Test
    public void testException3() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Argument should not be null!");
        UniqueId.fromByteArray(null);

    }

    @Test
    public void testException4() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid hexadecimal representation of an UniqueId");
        UniqueId.fromBase64String(UUID.randomUUID().toString());

    }

    @Test
    public void testException5() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid hexadecimal representation of an UniqueId");
        UniqueId.fromHexString(UUID.randomUUID().toString());

    }

    @Test
    public void testException6() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Argument need 15 bytes");
        UniqueId.fromByteArray(UUID.randomUUID().toString().getBytes());

    }

    @Test
    public void testException7() {
        thrown.expect(NullPointerException.class);
        UniqueId.get().compareTo(null);
    }

}
