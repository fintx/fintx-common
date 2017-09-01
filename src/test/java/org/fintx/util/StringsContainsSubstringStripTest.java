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
public class StringsContainsSubstringStripTest {
    
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
    /**
     * Incomplete supplementary character U+20000, high surrogate only.
     * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
     */
    private static final String CharUSuppCharHigh = "\uDC00";

    /**
     * Incomplete supplementary character U+20000, low surrogate only.
     * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
     */
    private static final String CharUSuppCharLow = "\uD840";

    @Test
    public void testContains_Char() {
        assertFalse(Strings.contains(null, ' '));
        assertFalse(Strings.contains("", ' '));
        assertFalse(Strings.contains("", null));
        assertFalse(Strings.contains(null, null));
        assertTrue(Strings.contains("abc", 'a'));
        assertTrue(Strings.contains("abc", 'b'));
        assertTrue(Strings.contains("abc", 'c'));
        assertFalse(Strings.contains("abc", 'z'));
    }

    @Test
    public void testContains_String() {
        assertFalse(Strings.contains(null, null));
        assertFalse(Strings.contains(null, ""));
        assertFalse(Strings.contains(null, "a"));
        assertFalse(Strings.contains("", null));
        assertTrue(Strings.contains("", ""));
        assertFalse(Strings.contains("", "a"));
        assertTrue(Strings.contains("abc", "a"));
        assertTrue(Strings.contains("abc", "b"));
        assertTrue(Strings.contains("abc", "c"));
        assertTrue(Strings.contains("abc", "abc"));
        assertFalse(Strings.contains("abc", "z"));
    }

    /**
     * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
     */
    @Test
    public void testContains_StringWithBadSupplementaryChars() {
        // Test edge case: 1/2 of a (broken) supplementary char
        assertFalse(Strings.contains(CharUSuppCharHigh, CharU20001));        assertFalse(Strings.contains(CharUSuppCharLow, CharU20001));
        assertFalse(Strings.contains(CharU20001, CharUSuppCharHigh));
        assertEquals(0, CharU20001.indexOf(CharUSuppCharLow));
        assertTrue(Strings.contains(CharU20001, CharUSuppCharLow));
        assertTrue(Strings.contains(CharU20001 + CharUSuppCharLow + "a", "a"));
        assertTrue(Strings.contains(CharU20001 + CharUSuppCharHigh + "a", "a"));
    }

    /**
     * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
     */
    @Test
    public void testContains_StringWithSupplementaryChars() {
        assertTrue(Strings.contains(CharU20000 + CharU20001, CharU20000));
        assertTrue(Strings.contains(CharU20000 + CharU20001, CharU20001));
        assertTrue(Strings.contains(CharU20000, CharU20000));
        assertFalse(Strings.contains(CharU20000, CharU20001));
    }

    private static final String FOO = "foo";
    private static final String BAR = "bar";
    private static final String BAZ = "baz";
    private static final String FOOBAR = "foobar";
    private static final String SENTENCE = "foo bar baz";

    //-----------------------------------------------------------------------

    @Test
    public void testSubstring_StringInt() {
        assertEquals(null, Strings.substring(null, 0,Integer.MAX_VALUE));
        assertEquals("", Strings.substring("", 0,Integer.MAX_VALUE));
        assertEquals("", Strings.substring("", 2,Integer.MAX_VALUE));

        assertEquals("", Strings.substring(SENTENCE, 80,Integer.MAX_VALUE));
        assertEquals(BAZ, Strings.substring(SENTENCE, 8,Integer.MAX_VALUE));
        assertEquals(BAZ, Strings.substring(SENTENCE, -3,Integer.MAX_VALUE));
        assertEquals(SENTENCE, Strings.substring(SENTENCE, 0,Integer.MAX_VALUE));
        assertEquals("abc", Strings.substring("abc", -4,Integer.MAX_VALUE));
        assertEquals("abc", Strings.substring("abc", -3,Integer.MAX_VALUE));
        assertEquals("bc", Strings.substring("abc", -2,Integer.MAX_VALUE));
        assertEquals("c", Strings.substring("abc", -1,Integer.MAX_VALUE));
        assertEquals("abc", Strings.substring("abc", 0,Integer.MAX_VALUE));
        assertEquals("bc", Strings.substring("abc", 1,Integer.MAX_VALUE));
        assertEquals("c", Strings.substring("abc", 2,Integer.MAX_VALUE));
        assertEquals("", Strings.substring("abc", 3,Integer.MAX_VALUE));
        assertEquals("", Strings.substring("abc", 4,Integer.MAX_VALUE));
    }

