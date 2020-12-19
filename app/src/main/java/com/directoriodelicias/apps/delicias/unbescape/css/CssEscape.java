/*
 * =============================================================================
 *
 *   Copyright (c) 2014, The UNBESCAPE team (http://www.unbescape.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package com.directoriodelicias.apps.delicias.unbescape.css;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>
 * Utility class for performing CSS escape/unescape operations.
 * </p>
 *
 * <p>
 * This class supports both escaping of <strong>CSS identifiers</strong> and
 * <strong>CSS Strings</strong> (or <em>literals</em>).
 * </p>
 *
 * <strong><u>Configuration of escape/unescape operations</u></strong>
 *
 * <p>
 * <strong>Escape</strong> operations can be (optionally) configured by means of:
 * </p>
 * <ul>
 *   <li><em>Level</em>, which defines how deep the escape operation must be (what
 *       chars are to be considered eligible for escaping, depending on the specific
 *       needs of the scenario). Its values are defined by the {@link CssIdentifierEscapeLevel}
 *       and {@link CssStringEscapeLevel} enums.</li>
 *   <li><em>Type</em>, which defines whether escaping should be performed by means of <em>backslash escapes</em>
 *       or by means of hexadecimal numerical escape sequences.
 *       Its values are defined by the {@link CssIdentifierEscapeType}
 *       and {@link CssStringEscapeType} enums.</li>
 * </ul>
 * <p>
 *   <strong>Unescape</strong> operations need no configuration parameters. Unescape operations
 *   will always perform <em>complete</em> unescape of backslash and hexadecimal escapes, including all
 *   required <em>tweaks</em> (i.e. optional whitespace characters) needed for unescaping.
 * </p>
 *
 * <strong><u>Features</u></strong>
 *
 * <p>
 *   Specific features of the CSS escape/unescape operations performed by means of this class:
 * </p>
 * <ul>
 *   <li>Complete set of CSS <em>Backslash Escapes</em> supported (e.g. <tt>&#92;+</tt>, <tt>&#92;(</tt>,
 *       <tt>&#92;)</tt>, etc.).</li>
 *   <li>Full set of escape syntax rules supported, both for <strong>CSS identifiers</strong> and
 *       <strong>CSS Strings</strong> (or <em>literals</em>).</li>
 *   <li>Non-standard tweaks supported: <tt>&#92;:</tt> not used because of lacking support in
 *       Internet Explorer &lt; 8, <tt>&#92;_</tt> escaped at the beginning of identifiers for better
 *       Internet Explorer 6 support, etc.</li>
 *   <li>Hexadecimal escapes (a.k.a. <em>unicode escapes</em>) are supported both in escape
 *       and unescape operations, and both in <em>compact</em> (<tt>&#92;E1 </tt>) and six-digit
 *       forms (<tt>&#92;0000E1</tt>).</li>
 *   <li>Support for the whole Unicode character set: <tt>&#92;u0000</tt> to <tt>&#92;u10FFFF</tt>, including
 *       characters not representable by only one <tt>char</tt> in Java (<tt>&gt;&#92;uFFFF</tt>).</li>
 *   <li>Support for unescaping unicode characters &gt; U+FFFF both when represented in standard form (one char,
 *       <tt>&#92;20000</tt>) and non-standard (surrogate pair, <tt>&#92;D840&#92;DC00</tt>, used by older
 *       WebKit browsers).</li>
 * </ul>
 *
 * <strong><u>Input/Output</u></strong>
 *
 * <p>
 *   There are two different input/output modes that can be used in escape/unescape operations:
 * </p>
 * <ul>
 *   <li><em><tt>String</tt> input, <tt>String</tt> output</em>: Input is specified as a <tt>String</tt> object
 *       and output is returned as another. In order to improve memory performance, all escape and unescape
 *       operations <u>will return the exact same input object as output if no escape/unescape modifications
 *       are required</u>.</li>
 *   <li><em><tt>char[]</tt> input, <tt>java.io.Writer</tt> output</em>: Input will be read from a char array
 *       (<tt>char[]</tt>) and output will be written into the specified <tt>java.io.Writer</tt>.
 *       Two <tt>int</tt> arguments called <tt>offset</tt> and <tt>len</tt> will be
 *       used for specifying the part of the <tt>char[]</tt> that should be escaped/unescaped. These methods
 *       should be called with <tt>offset = 0</tt> and <tt>len = text.length</tt> in order to process
 *       the whole <tt>char[]</tt>.</li>
 * </ul>
 *
 * <strong><u>Glossary</u></strong>
 *
 * <dl>
 *   <dt>Backslash escapes</dt>
 *     <dd>Escape sequences performed by means of prefixing a <em>backslash</em> (<tt>&#92;</tt>) to
 *         the escaped char: <tt>&#92;+</tt>, <tt>&#92;(</tt>, <tt>&#92;)</tt></dd>
 *   <dt>HEXA escapes</dt>
 *     <dd>Complete representation of unicode codepoints up to <tt>U+10FFFF</tt>, in two forms:
 *         <ul>
 *           <li><em>Compact</em>: non-zero-padded hexadecimal representation (<tt>&#92;E1 </tt>), followed
 *               by an optional whitespace (<tt>U+0020</tt>), required if after the escaped character comes
 *               a hexadecimal digit (<tt>[0-9A-Fa-f]</tt>) or another whitespace (<tt>&nbsp;</tt>).</li>
 *           <li><em>Six-digit</em>: zero-padded hexadecimal representation (<tt>&#92;0000E1</tt>), followed
 *               by an optional whitespace (<tt>U+0020</tt>), required if after the escaped character comes
 *               another whitespace (<tt>&nbsp;</tt>).</li>
 *         </ul>
 *     </dd>
 *   <dt>Unicode Codepoint</dt>
 *     <dd>Each of the <tt>int</tt> values conforming the Unicode code space.
 *         Normally corresponding to a Java <tt>char</tt> primitive value (codepoint &lt;= <tt>&#92;uFFFF</tt>),
 *         but might be two <tt>char</tt>s for codepoints <tt>&#92;u10000</tt> to <tt>&#92;u10FFFF</tt> if the
 *         first <tt>char</tt> is a high surrogate (<tt>&#92;uD800</tt> to <tt>&#92;uDBFF</tt>) and the
 *         second is a low surrogate (<tt>&#92;uDC00</tt> to <tt>&#92;uDFFF</tt>).</dd>
 * </dl>
 *
 * <strong><u>References</u></strong>
 *
 * <p>
 *   The following references apply:
 * </p>
 * <ul>
 *   <li><a href="http://www.w3.org/TR/CSS21/syndata.html#value-def-identifier" target="_blank">Cascading
 *       Style Sheets Level 2 Revision 1 (CSS 2.1) Specification</a> [w3.org]</li>
 *   <li><a href="http://mathiasbynens.be/notes/css-escapes">CSS character escape sequences</a> [mathiasbynens.be]</li>
 *   <li><a href="http://mothereff.in/css-escapes">CSS escapes tester</a> [mothereff.in]</li>
 * </ul>
 *
 * @author Daniel Fern&aacute;ndez
 * @since 1.0.0
 */
