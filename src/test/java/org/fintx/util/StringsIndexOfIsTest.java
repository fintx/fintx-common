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

import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class StringsIndexOfIsTest {
    
    private static final String BAR = "bar";
    /**
     * Supplementary character U+20000
     * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
     */
    private static final String CharU20000 = "\uD840\uDC00";
    /**
     * Supplementary character U+20001
     * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
     */
    private static final String CharU20001 = "\uD840\uDC01";

    private static final String FOO = "foo";

    private static final String FOOBAR = "foobar";

    private static final String[] FOOBAR_SUB_ARRAY = new String[] {"ob", "ba"};

    // The purpose of this class is to test Strings#equals(CharSequence, CharSequence)
    // with a CharSequence implementation whose equals(Object) override requires that the
    // other object be an instance of CustomCharSequence, even though, as char sequences,
    // `seq` may equal the other object.
    private static class CustomCharSequence implements CharSequence {
        private final CharSequence seq;

        CustomCharSequence(final CharSequence seq) {
            this.seq = seq;
        }

        @Override
        public char charAt(final int index) {
            return seq.charAt(index);
        }

        @Override
        public int length() {
            return seq.length();
        }

        @Override
        public CharSequence subSequence(final int start, final int end) {
            return new CustomCharSequence(seq.subSequence(start, end));
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null || !(obj instanceof CustomCharSequence)) {
                return false;
            }
            final CustomCharSequence other = (CustomCharSequence) obj;
            return seq.equals(other.seq);
        }

        @Override
        public int hashCode() {
            return seq.hashCode();
        }

        @Override
        public String toString() {
            return seq.toString();
        }
    }

    @Test
    public void testCustomCharSequence() {
        assertThat(new CustomCharSequence(FOO), IsNot.<CharSequence>not(FOO));
        assertThat(FOO, IsNot.<CharSequence>not(new CustomCharSequence(FOO)));
        assertEquals(new CustomCharSequence(FOO), new CustomCharSequence(FOO));
    }

    //-----------------------------------------------------------------------
    @Test
    public void testCompare_StringString() {
        assertTrue(Strings.compare(null, null) == 0);
        assertTrue(Strings.compare(null, "a") < 0);
        assertTrue(Strings.compare("a", null) > 0);
        assertTrue(Strings.compare("abc", "abc") == 0);
        assertTrue(Strings.compare("a", "b") < 0);
        assertTrue(Strings.compare("b", "a") > 0);
        assertTrue(Strings.compare("a", "B") > 0);
        assertTrue(Strings.compare("abc", "abd") < 0);
        assertTrue(Strings.compare("ab", "abc") < 0);
        assertTrue(Strings.compare("ab", "ab ") < 0);
        assertTrue(Strings.compare("abc", "ab ") > 0);
    }

    @Test
    public void testCompare_StringStringBoolean() {
        assertTrue(Strings.compare(null, null, false) == 0);
        assertTrue(Strings.compare(null, "a", true) < 0);
        assertTrue(Strings.compare(null, "a", false) > 0);
        assertTrue(Strings.compare("a", null, true) > 0);
        assertTrue(Strings.compare("a", null, false) < 0);
        assertTrue(Strings.compare("abc", "abc", false) == 0);
        assertTrue(Strings.compare("a", "b", false) < 0);
        assertTrue(Strings.compare("b", "a", false) > 0);
        assertTrue(Strings.compare("a", "B", false) > 0);
        assertTrue(Strings.compare("abc", "abd", false) < 0);
        assertTrue(Strings.compare("ab", "abc", false) < 0);
        assertTrue(Strings.compare("ab", "ab ", false) < 0);
        assertTrue(Strings.compare("abc", "ab ", false) > 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString() {
        assertTrue(Strings.compareIgnoreCase(null, null) == 0);
        assertTrue(Strings.compareIgnoreCase(null, "a") < 0);
        assertTrue(Strings.compareIgnoreCase("a", null) > 0);
        assertTrue(Strings.compareIgnoreCase("abc", "abc") == 0);
        assertTrue(Strings.compareIgnoreCase("abc", "ABC") == 0);
        assertTrue(Strings.compareIgnoreCase("a", "b") < 0);
        assertTrue(Strings.compareIgnoreCase("b", "a") > 0);
        assertTrue(Strings.compareIgnoreCase("a", "B") < 0);
        assertTrue(Strings.compareIgnoreCase("A", "b") < 0);
        assertTrue(Strings.compareIgnoreCase("abc", "ABD") < 0);
        assertTrue(Strings.compareIgnoreCase("ab", "ABC") < 0);
        assertTrue(Strings.compareIgnoreCase("ab", "AB ") < 0);
        assertTrue(Strings.compareIgnoreCase("abc", "AB ") > 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean() {
        assertTrue(Strings.compareIgnoreCase(null, null, false) == 0);
        assertTrue(Strings.compareIgnoreCase(null, "a", true) < 0);
        assertTrue(Strings.compareIgnoreCase(null, "a", false) > 0);
        assertTrue(Strings.compareIgnoreCase("a", null, true) > 0);
        assertTrue(Strings.compareIgnoreCase("a", null, false) < 0);
        assertTrue(Strings.compareIgnoreCase("abc", "abc", false) == 0);
        assertTrue(Strings.compareIgnoreCase("abc", "ABC", false) == 0);
        assertTrue(Strings.compareIgnoreCase("a", "b", false) < 0);
        assertTrue(Strings.compareIgnoreCase("b", "a", false) > 0);
        assertTrue(Strings.compareIgnoreCase("a", "B", false) < 0);
        assertTrue(Strings.compareIgnoreCase("A", "b", false) < 0);
        assertTrue(Strings.compareIgnoreCase("abc", "ABD", false) < 0);
        assertTrue(Strings.compareIgnoreCase("ab", "ABC", false) < 0);
        assertTrue(Strings.compareIgnoreCase("ab", "AB ", false) < 0);
        assertTrue(Strings.compareIgnoreCase("abc", "AB ", false) > 0);
    }

    //-----------------------------------------------------------------------
    @Test
    public void testIndexOf_char() {
        assertEquals(-1, Strings.indexOf(null, ' ',0));
        assertEquals(-1, Strings.indexOf("", ' ',0));
        assertEquals(0, Strings.indexOf("aabaabaa", 'a',0));
        assertEquals(2, Strings.indexOf("aabaabaa", 'b',0));

    }

    @Test
    public void testIndexOf_charInt() {
        assertEquals(-1, Strings.indexOf(null, ' ', 0));
        assertEquals(-1, Strings.indexOf(null, ' ', -1));
        assertEquals(-1, Strings.indexOf("", ' ', 0));
        assertEquals(-1, Strings.indexOf("", ' ', -1));
        assertEquals(0, Strings.indexOf("aabaabaa", 'a', 0));
        assertEquals(2, Strings.indexOf("aabaabaa", 'b', 0));
        assertEquals(5, Strings.indexOf("aabaabaa", 'b', 3));
        assertEquals(-1, Strings.indexOf("aabaabaa", 'b', 9));
        assertEquals(2, Strings.indexOf("aabaabaa", 'b', -1));
    }

    @Test
    public void testIndexOf_String() {
        assertEquals(-1, Strings.indexOf(null, null,0));
        assertEquals(-1, Strings.indexOf("", null,0));
        assertEquals(0, Strings.indexOf("", "",0));
        assertEquals(0, Strings.indexOf("aabaabaa", "a",0));
        assertEquals(2, Strings.indexOf("aabaabaa", "b",0));
        assertEquals(1, Strings.indexOf("aabaabaa", "ab",0));
        assertEquals(0, Strings.indexOf("aabaabaa", "",0));

    }

    @Test
    public void testIndexOf_StringInt() {
        assertEquals(-1, Strings.indexOf(null, null, 0));
        assertEquals(-1, Strings.indexOf(null, null, -1));
        assertEquals(-1, Strings.indexOf(null, "", 0));
        assertEquals(-1, Strings.indexOf(null, "", -1));
        assertEquals(-1, Strings.indexOf("", null, 0));
        assertEquals(-1, Strings.indexOf("", null, -1));
        assertEquals(0, Strings.indexOf("", "", 0));
        assertEquals(0, Strings.indexOf("", "", -1));
        assertEquals(0, Strings.indexOf("", "", 9));
        assertEquals(0, Strings.indexOf("abc", "", 0));
        assertEquals(0, Strings.indexOf("abc", "", -1));
        assertEquals(3, Strings.indexOf("abc", "", 9));
        assertEquals(3, Strings.indexOf("abc", "", 3));
        assertEquals(0, Strings.indexOf("aabaabaa", "a", 0));
        assertEquals(2, Strings.indexOf("aabaabaa", "b", 0));
        assertEquals(1, Strings.indexOf("aabaabaa", "ab", 0));
        assertEquals(5, Strings.indexOf("aabaabaa", "b", 3));
        assertEquals(-1, Strings.indexOf("aabaabaa", "b", 9));
        assertEquals(2, Strings.indexOf("aabaabaa", "b", -1));
        assertEquals(2,Strings.indexOf("aabaabaa", "", 2));

        // Test that startIndex works correctly, i.e. cannot match before startIndex
        assertEquals(7, Strings.indexOf("12345678", "8", 5));
        assertEquals(7, Strings.indexOf("12345678", "8", 6));
        assertEquals(7, Strings.indexOf("12345678", "8", 7)); // 7 is last index
        assertEquals(-1, Strings.indexOf("12345678", "8", 8));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt() {
        assertEquals(1, Strings.indexOfIgnoreCase("aabaabaa", "AB", -1));
        assertEquals(1, Strings.indexOfIgnoreCase("aabaabaa", "AB", 0));
        assertEquals(1, Strings.indexOfIgnoreCase("aabaabaa", "AB", 1));
        assertEquals(4, Strings.indexOfIgnoreCase("aabaabaa", "AB", 2));
        assertEquals(4, Strings.indexOfIgnoreCase("aabaabaa", "AB", 3));
        assertEquals(4, Strings.indexOfIgnoreCase("aabaabaa", "AB", 4));
        assertEquals(-1, Strings.indexOfIgnoreCase("aabaabaa", "AB", 5));
        assertEquals(-1, Strings.indexOfIgnoreCase("aabaabaa", "AB", 6));
        assertEquals(-1, Strings.indexOfIgnoreCase("aabaabaa", "AB", 7));
        assertEquals(-1, Strings.indexOfIgnoreCase("aabaabaa", "AB", 8));
        assertEquals(1, Strings.indexOfIgnoreCase("aab", "AB", 1));
        assertEquals(5, Strings.indexOfIgnoreCase("aabaabaa", "", 5));
        assertEquals(-1, Strings.indexOfIgnoreCase("ab", "AAB", 0));
        assertEquals(-1, Strings.indexOfIgnoreCase("aab", "AAB", 1));
        assertEquals(-1, Strings.indexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void testLastIndexOf_charInt() {
        assertEquals(-1, Strings.lastIndexOf(null, ' ', 0));
        assertEquals(-1, Strings.lastIndexOf(null, ' ', -1));
        assertEquals(-1, Strings.lastIndexOf("", ' ', 0));
        assertEquals(-1, Strings.lastIndexOf("", ' ', -1));
        assertEquals(7, Strings.lastIndexOf("aabaabaa", 'a', 8));
        assertEquals(5, Strings.lastIndexOf("aabaabaa", 'b', 8));
        assertEquals(2, Strings.lastIndexOf("aabaabaa", 'b', 3));
        assertEquals(5, Strings.lastIndexOf("aabaabaa", 'b', 9));
        assertEquals(-1, Strings.lastIndexOf("aabaabaa", 'b', -1));
        assertEquals(0, Strings.lastIndexOf("aabaabaa", 'a', 0));

    }

    @Test
    public void testLastIndexOf_StringInt() {
        assertEquals(-1, Strings.lastIndexOf(null, null, 0));
        assertEquals(-1, Strings.lastIndexOf(null, null, -1));
        assertEquals(-1, Strings.lastIndexOf(null, "", 0));
        assertEquals(-1, Strings.lastIndexOf(null, "", -1));
        assertEquals(-1, Strings.lastIndexOf("", null, 0));
        assertEquals(-1, Strings.lastIndexOf("", null, -1));
        assertEquals(0, Strings.lastIndexOf("", "", 0));
        assertEquals(-1, Strings.lastIndexOf("", "", -1));
        assertEquals(0, Strings.lastIndexOf("", "", 9));
        assertEquals(0, Strings.lastIndexOf("abc", "", 0));
        assertEquals(-1, Strings.lastIndexOf("abc", "", -1));
        assertEquals(3, Strings.lastIndexOf("abc", "", 9));
        assertEquals(7, Strings.lastIndexOf("aabaabaa", "a", 8));
        assertEquals(5, Strings.lastIndexOf("aabaabaa", "b", 8));
        assertEquals(4, Strings.lastIndexOf("aabaabaa", "ab", 8));
        assertEquals(2, Strings.lastIndexOf("aabaabaa", "b", 3));
        assertEquals(5, Strings.lastIndexOf("aabaabaa", "b", 9));
        assertEquals(-1, Strings.lastIndexOf("aabaabaa", "b", -1));
        assertEquals(-1, Strings.lastIndexOf("aabaabaa", "b", 0));
        assertEquals(0, Strings.lastIndexOf("aabaabaa", "a", 0));
        assertEquals(-1, Strings.lastIndexOf("aabaabaa", "a", -1));

        // Test that fromIndex works correctly, i.e. cannot match after fromIndex
        assertEquals(7, Strings.lastIndexOf("12345678", "8", 9));
        assertEquals(7, Strings.lastIndexOf("12345678", "8", 8));
        assertEquals(7, Strings.lastIndexOf("12345678", "8", 7)); // 7 is last index
        assertEquals(-1, Strings.lastIndexOf("12345678", "8", 6));

        assertEquals(-1, Strings.lastIndexOf("aabaabaa", "b", 1));
        assertEquals(2, Strings.lastIndexOf("aabaabaa", "b", 2));
        assertEquals(2, Strings.lastIndexOf("aabaabaa", "ba", 2));
        assertEquals(2, Strings.lastIndexOf("aabaabaa", "ba", 3));

    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt() {
        assertEquals(-1, Strings.lastIndexOfIgnoreCase(null, null, 0));
        assertEquals(-1, Strings.lastIndexOfIgnoreCase(null, null, -1));
        assertEquals(-1, Strings.lastIndexOfIgnoreCase(null, "", 0));
        assertEquals(-1, Strings.lastIndexOfIgnoreCase(null, "", -1));
        assertEquals(-1, Strings.lastIndexOfIgnoreCase("", null, 0));
        assertEquals(-1, Strings.lastIndexOfIgnoreCase("", null, -1));
        assertEquals(0, Strings.lastIndexOfIgnoreCase("", "", 0));
        assertEquals(-1, Strings.lastIndexOfIgnoreCase("", "", -1));
        assertEquals(0, Strings.lastIndexOfIgnoreCase("", "", 9));
        assertEquals(0, Strings.lastIndexOfIgnoreCase("abc", "", 0));
        assertEquals(-1, Strings.lastIndexOfIgnoreCase("abc", "", -1));
        assertEquals(3, Strings.lastIndexOfIgnoreCase("abc", "", 9));
        assertEquals(7, Strings.lastIndexOfIgnoreCase("aabaabaa", "A", 8));
        assertEquals(5, Strings.lastIndexOfIgnoreCase("aabaabaa", "B", 8));
        assertEquals(4, Strings.lastIndexOfIgnoreCase("aabaabaa", "AB", 8));
        assertEquals(2, Strings.lastIndexOfIgnoreCase("aabaabaa", "B", 3));
        assertEquals(5, Strings.lastIndexOfIgnoreCase("aabaabaa", "B", 9));
        assertEquals(-1, Strings.lastIndexOfIgnoreCase("aabaabaa", "B", -1));
        assertEquals(-1, Strings.lastIndexOfIgnoreCase("aabaabaa", "B", 0));
        assertEquals(0, Strings.lastIndexOfIgnoreCase("aabaabaa", "A", 0));
        assertEquals(1, Strings.lastIndexOfIgnoreCase("aab", "AB", 1));
    }

    @Test
    public void testLastOrdinalIndexOf() {
        assertEquals(-1, Strings.lastOrdinalIndexOf(null, "*", 42) );
        assertEquals(-1, Strings.lastOrdinalIndexOf("*", null, 42) );
        assertEquals(0, Strings.lastOrdinalIndexOf("", "", 42) );
        assertEquals(7, Strings.lastOrdinalIndexOf("aabaabaa", "a", 1) );
        assertEquals(6, Strings.lastOrdinalIndexOf("aabaabaa", "a", 2) );
        assertEquals(5, Strings.lastOrdinalIndexOf("aabaabaa", "b", 1) );
        assertEquals(2, Strings.lastOrdinalIndexOf("aabaabaa", "b", 2) );
        assertEquals(4, Strings.lastOrdinalIndexOf("aabaabaa", "ab", 1) );
        assertEquals(1, Strings.lastOrdinalIndexOf("aabaabaa", "ab", 2) );
        assertEquals(8, Strings.lastOrdinalIndexOf("aabaabaa", "", 1) );
        assertEquals(8, Strings.lastOrdinalIndexOf("aabaabaa", "", 2) );
    }

    @Test
    public void testOrdinalIndexOf() {
        assertEquals(-1, Strings.ordinalIndexOf(null, null, Integer.MIN_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("", null, Integer.MIN_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("", "", Integer.MIN_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "a", Integer.MIN_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "b", Integer.MIN_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "ab", Integer.MIN_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "", Integer.MIN_VALUE));

        assertEquals(-1, Strings.ordinalIndexOf(null, null, -1));
        assertEquals(-1, Strings.ordinalIndexOf("", null, -1));
        assertEquals(-1, Strings.ordinalIndexOf("", "", -1));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "a", -1));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "b", -1));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "ab", -1));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "", -1));

        assertEquals(-1, Strings.ordinalIndexOf(null, null, 0));
        assertEquals(-1, Strings.ordinalIndexOf("", null, 0));
        assertEquals(-1, Strings.ordinalIndexOf("", "", 0));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "a", 0));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "b", 0));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "ab", 0));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "", 0));

        assertEquals(-1, Strings.ordinalIndexOf(null, null, 1));
        assertEquals(-1, Strings.ordinalIndexOf("", null, 1));
        assertEquals(0, Strings.ordinalIndexOf("", "", 1));
        assertEquals(0, Strings.ordinalIndexOf("aabaabaa", "a", 1));
        assertEquals(2, Strings.ordinalIndexOf("aabaabaa", "b", 1));
        assertEquals(1, Strings.ordinalIndexOf("aabaabaa", "ab", 1));
        assertEquals(0, Strings.ordinalIndexOf("aabaabaa", "", 1));

        assertEquals(-1, Strings.ordinalIndexOf(null, null, 2));
        assertEquals(-1, Strings.ordinalIndexOf("", null, 2));
        assertEquals(0, Strings.ordinalIndexOf("", "", 2));
        assertEquals(1, Strings.ordinalIndexOf("aabaabaa", "a", 2));
        assertEquals(5, Strings.ordinalIndexOf("aabaabaa", "b", 2));
        assertEquals(4, Strings.ordinalIndexOf("aabaabaa", "ab", 2));
        assertEquals(0, Strings.ordinalIndexOf("aabaabaa", "", 2));

        assertEquals(-1, Strings.ordinalIndexOf(null, null, Integer.MAX_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("", null, Integer.MAX_VALUE));
        assertEquals(0, Strings.ordinalIndexOf("", "", Integer.MAX_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "a", Integer.MAX_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "b", Integer.MAX_VALUE));
        assertEquals(-1, Strings.ordinalIndexOf("aabaabaa", "ab", Integer.MAX_VALUE));
        assertEquals(0, Strings.ordinalIndexOf("aabaabaa", "", Integer.MAX_VALUE));

        assertEquals(-1, Strings.ordinalIndexOf("aaaaaaaaa", "a", 0));
        assertEquals(0, Strings.ordinalIndexOf("aaaaaaaaa", "a", 1));
        assertEquals(1, Strings.ordinalIndexOf("aaaaaaaaa", "a", 2));
        assertEquals(2, Strings.ordinalIndexOf("aaaaaaaaa", "a", 3));
        assertEquals(3, Strings.ordinalIndexOf("aaaaaaaaa", "a", 4));
        assertEquals(4, Strings.ordinalIndexOf("aaaaaaaaa", "a", 5));
        assertEquals(5, Strings.ordinalIndexOf("aaaaaaaaa", "a", 6));
        assertEquals(6, Strings.ordinalIndexOf("aaaaaaaaa", "a", 7));
        assertEquals(7, Strings.ordinalIndexOf("aaaaaaaaa", "a", 8));
        assertEquals(8, Strings.ordinalIndexOf("aaaaaaaaa", "a", 9));
        assertEquals(-1, Strings.ordinalIndexOf("aaaaaaaaa", "a", 10));

        // match at each possible position
        assertEquals(0, Strings.ordinalIndexOf("aaaaaa", "aa", 1));
        assertEquals(1, Strings.ordinalIndexOf("aaaaaa", "aa", 2));
        assertEquals(2, Strings.ordinalIndexOf("aaaaaa", "aa", 3));
        assertEquals(3, Strings.ordinalIndexOf("aaaaaa", "aa", 4));
        assertEquals(4, Strings.ordinalIndexOf("aaaaaa", "aa", 5));
        assertEquals(-1, Strings.ordinalIndexOf("aaaaaa", "aa", 6));

        assertEquals(0, Strings.ordinalIndexOf("ababab", "aba", 1));
        assertEquals(2, Strings.ordinalIndexOf("ababab", "aba", 2));
        assertEquals(-1, Strings.ordinalIndexOf("ababab", "aba", 3));

        assertEquals(0, Strings.ordinalIndexOf("abababab", "abab", 1));
        assertEquals(2, Strings.ordinalIndexOf("abababab", "abab", 2));
        assertEquals(4, Strings.ordinalIndexOf("abababab", "abab", 3));
        assertEquals(-1, Strings.ordinalIndexOf("abababab", "abab", 4));
    }

    @Test
    public void testLANG1193() {
        assertEquals(0, Strings.ordinalIndexOf("abc", "ab", 1));
    }

    @Test
    // Non-overlapping test
    public void testLANG1241_1() {
        //                                          0  3  6
        assertEquals(0, Strings.ordinalIndexOf("abaabaab", "ab", 1));
        assertEquals(3, Strings.ordinalIndexOf("abaabaab", "ab", 2));
        assertEquals(6, Strings.ordinalIndexOf("abaabaab", "ab", 3));
    }

    @Test
    // Overlapping matching test
    public void testLANG1241_2() {
        //                                          0 2 4
        assertEquals(0, Strings.ordinalIndexOf("abababa", "aba", 1));
        assertEquals(2, Strings.ordinalIndexOf("abababa", "aba", 2));
        assertEquals(4, Strings.ordinalIndexOf("abababa", "aba", 3));
        assertEquals(0, Strings.ordinalIndexOf("abababab", "abab", 1));
        assertEquals(2, Strings.ordinalIndexOf("abababab", "abab", 2));
        assertEquals(4, Strings.ordinalIndexOf("abababab", "abab", 3));
    }
    
    @Test
    public void testIsAlpha() {
        assertFalse(Strings.isAlpha(null));
        assertFalse(Strings.isAlpha(""));
        assertFalse(Strings.isAlpha(" "));
        assertTrue(Strings.isAlpha("a"));
        assertTrue(Strings.isAlpha("A"));
        assertTrue(Strings.isAlpha("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
        assertFalse(Strings.isAlpha("ham kso"));
        assertFalse(Strings.isAlpha("1"));
        assertFalse(Strings.isAlpha("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
        assertFalse(Strings.isAlpha("_"));
        assertFalse(Strings.isAlpha("hkHKHik*khbkuh"));
    }

    @Test
    public void testIsWhitespace() {
        assertFalse(Strings.isWhitespace(null));
        assertTrue(Strings.isWhitespace(""));
        assertTrue(Strings.isWhitespace(" "));
        assertTrue(Strings.isWhitespace("\t \n \t"));
        assertFalse(Strings.isWhitespace("\t aa\n \t"));
        assertTrue(Strings.isWhitespace(" "));
        assertFalse(Strings.isWhitespace(" a "));
        assertFalse(Strings.isWhitespace("a  "));
        assertFalse(Strings.isWhitespace("  a"));
        assertFalse(Strings.isWhitespace("aba"));
        assertTrue(Strings.isWhitespace(StringsTest.WHITESPACE));
        assertFalse(Strings.isWhitespace(StringsTest.NON_WHITESPACE));
    }

    @Test
    public void testIsNumeric() {
        assertFalse(Strings.isNumeric(null));
        assertFalse(Strings.isNumeric(""));
        assertFalse(Strings.isNumeric(" "));
        assertFalse(Strings.isNumeric("a"));
        assertFalse(Strings.isNumeric("A"));
        assertFalse(Strings.isNumeric("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
        assertFalse(Strings.isNumeric("ham kso"));
        assertTrue(Strings.isNumeric("1"));
        assertTrue(Strings.isNumeric("1000"));
        assertTrue(Strings.isNumeric("\u0967\u0968\u0969"));
        assertFalse(Strings.isNumeric("\u0967\u0968 \u0969"));
        assertFalse(Strings.isNumeric("2.3"));
        assertFalse(Strings.isNumeric("10 00"));
        assertFalse(Strings.isNumeric("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
        assertFalse(Strings.isNumeric("_"));
        assertFalse(Strings.isNumeric("hkHKHik*khbkuh"));
        assertFalse(Strings.isNumeric("+123"));
        assertFalse(Strings.isNumeric("-123"));
    }


}
