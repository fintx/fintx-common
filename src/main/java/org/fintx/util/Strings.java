/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fintx.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 * Operations on {@link java.lang.String} that are <code>null</code> safe.
 * </p>
 *
 * <ul>
 * <li><b>IsEmpty/IsBlank</b> - checks if a String contains text</li>
 * <li><b>Trim/Strip</b> - removes leading and trailing whitespace</li>
 * <li><b>Equals</b> - compares two strings null-safe</li>
 * <li><b>startsWith</b> - check if a String starts with a prefix null-safe</li>
 * <li><b>endsWith</b> - check if a String ends with a suffix null-safe</li>
 * <li><b>IndexOf/LastIndexOf/Contains</b> - null-safe index-of checks
 * <li><b>IndexOfAny/LastIndexOfAny/IndexOfAnyBut/LastIndexOfAnyBut</b> - index-of any of a set of Strings</li>
 * <li><b>ContainsOnly/ContainsNone/ContainsAny</b> - does String contains only/none/any of these characters</li>
 * <li><b>Substring/Left/Right/Mid</b> - null-safe substring extractions</li>
 * <li><b>SubstringBefore/SubstringAfter/SubstringBetween</b> - substring extraction relative to other strings</li>
 * <li><b>Split/Join</b> - splits a String into an array of substrings and vice versa</li>
 * <li><b>Remove/Delete</b> - removes part of a String</li>
 * <li><b>Replace/Overlay</b> - Searches a String and replaces one String with another</li>
 * <li><b>Chomp/Chop</b> - removes the last part of a String</li>
 * <li><b>LeftPad/RightPad/Center/Repeat</b> - pads a String</li>
 * <li><b>UpperCase/LowerCase/SwapCase/Capitalize/Uncapitalize</b> - changes the case of a String</li>
 * <li><b>CountMatches</b> - counts the number of occurrences of one String in another</li>
 * <li><b>IsAlpha/IsNumeric/IsWhitespace/IsAsciiPrintable</b> - checks the characters in a String</li>
 * <li><b>DefaultString</b> - protects against a null input String</li>
 * <li><b>Reverse/ReverseDelimited</b> - reverses a String</li>
 * <li><b>Abbreviate</b> - abbreviates a string using ellipsis</li>
 * <li><b>Difference</b> - compares Strings and reports on their differences</li>
 * <li><b>LevensteinDistance</b> - the number of changes needed to change one String into another</li>
 * </ul>
 *
 * <p>
 * The <code>StringUtils</code> class defines certain words related to String handling.
 * </p>
 *
 * <ul>
 * <li>null - <code>null</code></li>
 * <li>empty - a zero-length string (<code>""</code>)</li>
 * <li>space - the space character (<code>' '</code>, char 32)</li>
 * <li>whitespace - the characters defined by {@link Character#isWhitespace(char)}</li>
 * <li>trim - the characters &lt;= 32 as in {@link String#trim()}</li>
 * </ul>
 *
 * <p>
 * <code>StringUtils</code> handles <code>null</code> input Strings quietly. That is to say that a <code>null</code> input will return <code>null</code>. Where
 * a <code>boolean</code> or <code>int</code> is being returned details vary by method.
 * </p>
 *
 * <p>
 * A side effect of the <code>null</code> handling is that a <code>NullPointerException</code> should be considered a bug in <code>StringUtils</code> (except
 * for deprecated methods).
 * </p>
 *
 * <p>
 * Methods in this class give sample code to explain their operation. The symbol <code>*</code> is used to indicate any input including <code>null</code>.
 * </p>
 *
 * <p>
 * #ThreadSafe#
 * </p>
 * 
 * @see java.lang.String
 * @author Apache Software Foundation
 * @author <a href="http://jakarta.apache.org/turbine/">Apache Jakarta Turbine</a>
 * @author <a href="mailto:jon@latchkey.com">Jon S. Stevens</a>
 * @author Daniel L. Rall
 * @author <a href="mailto:gcoladonato@yahoo.com">Greg Coladonato</a>
 * @author <a href="mailto:ed@apache.org">Ed Korthof</a>
 * @author <a href="mailto:rand_mcneely@yahoo.com">Rand McNeely</a>
 * @author <a href="mailto:fredrik@westermarck.com">Fredrik Westermarck</a>
 * @author Holger Krauth
 * @author <a href="mailto:alex@purpletech.com">Alexander Day Chaffee</a>
 * @author <a href="mailto:hps@intermeta.de">Henning P. Schmiedehausen</a>
 * @author Arun Mammen Thomas
 * @author Gary Gregory
 * @author Phil Steitz
 * @author Al Chou
 * @author Michael Davey
 * @author Reuben Sivan
 * @author Chris Hyzer
 * @author Scott Johnson
 * @since 1.0
 * @version $Id: StringUtils.java 1058365 2011-01-13 00:04:49Z niallp $
 */
// @Immutable
public class Strings {
    // Performance testing notes (JDK 1.4, Jul03, scolebourne)
    // Whitespace:
    // Character.isWhitespace() is faster than WHITESPACE.indexOf()
    // where WHITESPACE is a string of all whitespace characters
    //
    // Character access:
    // String.charAt(n) versus toCharArray(), then array[n]
    // String.charAt(n) is about 15% worse for a 10K string
    // They are about equal for a length 50 string
    // String.charAt(n) is about 4 times better for a length 3 string
    // String.charAt(n) is best bet overall
    //
    // Append:
    // String.concat about twice as fast as StringBuffer.append
    // (not sure who tested this)

    /**
     * The empty String <code>""</code>.
     * 
     * @since 2.0
     */
    public static final String EMPTY = "";
    /**
     * The empty String <code>""</code>.
     * 
     * @since 2.0
     */
    public static final String[] EMPTY_ARRAY = new String[0];

    /**
     * Represents a failed index search.
     * 
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>
     * The maximum size to which the padding constant(s) can expand.
     * </p>
     */
    private static final int PAD_LIMIT = 8192;

    /**
     * <p>
     * <code>StringUtils</code> instances should NOT be constructed in standard programming. Instead, the class should be used as
     * <code>StringUtils.trim(" foo ");</code>.
     * </p>
     *
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance to operate.
     * </p>
     */
    public Strings() {
        super();
    }

    /**
     * Check whether the string is null
     * 
     * @param str the string to be check
     * @return boolean the result true empty; false not empty
     */
    public static boolean isEmpty(String str) {
        if (str != null && !str.trim().equals("")) {
            return false;
        }
        return true;
    }

    // Empty checks

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Strips any of a set of characters from the start and end of a String. This is similar to {@link String#trim()} but allows the characters to be stripped
     * to be controlled.
     * </p>
     *
     * <p>
     * A <code>null</code> input String returns <code>null</code>. An empty string ("") input returns the empty string.
     * </p>
     *
     * <p>
     * If the stripChars String is <code>null</code>, whitespace is stripped as defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.strip(null, *)          = null
     * StringUtils.strip("", *)            = ""
     * StringUtils.strip("abc", null)      = "abc"
     * StringUtils.strip("  abc", null)    = "abc"
     * StringUtils.strip("abc  ", null)    = "abc"
     * StringUtils.strip(" abc ", null)    = "abc"
     * StringUtils.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str the String to remove characters from, may be null
     * @param stripChars the characters to remove, null treated as whitespace
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String strip(String str, String stripChars) {
        if (isEmpty(str)) {
            return str;
        }
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }

    /**
     * <p>
     * Strips any of a set of characters from the start of a String.
     * </p>
     *
     * <p>
     * A <code>null</code> input String returns <code>null</code>. An empty string ("") input returns the empty string.
     * </p>
     *
     * <p>
     * If the stripChars String is <code>null</code>, whitespace is stripped as defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.stripStart(null, *)          = null
     * StringUtils.stripStart("", *)            = ""
     * StringUtils.stripStart("abc", "")        = "abc"
     * StringUtils.stripStart("abc", null)      = "abc"
     * StringUtils.stripStart("  abc", null)    = "abc"
     * StringUtils.stripStart("abc  ", null)    = "abc  "
     * StringUtils.stripStart(" abc ", null)    = "abc "
     * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str the String to remove characters from, may be null
     * @param stripChars the characters to remove, null treated as whitespace
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != INDEX_NOT_FOUND)) {
                start++;
            }
        }
        return str.substring(start);
    }

    /**
     * <p>
     * Strips any of a set of characters from the end of a String.
     * </p>
     *
     * <p>
     * A <code>null</code> input String returns <code>null</code>. An empty string ("") input returns the empty string.
     * </p>
     *
     * <p>
     * If the stripChars String is <code>null</code>, whitespace is stripped as defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.stripEnd(null, *)          = null
     * StringUtils.stripEnd("", *)            = ""
     * StringUtils.stripEnd("abc", "")        = "abc"
     * StringUtils.stripEnd("abc", null)      = "abc"
     * StringUtils.stripEnd("  abc", null)    = "  abc"
     * StringUtils.stripEnd("abc  ", null)    = "abc"
     * StringUtils.stripEnd(" abc ", null)    = " abc"
     * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     * StringUtils.stripEnd("120.00", ".0")   = "12"
     * </pre>
     *
     * @param str the String to remove characters from, may be null
     * @param stripChars the set of characters to remove, null treated as whitespace
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != INDEX_NOT_FOUND)) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    /**
     * <p>
     * Strips any of a set of characters from the start and end of every String in an array.
     * </p>
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <p>
     * A new array is returned each time, except for length zero. A <code>null</code> array will return <code>null</code>. An empty array will return itself. A
     * <code>null</code> array entry will be ignored. A <code>null</code> stripChars will strip whitespace as defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.stripAll(null, *)                = null
     * StringUtils.stripAll([], *)                  = []
     * StringUtils.stripAll(["abc", "  abc"], null) = ["abc", "abc"]
     * StringUtils.stripAll(["abc  ", null], null)  = ["abc", null]
     * StringUtils.stripAll(["abc  ", null], "yz")  = ["abc  ", null]
     * StringUtils.stripAll(["yabcz", null], "yz")  = ["abc", null]
     * </pre>
     *
     * @param strs the array to remove characters from, may be null
     * @param stripChars the characters to remove, null treated as whitespace
     * @return the stripped Strings, <code>null</code> if null array input
     */
    public static String[] stripAll(String[] strs, String stripChars) {
        int strsLen;
        if (strs == null || (strsLen = strs.length) == 0) {
            return strs;
        }
        String[] newArr = new String[strsLen];
        for (int i = 0; i < strsLen; i++) {
            newArr[i] = strip(strs[i], stripChars);
        }
        return newArr;
    }

    // Equals
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Compares two Strings, returning <code>true</code> if they are equal.
     * </p>
     *
     * <p>
     * <code>null</code>s are handled without exceptions. Two <code>null</code> references are considered to be equal. The comparison is case sensitive.
     * </p>
     *
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @see java.lang.String#equals(Object)
     * @param str1 the first String, may be null
     * @param str2 the second String, may be null
     * @return <code>true</code> if the Strings are equal, case sensitive, or both <code>null</code>
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * <p>
     * Finds the first index within a String from a start position, handling <code>null</code>. This method uses {@link String#indexOf(int, int)}.
     * </p>
     *
     * <p>
     * A <code>null</code> or empty ("") String will return <code>(INDEX_NOT_FOUND) -1</code>. A negative start position is treated as zero. A start position
     * greater than the string length returns <code>-1</code>.
     * </p>
     *
     * <pre>
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf("", *, *)            = -1
     * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
     * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
     * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
     * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchChar the character to find
     * @param startPos the start position, negative treated as zero
     * @return the first index of the search character, -1 if no match or <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, char searchChar, int startPos) {
        if (isEmpty(str)) {
            return INDEX_NOT_FOUND;
        }
        return str.indexOf(searchChar, startPos);
    }

    /**
     * <p>
     * Finds the n-th index within a String, handling <code>null</code>. This method uses {@link String#indexOf(String)} .
     * </p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>.
     * </p>
     *
     * <pre>
     * StringUtils.ordinalIndexOf(null, *, *)          = -1
     * StringUtils.ordinalIndexOf(*, null, *)          = -1
     * StringUtils.ordinalIndexOf("", "", *)           = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "a", 1)  = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "a", 2)  = 1
     * StringUtils.ordinalIndexOf("aabaabaa", "b", 1)  = 2
     * StringUtils.ordinalIndexOf("aabaabaa", "b", 2)  = 5
     * StringUtils.ordinalIndexOf("aabaabaa", "ab", 1) = 1
     * StringUtils.ordinalIndexOf("aabaabaa", "ab", 2) = 4
     * StringUtils.ordinalIndexOf("aabaabaa", "", 1)   = 0
     * StringUtils.ordinalIndexOf("aabaabaa", "", 2)   = 0
     * </pre>
     *
     * <p>
     * Note that 'head(String str, int n)' may be implemented as:
     * </p>
     *
     * <pre>
     * str.substring(0, lastOrdinalIndexOf(str, "\n", n))
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @param ordinal the n-th <code>searchStr</code> to find
     * @return the n-th index of the search String, <code>-1</code> (<code>INDEX_NOT_FOUND</code>) if no match or <code>null</code> string input
     * @since 2.1
     */
    public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, false);
    }

    /**
     * <p>
     * Finds the n-th index within a String, handling <code>null</code>. This method uses {@link String#indexOf(String)} .
     * </p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>.
     * </p>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @param ordinal the n-th <code>searchStr</code> to find
     * @param lastIndex true if lastOrdinalIndexOf() otherwise false if ordinalIndexOf()
     * @return the n-th index of the search String, <code>-1</code> (<code>INDEX_NOT_FOUND</code>) if no match or <code>null</code> string input
     */
    // Shared code between ordinalIndexOf(String,String,int) and lastOrdinalIndexOf(String,String,int)
    private static int ordinalIndexOf(String str, String searchStr, int ordinal, boolean lastIndex) {
        if (str == null || searchStr == null || ordinal <= 0) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return lastIndex ? str.length() : 0;
        }
        int found = 0;
        int index = lastIndex ? str.length() : INDEX_NOT_FOUND;
        do {
            if (lastIndex) {
                index = str.lastIndexOf(searchStr, index - 1);
            } else {
                index = str.indexOf(searchStr, index + 1);
            }
            if (index < 0) {
                return index;
            }
            found++;
        } while (found < ordinal);
        return index;
    }

    /**
     * <p>
     * Finds the first index within a String, handling <code>null</code>. This method uses {@link String#indexOf(String, int)}.
     * </p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start position is treated as zero. An empty ("") search String always matches. A start
     * position greater than the string length only matches an empty search String.
     * </p>
     *
     * <pre>
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf(*, null, *)          = -1
     * StringUtils.indexOf("", "", 0)           = 0
     * StringUtils.indexOf("", *, 0)            = -1 (except when * = "")
     * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtils.indexOf("aabaabaa", "b", -1) = 2
     * StringUtils.indexOf("aabaabaa", "", 2)   = 2
     * StringUtils.indexOf("abc", "", 9)        = 3
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @param startPos the start position, negative treated as zero
     * @return the first index of the search String, -1 if no match or <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        // JDK1.2/JDK1.3 have a bug, when startPos > str.length for "", hence
        if (searchStr.length() == 0 && startPos >= str.length()) {
            return str.length();
        }
        return str.indexOf(searchStr, startPos);
    }

    /**
     * <p>
     * Case in-sensitive find of the first index within a String from the specified position.
     * </p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start position is treated as zero. An empty ("") search String always matches. A start
     * position greater than the string length only matches an empty search String.
     * </p>
     *
     * <pre>
     * StringUtils.indexOfIgnoreCase(null, *, *)          = -1
     * StringUtils.indexOfIgnoreCase(*, null, *)          = -1
     * StringUtils.indexOfIgnoreCase("", "", 0)           = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
     * StringUtils.indexOfIgnoreCase("abc", "", 9)        = 3
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @param startPos the start position, negative treated as zero
     * @return the first index of the search String, -1 if no match or <code>null</code> string input
     * @since 2.5
     */
    public static int indexOfIgnoreCase(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        int endLimit = (str.length() - searchStr.length()) + 1;
        if (startPos > endLimit) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }
        for (int i = startPos; i < endLimit; i++) {
            if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>
     * Finds the last index within a String from a start position, handling <code>null</code>. This method uses {@link String#lastIndexOf(int, int)}.
     * </p>
     *
     * <p>
     * A <code>null</code> or empty ("") String will return <code>-1</code>. A negative start position returns <code>-1</code>. A start position greater than
     * the string length searches the whole string.
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf("", *,  *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
     * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchChar the character to find
     * @param startPos the start position
     * @return the last index of the search character, -1 if no match or <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, char searchChar, int startPos) {
        if (isEmpty(str)) {
            return INDEX_NOT_FOUND;
        }
        return str.lastIndexOf(searchChar, startPos);
    }

    /**
     * <p>
     * Finds the n-th last index within a String, handling <code>null</code>. This method uses {@link String#lastIndexOf(String)}.
     * </p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>.
     * </p>
     *
     * <pre>
     * StringUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * StringUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * StringUtils.lastOrdinalIndexOf("", "", *)           = 0
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
     * </pre>
     *
     * <p>
     * Note that 'tail(String str, int n)' may be implemented as:
     * </p>
     *
     * <pre>
     * str.substring(lastOrdinalIndexOf(str, "\n", n) + 1)
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @param ordinal the n-th last <code>searchStr</code> to find
     * @return the n-th last index of the search String, <code>-1</code> (<code>INDEX_NOT_FOUND</code>) if no match or <code>null</code> string input
     * @since 2.5
     */
    public static int lastOrdinalIndexOf(String str, String searchStr, int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, true);
    }

    /**
     * <p>
     * Finds the first index within a String, handling <code>null</code>. This method uses {@link String#lastIndexOf(String, int)}.
     * </p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start position returns <code>-1</code>. An empty ("") search String always matches
     * unless the start position is negative. A start position greater than the string length searches the whole string.
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf(*, null, *)          = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
     * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
     * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @param startPos the start position, negative treated as zero
     * @return the first index of the search String, -1 if no match or <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        return str.lastIndexOf(searchStr, startPos);
    }

    /**
     * <p>
     * Case in-sensitive find of the last index within a String from the specified position.
     * </p>
     *
     * <p>
     * A <code>null</code> String will return <code>-1</code>. A negative start position returns <code>-1</code>. An empty ("") search String always matches
     * unless the start position is negative. A start position greater than the string length searches the whole string.
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOfIgnoreCase(null, *, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase(*, null, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @param startPos the start position
     * @return the first index of the search String, -1 if no match or <code>null</code> string input
     * @since 2.5
     */
    public static int lastIndexOfIgnoreCase(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        if (startPos > (str.length() - searchStr.length())) {
            startPos = str.length() - searchStr.length();
        }
        if (startPos < 0) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }

        for (int i = startPos; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    // Contains
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Checks if String contains a search character, handling <code>null</code>. This method uses {@link String#indexOf(int)}.
     * </p>
     *
     * <p>
     * A <code>null</code> or empty ("") String will return <code>false</code>.
     * </p>
     *
     * <pre>
     * StringUtils.contains(null, *)    = false
     * StringUtils.contains("", *)      = false
     * StringUtils.contains("abc", 'a') = true
     * StringUtils.contains("abc", 'z') = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchChar the character to find
     * @return true if the String contains the search character, false if not or <code>null</code> string input
     * @since 2.0
     */
    public static boolean contains(String str, char searchChar) {
        if (isEmpty(str)) {
            return false;
        }
        return str.indexOf(searchChar) >= 0;
    }

    /**
     * <p>
     * Checks if String contains a search String, handling <code>null</code>. This method uses {@link String#indexOf(String)}.
     * </p>
     *
     * <p>
     * A <code>null</code> String will return <code>false</code>.
     * </p>
     *
     * <pre>
     * StringUtils.contains(null, *)     = false
     * StringUtils.contains(*, null)     = false
     * StringUtils.contains("", "")      = true
     * StringUtils.contains("abc", "")   = true
     * StringUtils.contains("abc", "a")  = true
     * StringUtils.contains("abc", "z")  = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @param searchStr the String to find, may be null
     * @return true if the String contains the search String, false if not or <code>null</code> string input
     * @since 2.0
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }

    /**
     * <p>
     * Gets a substring from the specified String avoiding exceptions.
     * </p>
     *
     * <p>
     * A negative start position can be used to start/end <code>n</code> characters from the end of the String.
     * </p>
     *
     * <p>
     * The returned substring starts with the character in the <code>start</code> position and ends before the <code>end</code> position. All position counting
     * is zero-based -- i.e., to start at the beginning of the string use <code>start = 0</code>. Negative start and end positions can be used to specify
     * offsets relative to the end of the String.
     * </p>
     *
     * <p>
     * If <code>start</code> is not strictly to the left of <code>end</code>, "" is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param str the String to get the substring from, may be null
     * @param start the position to start from, negative means count back from the end of the String by this many characters
     * @param end the position to end at (exclusive), negative means count back from the end of the String by this many characters
     * @return substring from start position to end positon, <code>null</code> if null String input
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    // Left/Right/Mid
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets the leftmost <code>len</code> characters of a String.
     * </p>
     *
     * <p>
     * If <code>len</code> characters are not available, or the String is <code>null</code>, the String will be returned without an exception. An empty String
     * is returned if len is negative.
     * </p>
     *
     * <pre>
     * StringUtils.left(null, *)    = null
     * StringUtils.left(*, -ve)     = ""
     * StringUtils.left("", *)      = ""
     * StringUtils.left("abc", 0)   = ""
     * StringUtils.left("abc", 2)   = "ab"
     * StringUtils.left("abc", 4)   = "abc"
     * </pre>
     *
     * @param str the String to get the leftmost characters from, may be null
     * @param len the length of the required String
     * @return the leftmost characters, <code>null</code> if null String input
     */
    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    /**
     * <p>
     * Gets the rightmost <code>len</code> characters of a String.
     * </p>
     *
     * <p>
     * If <code>len</code> characters are not available, or the String is <code>null</code>, the String will be returned without an an exception. An empty
     * String is returned if len is negative.
     * </p>
     *
     * <pre>
     * StringUtils.right(null, *)    = null
     * StringUtils.right(*, -ve)     = ""
     * StringUtils.right("", *)      = ""
     * StringUtils.right("abc", 0)   = ""
     * StringUtils.right("abc", 2)   = "bc"
     * StringUtils.right("abc", 4)   = "abc"
     * </pre>
     *
     * @param str the String to get the rightmost characters from, may be null
     * @param len the length of the required String
     * @return the rightmost characters, <code>null</code> if null String input
     */
    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    /**
     * <p>
     * Gets <code>len</code> characters from the middle of a String.
     * </p>
     *
     * <p>
     * If <code>len</code> characters are not available, the remainder of the String will be returned without an exception. If the String is <code>null</code>,
     * <code>null</code> will be returned. An empty String is returned if len is negative or exceeds the length of <code>str</code>.
     * </p>
     *
     * <pre>
     * StringUtils.mid(null, *, *)    = null
     * StringUtils.mid(*, *, -ve)     = ""
     * StringUtils.mid("", 0, *)      = ""
     * StringUtils.mid("abc", 0, 2)   = "ab"
     * StringUtils.mid("abc", 0, 4)   = "abc"
     * StringUtils.mid("abc", 2, 4)   = "c"
     * StringUtils.mid("abc", 4, 2)   = ""
     * StringUtils.mid("abc", -2, 2)  = "ab"
     * </pre>
     *
     * @param str the String to get the characters from, may be null
     * @param pos the position to start from, negative treated as zero
     * @param len the length of the required String
     * @return the middle characters, <code>null</code> if null String input
     */
    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0 || pos > str.length()) {
            return EMPTY;
        }
        if (pos < 0) {
            pos = 0;
        }
        if (str.length() <= (pos + len)) {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }

    // SubStringAfter/SubStringBefore
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets the substring before the first occurrence of a separator. The separator is not returned.
     * </p>
     *
     * <p>
     * A <code>null</code> string input will return <code>null</code>. An empty ("") string input will return the empty string. A <code>null</code> separator
     * will return the input string.
     * </p>
     *
     * <p>
     * If nothing is found, the string input is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substringBefore(null, *)      = null
     * StringUtils.substringBefore("", *)        = ""
     * StringUtils.substringBefore("abc", "a")   = ""
     * StringUtils.substringBefore("abcba", "b") = "a"
     * StringUtils.substringBefore("abc", "c")   = "ab"
     * StringUtils.substringBefore("abc", "d")   = "abc"
     * StringUtils.substringBefore("abc", "")    = ""
     * StringUtils.substringBefore("abc", null)  = "abc"
     * </pre>
     *
     * @param str the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring before the first occurrence of the separator, <code>null</code> if null String input
     * @since 2.0
     */
    public static String substringBefore(String str, String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.length() == 0) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * <p>
     * Gets the substring after the first occurrence of a separator. The separator is not returned.
     * </p>
     *
     * <p>
     * A <code>null</code> string input will return <code>null</code>. An empty ("") string input will return the empty string. A <code>null</code> separator
     * will return the empty string if the input string is not <code>null</code>.
     * </p>
     *
     * <p>
     * If nothing is found, the empty string is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substringAfter(null, *)      = null
     * StringUtils.substringAfter("", *)        = ""
     * StringUtils.substringAfter(*, null)      = ""
     * StringUtils.substringAfter("abc", "a")   = "bc"
     * StringUtils.substringAfter("abcba", "b") = "cba"
     * StringUtils.substringAfter("abc", "c")   = ""
     * StringUtils.substringAfter("abc", "d")   = ""
     * StringUtils.substringAfter("abc", "")    = "abc"
     * </pre>
     *
     * @param str the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring after the first occurrence of the separator, <code>null</code> if null String input
     * @since 2.0
     */
    public static String substringAfter(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * <p>
     * Gets the substring before the last occurrence of a separator. The separator is not returned.
     * </p>
     *
     * <p>
     * A <code>null</code> string input will return <code>null</code>. An empty ("") string input will return the empty string. An empty or <code>null</code>
     * separator will return the input string.
     * </p>
     *
     * <p>
     * If nothing is found, the string input is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substringBeforeLast(null, *)      = null
     * StringUtils.substringBeforeLast("", *)        = ""
     * StringUtils.substringBeforeLast("abcba", "b") = "abc"
     * StringUtils.substringBeforeLast("abc", "c")   = "ab"
     * StringUtils.substringBeforeLast("a", "a")     = ""
     * StringUtils.substringBeforeLast("a", "z")     = "a"
     * StringUtils.substringBeforeLast("a", null)    = "a"
     * StringUtils.substringBeforeLast("a", "")      = "a"
     * </pre>
     *
     * @param str the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring before the last occurrence of the separator, <code>null</code> if null String input
     * @since 2.0
     */
    public static String substringBeforeLast(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * <p>
     * Gets the substring after the last occurrence of a separator. The separator is not returned.
     * </p>
     *
     * <p>
     * A <code>null</code> string input will return <code>null</code>. An empty ("") string input will return the empty string. An empty or <code>null</code>
     * separator will return the empty string if the input string is not <code>null</code>.
     * </p>
     *
     * <p>
     * If nothing is found, the empty string is returned.
     * </p>
     *
     * <pre>
     * StringUtils.substringAfterLast(null, *)      = null
     * StringUtils.substringAfterLast("", *)        = ""
     * StringUtils.substringAfterLast(*, "")        = ""
     * StringUtils.substringAfterLast(*, null)      = ""
     * StringUtils.substringAfterLast("abc", "a")   = "bc"
     * StringUtils.substringAfterLast("abcba", "b") = "a"
     * StringUtils.substringAfterLast("abc", "c")   = ""
     * StringUtils.substringAfterLast("a", "a")     = ""
     * StringUtils.substringAfterLast("a", "z")     = ""
     * </pre>
     *
     * @param str the String to get a substring from, may be null
     * @param separator the String to search for, may be null
     * @return the substring after the last occurrence of the separator, <code>null</code> if null String input
     * @since 2.0
     */
    public static String substringAfterLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(separator)) {
            return EMPTY;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND || pos == (str.length() - separator.length())) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * <p>
     * Gets the String that is nested in between two Strings. Only the first match is returned.
     * </p>
     *
     * <p>
     * A <code>null</code> input String returns <code>null</code>. A <code>null</code> open/close returns <code>null</code> (no match). An empty ("") open and
     * close returns an empty string.
     * </p>
     *
     * <pre>
     * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringUtils.substringBetween(null, *, *)          = null
     * StringUtils.substringBetween(*, null, *)          = null
     * StringUtils.substringBetween(*, *, null)          = null
     * StringUtils.substringBetween("", "", "")          = ""
     * StringUtils.substringBetween("", "", "]")         = null
     * StringUtils.substringBetween("", "[", "]")        = null
     * StringUtils.substringBetween("yabcz", "", "")     = ""
     * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param str the String containing the substring, may be null
     * @param open the String before the substring, may be null
     * @param close the String after the substring, may be null
     * @return the substring, <code>null</code> if no match
     * @since 2.0
     */
    public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != INDEX_NOT_FOUND) {
            int end = str.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * <p>
     * Deletes all whitespaces from a String as defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.deleteWhitespace(null)         = null
     * StringUtils.deleteWhitespace("")           = ""
     * StringUtils.deleteWhitespace("abc")        = "abc"
     * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str the String to delete whitespace from, may be null
     * @return the String without whitespaces, <code>null</code> if null String input
     */
    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }

    /**
     * Performs the logic for the {@code split} and {@code splitPreserveAllTokens} methods that do not return a maximum array length.
     *
     * @param str the String to parse, may be {@code null}
     * @param separatorChar the separate character
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split(final String str, final char separatorChar) {
        // Performance tuned for 2.0 (JDK1.4)

        // preserveAllTokens if {@code true}, adjacent separators are treated as empty token separators; if {@code false}, adjacent separators are treated as
        // one separator.
        boolean preserveAllTokens = true;
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return EMPTY_ARRAY;
        }
        final List<String> list = new ArrayList<>();
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match || preserveAllTokens) {
                    list.add(str.substring(start, i));
                    match = false;
                    lastMatch = true;
                }
                start = ++i;
                continue;
            }
            lastMatch = false;
            match = true;
            i++;
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[list.size()]);
    }

    // Remove
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Removes a substring only if it is at the begining of a source string, otherwise returns the source string.
     * </p>
     *
     * <p>
     * A <code>null</code> source string will return <code>null</code>. An empty ("") source string will return the empty string. A <code>null</code> search
     * string will return the source string.
     * </p>
     *
     * <pre>
     * StringUtils.removeStart(null, *)      = null
     * StringUtils.removeStart("", *)        = ""
     * StringUtils.removeStart(*, null)      = *
     * StringUtils.removeStart("www.domain.com", "www.")   = "domain.com"
     * StringUtils.removeStart("domain.com", "www.")       = "domain.com"
     * StringUtils.removeStart("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeStart("abc", "")    = "abc"
     * </pre>
     *
     * @param str the source String to search, may be null
     * @param remove the String to search for and remove, may be null
     * @return the substring with the string removed if found, <code>null</code> if null String input
     * @since 2.1
     */
    public static String removeStart(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.startsWith(remove)) {
            return str.substring(remove.length());
        }
        return str;
    }

    /**
     * <p>
     * Case insensitive removal of a substring if it is at the begining of a source string, otherwise returns the source string.
     * </p>
     *
     * <p>
     * A <code>null</code> source string will return <code>null</code>. An empty ("") source string will return the empty string. A <code>null</code> search
     * string will return the source string.
     * </p>
     *
     * <pre>
     * StringUtils.removeStartIgnoreCase(null, *)      = null
     * StringUtils.removeStartIgnoreCase("", *)        = ""
     * StringUtils.removeStartIgnoreCase(*, null)      = *
     * StringUtils.removeStartIgnoreCase("www.domain.com", "www.")   = "domain.com"
     * StringUtils.removeStartIgnoreCase("www.domain.com", "WWW.")   = "domain.com"
     * StringUtils.removeStartIgnoreCase("domain.com", "www.")       = "domain.com"
     * StringUtils.removeStartIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeStartIgnoreCase("abc", "")    = "abc"
     * </pre>
     *
     * @param str the source String to search, may be null
     * @param remove the String to search for (case insensitive) and remove, may be null
     * @return the substring with the string removed if found, <code>null</code> if null String input
     * @since 2.4
     */
    public static String removeStartIgnoreCase(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.toLowerCase().startsWith(remove.toLowerCase())) {
            return str.substring(remove.length());
        }
        return str;
    }

    /**
     * <p>
     * Removes a substring only if it is at the end of a source string, otherwise returns the source string.
     * </p>
     *
     * <p>
     * A <code>null</code> source string will return <code>null</code>. An empty ("") source string will return the empty string. A <code>null</code> search
     * string will return the source string.
     * </p>
     *
     * <pre>
     * StringUtils.removeEnd(null, *)      = null
     * StringUtils.removeEnd("", *)        = ""
     * StringUtils.removeEnd(*, null)      = *
     * StringUtils.removeEnd("www.domain.com", ".com.")  = "www.domain.com"
     * StringUtils.removeEnd("www.domain.com", ".com")   = "www.domain"
     * StringUtils.removeEnd("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeEnd("abc", "")    = "abc"
     * </pre>
     *
     * @param str the source String to search, may be null
     * @param remove the String to search for and remove, may be null
     * @return the substring with the string removed if found, <code>null</code> if null String input
     * @since 2.1
     */
    public static String removeEnd(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    /**
     * <p>
     * Case insensitive removal of a substring if it is at the end of a source string, otherwise returns the source string.
     * </p>
     *
     * <p>
     * A <code>null</code> source string will return <code>null</code>. An empty ("") source string will return the empty string. A <code>null</code> search
     * string will return the source string.
     * </p>
     *
     * <pre>
     * StringUtils.removeEndIgnoreCase(null, *)      = null
     * StringUtils.removeEndIgnoreCase("", *)        = ""
     * StringUtils.removeEndIgnoreCase(*, null)      = *
     * StringUtils.removeEndIgnoreCase("www.domain.com", ".com.")  = "www.domain.com"
     * StringUtils.removeEndIgnoreCase("www.domain.com", ".com")   = "www.domain"
     * StringUtils.removeEndIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeEndIgnoreCase("abc", "")    = "abc"
     * StringUtils.removeEndIgnoreCase("www.domain.com", ".COM") = "www.domain")
     * StringUtils.removeEndIgnoreCase("www.domain.COM", ".com") = "www.domain")
     * </pre>
     *
     * @param str the source String to search, may be null
     * @param remove the String to search for (case insensitive) and remove, may be null
     * @return the substring with the string removed if found, <code>null</code> if null String input
     * @since 2.4
     */
    public static String removeEndIgnoreCase(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.toLowerCase().endsWith(remove.toLowerCase())) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    /**
     * <p>
     * Removes all occurrences of a character from within the source string.
     * </p>
     *
     * <p>
     * A <code>null</code> source string will return <code>null</code>. An empty ("") source string will return the empty string.
     * </p>
     *
     * <pre>
     * StringUtils.remove(null, *)       = null
     * StringUtils.remove("", *)         = ""
     * StringUtils.remove("queued", 'u') = "qeed"
     * StringUtils.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str the source String to search, may be null
     * @param remove the char to search for and remove, may be null
     * @return the substring with the char removed if found, <code>null</code> if null String input
     * @since 2.1
     */
    public static String remove(String str, char remove) {
        if (isEmpty(str) || str.indexOf(remove) == INDEX_NOT_FOUND) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * <p>
     * Returns padding using the specified delimiter repeated to a given length.
     * </p>
     *
     * <pre>
     * StringUtils.padding(0, 'e')  = ""
     * StringUtils.padding(3, 'e')  = "eee"
     * StringUtils.padding(-2, 'e') = IndexOutOfBoundsException
     * </pre>
     *
     * <p>
     * Note: this method doesn't not support padding with <a href="http://www.unicode.org/glossary/#supplementary_character">Unicode Supplementary
     * Characters</a> as they require a pair of <code>char</code>s to be represented. If you are needing to support full I18N of your applications consider
     * using {@link #repeat(String, int)} instead.
     * </p>
     *
     * @param repeat number of times to repeat delim
     * @param padChar character to repeat
     * @return String with repeated character
     * @throws IndexOutOfBoundsException if <code>repeat &lt; 0</code>
     * @see #repeat(String, int)
     */
    private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    /**
     * <p>
     * Right pad a String with a specified character.
     * </p>
     *
     * <p>
     * The String is padded to the size of <code>size</code>.
     * </p>
     *
     * <pre>
     * StringUtils.rightPad(null, *, *)     = null
     * StringUtils.rightPad("", 3, 'z')     = "zzz"
     * StringUtils.rightPad("bat", 3, 'z')  = "bat"
     * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
     * StringUtils.rightPad("bat", 1, 'z')  = "bat"
     * StringUtils.rightPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str the String to pad out, may be null
     * @param size the size to pad to
     * @param padChar the character to pad with
     * @return right padded String or original String if no padding is necessary, <code>null</code> if null String input
     * @since 2.0
     */
    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(padding(pads, padChar));
    }

    /**
     * <p>
     * Right pad a String with a specified String.
     * </p>
     *
     * <p>
     * The String is padded to the size of <code>size</code>.
     * </p>
     *
     * <pre>
     * StringUtils.rightPad(null, *, *)      = null
     * StringUtils.rightPad("", 3, "z")      = "zzz"
     * StringUtils.rightPad("bat", 3, "yz")  = "bat"
     * StringUtils.rightPad("bat", 5, "yz")  = "batyz"
     * StringUtils.rightPad("bat", 8, "yz")  = "batyzyzy"
     * StringUtils.rightPad("bat", 1, "yz")  = "bat"
     * StringUtils.rightPad("bat", -1, "yz") = "bat"
     * StringUtils.rightPad("bat", 5, null)  = "bat  "
     * StringUtils.rightPad("bat", 5, "")    = "bat  "
     * </pre>
     *
     * @param str the String to pad out, may be null
     * @param size the size to pad to
     * @param padStr the String to pad with, null or empty treated as single space
     * @return right padded String or original String if no padding is necessary, <code>null</code> if null String input
     */
    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }

    /**
     * <p>
     * Left pad a String with a specified character.
     * </p>
     *
     * <p>
     * Pad to a size of <code>size</code>.
     * </p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)     = null
     * StringUtils.leftPad("", 3, 'z')     = "zzz"
     * StringUtils.leftPad("bat", 3, 'z')  = "bat"
     * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtils.leftPad("bat", 1, 'z')  = "bat"
     * StringUtils.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str the String to pad out, may be null
     * @param size the size to pad to
     * @param padChar the character to pad with
     * @return left padded String or original String if no padding is necessary, <code>null</code> if null String input
     * @since 2.0
     */
    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }

    /**
     * <p>
     * Left pad a String with a specified String.
     * </p>
     *
     * <p>
     * Pad to a size of <code>size</code>.
     * </p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)      = null
     * StringUtils.leftPad("", 3, "z")      = "zzz"
     * StringUtils.leftPad("bat", 3, "yz")  = "bat"
     * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtils.leftPad("bat", 1, "yz")  = "bat"
     * StringUtils.leftPad("bat", -1, "yz") = "bat"
     * StringUtils.leftPad("bat", 5, null)  = "  bat"
     * StringUtils.leftPad("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str the String to pad out, may be null
     * @param size the size to pad to
     * @param padStr the String to pad with, null or empty treated as single space
     * @return left padded String or original String if no padding is necessary, <code>null</code> if null String input
     */
    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }

    /**
     * <p>
     * Centers a String in a larger String of size <code>size</code>. Uses a supplied character as the value to pad the String with.
     * </p>
     *
     * <p>
     * If the size is less than the String length, the String is returned. A <code>null</code> String returns <code>null</code>. A negative size is treated as
     * zero.
     * </p>
     *
     * <pre>
     * StringUtils.center(null, *, *)     = null
     * StringUtils.center("", 4, ' ')     = "    "
     * StringUtils.center("ab", -1, ' ')  = "ab"
     * StringUtils.center("ab", 4, ' ')   = " ab"
     * StringUtils.center("abcd", 2, ' ') = "abcd"
     * StringUtils.center("a", 4, ' ')    = " a  "
     * StringUtils.center("a", 4, 'y')    = "yayy"
     * </pre>
     *
     * @param str the String to center, may be null
     * @param size the int size of new String, negative treated as zero
     * @param padChar the character to pad the new String with
     * @return centered String, <code>null</code> if null String input
     * @since 2.0
     */
    public static String center(String str, int size, char padChar) {
        if (str == null || size <= 0) {
            return str;
        }
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        str = leftPad(str, strLen + pads / 2, padChar);
        str = rightPad(str, size, padChar);
        return str;
    }

    /**
     * <p>
     * Centers a String in a larger String of size <code>size</code>. Uses a supplied String as the value to pad the String with.
     * </p>
     *
     * <p>
     * If the size is less than the String length, the String is returned. A <code>null</code> String returns <code>null</code>. A negative size is treated as
     * zero.
     * </p>
     *
     * <pre>
     * StringUtils.center(null, *, *)     = null
     * StringUtils.center("", 4, " ")     = "    "
     * StringUtils.center("ab", -1, " ")  = "ab"
     * StringUtils.center("ab", 4, " ")   = " ab"
     * StringUtils.center("abcd", 2, " ") = "abcd"
     * StringUtils.center("a", 4, " ")    = " a  "
     * StringUtils.center("a", 4, "yz")   = "yayz"
     * StringUtils.center("abc", 7, null) = "  abc  "
     * StringUtils.center("abc", 7, "")   = "  abc  "
     * </pre>
     *
     * @param str the String to center, may be null
     * @param size the int size of new String, negative treated as zero
     * @param padStr the String to pad the new String with, must not be null or empty
     * @return centered String, <code>null</code> if null String input
     * @throws IllegalArgumentException if padStr is <code>null</code> or empty
     */
    public static String center(String str, int size, String padStr) {
        if (str == null || size <= 0) {
            return str;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        str = leftPad(str, strLen + pads / 2, padStr);
        str = rightPad(str, size, padStr);
        return str;
    }

    // Count matches
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Counts how many times the substring appears in the larger String.
     * </p>
     *
     * <p>
     * A <code>null</code> or empty ("") String input returns <code>0</code>.
     * </p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", null)  = 0
     * StringUtils.countMatches("abba", "")    = 0
     * StringUtils.countMatches("abba", "a")   = 2
     * StringUtils.countMatches("abba", "ab")  = 1
     * StringUtils.countMatches("abba", "xxx") = 0
     * </pre>
     *
     * @param str the String to check, may be null
     * @param sub the substring to count, may be null
     * @return the number of occurrences, 0 if either String is <code>null</code>
     */
    public static int countMatches(String str, String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != INDEX_NOT_FOUND) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    // Character Tests
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Checks if the String contains only unicode letters.
     * </p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = true
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters, and is non-null
     */
    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetter(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only unicode letters and space (' ').
     * </p>
     *
     * <p>
     * <code>null</code> will return <code>false</code> An empty String (length()=0) will return <code>true</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isAlphaSpace(null)   = false
     * StringUtils.isAlphaSpace("")     = true
     * StringUtils.isAlphaSpace("  ")   = true
     * StringUtils.isAlphaSpace("abc")  = true
     * StringUtils.isAlphaSpace("ab c") = true
     * StringUtils.isAlphaSpace("ab2c") = false
     * StringUtils.isAlphaSpace("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters and space, and is non-null
     */
    public static boolean isAlphaSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isLetter(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only unicode letters or digits.
     * </p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = true
     * StringUtils.isAlphanumeric("  ")   = false
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = false
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters or digits, and is non-null
     */
    public static boolean isAlphanumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetterOrDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only unicode letters, digits or space (<code>' '</code>).
     * </p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = true
     * StringUtils.isAlphanumeric("  ")   = true
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = true
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains letters, digits or space, and is non-null
     */
    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isLetterOrDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only unicode digits. A decimal point is not a unicode digit and returns false.
     * </p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only unicode digits or space (<code>' '</code>). A decimal point is not a unicode digit and returns false.
     * </p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = true
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = true
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains digits or space, and is non-null
     */
    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only whitespace.
     * </p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains whitespace, and is non-null
     * @since 2.0
     */
    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only lowercase characters.
     * </p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>false</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isAllLowerCase(null)   = false
     * StringUtils.isAllLowerCase("")     = false
     * StringUtils.isAllLowerCase("  ")   = false
     * StringUtils.isAllLowerCase("abc")  = true
     * StringUtils.isAllLowerCase("abC") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains lowercase characters, and is non-null
     * @since 2.5
     */
    public static boolean isAllLowerCase(String str) {
        if (str == null || isEmpty(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLowerCase(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if the String contains only uppercase characters.
     * </p>
     *
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>false</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isAllUpperCase(null)   = false
     * StringUtils.isAllUpperCase("")     = false
     * StringUtils.isAllUpperCase("  ")   = false
     * StringUtils.isAllUpperCase("ABC")  = true
     * StringUtils.isAllUpperCase("aBC") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains uppercase characters, and is non-null
     * @since 2.5
     */
    public static boolean isAllUpperCase(String str) {
        if (str == null || isEmpty(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isUpperCase(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    // Difference
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Compares two Strings, and returns the portion where they differ. (More precisely, return the remainder of the second String, starting from where it's
     * different from the first.)
     * </p>
     *
     * <p>
     * For example, <code>difference("i am a machine", "i am a robot") -- "robot"</code>.
     * </p>
     *
     * <pre>
     * StringUtils.difference(null, null) = null
     * StringUtils.difference("", "") = ""
     * StringUtils.difference("", "abc") = "abc"
     * StringUtils.difference("abc", "") = ""
     * StringUtils.difference("abc", "abc") = ""
     * StringUtils.difference("ab", "abxyz") = "xyz"
     * StringUtils.difference("abcde", "abxyz") = "xyz"
     * StringUtils.difference("abcde", "xyz") = "xyz"
     * </pre>
     *
     * @param str1 the first String, may be null
     * @param str2 the second String, may be null
     * @return the portion of str2 where it differs from str1; returns the empty String if they are equal
     * @since 2.0
     */
    public static String difference(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        int at = indexOfDifference(str1, str2);
        if (at == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str2.substring(at);
    }

    /**
     * <p>
     * Compares all Strings in an array and returns the index at which the Strings begin to differ.
     * </p>
     *
     * <p>
     * For example, <code>indexOfDifference(new String[] {"i am a machine", "i am a robot"}) -- 7</code>
     * </p>
     *
     * <pre>
     * StringUtils.indexOfDifference(null) = -1
     * StringUtils.indexOfDifference(new String[] {}) = -1
     * StringUtils.indexOfDifference(new String[] {"abc"}) = -1
     * StringUtils.indexOfDifference(new String[] {null, null}) = -1
     * StringUtils.indexOfDifference(new String[] {"", ""}) = -1
     * StringUtils.indexOfDifference(new String[] {"", null}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
     * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"", "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", ""}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", "abc"}) = -1
     * StringUtils.indexOfDifference(new String[] {"abc", "a"}) = 1
     * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"}) = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"}) = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"}) = 0
     * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"}) = 0
     * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
     * </pre>
     *
     * @param strs array of strings, entries may be null
     * @return the index where the strings begin to differ; -1 if they are all equal
     * @since 2.4
     */
    public static int indexOfDifference(String...strs) {
        if (strs == null || strs.length <= 1) {
            return INDEX_NOT_FOUND;
        }
        boolean anyStringNull = false;
        boolean allStringsNull = true;
        int arrayLen = strs.length;
        int shortestStrLen = Integer.MAX_VALUE;
        int longestStrLen = 0;

        // find the min and max string lengths; this avoids checking to make
        // sure we are not exceeding the length of the string each time through
        // the bottom loop.
        for (int i = 0; i < arrayLen; i++) {
            if (strs[i] == null) {
                anyStringNull = true;
                shortestStrLen = 0;
            } else {
                allStringsNull = false;
                shortestStrLen = Math.min(strs[i].length(), shortestStrLen);
                longestStrLen = Math.max(strs[i].length(), longestStrLen);
            }
        }

        // handle lists containing all nulls or all empty strings
        if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
            return INDEX_NOT_FOUND;
        }

        // handle lists containing some nulls or some empty strings
        if (shortestStrLen == 0) {
            return 0;
        }

        // find the position with the first difference across all strings
        int firstDiff = -1;
        for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
            char comparisonChar = strs[0].charAt(stringPos);
            for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
                if (strs[arrayPos].charAt(stringPos) != comparisonChar) {
                    firstDiff = stringPos;
                    break;
                }
            }
            if (firstDiff != -1) {
                break;
            }
        }

        if (firstDiff == -1 && shortestStrLen != longestStrLen) {
            // we compared all of the characters up to the length of the
            // shortest string and didn't find a match, but the string lengths
            // vary, so return the length of the shortest string.
            return shortestStrLen;
        }
        return firstDiff;
    }

    /**
     * <p>
     * Compares all Strings in an array and returns the initial sequence of characters that is common to all of them.
     * </p>
     *
     * <p>
     * For example, <code>getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) -- "i am a "</code>
     * </p>
     *
     * <pre>
     * StringUtils.getCommonPrefix(null) = ""
     * StringUtils.getCommonPrefix(new String[] {}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc"}) = "abc"
     * StringUtils.getCommonPrefix(new String[] {null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", ""}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", null}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", ""}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", "abc"}) = "abc"
     * StringUtils.getCommonPrefix(new String[] {"abc", "a"}) = "a"
     * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"}) = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}) = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
     * </pre>
     *
     * @param strs array of String objects, entries may be null
     * @return the initial sequence of characters that are common to all Strings in the array; empty String if the array is null, the elements are all null or
     *         if there is no common prefix.
     * @since 2.4
     */
    public static String getCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return EMPTY;
        }
        int smallestIndexOfDiff = indexOfDifference(strs);
        if (smallestIndexOfDiff == INDEX_NOT_FOUND) {
            // all strings were identical
            if (strs[0] == null) {
                return EMPTY;
            }
            return strs[0];
        } else if (smallestIndexOfDiff == 0) {
            // there were no common initial characters
            return EMPTY;
        } else {
            // we found a common initial character sequence
            return strs[0].substring(0, smallestIndexOfDiff);
        }
    }

    // Misc
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Find the Levenshtein distance between two Strings.
     * </p>
     *
     * <p>
     * This is the number of changes needed to change one String into another, where each change is a single character modification (deletion, insertion or
     * substitution).
     * </p>
     *
     * <p>
     * The previous implementation of the Levenshtein distance algorithm was from
     * <a href="http://www.merriampark.com/ld.htm">http://www.merriampark.com/ld.htm</a>
     * </p>
     *
     * <p>
     * Chas Emerick has written an implementation in Java, which avoids an OutOfMemoryError which can occur when my Java implementation is used with very large
     * strings.<br>
     * This implementation of the Levenshtein distance algorithm is from
     * <a href="http://www.merriampark.com/ldjava.htm">http://www.merriampark.com/ldjava.htm</a>
     * </p>
     *
     * <pre>
     * StringUtils.getLevenshteinDistance(null, *)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance(*, null)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance("","")               = 0
     * StringUtils.getLevenshteinDistance("","a")              = 1
     * StringUtils.getLevenshteinDistance("aaapppp", "")       = 7
     * StringUtils.getLevenshteinDistance("frog", "fog")       = 1
     * StringUtils.getLevenshteinDistance("fly", "ant")        = 3
     * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
     * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
     * StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz") = 8
     * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
     * </pre>
     *
     * @param s the first String, must not be null
     * @param t the second String, must not be null
     * @return result distance
     * @throws IllegalArgumentException if either String input <code>null</code>
     */
    public static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        /*
         * The difference between this impl. and the previous is that, rather than creating and retaining a matrix of size s.length()+1 by t.length()+1, we
         * maintain two single-dimensional arrays of length s.length()+1. The first, d, is the 'current working' distance array that maintains the newest
         * distance cost counts as we iterate through the characters of String s. Each time we increment the index of String t we are comparing, d is copied to
         * p, the second int[]. Doing so allows us to retain the previous cost counts as required by the algorithm (taking the minimum of the cost count to the
         * left, up one, and diagonally up and to the left of the current cost count being calculated). (Note that the arrays aren't really copied anymore, just
         * switched...this is clearly much better than cloning an array or doing a System.arraycopy() each time through the outer loop.)
         * 
         * Effectively, the difference between the two implementations is this one does not cause an out of memory condition when calculating the LD over two
         * very large strings.
         */

        int n = s.length(); // length of s
        int m = t.length(); // length of t

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        if (n > m) {
            // swap the input strings to consume less memory
            String tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int p[] = new int[n + 1]; // 'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; // placeholder to assist in swapping p and d

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];
    }

    // startsWith
    // -----------------------------------------------------------------------

    // endsWith
    // -----------------------------------------------------------------------

    // public static void main(String[] args) {
    //
    // }

}