public final class CssEscape {


    private CssEscape() {
        super();
    }

    /**
     * <p>
     * Perform a (configurable) CSS String <strong>escape</strong> operation on a <tt>String</tt> input.
     * </p>
     * <p>
     * This method will perform an escape operation according to the specified
     * {@link CssStringEscapeType} and
     * {@link CssStringEscapeLevel} argument values.
     * </p>
     * <p>
     * All other <tt>String</tt>-based <tt>escapeCssString*(...)</tt> methods call this one with preconfigured
     * <tt>type</tt> and <tt>level</tt> values.
     * </p>
     * <p>
     * This method is <strong>thread-safe</strong>.
     * </p>
     *
     * @param text  the <tt>String</tt> to be escaped.
     * @param type  the type of escape operation to be performed, see
     *              {@link CssStringEscapeType}.
     * @param level the escape level to be applied, see {@link CssStringEscapeLevel}.
     * @return The escaped result <tt>String</tt>. As a memory-performance improvement, will return the exact
     * same object as the <tt>text</tt> input argument if no escaping modifications were required (and
     * no additional <tt>String</tt> objects will be created during processing). Will
     * return <tt>null</tt> if <tt>text</tt> is <tt>null</tt>.
     */
    public static String escapeCssString(final String text,
                                         final CssStringEscapeType type, final CssStringEscapeLevel level) {

        if (type == null) {
            throw new IllegalArgumentException("The 'type' argument cannot be null");
        }

        if (level == null) {
            throw new IllegalArgumentException("The 'level' argument cannot be null");
        }

        return CssStringEscapeUtil.escape(text, type, level);

    }

    /**
     * <p>
     * Perform a (configurable) CSS String <strong>escape</strong> operation on a <tt>char[]</tt> input.
     * </p>
     * <p>
     * This method will perform an escape operation according to the specified
     * {@link CssStringEscapeType} and
     * {@link CssStringEscapeLevel} argument values.
     * </p>
     * <p>
     * All other <tt>char[]</tt>-based <tt>escapeCssString*(...)</tt> methods call this one with preconfigured
     * <tt>type</tt> and <tt>level</tt> values.
     * </p>
     * <p>
     * This method is <strong>thread-safe</strong>.
     * </p>
     *
     * @param text   the <tt>char[]</tt> to be escaped.
     * @param offset the position in <tt>text</tt> at which the escape operation should start.
     * @param len    the number of characters in <tt>text</tt> that should be escaped.
     * @param writer the <tt>java.io.Writer</tt> to which the escaped result will be written. Nothing will
     *               be written at all to this writer if <tt>text</tt> is <tt>null</tt>.
     * @param type   the type of escape operation to be performed, see
     *               {@link CssStringEscapeType}.
     * @param level  the escape level to be applied, see {@link CssStringEscapeLevel}.
     * @throws IOException if an input/output exception occurs
     */
    public static void escapeCssString(final char[] text, final int offset, final int len, final Writer writer,
                                       final CssStringEscapeType type, final CssStringEscapeLevel level)
            throws IOException {

        if (writer == null) {
            throw new IllegalArgumentException("Argument 'writer' cannot be null");
        }

        if (type == null) {
            throw new IllegalArgumentException("The 'type' argument cannot be null");
        }

        if (level == null) {
            throw new IllegalArgumentException("The 'level' argument cannot be null");
        }

        final int textLen = (text == null ? 0 : text.length);

        if (offset < 0 || offset > textLen) {
            throw new IllegalArgumentException(
                    "Invalid (offset, len). offset=" + offset + ", len=" + len + ", text.length=" + textLen);
        }

        if (len < 0 || (offset + len) > textLen) {
            throw new IllegalArgumentException(
                    "Invalid (offset, len). offset=" + offset + ", len=" + len + ", text.length=" + textLen);
        }

        CssStringEscapeUtil.escape(text, offset, len, writer, type, level);

    }