    @Test
    public void testSubstring_StringIntInt() {
        assertEquals(null, Strings.substring(null, 0, 0));
        assertEquals(null, Strings.substring(null, 1, 2));
        assertEquals("", Strings.substring("", 0, 0));
        assertEquals("", Strings.substring("", 1, 2));
        assertEquals("", Strings.substring("", -2, -1));

        assertEquals("", Strings.substring(SENTENCE, 8, 6));
        assertEquals(FOO, Strings.substring(SENTENCE, 0, 3));
        assertEquals("o", Strings.substring(SENTENCE, -9, 3));
        assertEquals(FOO, Strings.substring(SENTENCE, 0, -8));
        assertEquals("o", Strings.substring(SENTENCE, -9, -8));
        assertEquals(SENTENCE, Strings.substring(SENTENCE, 0, 80));
        assertEquals("", Strings.substring(SENTENCE, 2, 2));
        assertEquals("b",Strings.substring("abc", -2, -1));
    }

    @Test
    public void testLeft_String() {
        assertSame(null, Strings.left(null, -1));
        assertSame(null, Strings.left(null, 0));
        assertSame(null, Strings.left(null, 2));

        assertEquals("", Strings.left("", -1));
        assertEquals("", Strings.left("", 0));
        assertEquals("", Strings.left("", 2));

        assertEquals("", Strings.left(FOOBAR, -1));
        assertEquals("", Strings.left(FOOBAR, 0));
        assertEquals(FOO, Strings.left(FOOBAR, 3));
        assertSame(FOOBAR, Strings.left(FOOBAR, 80));
    }

    @Test
    public void testRight_String() {
        assertSame(null, Strings.right(null, -1));
        assertSame(null, Strings.right(null, 0));
        assertSame(null, Strings.right(null, 2));

        assertEquals("", Strings.right("", -1));
        assertEquals("", Strings.right("", 0));
        assertEquals("", Strings.right("", 2));

        assertEquals("", Strings.right(FOOBAR, -1));
        assertEquals("", Strings.right(FOOBAR, 0));
        assertEquals(BAR, Strings.right(FOOBAR, 3));
        assertSame(FOOBAR, Strings.right(FOOBAR, 80));
    }

    @Test
    public void testMid_String() {
        assertSame(null, Strings.mid(null, -1, 0));
        assertSame(null, Strings.mid(null, 0, -1));
        assertSame(null, Strings.mid(null, 3, 0));
        assertSame(null, Strings.mid(null, 3, 2));

        assertEquals("", Strings.mid("", 0, -1));
        assertEquals("", Strings.mid("", 0, 0));
        assertEquals("", Strings.mid("", 0, 2));

        assertEquals("", Strings.mid(FOOBAR, 3, -1));
        assertEquals("", Strings.mid(FOOBAR, 3, 0));
        assertEquals("b", Strings.mid(FOOBAR, 3, 1));
        assertEquals(FOO, Strings.mid(FOOBAR, 0, 3));
        assertEquals(BAR, Strings.mid(FOOBAR, 3, 3));
        assertEquals(FOOBAR, Strings.mid(FOOBAR, 0, 80));
        assertEquals(BAR, Strings.mid(FOOBAR, 3, 80));
        assertEquals("", Strings.mid(FOOBAR, 9, 3));
        assertEquals(FOO, Strings.mid(FOOBAR, -1, 3));
    }

