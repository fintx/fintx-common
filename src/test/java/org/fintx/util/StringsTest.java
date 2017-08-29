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
public class StringsTest {

   

    static final String WHITESPACE;
    static final String NON_WHITESPACE;
    static final String HARD_SPACE;
    static final String TRIMMABLE;
    static final String NON_TRIMMABLE;

    static {
        String ws = "";
        String nws = "";
        final String hs = String.valueOf(((char) 160));
        String tr = "";
        String ntr = "";
        for (int i = 0; i < Character.MAX_VALUE; i++) {
            if (Character.isWhitespace((char) i)) {
                ws += String.valueOf((char) i);
                if (i > 32) {
                    ntr += String.valueOf((char) i);
                }
            } else if (i < 40) {
                nws += String.valueOf((char) i);
            }
        }
        for (int i = 0; i <= 32; i++) {
            tr += String.valueOf((char) i);
        }
        WHITESPACE = ws;
        NON_WHITESPACE = nws;
        HARD_SPACE = hs;
        TRIMMABLE = tr;
        NON_TRIMMABLE = ntr;
    }

    private static final String[] ARRAY_LIST = {"foo", "bar", "baz"};
    private static final String[] EMPTY_ARRAY_LIST = {};
    private static final String[] NULL_ARRAY_LIST = {null};
    private static final Object[] NULL_TO_STRING_LIST = {
            new Object() {
                @Override
                public String toString() {
                    return null;
                }
            }
    };
    private static final String[] MIXED_ARRAY_LIST = {null, "", "foo"};
    private static final Object[] MIXED_TYPE_LIST = {"foo", Long.valueOf(2L)};
    private static final long[] LONG_PRIM_LIST = {1, 2};
    private static final int[] INT_PRIM_LIST = {1, 2};
    private static final byte[] BYTE_PRIM_LIST = {1, 2};
    private static final short[] SHORT_PRIM_LIST = {1, 2};
    private static final char[] CHAR_PRIM_LIST = {'1', '2'};
    private static final float[] FLOAT_PRIM_LIST = {1, 2};
    private static final double[] DOUBLE_PRIM_LIST = {1, 2};

    private static final String SEPARATOR = ",";
    private static final char SEPARATOR_CHAR = ';';

    private static final String TEXT_LIST = "foo,bar,baz";
    private static final String TEXT_LIST_CHAR = "foo;bar;baz";
    private static final String TEXT_LIST_NOSEP = "foobarbaz";

    private static final String FOO_UNCAP = "foo";
    private static final String FOO_CAP = "Foo";

    private static final String SENTENCE_UNCAP = "foo bar baz";
    private static final String SENTENCE_CAP = "Foo Bar Baz";