    /**
     * <p>
     * Perform a (configurable) CSS Identifier <strong>escape</strong> operation on a <tt>String</tt> input.
     * </p>
     * <p>
     * This method will perform an escape operation according to the specified
     * {@link CssIdentifierEscapeType} and
     * {@link CssIdentifierEscapeLevel} argument values.
     * </p>
     * <p>
     * All other <tt>String</tt>-based <tt>escapeCssIdentifier*(...)</tt> methods call this one with preconfigured
     * <tt>type</tt> and <tt>level</tt> values.
     * </p>
     * <p>
     * This method is <strong>thread-safe</strong>.
     * </p>
     *
     * @param text  the <tt>String</tt> to be escaped.
     * @param type  the type of escape operation to be performed, see
     *              {@link CssIdentifierEscapeType}.
     * @param level the escape level to be applied, see {@link CssIdentifierEscapeLevel}.
     * @return The escaped result <tt>String</tt>. As a memory-performance improvement, will return the exact
     * same object as the <tt>text</tt> input argument if no escaping modifications were required (and
     * no additional <tt>String</tt> objects will be created during processing). Will
     * return <tt>null</tt> if <tt>text</tt> is <tt>null</tt>.
     */
    public static String escapeCssIdentifier(final String text,
                                             final CssIdentifierEscapeType type, final CssIdentifierEscapeLevel level) {

        if (type == null) {
            throw new IllegalArgumentException("The 'type' argument cannot be null");
        }

        if (level == null) {
            throw new IllegalArgumentException("The 'level' argument cannot be null");
        }

        return CssIdentifierEscapeUtil.escape(text, type, level);

    }

    /**
     * <p>
     * Perform a (configurable) CSS Identifier <strong>escape</strong> operation on a <tt>char[]</tt> input.
     * </p>
     * <p>
     * This method will perform an escape operation according to the specified
     * {@link CssIdentifierEscapeType} and
     * {@link CssIdentifierEscapeLevel} argument values.
     * </p>
     * <p>
     * All other <tt>char[]</tt>-based <tt>escapeCssIdentifier*(...)</tt> methods call this one with preconfigured
     * <tt>type</tt> and <tt>level</tt> values.
     * </p>
     * <p>
     * This method is <strong>thread-safe</strong>.
     * </p>
     *
     * @param text   the <tt>char[]</tt> to be escaped.
     * @param offset the position in <tt>text</tt> at which the escape operation should start.
     * @param len    the number of characters in <tt>text</tt> that should be escaped.
     * @param writer the <tt>java.io.Writer</tt> to which the escaped result will be written. Nothing will
     *               be written at all to this writer if <tt>text</tt> is <tt>null</tt>.
     * @param type   the type of escape operation to be performed, see
     *               {@link CssIdentifierEscapeType}.
     * @param level  the escape level to be applied, see {@link CssIdentifierEscapeLevel}.
     * @throws IOException if an input/output exception occurs
     */
    public static void escapeCssIdentifier(final char[] text, final int offset, final int len, final Writer writer,
                                           final CssIdentifierEscapeType type, final CssIdentifierEscapeLevel level)
            throws IOException {

        if (writer == null) {
            throw new IllegalArgumentException("Argument 'writer' cannot be null");
        }

        if (type == null) {
            throw new IllegalArgumentException("The 'type' argument cannot be null");
        }

        if (level == null) {
            throw new IllegalArgumentException("The 'level' argument cannot be null");
        }

        final int textLen = (text == null ? 0 : text.length);

        if (offset < 0 || offset > textLen) {
            throw new IllegalArgumentException(
                    "Invalid (offset, len). offset=" + offset + ", len=" + len + ", text.length=" + textLen);
        }

        if (len < 0 || (offset + len) > textLen) {
            throw new IllegalArgumentException(
                    "Invalid (offset, len). offset=" + offset + ", len=" + len + ", text.length=" + textLen);
        }

        CssIdentifierEscapeUtil.escape(text, offset, len, writer, type, level);

    }


}