    //-----------------------------------------------------------------------
    @Test
    public void testSubstringBefore_StringString() {
        assertEquals("foo", Strings.substringBefore("fooXXbarXXbaz", "XX"));

        assertEquals(null, Strings.substringBefore(null, null));
        assertEquals(null, Strings.substringBefore(null, ""));
        assertEquals(null, Strings.substringBefore(null, "XX"));
        assertEquals("", Strings.substringBefore("", null));
        assertEquals("", Strings.substringBefore("", ""));
        assertEquals("", Strings.substringBefore("", "XX"));

        assertEquals("foo", Strings.substringBefore("foo", null));
        assertEquals("foo", Strings.substringBefore("foo", "b"));
        assertEquals("f", Strings.substringBefore("foot", "o"));
        assertEquals("", Strings.substringBefore("abc", "a"));
        assertEquals("a", Strings.substringBefore("abcba", "b"));
        assertEquals("ab", Strings.substringBefore("abc", "c"));
        assertEquals("", Strings.substringBefore("abc", ""));
    }

    @Test
    public void testSubstringAfter_StringString() {
        assertEquals("barXXbaz", Strings.substringAfter("fooXXbarXXbaz", "XX"));

        assertEquals(null, Strings.substringAfter(null, null));
        assertEquals(null, Strings.substringAfter(null, ""));
        assertEquals(null, Strings.substringAfter(null, "XX"));
        assertEquals("", Strings.substringAfter("", null));
        assertEquals("", Strings.substringAfter("", ""));
        assertEquals("", Strings.substringAfter("", "XX"));

        assertEquals("", Strings.substringAfter("foo", null));
        assertEquals("ot", Strings.substringAfter("foot", "o"));
        assertEquals("bc", Strings.substringAfter("abc", "a"));
        assertEquals("cba", Strings.substringAfter("abcba", "b"));
        assertEquals("", Strings.substringAfter("abc", "c"));
        assertEquals("abc", Strings.substringAfter("abc", ""));
        assertEquals("", Strings.substringAfter("abc", "d"));
    }

    @Test
    public void testSubstringBeforeLast_StringString() {
        assertEquals("fooXXbar", Strings.substringBeforeLast("fooXXbarXXbaz", "XX"));

        assertEquals(null, Strings.substringBeforeLast(null, null));
        assertEquals(null, Strings.substringBeforeLast(null, ""));
        assertEquals(null, Strings.substringBeforeLast(null, "XX"));
        assertEquals("", Strings.substringBeforeLast("", null));
        assertEquals("", Strings.substringBeforeLast("", ""));
        assertEquals("", Strings.substringBeforeLast("", "XX"));

        assertEquals("foo", Strings.substringBeforeLast("foo", null));
        assertEquals("foo", Strings.substringBeforeLast("foo", "b"));
        assertEquals("fo", Strings.substringBeforeLast("foo", "o"));
        assertEquals("abc\r\n", Strings.substringBeforeLast("abc\r\n", "d"));
        assertEquals("abc", Strings.substringBeforeLast("abcdabc", "d"));
        assertEquals("abcdabc", Strings.substringBeforeLast("abcdabcd", "d"));
        assertEquals("a", Strings.substringBeforeLast("abc", "b"));
        assertEquals("abc ", Strings.substringBeforeLast("abc \n", "\n"));
        assertEquals("a", Strings.substringBeforeLast("a", null));
        assertEquals("a", Strings.substringBeforeLast("a", ""));
        assertEquals("", Strings.substringBeforeLast("a", "a"));
    }

