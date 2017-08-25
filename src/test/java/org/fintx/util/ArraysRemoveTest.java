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

import org.junit.Test;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class ArraysRemoveTest {

    @Test
    public void testRemoveObjectArray() {
        Object[] array;
        array = Arrays.remove(new Object[] { "a" }, 0);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_OBJECT_ARRAY, array));
        assertEquals(Object.class, array.getClass().getComponentType());
        array = Arrays.remove(new Object[] { "a", "b" }, 0);
        assertTrue(Objects.deepEquals(new Object[] { "b" }, array));
        assertEquals(Object.class, array.getClass().getComponentType());
        array = Arrays.remove(new Object[] { "a", "b" }, 1);
        assertTrue(Objects.deepEquals(new Object[] { "a" }, array));
        assertEquals(Object.class, array.getClass().getComponentType());
        array = Arrays.remove(new Object[] { "a", "b", "c" }, 1);
        assertTrue(Objects.deepEquals(new Object[] { "a", "c" }, array));
        assertEquals(Object.class, array.getClass().getComponentType());
        try {
            Arrays.remove(new Object[] { "a", "b" }, -1);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove(new Object[] { "a", "b" }, 2);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove((Object[]) null, 0);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemoveNumberArray() {
        final Number[] inarray = { Integer.valueOf(1), Long.valueOf(2), Byte.valueOf((byte) 3) };
        assertEquals(3, inarray.length);
        Number[] outarray;
        outarray = Arrays.remove(inarray, 1);
        assertEquals(2, outarray.length);
        assertEquals(Number.class, outarray.getClass().getComponentType());
        outarray = Arrays.remove(outarray, 1);
        assertEquals(1, outarray.length);
        assertEquals(Number.class, outarray.getClass().getComponentType());
        outarray = Arrays.remove(outarray, 0);
        assertEquals(0, outarray.length);
        assertEquals(Number.class, outarray.getClass().getComponentType());
    }

    @Test
    public void testRemoveBooleanArray() {
        boolean[] array;
        array = Arrays.remove(new boolean[] { true }, 0);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BOOLEAN_ARRAY, array));
        assertEquals(Boolean.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new boolean[] { true, false }, 0);
        assertTrue(Objects.deepEquals(new boolean[] { false }, array));
        assertEquals(Boolean.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new boolean[] { true, false }, 1);
        assertTrue(Objects.deepEquals(new boolean[] { true }, array));
        assertEquals(Boolean.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new boolean[] { true, false, true }, 1);
        assertTrue(Objects.deepEquals(new boolean[] { true, true }, array));
        assertEquals(Boolean.TYPE, array.getClass().getComponentType());
        try {
            Arrays.remove(new boolean[] { true, false }, -1);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove(new boolean[] { true, false }, 2);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove((boolean[]) null, 0);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemoveByteArray() {
        byte[] array;
        array = Arrays.remove(new byte[] { 1 }, 0);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BYTE_ARRAY, array));
        assertEquals(Byte.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new byte[] { 1, 2 }, 0);
        assertTrue(Objects.deepEquals(new byte[] { 2 }, array));
        assertEquals(Byte.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new byte[] { 1, 2 }, 1);
        assertTrue(Objects.deepEquals(new byte[] { 1 }, array));
        assertEquals(Byte.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new byte[] { 1, 2, 1 }, 1);
        assertTrue(Objects.deepEquals(new byte[] { 1, 1 }, array));
        assertEquals(Byte.TYPE, array.getClass().getComponentType());
        try {
            Arrays.remove(new byte[] { 1, 2 }, -1);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove(new byte[] { 1, 2 }, 2);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove((byte[]) null, 0);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemoveCharArray() {
        char[] array;
        array = Arrays.remove(new char[] { 'a' }, 0);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_CHAR_ARRAY, array));
        assertEquals(Character.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new char[] { 'a', 'b' }, 0);
        assertTrue(Objects.deepEquals(new char[] { 'b' }, array));
        assertEquals(Character.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new char[] { 'a', 'b' }, 1);
        assertTrue(Objects.deepEquals(new char[] { 'a' }, array));
        assertEquals(Character.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new char[] { 'a', 'b', 'c' }, 1);
        assertTrue(Objects.deepEquals(new char[] { 'a', 'c' }, array));
        assertEquals(Character.TYPE, array.getClass().getComponentType());
        try {
            Arrays.remove(new char[] { 'a', 'b' }, -1);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove(new char[] { 'a', 'b' }, 2);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove((char[]) null, 0);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemoveDoubleArray() {
        double[] array;
        array = Arrays.remove(new double[] { 1 }, 0);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_DOUBLE_ARRAY, array));
        assertEquals(Double.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new double[] { 1, 2 }, 0);
        assertTrue(Objects.deepEquals(new double[] { 2 }, array));
        assertEquals(Double.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new double[] { 1, 2 }, 1);
        assertTrue(Objects.deepEquals(new double[] { 1 }, array));
        assertEquals(Double.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new double[] { 1, 2, 1 }, 1);
        assertTrue(Objects.deepEquals(new double[] { 1, 1 }, array));
        assertEquals(Double.TYPE, array.getClass().getComponentType());
        try {
            Arrays.remove(new double[] { 1, 2 }, -1);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove(new double[] { 1, 2 }, 2);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove((double[]) null, 0);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemoveFloatArray() {
        float[] array;
        array = Arrays.remove(new float[] { 1 }, 0);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_FLOAT_ARRAY, array));
        assertEquals(Float.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new float[] { 1, 2 }, 0);
        assertTrue(Objects.deepEquals(new float[] { 2 }, array));
        assertEquals(Float.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new float[] { 1, 2 }, 1);
        assertTrue(Objects.deepEquals(new float[] { 1 }, array));
        assertEquals(Float.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new float[] { 1, 2, 1 }, 1);
        assertTrue(Objects.deepEquals(new float[] { 1, 1 }, array));
        assertEquals(Float.TYPE, array.getClass().getComponentType());
        try {
            Arrays.remove(new float[] { 1, 2 }, -1);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove(new float[] { 1, 2 }, 2);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove((float[]) null, 0);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemoveIntArray() {
        int[] array;
        array = Arrays.remove(new int[] { 1 }, 0);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_INT_ARRAY, array));
        assertEquals(Integer.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new int[] { 1, 2 }, 0);
        assertTrue(Objects.deepEquals(new int[] { 2 }, array));
        assertEquals(Integer.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new int[] { 1, 2 }, 1);
        assertTrue(Objects.deepEquals(new int[] { 1 }, array));
        assertEquals(Integer.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new int[] { 1, 2, 1 }, 1);
        assertTrue(Objects.deepEquals(new int[] { 1, 1 }, array));
        assertEquals(Integer.TYPE, array.getClass().getComponentType());
        try {
            Arrays.remove(new int[] { 1, 2 }, -1);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove(new int[] { 1, 2 }, 2);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove((int[]) null, 0);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemoveLongArray() {
        long[] array;
        array = Arrays.remove(new long[] { 1 }, 0);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_LONG_ARRAY, array));
        assertEquals(Long.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new long[] { 1, 2 }, 0);
        assertTrue(Objects.deepEquals(new long[] { 2 }, array));
        assertEquals(Long.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new long[] { 1, 2 }, 1);
        assertTrue(Objects.deepEquals(new long[] { 1 }, array));
        assertEquals(Long.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new long[] { 1, 2, 1 }, 1);
        assertTrue(Objects.deepEquals(new long[] { 1, 1 }, array));
        assertEquals(Long.TYPE, array.getClass().getComponentType());
        try {
            Arrays.remove(new long[] { 1, 2 }, -1);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove(new long[] { 1, 2 }, 2);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove((long[]) null, 0);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemoveShortArray() {
        short[] array;
        array = Arrays.remove(new short[] { 1 }, 0);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_SHORT_ARRAY, array));
        assertEquals(Short.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new short[] { 1, 2 }, 0);
        assertTrue(Objects.deepEquals(new short[] { 2 }, array));
        assertEquals(Short.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new short[] { 1, 2 }, 1);
        assertTrue(Objects.deepEquals(new short[] { 1 }, array));
        assertEquals(Short.TYPE, array.getClass().getComponentType());
        array = Arrays.remove(new short[] { 1, 2, 1 }, 1);
        assertTrue(Objects.deepEquals(new short[] { 1, 1 }, array));
        assertEquals(Short.TYPE, array.getClass().getComponentType());
        try {
            Arrays.remove(new short[] { 1, 2 }, -1);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove(new short[] { 1, 2 }, 2);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
        try {
            Arrays.remove((short[]) null, 0);
            fail("IndexOutOfBoundsException expected");
        } catch (final IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemoveElementObjectArray() {
        Object[] array;
        array = Arrays.removeElement(null, "a");
        assertNull(array);
        array = Arrays.removeElement(Arrays.EMPTY_OBJECT_ARRAY, "a");
        assertTrue(Objects.deepEquals(Arrays.EMPTY_OBJECT_ARRAY, array));
        assertEquals(Object.class, array.getClass().getComponentType());
        array = Arrays.removeElement(new Object[] { "a" }, "a");
        assertTrue(Objects.deepEquals(Arrays.EMPTY_OBJECT_ARRAY, array));
        assertEquals(Object.class, array.getClass().getComponentType());
        array = Arrays.removeElement(new Object[] { "a", "b" }, "a");
        assertTrue(Objects.deepEquals(new Object[] { "b" }, array));
        assertEquals(Object.class, array.getClass().getComponentType());
        array = Arrays.removeElement(new Object[] { "a", "b", "a" }, "a");
        assertTrue(Objects.deepEquals(new Object[] { "b", "a" }, array));
        assertEquals(Object.class, array.getClass().getComponentType());
    }

    @Test
    public void testRemoveElementBooleanArray() {
        boolean[] array;
        array = Arrays.removeElement((boolean[]) null, true);
        assertNull(array);
        array = Arrays.removeElement(Arrays.EMPTY_BOOLEAN_ARRAY, true);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BOOLEAN_ARRAY, array));
        assertEquals(Boolean.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new boolean[] { true }, true);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BOOLEAN_ARRAY, array));
        assertEquals(Boolean.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new boolean[] { true, false }, true);
        assertTrue(Objects.deepEquals(new boolean[] { false }, array));
        assertEquals(Boolean.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new boolean[] { true, false, true }, true);
        assertTrue(Objects.deepEquals(new boolean[] { false, true }, array));
        assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    }

    @Test
    public void testRemoveElementByteArray() {
        byte[] array;
        array = Arrays.removeElement((byte[]) null, (byte) 1);
        assertNull(array);
        array = Arrays.removeElement(Arrays.EMPTY_BYTE_ARRAY, (byte) 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BYTE_ARRAY, array));
        assertEquals(Byte.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new byte[] { 1 }, (byte) 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BYTE_ARRAY, array));
        assertEquals(Byte.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new byte[] { 1, 2 }, (byte) 1);
        assertTrue(Objects.deepEquals(new byte[] { 2 }, array));
        assertEquals(Byte.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new byte[] { 1, 2, 1 }, (byte) 1);
        assertTrue(Objects.deepEquals(new byte[] { 2, 1 }, array));
        assertEquals(Byte.TYPE, array.getClass().getComponentType());
    }

    @Test
    public void testRemoveElementCharArray() {
        char[] array;
        array = Arrays.removeElement((char[]) null, 'a');
        assertNull(array);
        array = Arrays.removeElement(Arrays.EMPTY_CHAR_ARRAY, 'a');
        assertTrue(Objects.deepEquals(Arrays.EMPTY_CHAR_ARRAY, array));
        assertEquals(Character.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new char[] { 'a' }, 'a');
        assertTrue(Objects.deepEquals(Arrays.EMPTY_CHAR_ARRAY, array));
        assertEquals(Character.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new char[] { 'a', 'b' }, 'a');
        assertTrue(Objects.deepEquals(new char[] { 'b' }, array));
        assertEquals(Character.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new char[] { 'a', 'b', 'a' }, 'a');
        assertTrue(Objects.deepEquals(new char[] { 'b', 'a' }, array));
        assertEquals(Character.TYPE, array.getClass().getComponentType());
    }

    @Test
    @SuppressWarnings("cast")
    public void testRemoveElementDoubleArray() {
        double[] array;
        array = Arrays.removeElement((double[]) null, (double) 1);
        assertNull(array);
        array = Arrays.removeElement(Arrays.EMPTY_DOUBLE_ARRAY, (double) 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_DOUBLE_ARRAY, array));
        assertEquals(Double.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new double[] { 1 }, (double) 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_DOUBLE_ARRAY, array));
        assertEquals(Double.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new double[] { 1, 2 }, (double) 1);
        assertTrue(Objects.deepEquals(new double[] { 2 }, array));
        assertEquals(Double.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new double[] { 1, 2, 1 }, (double) 1);
        assertTrue(Objects.deepEquals(new double[] { 2, 1 }, array));
        assertEquals(Double.TYPE, array.getClass().getComponentType());
    }

    @Test
    @SuppressWarnings("cast")
    public void testRemoveElementFloatArray() {
        float[] array;
        array = Arrays.removeElement((float[]) null, (float) 1);
        assertNull(array);
        array = Arrays.removeElement(Arrays.EMPTY_FLOAT_ARRAY, (float) 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_FLOAT_ARRAY, array));
        assertEquals(Float.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new float[] { 1 }, (float) 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_FLOAT_ARRAY, array));
        assertEquals(Float.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new float[] { 1, 2 }, (float) 1);
        assertTrue(Objects.deepEquals(new float[] { 2 }, array));
        assertEquals(Float.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new float[] { 1, 2, 1 }, (float) 1);
        assertTrue(Objects.deepEquals(new float[] { 2, 1 }, array));
        assertEquals(Float.TYPE, array.getClass().getComponentType());
    }

    @Test
    public void testRemoveElementIntArray() {
        int[] array;
        array = Arrays.removeElement((int[]) null, 1);
        assertNull(array);
        array = Arrays.removeElement(Arrays.EMPTY_INT_ARRAY, 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_INT_ARRAY, array));
        assertEquals(Integer.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new int[] { 1 }, 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_INT_ARRAY, array));
        assertEquals(Integer.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new int[] { 1, 2 }, 1);
        assertTrue(Objects.deepEquals(new int[] { 2 }, array));
        assertEquals(Integer.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new int[] { 1, 2, 1 }, 1);
        assertTrue(Objects.deepEquals(new int[] { 2, 1 }, array));
        assertEquals(Integer.TYPE, array.getClass().getComponentType());
    }

    @Test
    @SuppressWarnings("cast")
    public void testRemoveElementLongArray() {
        long[] array;
        array = Arrays.removeElement((long[]) null, 1L);
        assertNull(array);
        array = Arrays.removeElement(Arrays.EMPTY_LONG_ARRAY, 1L);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_LONG_ARRAY, array));
        assertEquals(Long.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new long[] { 1 }, 1L);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_LONG_ARRAY, array));
        assertEquals(Long.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new long[] { 1, 2 }, 1L);
        assertTrue(Objects.deepEquals(new long[] { 2 }, array));
        assertEquals(Long.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new long[] { 1, 2, 1 }, 1L);
        assertTrue(Objects.deepEquals(new long[] { 2, 1 }, array));
        assertEquals(Long.TYPE, array.getClass().getComponentType());
    }

    @Test
    public void testRemoveElementShortArray() {
        short[] array;
        array = Arrays.removeElement((short[]) null, (short) 1);
        assertNull(array);
        array = Arrays.removeElement(Arrays.EMPTY_SHORT_ARRAY, (short) 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_SHORT_ARRAY, array));
        assertEquals(Short.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new short[] { 1 }, (short) 1);
        assertTrue(Objects.deepEquals(Arrays.EMPTY_SHORT_ARRAY, array));
        assertEquals(Short.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new short[] { 1, 2 }, (short) 1);
        assertTrue(Objects.deepEquals(new short[] { 2 }, array));
        assertEquals(Short.TYPE, array.getClass().getComponentType());
        array = Arrays.removeElement(new short[] { 1, 2, 1 }, (short) 1);
        assertTrue(Objects.deepEquals(new short[] { 2, 1 }, array));
        assertEquals(Short.TYPE, array.getClass().getComponentType());
    }

    @Test
    public void testRemoveAllBooleanOccurences() {
        boolean[] a = null;
        assertNull(Arrays.removeAllOccurences(a, true));

        a = new boolean[0];
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.removeAllOccurences(a, true)));

        a = new boolean[] { true };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.removeAllOccurences(a, true)));

        a = new boolean[] { true, true };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BOOLEAN_ARRAY, Arrays.removeAllOccurences(a, true)));

        a = new boolean[] { false, true, true, false, true };
        assertTrue(Objects.deepEquals(new boolean[] { false, false }, Arrays.removeAllOccurences(a, true)));

        a = new boolean[] { false, true, true, false, true };
        assertTrue(Objects.deepEquals(new boolean[] { true, true, true }, Arrays.removeAllOccurences(a, false)));
    }

    @Test
    public void testRemoveAllCharOccurences() {
        char[] a = null;
        assertNull(Arrays.removeAllOccurences(a, '2'));

        a = new char[0];
        assertTrue(Objects.deepEquals(Arrays.EMPTY_CHAR_ARRAY, Arrays.removeAllOccurences(a, '2')));

        a = new char[] { '2' };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_CHAR_ARRAY, Arrays.removeAllOccurences(a, '2')));

        a = new char[] { '2', '2' };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_CHAR_ARRAY, Arrays.removeAllOccurences(a, '2')));

        a = new char[] { '1', '2', '2', '3', '2' };
        assertTrue(Objects.deepEquals(new char[] { '1', '3' }, Arrays.removeAllOccurences(a, '2')));

        a = new char[] { '1', '2', '2', '3', '2' };
        assertTrue(Objects.deepEquals(new char[] { '1', '2', '2', '3', '2' }, Arrays.removeAllOccurences(a, '4')));
    }

    @Test
    public void testRemoveAllByteOccurences() {
        byte[] a = null;
        assertNull(Arrays.removeAllOccurences(a, (byte) 2));

        a = new byte[0];
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BYTE_ARRAY, Arrays.removeAllOccurences(a, (byte) 2)));

        a = new byte[] { 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BYTE_ARRAY, Arrays.removeAllOccurences(a, (byte) 2)));

        a = new byte[] { 2, 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_BYTE_ARRAY, Arrays.removeAllOccurences(a, (byte) 2)));

        a = new byte[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new byte[] { 1, 3 }, Arrays.removeAllOccurences(a, (byte) 2)));

        a = new byte[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new byte[] { 1, 2, 2, 3, 2 }, Arrays.removeAllOccurences(a, (byte) 4)));
    }

    @Test
    public void testRemoveAllShortOccurences() {
        short[] a = null;
        assertNull(Arrays.removeAllOccurences(a, (short) 2));

        a = new short[0];
        assertTrue(Objects.deepEquals(Arrays.EMPTY_SHORT_ARRAY, Arrays.removeAllOccurences(a, (short) 2)));

        a = new short[] { 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_SHORT_ARRAY, Arrays.removeAllOccurences(a, (short) 2)));

        a = new short[] { 2, 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_SHORT_ARRAY, Arrays.removeAllOccurences(a, (short) 2)));

        a = new short[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new short[] { 1, 3 }, Arrays.removeAllOccurences(a, (short) 2)));

        a = new short[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new short[] { 1, 2, 2, 3, 2 }, Arrays.removeAllOccurences(a, (short) 4)));
    }

    @Test
    public void testRemoveAllIntOccurences() {
        int[] a = null;
        assertNull(Arrays.removeAllOccurences(a, 2));

        a = new int[0];
        assertTrue(Objects.deepEquals(Arrays.EMPTY_INT_ARRAY, Arrays.removeAllOccurences(a, 2)));

        a = new int[] { 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_INT_ARRAY, Arrays.removeAllOccurences(a, 2)));

        a = new int[] { 2, 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_INT_ARRAY, Arrays.removeAllOccurences(a, 2)));

        a = new int[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new int[] { 1, 3 }, Arrays.removeAllOccurences(a, 2)));

        a = new int[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new int[] { 1, 2, 2, 3, 2 }, Arrays.removeAllOccurences(a, 4)));
    }

    @Test
    public void testRemoveAllLongOccurences() {
        long[] a = null;
        assertNull(Arrays.removeAllOccurences(a, 2));

        a = new long[0];
        assertTrue(Objects.deepEquals(Arrays.EMPTY_LONG_ARRAY, Arrays.removeAllOccurences(a, (long)2/*!!!*/)));

        a = new long[] { 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_LONG_ARRAY, Arrays.removeAllOccurences(a, (long)2/*!!!*/)));

        a = new long[] { 2, 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_LONG_ARRAY, Arrays.removeAllOccurences(a, (long)2/*!!!*/)));

        a = new long[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new long[] { 1, 3 }, Arrays.removeAllOccurences(a, (long)2/*!!!*/)));

        a = new long[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new long[] { 1, 2, 2, 3, 2 }, Arrays.removeAllOccurences(a, (long)4/*!!!*/)));
    }

    @Test
    public void testRemoveAllFloatOccurences() {
        float[] a = null;
        assertNull(Arrays.removeAllOccurences(a, 2));

        a = new float[0];
        assertTrue(Objects.deepEquals(Arrays.EMPTY_FLOAT_ARRAY, Arrays.removeAllOccurences(a, (float)2/*!!!*/)));

        a = new float[] { 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_FLOAT_ARRAY, Arrays.removeAllOccurences(a, (float)2/*!!!*/)));

        a = new float[] { 2, 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_FLOAT_ARRAY, Arrays.removeAllOccurences(a, (float)2/*!!!*/)));

        a = new float[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new float[] { 1, 3 }, Arrays.removeAllOccurences(a, (float)2/*!!!*/)));

        a = new float[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new float[] { 1, 2, 2, 3, 2 }, Arrays.removeAllOccurences(a, (float)4/*!!!*/)));
    }

    @Test
    public void testRemoveAllDoubleOccurences() {
        double[] a = null;
        assertNull(Arrays.removeAllOccurences(a, 2));

        a = new double[0];
        assertTrue(Objects.deepEquals(Arrays.EMPTY_DOUBLE_ARRAY, Arrays.removeAllOccurences(a, (double)2/*!!!*/)));

        a = new double[] { 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_DOUBLE_ARRAY, Arrays.removeAllOccurences(a, (double)2/*!!!*/)));

        a = new double[] { 2, 2 };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_DOUBLE_ARRAY, Arrays.removeAllOccurences(a, (double)2/*!!!*/)));

        a = new double[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new double[] { 1, 3 }, Arrays.removeAllOccurences(a, (double)2/*!!!*/)));

        a = new double[] { 1, 2, 2, 3, 2 };
        assertTrue(Objects.deepEquals(new double[] { 1, 2, 2, 3, 2 }, Arrays.removeAllOccurences(a, (double)4/*!!!*/)));
    }

    @Test
    public void testRemoveAllObjectOccurences() {
        String[] a = null;
        assertNull(Arrays.removeAllOccurences(a, "2"));

        a = new String[0];
        assertTrue(Objects.deepEquals(Arrays.EMPTY_STRING_ARRAY, Arrays.removeAllOccurences(a, "2")));

        a = new String[] { "2" };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_STRING_ARRAY, Arrays.removeAllOccurences(a, "2")));

        a = new String[] { "2", "2" };
        assertTrue(Objects.deepEquals(Arrays.EMPTY_STRING_ARRAY, Arrays.removeAllOccurences(a, "2")));

        a = new String[] { "1", "2", "2", "3", "2" };
        assertTrue(Objects.deepEquals(new String[] { "1", "3" }, Arrays.removeAllOccurences(a, "2")));

        a = new String[] { "1", "2", "2", "3", "2" };
        assertTrue(Objects.deepEquals(new String[] { "1", "2", "2", "3", "2" }, Arrays.removeAllOccurences(a, "4")));
    }

}