    //-----------------------------------------------------------------------
    @Test
    public void testConstructor() {
        assertNotNull(new Strings());
        final Constructor<?>[] cons = Strings.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(Strings.class.getModifiers()));
        assertFalse(Modifier.isFinal(Strings.class.getModifiers()));
    }

    @Test
    public void testCapitalize() {
        assertNull(Strings.capitalize(null));

        assertEquals("capitalize(empty-string) failed",
                "", Strings.capitalize(""));
        assertEquals("capitalize(single-char-string) failed",
                "X", Strings.capitalize("x"));
        assertEquals("capitalize(String) failed",
                FOO_CAP, Strings.capitalize(FOO_CAP));
        assertEquals("capitalize(string) failed",
                FOO_CAP, Strings.capitalize(FOO_UNCAP));

        assertEquals("capitalize(String) is not using TitleCase",
                "\u01C8", Strings.capitalize("\u01C9"));

        // Javadoc examples
        assertNull(Strings.capitalize(null));
        assertEquals("", Strings.capitalize(""));
        assertEquals("Cat", Strings.capitalize("cat"));
        assertEquals("CAt", Strings.capitalize("cAt"));
        assertEquals("'cat'", Strings.capitalize("'cat'"));
    }

    @Test
    public void testUnCapitalize() {
        assertNull(Strings.uncapitalize(null));

        assertEquals("uncapitalize(String) failed",
                FOO_UNCAP, Strings.uncapitalize(FOO_CAP));
        assertEquals("uncapitalize(string) failed",
                FOO_UNCAP, Strings.uncapitalize(FOO_UNCAP));
        assertEquals("uncapitalize(empty-string) failed",
                "", Strings.uncapitalize(""));
        assertEquals("uncapitalize(single-char-string) failed",
                "x", Strings.uncapitalize("X"));

        // Examples from uncapitalize Javadoc
        assertEquals("cat", Strings.uncapitalize("cat"));
        assertEquals("cat", Strings.uncapitalize("Cat"));
        assertEquals("cAT", Strings.uncapitalize("CAT"));
    }

    @Test
    public void testReCapitalize() {
        // reflection type of tests: Sentences.
        assertEquals("uncapitalize(capitalize(String)) failed",
                SENTENCE_UNCAP, Strings.uncapitalize(Strings.capitalize(SENTENCE_UNCAP)));
        assertEquals("capitalize(uncapitalize(String)) failed",
                SENTENCE_CAP, Strings.capitalize(Strings.uncapitalize(SENTENCE_CAP)));

        // reflection type of tests: One word.
        assertEquals("uncapitalize(capitalize(String)) failed",
                FOO_UNCAP, Strings.uncapitalize(Strings.capitalize(FOO_UNCAP)));
        assertEquals("capitalize(uncapitalize(String)) failed",
                FOO_CAP, Strings.capitalize(Strings.uncapitalize(FOO_CAP)));
    }

    //-----------------------------------------------------------------------
    @Test
    public void testJoin_Objects() {
        assertEquals("abc", Strings.join("a", "b", "c"));
        assertEquals("a", Strings.join(null, "", "a"));
        assertNull(Strings.join((Object[]) null));
    }

    @Test
    public void testJoin_Objectarray() {
//        assertNull(Strings.join(null)); // generates warning
        assertNull(Strings.join((Object[]) null)); // equivalent explicit cast
        // test additional varargs calls
        assertEquals("", Strings.join()); // empty array
        assertEquals(null, Strings.join((Object) null)); // => new Object[]{null}

        assertEquals("", Strings.join(EMPTY_ARRAY_LIST));
        assertEquals("", Strings.join(NULL_ARRAY_LIST));
        assertEquals("null", Strings.join(NULL_TO_STRING_LIST));
        assertEquals("abc", Strings.join("a", "b", "c"));
        assertEquals("a", Strings.join(null, "a", ""));
        assertEquals("foo", Strings.join(MIXED_ARRAY_LIST));
        assertEquals("foo2", Strings.join(MIXED_TYPE_LIST));
    }

    @Test
    public void testJoin_ArrayCharSeparator() {
        assertNull(Strings.join((Object[]) null, ','));
        assertEquals(TEXT_LIST_CHAR, Strings.join(ARRAY_LIST, SEPARATOR_CHAR));
        assertEquals("", Strings.join(EMPTY_ARRAY_LIST, SEPARATOR_CHAR));
        assertEquals(";;foo", Strings.join(MIXED_ARRAY_LIST, SEPARATOR_CHAR));
        assertEquals("foo;2", Strings.join(MIXED_TYPE_LIST, SEPARATOR_CHAR));

        assertNull(Strings.join((Object[]) null, ',', 0, 1));
        assertEquals("/", Strings.join(MIXED_ARRAY_LIST, '/', 0, MIXED_ARRAY_LIST.length - 1));
        assertEquals("foo", Strings.join(MIXED_TYPE_LIST, '/', 0, 1));
        assertEquals("null", Strings.join(NULL_TO_STRING_LIST, '/', 0, 1));
        assertEquals("foo/2", Strings.join(MIXED_TYPE_LIST, '/', 0, 2));
        assertEquals("2", Strings.join(MIXED_TYPE_LIST, '/', 1, 2));
        assertEquals("", Strings.join(MIXED_TYPE_LIST, '/', 2, 1));
    }

    @Test
    public void testJoin_ArrayOfChars() {
        assertNull(Strings.join((char[]) null, ','));
        assertEquals("1;2", Strings.join(CHAR_PRIM_LIST, SEPARATOR_CHAR));
        assertEquals("2", Strings.join(CHAR_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
        assertNull(Strings.join((char[]) null, SEPARATOR_CHAR, 0, 1));
        assertEquals(Strings.EMPTY, Strings.join(CHAR_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
        assertEquals(Strings.EMPTY, Strings.join(CHAR_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfBytes() {
        assertNull(Strings.join((byte[]) null, ','));
        assertEquals("1;2", Strings.join(BYTE_PRIM_LIST, SEPARATOR_CHAR));
        assertEquals("2", Strings.join(BYTE_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
        assertNull(Strings.join((byte[]) null, SEPARATOR_CHAR, 0, 1));
        assertEquals(Strings.EMPTY, Strings.join(BYTE_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
        assertEquals(Strings.EMPTY, Strings.join(BYTE_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfInts() {
        assertNull(Strings.join((int[]) null, ','));
        assertEquals("1;2", Strings.join(INT_PRIM_LIST, SEPARATOR_CHAR));
        assertEquals("2", Strings.join(INT_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
        assertNull(Strings.join((int[]) null, SEPARATOR_CHAR, 0, 1));
        assertEquals(Strings.EMPTY, Strings.join(INT_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
        assertEquals(Strings.EMPTY, Strings.join(INT_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfLongs() {
        assertNull(Strings.join((long[]) null, ','));
        assertEquals("1;2", Strings.join(LONG_PRIM_LIST, SEPARATOR_CHAR));
        assertEquals("2", Strings.join(LONG_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
        assertNull(Strings.join((long[]) null, SEPARATOR_CHAR, 0, 1));
        assertEquals(Strings.EMPTY, Strings.join(LONG_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
        assertEquals(Strings.EMPTY, Strings.join(LONG_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfFloats() {
        assertNull(Strings.join((float[]) null, ','));
        assertEquals("1.0;2.0", Strings.join(FLOAT_PRIM_LIST, SEPARATOR_CHAR));
        assertEquals("2.0", Strings.join(FLOAT_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
        assertNull(Strings.join((float[]) null, SEPARATOR_CHAR, 0, 1));
        assertEquals(Strings.EMPTY, Strings.join(FLOAT_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
        assertEquals(Strings.EMPTY, Strings.join(FLOAT_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfDoubles() {
        assertNull(Strings.join((double[]) null, ','));
        assertEquals("1.0;2.0", Strings.join(DOUBLE_PRIM_LIST, SEPARATOR_CHAR));
        assertEquals("2.0", Strings.join(DOUBLE_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
        assertNull(Strings.join((double[]) null, SEPARATOR_CHAR, 0, 1));
        assertEquals(Strings.EMPTY, Strings.join(DOUBLE_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
        assertEquals(Strings.EMPTY, Strings.join(DOUBLE_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfShorts() {
        assertNull(Strings.join((short[]) null, ','));
        assertEquals("1;2", Strings.join(SHORT_PRIM_LIST, SEPARATOR_CHAR));
        assertEquals("2", Strings.join(SHORT_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
        assertNull(Strings.join((short[]) null, SEPARATOR_CHAR, 0, 1));
        assertEquals(Strings.EMPTY, Strings.join(SHORT_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
        assertEquals(Strings.EMPTY, Strings.join(SHORT_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayString() {
        assertEquals(TEXT_LIST_NOSEP, Strings.join(ARRAY_LIST, null));
        assertEquals(TEXT_LIST_NOSEP, Strings.join(ARRAY_LIST, ""));

        assertEquals("", Strings.join(NULL_ARRAY_LIST, null));

        assertEquals("", Strings.join(EMPTY_ARRAY_LIST, null));
        assertEquals("", Strings.join(EMPTY_ARRAY_LIST, ""));
        assertEquals("", Strings.join(EMPTY_ARRAY_LIST, SEPARATOR));

        assertEquals(TEXT_LIST, Strings.join(ARRAY_LIST, SEPARATOR));
        assertEquals(",,foo", Strings.join(MIXED_ARRAY_LIST, SEPARATOR));
        assertEquals("foo,2", Strings.join(MIXED_TYPE_LIST, SEPARATOR));

        assertEquals("/", Strings.join(MIXED_ARRAY_LIST, "/", 0, MIXED_ARRAY_LIST.length - 1));
        assertEquals("", Strings.join(MIXED_ARRAY_LIST, "", 0, MIXED_ARRAY_LIST.length - 1));
        assertEquals("foo", Strings.join(MIXED_TYPE_LIST, "/", 0, 1));
        assertEquals("foo/2", Strings.join(MIXED_TYPE_LIST, "/", 0, 2));
        assertEquals("2", Strings.join(MIXED_TYPE_LIST, "/", 1, 2));
        assertEquals("", Strings.join(MIXED_TYPE_LIST, "/", 2, 1));
    }

    @Test
    public void testJoin_IteratorChar() {
        assertNull(Strings.join((Iterator<?>) null, ','));
        assertEquals(TEXT_LIST_CHAR, Strings.join(java.util.Arrays.asList(ARRAY_LIST).iterator(), SEPARATOR_CHAR));
        assertEquals("", Strings.join(java.util.Arrays.asList(NULL_ARRAY_LIST).iterator(), SEPARATOR_CHAR));
        assertEquals("", Strings.join(java.util.Arrays.asList(EMPTY_ARRAY_LIST).iterator(), SEPARATOR_CHAR));
        assertEquals("foo", Strings.join(Collections.singleton("foo").iterator(), 'x'));
    }

    @Test
    public void testJoin_IteratorString() {
        assertNull(Strings.join((Iterator<?>) null, null));
        assertEquals(TEXT_LIST_NOSEP, Strings.join(java.util.Arrays.asList(ARRAY_LIST).iterator(), null));
        assertEquals(TEXT_LIST_NOSEP, Strings.join(java.util.Arrays.asList(ARRAY_LIST).iterator(), ""));
        assertEquals("foo", Strings.join(Collections.singleton("foo").iterator(), "x"));
        assertEquals("foo", Strings.join(Collections.singleton("foo").iterator(), null));

        assertEquals("", Strings.join(java.util.Arrays.asList(NULL_ARRAY_LIST).iterator(), null));

        assertEquals("", Strings.join(java.util.Arrays.asList(EMPTY_ARRAY_LIST).iterator(), null));
        assertEquals("", Strings.join(java.util.Arrays.asList(EMPTY_ARRAY_LIST).iterator(), ""));
        assertEquals("", Strings.join(java.util.Arrays.asList(EMPTY_ARRAY_LIST).iterator(), SEPARATOR));

        assertEquals(TEXT_LIST, Strings.join(java.util.Arrays.asList(ARRAY_LIST).iterator(), SEPARATOR));

        assertNull(Strings.join(java.util.Arrays.asList(NULL_TO_STRING_LIST).iterator(), SEPARATOR));
    }

    @Test
    public void testJoin_IterableString() {
        assertNull(Strings.join((Iterable<?>) null, null));
        assertEquals(TEXT_LIST_NOSEP, Strings.join(java.util.Arrays.asList(ARRAY_LIST).iterator(), null));
        assertEquals(TEXT_LIST_NOSEP, Strings.join(java.util.Arrays.asList(ARRAY_LIST).iterator(), ""));
        assertEquals("foo", Strings.join(Collections.singleton("foo").iterator(), "x"));
        assertEquals("foo", Strings.join(Collections.singleton("foo").iterator(), null));

        assertEquals("", Strings.join(java.util.Arrays.asList(NULL_ARRAY_LIST).iterator(), null));

        assertEquals("", Strings.join(java.util.Arrays.asList(EMPTY_ARRAY_LIST).iterator(), null));
        assertEquals("", Strings.join(java.util.Arrays.asList(EMPTY_ARRAY_LIST).iterator(), ""));
        assertEquals("", Strings.join(java.util.Arrays.asList(EMPTY_ARRAY_LIST).iterator(), SEPARATOR));

        assertEquals(TEXT_LIST, Strings.join(java.util.Arrays.asList(ARRAY_LIST).iterator(), SEPARATOR));
    }

  


    @Test
    public void testSplit_String() {
        assertEquals(0, Strings.split("",'a').length);

        String str = "a b  .c";
        String[] res = Strings.split(str,null);
        assertEquals(4, res.length);
        assertEquals("a", res[0]);
        assertEquals("b", res[1]);
        assertEquals("", res[2]);
        assertEquals(".c", res[3]);

        str = " a ";
        res = Strings.split(str,null);
        assertEquals(3, res.length);
        assertEquals("a", res[1]);

        str = "a" + WHITESPACE + "b" + NON_WHITESPACE + "c";
        res = Strings.split(str,null,-1,false);
        assertEquals(2, res.length);
        assertEquals("a", res[0]);
        assertEquals("b" + NON_WHITESPACE + "c", res[1]);
    }

    @Test
    public void testSplit_StringChar() {
        assertNull(Strings.split(null, '.'));
        assertEquals(0, Strings.split("", '.').length);

        String str = "a.b.. c";
        String[] res = Strings.split(str, '.');
        assertEquals(4, res.length);
        assertEquals("a", res[0]);
        assertEquals("b", res[1]);
        assertEquals("", res[2]);
        assertEquals(" c", res[3]);

        str = ".a.";
        res = Strings.split(str, '.');
        assertEquals(3, res.length);
        assertEquals("a", res[1]);

        str = "a b c";
        res = Strings.split(str, ' ');
        assertEquals(3, res.length);
        assertEquals("a", res[0]);
        assertEquals("b", res[1]);
        assertEquals("c", res[2]);
    }

    @Test
    public void testSplit_StringString_StringStringInt() {
        assertNull(Strings.split(null, "."));
        
        assertNull(Strings.split(null,".", 3,true));

        assertEquals(0, Strings.split("", ".").length);
        assertEquals(0, Strings.split("", ".", 3,true).length);

        innerTestSplit('.', ".", ' ');
        innerTestSplit('.', ".", ',');
        innerTestSplit('.', ".,", 'x');
        for (int i = 0; i < WHITESPACE.length(); i++) {
            for (int j = 0; j < NON_WHITESPACE.length(); j++) {
                innerTestSplit(WHITESPACE.charAt(i), null, NON_WHITESPACE.charAt(j));
                innerTestSplit(WHITESPACE.charAt(i), String.valueOf(WHITESPACE.charAt(i)), NON_WHITESPACE.charAt(j));
            }
        }

        String[] results;
        final String[] expectedResults = {"ab", "de fg"};
        results = Strings.split("ab   de fg", null, 2,false);
        assertEquals(expectedResults.length, results.length);
        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(expectedResults[i], results[i]);
        }

        final String[] expectedResults2 = {"ab", "cd:ef"};
        results = Strings.split("ab:cd:ef", ":", 2,true);
        assertEquals(expectedResults2.length, results.length);
        for (int i = 0; i < expectedResults2.length; i++) {
            assertEquals(expectedResults2[i], results[i]);
        }
    }

    private void innerTestSplit(final char separator, final String sepStr, final char noMatch) {
        final String msg = "Failed on separator hex(" + Integer.toHexString(separator) +
                "), noMatch hex(" + Integer.toHexString(noMatch) + "), sepStr(" + sepStr + ")";

        final String str = "a" + separator + "b" + separator + separator + noMatch + "c";
        String[] res;
        // (str, sepStr)
        res = Strings.split(str, sepStr);
        assertEquals(msg, 4, res.length);
        assertEquals(msg, "a", res[0]);
        assertEquals(msg, "b", res[1]);
        assertEquals(msg, noMatch + "c", res[3]);

        final String str2 = separator + "a" + separator;
        res = Strings.split(str2, sepStr);
        assertEquals(msg, 3, res.length);
        assertEquals(msg, "a", res[1]);

        res = Strings.split(str, sepStr,-1,true);
        assertEquals(msg, 4, res.length);
        assertEquals(msg, "a", res[0]);
        assertEquals(msg, "b", res[1]);
        assertEquals(msg, noMatch + "c", res[3]);

        res = Strings.split(str, sepStr, 0,true);
        assertEquals(msg, 4, res.length);
        assertEquals(msg, "a", res[0]);
        assertEquals(msg, "b", res[1]);
        assertEquals(msg, noMatch + "c", res[3]);

        res = Strings.split(str, sepStr, 1,true);
        assertEquals(msg, 1, res.length);
        assertEquals(msg, str, res[0]);

        res = Strings.split(str, sepStr, 2,true);
        assertEquals(msg, 2, res.length);
        assertEquals(msg, "a", res[0]);
        assertEquals(msg, str.substring(2), res[1]);
    }

    @Test
    public void testSplitByWholeString_StringStringBoolean() {
        assertArrayEquals(null, Strings.splitByWholeSeparator(null, "."));

        assertEquals(0, Strings.splitByWholeSeparator("", ".").length);

        final String stringToSplitOnNulls = "ab   de fg";
        final String[] splitOnNullExpectedResults = {"ab", "de", "fg"};

        final String[] splitOnNullResults = Strings.splitByWholeSeparator(stringToSplitOnNulls, null);
        assertEquals(splitOnNullExpectedResults.length, splitOnNullResults.length);
        for (int i = 0; i < splitOnNullExpectedResults.length; i += 1) {
            assertEquals(splitOnNullExpectedResults[i], splitOnNullResults[i]);
        }

        final String stringToSplitOnCharactersAndString = "abstemiouslyaeiouyabstemiously";

        final String[] splitOnStringExpectedResults = {"abstemiously", "abstemiously"};
        final String[] splitOnStringResults = Strings.splitByWholeSeparator(stringToSplitOnCharactersAndString, "aeiouy");
        assertEquals(splitOnStringExpectedResults.length, splitOnStringResults.length);
        for (int i = 0; i < splitOnStringExpectedResults.length; i += 1) {
            assertEquals(splitOnStringExpectedResults[i], splitOnStringResults[i]);
        }

        final String[] splitWithMultipleSeparatorExpectedResults = {"ab", "cd", "ef"};
        final String[] splitWithMultipleSeparator = Strings.splitByWholeSeparator("ab:cd::ef", ":");
        assertEquals(splitWithMultipleSeparatorExpectedResults.length, splitWithMultipleSeparator.length);
        for (int i = 0; i < splitWithMultipleSeparatorExpectedResults.length; i++) {
            assertEquals(splitWithMultipleSeparatorExpectedResults[i], splitWithMultipleSeparator[i]);
        }
    }

    @Test
    public void testSplitByWholeString_StringStringBooleanInt() {
        assertArrayEquals(null, Strings.splitByWholeSeparator(null, ".", 3));

        assertEquals(0, Strings.splitByWholeSeparator("", ".", 3).length);

        final String stringToSplitOnNulls = "ab   de fg";
        final String[] splitOnNullExpectedResults = {"ab", "de fg"};
        //String[] splitOnNullExpectedResults = { "ab", "de" } ;

        final String[] splitOnNullResults = Strings.splitByWholeSeparator(stringToSplitOnNulls, null, 2);
        assertEquals(splitOnNullExpectedResults.length, splitOnNullResults.length);
        for (int i = 0; i < splitOnNullExpectedResults.length; i += 1) {
            assertEquals(splitOnNullExpectedResults[i], splitOnNullResults[i]);
        }

        final String stringToSplitOnCharactersAndString = "abstemiouslyaeiouyabstemiouslyaeiouyabstemiously";

        final String[] splitOnStringExpectedResults = {"abstemiously", "abstemiouslyaeiouyabstemiously"};
        //String[] splitOnStringExpectedResults = { "abstemiously", "abstemiously" } ;
        final String[] splitOnStringResults = Strings.splitByWholeSeparator(stringToSplitOnCharactersAndString, "aeiouy", 2);
        assertEquals(splitOnStringExpectedResults.length, splitOnStringResults.length);
        for (int i = 0; i < splitOnStringExpectedResults.length; i++) {
            assertEquals(splitOnStringExpectedResults[i], splitOnStringResults[i]);
        }
    }

    @Test
    public void testDeleteWhitespace_String() {
        assertNull(Strings.deleteWhitespace(null));
        assertEquals("", Strings.deleteWhitespace(""));
        assertEquals("", Strings.deleteWhitespace("  \u000C  \t\t\u001F\n\n \u000B  "));
        assertEquals("", Strings.deleteWhitespace(StringsTest.WHITESPACE));
        assertEquals(StringsTest.NON_WHITESPACE, Strings.deleteWhitespace(StringsTest.NON_WHITESPACE));
        // Note: u-2007 and u-000A both cause problems in the source code
        // it should ignore 2007 but delete 000A
        assertEquals("\u00A0\u202F", Strings.deleteWhitespace("  \u00A0  \t\t\n\n \u202F  "));
        assertEquals("\u00A0\u202F", Strings.deleteWhitespace("\u00A0\u202F"));
        assertEquals("test", Strings.deleteWhitespace("\u000Bt  \t\n\u0009e\rs\n\n   \tt"));
    }

    @Test
    public void testReplace_StringStringString() {
        assertNull(Strings.replace(null, null, null,-1,false));
        assertNull(Strings.replace(null, null, "any",-1,false));
        assertNull(Strings.replace(null, "any", null,-1,false));
        assertNull(Strings.replace(null, "any", "any",-1,false));

        assertEquals("", Strings.replace("", null, null,-1,false));
        assertEquals("", Strings.replace("", null, "any",-1,false));
        assertEquals("", Strings.replace("", "any", null,-1,false));
        assertEquals("", Strings.replace("", "any", "any",-1,false));

        assertEquals("FOO", Strings.replace("FOO", "", "any",-1,false));
        assertEquals("FOO", Strings.replace("FOO", null, "any",-1,false));
        assertEquals("FOO", Strings.replace("FOO", "F", null,-1,false));
        assertEquals("FOO", Strings.replace("FOO", null, null,-1,false));

        assertEquals("", Strings.replace("foofoofoo", "foo", "",-1,false));
        assertEquals("barbarbar", Strings.replace("foofoofoo", "foo", "bar",-1,false));
        assertEquals("farfarfar", Strings.replace("foofoofoo", "oo", "ar",-1,false));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString() {
        assertEquals(null, Strings.replace(null, null, null,-1,true));
        assertEquals(null, Strings.replace(null, null, "any",-1,true));
        assertEquals(null, Strings.replace(null, "any", null,-1,true));
        assertEquals(null, Strings.replace(null, "any", "any",-1,true));

        assertEquals("", Strings.replace("", null, null,-1,true));
        assertEquals("", Strings.replace("", null, "any",-1,true));
        assertEquals("", Strings.replace("", "any", null,-1,true));
        assertEquals("", Strings.replace("", "any", "any",-1,true));

        assertEquals("FOO", Strings.replace("FOO", "", "any",-1,true));
        assertEquals("FOO", Strings.replace("FOO", null, "any",-1,true));
        assertEquals("FOO", Strings.replace("FOO", "F", null,-1,true));
        assertEquals("FOO", Strings.replace("FOO", null, null,-1,true));

        assertEquals("", Strings.replace("foofoofoo", "foo", "",-1,true));
        assertEquals("barbarbar", Strings.replace("foofoofoo", "foo", "bar",-1,true));
        assertEquals("farfarfar", Strings.replace("foofoofoo", "oo", "ar",-1,true));

        // IgnoreCase
        assertEquals("", Strings.replace("foofoofoo", "FOO", "",-1,true));
        assertEquals("barbarbar", Strings.replace("fooFOOfoo", "foo", "bar",-1,true));
        assertEquals("farfarfar", Strings.replace("foofOOfoo", "OO", "ar",-1,true));
    }

    @Test
    public void testReplacePattern() {
        assertNull(Strings.replacePattern(null, "", ""));
        assertEquals("any", Strings.replacePattern("any", null, ""));
        assertEquals("any", Strings.replacePattern("any", "", null));

        assertEquals("zzz", Strings.replacePattern("", "", "zzz"));
        assertEquals("zzz", Strings.replacePattern("", ".*", "zzz"));
        assertEquals("", Strings.replacePattern("", ".+", "zzz"));

        assertEquals("z", Strings.replacePattern("<__>\n<__>", "<.*>", "z"));
        assertEquals("z", Strings.replacePattern("<__>\\n<__>", "<.*>", "z"));
        assertEquals("X", Strings.replacePattern("<A>\nxy\n</A>", "<A>.*</A>", "X"));

        assertEquals("ABC___123", Strings.replacePattern("ABCabc123", "[a-z]", "_"));
        assertEquals("ABC_123", Strings.replacePattern("ABCabc123", "[^A-Z0-9]+", "_"));
        assertEquals("ABC123", Strings.replacePattern("ABCabc123", "[^A-Z0-9]+", ""));
        assertEquals("Lorem_ipsum_dolor_sit",
                     Strings.replacePattern("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"));
    }

    @Test
    public void testReplace_StringStringStringInt() {
        assertNull(Strings.replace(null, null, null, 2));
        assertNull(Strings.replace(null, null, "any", 2));
        assertNull(Strings.replace(null, "any", null, 2));
        assertNull(Strings.replace(null, "any", "any", 2));

        assertEquals("", Strings.replace("", null, null, 2));
        assertEquals("", Strings.replace("", null, "any", 2));
        assertEquals("", Strings.replace("", "any", null, 2));
        assertEquals("", Strings.replace("", "any", "any", 2));

        final String str = new String(new char[]{'o', 'o', 'f', 'o', 'o'});
        assertSame(str, Strings.replace(str, "x", "", -1));

        assertEquals("f", Strings.replace("oofoo", "o", "", -1));
        assertEquals("oofoo", Strings.replace("oofoo", "o", "", 0));
        assertEquals("ofoo", Strings.replace("oofoo", "o", "", 1));
        assertEquals("foo", Strings.replace("oofoo", "o", "", 2));
        assertEquals("fo", Strings.replace("oofoo", "o", "", 3));
        assertEquals("f", Strings.replace("oofoo", "o", "", 4));

        assertEquals("f", Strings.replace("oofoo", "o", "", -5));
        assertEquals("f", Strings.replace("oofoo", "o", "", 1000));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringStringInt() {
        assertEquals(null, Strings.replaceIgnoreCase(null, null, null, 2));
        assertEquals(null, Strings.replaceIgnoreCase(null, null, "any", 2));
        assertEquals(null, Strings.replaceIgnoreCase(null, "any", null, 2));
        assertEquals(null, Strings.replaceIgnoreCase(null, "any", "any", 2));

        assertEquals("", Strings.replaceIgnoreCase("", null, null, 2));
        assertEquals("", Strings.replaceIgnoreCase("", null, "any", 2));
        assertEquals("", Strings.replaceIgnoreCase("", "any", null, 2));
        assertEquals("", Strings.replaceIgnoreCase("", "any", "any", 2));

        final String str = new String(new char[] { 'o', 'o', 'f', 'o', 'o' });
        assertSame(str, Strings.replaceIgnoreCase(str, "x", "", -1));

        assertEquals("f", Strings.replaceIgnoreCase("oofoo", "o", "", -1));
        assertEquals("oofoo", Strings.replaceIgnoreCase("oofoo", "o", "", 0));
        assertEquals("ofoo", Strings.replaceIgnoreCase("oofoo", "o", "", 1));
        assertEquals("foo", Strings.replaceIgnoreCase("oofoo", "o", "", 2));
        assertEquals("fo", Strings.replaceIgnoreCase("oofoo", "o", "", 3));
        assertEquals("f", Strings.replaceIgnoreCase("oofoo", "o", "", 4));

        assertEquals("f", Strings.replaceIgnoreCase("oofoo", "o", "", -5));
        assertEquals("f", Strings.replaceIgnoreCase("oofoo", "o", "", 1000));

        // IgnoreCase
        assertEquals("f", Strings.replaceIgnoreCase("oofoo", "O", "", -1));
        assertEquals("oofoo", Strings.replaceIgnoreCase("oofoo", "O", "", 0));
        assertEquals("ofoo", Strings.replaceIgnoreCase("oofoo", "O", "", 1));
        assertEquals("foo", Strings.replaceIgnoreCase("oofoo", "O", "", 2));
        assertEquals("fo", Strings.replaceIgnoreCase("oofoo", "O", "", 3));
        assertEquals("f", Strings.replaceIgnoreCase("oofoo", "O", "", 4));

        assertEquals("f", Strings.replaceIgnoreCase("oofoo", "O", "", -5));
        assertEquals("f", Strings.replaceIgnoreCase("oofoo", "O", "", 1000));
    }

    @Test
    public void testOverlay_StringStringIntInt() {
        assertNull(Strings.overlay(null, null, 2, 4));
        assertNull(Strings.overlay(null, null, -2, -4));

        assertEquals("", Strings.overlay("", null, 0, 0));
        assertEquals("", Strings.overlay("", "", 0, 0));
        assertEquals("zzzz", Strings.overlay("", "zzzz", 0, 0));
        assertEquals("zzzz", Strings.overlay("", "zzzz", 2, 4));
        assertEquals("zzzz", Strings.overlay("", "zzzz", -2, -4));

        assertEquals("abef", Strings.overlay("abcdef", null, 2, 4));
        assertEquals("abef", Strings.overlay("abcdef", null, 4, 2));
        assertEquals("abef", Strings.overlay("abcdef", "", 2, 4));
        assertEquals("abef", Strings.overlay("abcdef", "", 4, 2));
        assertEquals("abzzzzef", Strings.overlay("abcdef", "zzzz", 2, 4));
        assertEquals("abzzzzef", Strings.overlay("abcdef", "zzzz", 4, 2));

        assertEquals("zzzzef", Strings.overlay("abcdef", "zzzz", -1, 4));
        assertEquals("zzzzef", Strings.overlay("abcdef", "zzzz", 4, -1));
        assertEquals("zzzzabcdef", Strings.overlay("abcdef", "zzzz", -2, -1));
        assertEquals("zzzzabcdef", Strings.overlay("abcdef", "zzzz", -1, -2));
        assertEquals("abcdzzzz", Strings.overlay("abcdef", "zzzz", 4, 10));
        assertEquals("abcdzzzz", Strings.overlay("abcdef", "zzzz", 10, 4));
        assertEquals("abcdefzzzz", Strings.overlay("abcdef", "zzzz", 8, 10));
        assertEquals("abcdefzzzz", Strings.overlay("abcdef", "zzzz", 10, 8));
    }

    @Test
    public void testRepeat_StringInt() {
        assertNull(Strings.repeat(null, 2));
        assertEquals("", Strings.repeat("ab", 0));
        assertEquals("", Strings.repeat("", 3));
        assertEquals("aaa", Strings.repeat("a", 3));
        assertEquals("", Strings.repeat("a", -2));
        assertEquals("ababab", Strings.repeat("ab", 3));
        assertEquals("abcabcabc", Strings.repeat("abc", 3));
        final String str = Strings.repeat("a", 10000);  // bigger than pad limit
        assertEquals(10000, str.length());
        assertTrue(Strings.containsOnly(str, 'a'));
    }

    @Test
    public void testRepeat_StringStringInt() {
        assertNull(Strings.repeat(null, null, 2));
        assertNull(Strings.repeat(null, "x", 2));
        assertEquals("", Strings.repeat("", null, 2));

        assertEquals("", Strings.repeat("ab", "", 0));
        assertEquals("", Strings.repeat("", "", 2));

        assertEquals("xx", Strings.repeat("", "x", 3));

        assertEquals("?, ?, ?", Strings.repeat("?", ", ", 3));
    }

    @Test
    public void testRepeat_CharInt() {
        assertEquals("zzz", Strings.repeat('z', 3));
        assertEquals("", Strings.repeat('z', 0));
        assertEquals("", Strings.repeat('z', -2));
    }

    @Test
    public void testRightPad_StringIntChar() {
        assertNull(Strings.rightPad(null, 5, ' '));
        assertEquals("     ", Strings.rightPad("", 5, ' '));
        assertEquals("abc  ", Strings.rightPad("abc", 5, ' '));
        assertEquals("abc", Strings.rightPad("abc", 2, ' '));
        assertEquals("abc", Strings.rightPad("abc", -1, ' '));
        assertEquals("abcxx", Strings.rightPad("abc", 5, 'x'));
        final String str = Strings.rightPad("aaa", 10000, 'a');  // bigger than pad length
        assertEquals(10000, str.length());
        assertTrue(Strings.containsOnly(str, 'a'));
    }

    @Test
    public void testRightPad_StringIntString() {
        assertNull(Strings.rightPad(null, 5, "-+"));
        assertEquals("     ", Strings.rightPad("", 5, " "));
        assertNull(Strings.rightPad(null, 8, null));
        assertEquals("abc-+-+", Strings.rightPad("abc", 7, "-+"));
        assertEquals("abc-+~", Strings.rightPad("abc", 6, "-+~"));
        assertEquals("abc-+", Strings.rightPad("abc", 5, "-+~"));
        assertEquals("abc", Strings.rightPad("abc", 2, " "));
        assertEquals("abc", Strings.rightPad("abc", -1, " "));
        assertEquals("abc  ", Strings.rightPad("abc", 5, null));
        assertEquals("abc  ", Strings.rightPad("abc", 5, ""));
    }

    @Test
    public void testLeftPad_StringIntChar() {
        assertNull(Strings.leftPad(null, 5, ' '));
        assertEquals("     ", Strings.leftPad("", 5, ' '));
        assertEquals("  abc", Strings.leftPad("abc", 5, ' '));
        assertEquals("xxabc", Strings.leftPad("abc", 5, 'x'));
        assertEquals("\uffff\uffffabc", Strings.leftPad("abc", 5, '\uffff'));
        assertEquals("abc", Strings.leftPad("abc", 2, ' '));
        final String str = Strings.leftPad("aaa", 10000, 'a');  // bigger than pad length
        assertEquals(10000, str.length());
        assertTrue(Strings.containsOnly(str, 'a'));
    }

    @Test
    public void testLeftPad_StringIntString() {
        assertNull(Strings.leftPad(null, 5, "-+"));
        assertNull(Strings.leftPad(null, 5, null));
        assertEquals("     ", Strings.leftPad("", 5, " "));
        assertEquals("-+-+abc", Strings.leftPad("abc", 7, "-+"));
        assertEquals("-+~abc", Strings.leftPad("abc", 6, "-+~"));
        assertEquals("-+abc", Strings.leftPad("abc", 5, "-+~"));
        assertEquals("abc", Strings.leftPad("abc", 2, " "));
        assertEquals("abc", Strings.leftPad("abc", -1, " "));
        assertEquals("  abc", Strings.leftPad("abc", 5, null));
        assertEquals("  abc", Strings.leftPad("abc", 5, ""));
    }

    @Test
    public void testCenter_StringIntChar() {
        assertNull(Strings.center(null, -1, ' '));
        assertNull(Strings.center(null, 4, ' '));
        assertEquals("    ", Strings.center("", 4, ' '));
        assertEquals("ab", Strings.center("ab", 0, ' '));
        assertEquals("ab", Strings.center("ab", -1, ' '));
        assertEquals("ab", Strings.center("ab", 1, ' '));
        assertEquals("    ", Strings.center("", 4, ' '));
        assertEquals(" ab ", Strings.center("ab", 4, ' '));
        assertEquals("abcd", Strings.center("abcd", 2, ' '));
        assertEquals(" a  ", Strings.center("a", 4, ' '));
        assertEquals("  a  ", Strings.center("a", 5, ' '));
        assertEquals("xxaxx", Strings.center("a", 5, 'x'));
    }

    @Test
    public void testCenter_StringIntString() {
        assertNull(Strings.center(null, 4, null));
        assertNull(Strings.center(null, -1, " "));
        assertNull(Strings.center(null, 4, " "));
        assertEquals("    ", Strings.center("", 4, " "));
        assertEquals("ab", Strings.center("ab", 0, " "));
        assertEquals("ab", Strings.center("ab", -1, " "));
        assertEquals("ab", Strings.center("ab", 1, " "));
        assertEquals("    ", Strings.center("", 4, " "));
        assertEquals(" ab ", Strings.center("ab", 4, " "));
        assertEquals("abcd", Strings.center("abcd", 2, " "));
        assertEquals(" a  ", Strings.center("a", 4, " "));
        assertEquals("yayz", Strings.center("a", 4, "yz"));
        assertEquals("yzyayzy", Strings.center("a", 7, "yz"));
        assertEquals("  abc  ", Strings.center("abc", 7, null));
        assertEquals("  abc  ", Strings.center("abc", 7, ""));
    }

    //-----------------------------------------------------------------------
    @Test
    public void testDifference_StringString() {
        assertNull(Strings.difference(null, null));
        assertEquals("", Strings.difference("", ""));
        assertEquals("abc", Strings.difference("", "abc"));
        assertEquals("", Strings.difference("abc", ""));
        assertEquals("i am a robot", Strings.difference(null, "i am a robot"));
        assertEquals("i am a machine", Strings.difference("i am a machine", null));
        assertEquals("robot", Strings.difference("i am a machine", "i am a robot"));
        assertEquals("", Strings.difference("abc", "abc"));
        assertEquals("you are a robot", Strings.difference("i am a robot", "you are a robot"));
    }

    @Test
    public void testDifferenceAt_StringString() {
        assertEquals(-1, Strings.indexOfDifference(null, null));
        assertEquals(0, Strings.indexOfDifference(null, "i am a robot"));
        assertEquals(-1, Strings.indexOfDifference("", ""));
        assertEquals(0, Strings.indexOfDifference("", "abc"));
        assertEquals(0, Strings.indexOfDifference("abc", ""));
        assertEquals(0, Strings.indexOfDifference("i am a machine", null));
        assertEquals(7, Strings.indexOfDifference("i am a machine", "i am a robot"));
        assertEquals(-1, Strings.indexOfDifference("foo", "foo"));
        assertEquals(0, Strings.indexOfDifference("i am a robot", "you are a robot"));
        //System.out.println("indexOfDiff: " + Strings.indexOfDifference("i am a robot", "not machine"));
    }

    /**
     * A sanity check for {@link Strings#EMPTY}.
     */
    @Test
    public void testEMPTY() {
        assertNotNull(Strings.EMPTY);
        assertEquals("", Strings.EMPTY);
        assertEquals(0, Strings.EMPTY.length());
    }

    /**
     * Test for {@link Strings#isAllLowerCase(CharSequence)}.
     */
    @Test
    public void testIsAllLowerCase() {
        assertFalse(Strings.isAllLowerCase(null));
        assertFalse(Strings.isAllLowerCase(Strings.EMPTY));
        assertFalse(Strings.isAllLowerCase("  "));
        assertTrue(Strings.isAllLowerCase("abc"));
        assertFalse(Strings.isAllLowerCase("abc "));
        assertFalse(Strings.isAllLowerCase("abc\n"));
        assertFalse(Strings.isAllLowerCase("abC"));
        assertFalse(Strings.isAllLowerCase("ab c"));
        assertFalse(Strings.isAllLowerCase("ab1c"));
        assertFalse(Strings.isAllLowerCase("ab/c"));
    }

    /**
     * Test for {@link Strings#isAllUpperCase(CharSequence)}.
     */
    @Test
    public void testIsAllUpperCase() {
        assertFalse(Strings.isAllUpperCase(null));
        assertFalse(Strings.isAllUpperCase(Strings.EMPTY));
        assertFalse(Strings.isAllUpperCase("  "));
        assertTrue(Strings.isAllUpperCase("ABC"));
        assertFalse(Strings.isAllUpperCase("ABC "));
        assertFalse(Strings.isAllUpperCase("ABC\n"));
        assertFalse(Strings.isAllUpperCase("aBC"));
        assertFalse(Strings.isAllUpperCase("A C"));
        assertFalse(Strings.isAllUpperCase("A1C"));
        assertFalse(Strings.isAllUpperCase("A/C"));
    }

    /**
     * Test for {@link Strings#isMixedCase(CharSequence)}.
     */
    @Test
    public void testIsMixedCase() {
        assertFalse(Strings.isMixedCase(null));
        assertFalse(Strings.isMixedCase(Strings.EMPTY));
        assertFalse(Strings.isMixedCase(" "));
        assertFalse(Strings.isMixedCase("A"));
        assertFalse(Strings.isMixedCase("a"));
        assertFalse(Strings.isMixedCase("/"));
        assertFalse(Strings.isMixedCase("A/"));
        assertFalse(Strings.isMixedCase("/b"));
        assertFalse(Strings.isMixedCase("abc"));
        assertFalse(Strings.isMixedCase("ABC"));
        assertTrue(Strings.isMixedCase("aBc"));
        assertTrue(Strings.isMixedCase("aBc "));
        assertTrue(Strings.isMixedCase("A c"));
        assertTrue(Strings.isMixedCase("aBc\n"));
        assertTrue(Strings.isMixedCase("A1c"));
        assertTrue(Strings.isMixedCase("a/C"));
    }

    @Test
    public void testRemoveStart() {
        // Strings.removeStart("", *)        = ""
        assertNull(Strings.removeStart(null, null));
        assertNull(Strings.removeStart(null, ""));
        assertNull(Strings.removeStart(null, "a"));

        // Strings.removeStart(*, null)      = *
        assertEquals(Strings.removeStart("", null), "");
        assertEquals(Strings.removeStart("", ""), "");
        assertEquals(Strings.removeStart("", "a"), "");

        // All others:
        assertEquals(Strings.removeStart("www.domain.com", "www."), "domain.com");
        assertEquals(Strings.removeStart("domain.com", "www."), "domain.com");
        assertEquals(Strings.removeStart("domain.com", ""), "domain.com");
        assertEquals(Strings.removeStart("domain.com", null), "domain.com");
    }

    @Test
    public void testRemoveStartIgnoreCase() {
        // Strings.removeStart("", *)        = ""
        assertNull("removeStartIgnoreCase(null, null)", Strings.removeStartIgnoreCase(null, null));
        assertNull("removeStartIgnoreCase(null, \"\")", Strings.removeStartIgnoreCase(null, ""));
        assertNull("removeStartIgnoreCase(null, \"a\")", Strings.removeStartIgnoreCase(null, "a"));

        // Strings.removeStart(*, null)      = *
        assertEquals("removeStartIgnoreCase(\"\", null)", Strings.removeStartIgnoreCase("", null), "");
        assertEquals("removeStartIgnoreCase(\"\", \"\")", Strings.removeStartIgnoreCase("", ""), "");
        assertEquals("removeStartIgnoreCase(\"\", \"a\")", Strings.removeStartIgnoreCase("", "a"), "");

        // All others:
        assertEquals("removeStartIgnoreCase(\"www.domain.com\", \"www.\")", Strings.removeStartIgnoreCase("www.domain.com", "www."), "domain.com");
        assertEquals("removeStartIgnoreCase(\"domain.com\", \"www.\")", Strings.removeStartIgnoreCase("domain.com", "www."), "domain.com");
        assertEquals("removeStartIgnoreCase(\"domain.com\", \"\")", Strings.removeStartIgnoreCase("domain.com", ""), "domain.com");
        assertEquals("removeStartIgnoreCase(\"domain.com\", null)", Strings.removeStartIgnoreCase("domain.com", null), "domain.com");

        // Case insensitive:
        assertEquals("removeStartIgnoreCase(\"www.domain.com\", \"WWW.\")", Strings.removeStartIgnoreCase("www.domain.com", "WWW."), "domain.com");
    }

    @Test
    public void testRemoveEnd() {
        // Strings.removeEnd("", *)        = ""
        assertNull(Strings.removeEnd(null, null));
        assertNull(Strings.removeEnd(null, ""));
        assertNull(Strings.removeEnd(null, "a"));

        // Strings.removeEnd(*, null)      = *
        assertEquals(Strings.removeEnd("", null), "");
        assertEquals(Strings.removeEnd("", ""), "");
        assertEquals(Strings.removeEnd("", "a"), "");

        // All others:
        assertEquals(Strings.removeEnd("www.domain.com.", ".com"), "www.domain.com.");
        assertEquals(Strings.removeEnd("www.domain.com", ".com"), "www.domain");
        assertEquals(Strings.removeEnd("www.domain", ".com"), "www.domain");
        assertEquals(Strings.removeEnd("domain.com", ""), "domain.com");
        assertEquals(Strings.removeEnd("domain.com", null), "domain.com");
    }

    @Test
    public void testRemoveEndIgnoreCase() {
        // Strings.removeEndIgnoreCase("", *)        = ""
        assertNull("removeEndIgnoreCase(null, null)", Strings.removeEndIgnoreCase(null, null));
        assertNull("removeEndIgnoreCase(null, \"\")", Strings.removeEndIgnoreCase(null, ""));
        assertNull("removeEndIgnoreCase(null, \"a\")", Strings.removeEndIgnoreCase(null, "a"));

        // Strings.removeEnd(*, null)      = *
        assertEquals("removeEndIgnoreCase(\"\", null)", Strings.removeEndIgnoreCase("", null), "");
        assertEquals("removeEndIgnoreCase(\"\", \"\")", Strings.removeEndIgnoreCase("", ""), "");
        assertEquals("removeEndIgnoreCase(\"\", \"a\")", Strings.removeEndIgnoreCase("", "a"), "");

        // All others:
        assertEquals("removeEndIgnoreCase(\"www.domain.com.\", \".com\")", Strings.removeEndIgnoreCase("www.domain.com.", ".com"), "www.domain.com.");
        assertEquals("removeEndIgnoreCase(\"www.domain.com\", \".com\")", Strings.removeEndIgnoreCase("www.domain.com", ".com"), "www.domain");
        assertEquals("removeEndIgnoreCase(\"www.domain\", \".com\")", Strings.removeEndIgnoreCase("www.domain", ".com"), "www.domain");
        assertEquals("removeEndIgnoreCase(\"domain.com\", \"\")", Strings.removeEndIgnoreCase("domain.com", ""), "domain.com");
        assertEquals("removeEndIgnoreCase(\"domain.com\", null)", Strings.removeEndIgnoreCase("domain.com", null), "domain.com");

        // Case insensitive:
        assertEquals("removeEndIgnoreCase(\"www.domain.com\", \".COM\")", Strings.removeEndIgnoreCase("www.domain.com", ".COM"), "www.domain");
        assertEquals("removeEndIgnoreCase(\"www.domain.COM\", \".com\")", Strings.removeEndIgnoreCase("www.domain.COM", ".com"), "www.domain");
    }

    @Test
    public void testRemove_String() {
        // Strings.remove(null, *)        = null
        assertNull(Strings.remove(null, null));
        assertNull(Strings.remove(null, ""));
        assertNull(Strings.remove(null, "a"));

        // Strings.remove("", *)          = ""
        assertEquals("", Strings.remove("", null));
        assertEquals("", Strings.remove("", ""));
        assertEquals("", Strings.remove("", "a"));

        // Strings.remove(*, null)        = *
        assertNull(Strings.remove(null, null));
        assertEquals("", Strings.remove("", null));
        assertEquals("a", Strings.remove("a", null));

        // Strings.remove(*, "")          = *
        assertNull(Strings.remove(null, ""));
        assertEquals("", Strings.remove("", ""));
        assertEquals("a", Strings.remove("a", ""));

        // Strings.remove("queued", "ue") = "qd"
        assertEquals("qd", Strings.remove("queued", "ue"));

        // Strings.remove("queued", "zz") = "queued"
        assertEquals("queued", Strings.remove("queued", "zz"));
    }

    @Test
    public void testRemoveIgnoreCase_String() {
        // Strings.removeIgnoreCase(null, *) = null
        assertEquals(null, Strings.removeIgnoreCase(null, null));
        assertEquals(null, Strings.removeIgnoreCase(null, ""));
        assertEquals(null, Strings.removeIgnoreCase(null, "a"));

        // Strings.removeIgnoreCase("", *) = ""
        assertEquals("", Strings.removeIgnoreCase("", null));
        assertEquals("", Strings.removeIgnoreCase("", ""));
        assertEquals("", Strings.removeIgnoreCase("", "a"));

        // Strings.removeIgnoreCase(*, null) = *
        assertEquals(null, Strings.removeIgnoreCase(null, null));
        assertEquals("", Strings.removeIgnoreCase("", null));
        assertEquals("a", Strings.removeIgnoreCase("a", null));

        // Strings.removeIgnoreCase(*, "") = *
        assertEquals(null, Strings.removeIgnoreCase(null, ""));
        assertEquals("", Strings.removeIgnoreCase("", ""));
        assertEquals("a", Strings.removeIgnoreCase("a", ""));

        // Strings.removeIgnoreCase("queued", "ue") = "qd"
        assertEquals("qd", Strings.removeIgnoreCase("queued", "ue"));

        // Strings.removeIgnoreCase("queued", "zz") = "queued"
        assertEquals("queued", Strings.removeIgnoreCase("queued", "zz"));

        // IgnoreCase
        // Strings.removeIgnoreCase("quEUed", "UE") = "qd"
        assertEquals("qd", Strings.removeIgnoreCase("quEUed", "UE"));

        // Strings.removeIgnoreCase("queued", "zZ") = "queued"
        assertEquals("queued", Strings.removeIgnoreCase("queued", "zZ"));
    }

    @Test
    public void testRemove_char() {
        // Strings.remove(null, *)       = null
        assertNull(Strings.remove(null, 'a'));
        assertNull(Strings.remove(null, 'a'));
        assertNull(Strings.remove(null, 'a'));

        // Strings.remove("", *)          = ""
        assertEquals("", Strings.remove("", 'a'));
        assertEquals("", Strings.remove("", 'a'));
        assertEquals("", Strings.remove("", 'a'));

        // Strings.remove("queued", 'u') = "qeed"
        assertEquals("qeed", Strings.remove("queued", 'u'));

        // Strings.remove("queued", 'z') = "queued"
        assertEquals("queued", Strings.remove("queued", 'z'));
    }

    @Test
    public void testLANG666() {
        assertEquals("12", Strings.stripEnd("120.00", ".0"));
        assertEquals("121", Strings.stripEnd("121.00", ".0"));
    }

    /**
     * Tests {@link Strings#toString(byte[], String)}
     *
     * @throws java.io.UnsupportedEncodingException because the method under test max throw it
     * @see Strings#toString(byte[], String)
     */
    @Test
    public void testToString() throws UnsupportedEncodingException {
        final String expectedString = "The quick brown fox jumps over the lazy dog.";
        byte[] expectedBytes = expectedString.getBytes(Charset.defaultCharset());
        // sanity check start
        assertArrayEquals(expectedBytes, expectedString.getBytes());
        // sanity check end
        assertEquals(expectedString, Strings.toEncodedString(expectedBytes, null));
        assertEquals(expectedString, Strings.toEncodedString(expectedBytes, Charset.forName(System.getProperty("file.encoding"))));
        final String encoding = "UTF-16";
        expectedBytes = expectedString.getBytes(Charset.forName(encoding));
        assertEquals(expectedString, Strings.toEncodedString(expectedBytes, Charset.forName(encoding)));
    }

    // -----------------------------------------------------------------------

}