    @Test
    public void testSubstringAfterLast_StringString() {
        assertEquals("baz", Strings.substringAfterLast("fooXXbarXXbaz", "XX"));

        assertEquals(null, Strings.substringAfterLast(null, null));
        assertEquals(null, Strings.substringAfterLast(null, ""));
        assertEquals(null, Strings.substringAfterLast(null, "XX"));
        assertEquals("", Strings.substringAfterLast("", null));
        assertEquals("", Strings.substringAfterLast("", ""));
        assertEquals("", Strings.substringAfterLast("", "a"));

        assertEquals("", Strings.substringAfterLast("foo", null));
        assertEquals("", Strings.substringAfterLast("foo", "b"));
        assertEquals("t", Strings.substringAfterLast("foot", "o"));
        assertEquals("bc", Strings.substringAfterLast("abc", "a"));
        assertEquals("a", Strings.substringAfterLast("abcba", "b"));
        assertEquals("", Strings.substringAfterLast("abc", "c"));
        assertEquals("", Strings.substringAfterLast("", "d"));
        assertEquals("", Strings.substringAfterLast("abc", ""));
    }

    

    @Test
    public void testSubstringBetween_StringStringString() {
        assertEquals(null, Strings.substringBetween(null, "", ""));
        assertEquals(null, Strings.substringBetween("", null, ""));
        assertEquals(null, Strings.substringBetween("", "", null));
        assertEquals("", Strings.substringBetween("", "", ""));
        assertEquals("", Strings.substringBetween("foo", "", ""));
        assertEquals(null, Strings.substringBetween("foo", "", "]"));
        assertEquals(null, Strings.substringBetween("foo", "[", "]"));
        assertEquals("", Strings.substringBetween("    ", " ", "  "));
        assertEquals("bar", Strings.substringBetween("<foo>bar</foo>", "<foo>", "</foo>") );
    }

   

    //-----------------------------------------------------------------------
    @Test
    public void testCountMatches_String() {
        assertEquals(0, Strings.countMatches(null, null));
        assertEquals(0, Strings.countMatches("blah", null));
        assertEquals(0, Strings.countMatches(null, "DD"));

        assertEquals(0, Strings.countMatches("x", ""));
        assertEquals(0, Strings.countMatches("", ""));

        assertEquals(3,
             Strings.countMatches("one long someone sentence of one", "one"));
        assertEquals(0,
             Strings.countMatches("one long someone sentence of one", "two"));
        assertEquals(4,
             Strings.countMatches("oooooooooooo", "ooo"));
    }

    @Test
    public void testCountMatches_char() {
       
        assertEquals(4, Strings.countMatches("oooooooooooo", "ooo"));
    }

    //-----------------------------------------------------------------------

    @Test
    public void testStrip_StringString() {
        // null strip
        assertEquals(null, Strings.strip(null, null));
        assertEquals("", Strings.strip("", null));
        assertEquals("", Strings.strip("        ", null));
        assertEquals("abc", Strings.strip("  abc  ", null));
        assertEquals(StringsTest.NON_WHITESPACE,
            Strings.strip(StringsTest.WHITESPACE + StringsTest.NON_WHITESPACE + StringsTest.WHITESPACE, null));

        // "" strip
        assertEquals(null, Strings.strip(null, ""));
        assertEquals("", Strings.strip("", ""));
        assertEquals("        ", Strings.strip("        ", ""));
        assertEquals("  abc  ", Strings.strip("  abc  ", ""));
        assertEquals(StringsTest.WHITESPACE, Strings.strip(StringsTest.WHITESPACE, ""));

        // " " strip
        assertEquals(null, Strings.strip(null, " "));
        assertEquals("", Strings.strip("", " "));
        assertEquals("", Strings.strip("        ", " "));
        assertEquals("abc", Strings.strip("  abc  ", " "));

        // "ab" strip
        assertEquals(null, Strings.strip(null, "ab"));
        assertEquals("", Strings.strip("", "ab"));
        assertEquals("        ", Strings.strip("        ", "ab"));
        assertEquals("  abc  ", Strings.strip("  abc  ", "ab"));
        assertEquals("c", Strings.strip("abcabab", "ab"));
        assertEquals(StringsTest.WHITESPACE, Strings.strip(StringsTest.WHITESPACE, ""));
    }

    @Test
    public void testStripStart_StringString() {
        // null stripStart
        assertEquals(null, Strings.stripStart(null, null));
        assertEquals("", Strings.stripStart("", null));
        assertEquals("", Strings.stripStart("        ", null));
        assertEquals("abc  ", Strings.stripStart("  abc  ", null));
        assertEquals(StringsTest.NON_WHITESPACE + StringsTest.WHITESPACE,
            Strings.stripStart(StringsTest.WHITESPACE + StringsTest.NON_WHITESPACE + StringsTest.WHITESPACE, null));

        // "" stripStart
        assertEquals(null, Strings.stripStart(null, ""));
        assertEquals("", Strings.stripStart("", ""));
        assertEquals("        ", Strings.stripStart("        ", ""));
        assertEquals("  abc  ", Strings.stripStart("  abc  ", ""));
        assertEquals(StringsTest.WHITESPACE, Strings.stripStart(StringsTest.WHITESPACE, ""));

        // " " stripStart
        assertEquals(null, Strings.stripStart(null, " "));
        assertEquals("", Strings.stripStart("", " "));
        assertEquals("", Strings.stripStart("        ", " "));
        assertEquals("abc  ", Strings.stripStart("  abc  ", " "));

        // "ab" stripStart
        assertEquals(null, Strings.stripStart(null, "ab"));
        assertEquals("", Strings.stripStart("", "ab"));
        assertEquals("        ", Strings.stripStart("        ", "ab"));
        assertEquals("  abc  ", Strings.stripStart("  abc  ", "ab"));
        assertEquals("cabab", Strings.stripStart("abcabab", "ab"));
        assertEquals(StringsTest.WHITESPACE, Strings.stripStart(StringsTest.WHITESPACE, ""));
    }

    @Test
    public void testStripEnd_StringString() {
        // null stripEnd
        assertEquals(null, Strings.stripEnd(null, null));
        assertEquals("", Strings.stripEnd("", null));
        assertEquals("", Strings.stripEnd("        ", null));
        assertEquals("  abc", Strings.stripEnd("  abc  ", null));
        assertEquals(StringsTest.WHITESPACE + StringsTest.NON_WHITESPACE,
            Strings.stripEnd(StringsTest.WHITESPACE + StringsTest.NON_WHITESPACE + StringsTest.WHITESPACE, null));

        // "" stripEnd
        assertEquals(null, Strings.stripEnd(null, ""));
        assertEquals("", Strings.stripEnd("", ""));
        assertEquals("        ", Strings.stripEnd("        ", ""));
        assertEquals("  abc  ", Strings.stripEnd("  abc  ", ""));
        assertEquals(StringsTest.WHITESPACE, Strings.stripEnd(StringsTest.WHITESPACE, ""));

        // " " stripEnd
        assertEquals(null, Strings.stripEnd(null, " "));
        assertEquals("", Strings.stripEnd("", " "));
        assertEquals("", Strings.stripEnd("        ", " "));
        assertEquals("  abc", Strings.stripEnd("  abc  ", " "));

        // "ab" stripEnd
        assertEquals(null, Strings.stripEnd(null, "ab"));
        assertEquals("", Strings.stripEnd("", "ab"));
        assertEquals("        ", Strings.stripEnd("        ", "ab"));
        assertEquals("  abc  ", Strings.stripEnd("  abc  ", "ab"));
        assertEquals("abc", Strings.stripEnd("abcabab", "ab"));
        assertEquals(StringsTest.WHITESPACE, Strings.stripEnd(StringsTest.WHITESPACE, ""));
    }

    @Test
    public void testStripAll() {
        // test stripAll method, merely an array version of the above strip
        final String[] empty = new String[0];
        final String[] fooSpace = new String[] { "  "+FOO+"  ", "  "+FOO, FOO+"  " };
        final String[] fooDots = new String[] { ".."+FOO+"..", ".."+FOO, FOO+".." };
        final String[] foo = new String[] { FOO, FOO, FOO };

        assertNull(Strings.stripAll(null, null));
        assertArrayEquals(foo, Strings.stripAll(fooSpace, null));
        assertArrayEquals(foo, Strings.stripAll(fooDots, "."));
    }

   
}
