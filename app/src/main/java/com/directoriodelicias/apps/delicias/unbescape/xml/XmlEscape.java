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
package com.directoriodelicias.apps.delicias.unbescape.xml;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>
 * Utility class for performing XML escape/unescape operations.
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
 *       needs of the scenario). Its values are defined by the {@link org.unbescape.xml.XmlEscapeLevel}
 *       enum.</li>
 *   <li><em>Type</em>, which defines whether escaping should be performed by means of CERs
 *       (Character Entity References) or by means of decimal/hexadecimal numerical references.
 *       Its values are defined by the {@link org.unbescape.xml.XmlEscapeType} enum.</li>
 * </ul>
 * <p>
 *   <strong>Unescape</strong> operations need no configuration parameters. Unescape operations
 *   will always perform <em>complete</em> unescape of CERs, decimal and hexadecimal references.
 * </p>
 *
 * <strong><u>Features</u></strong>
 *
 * <p>
 *   This class supports both XML 1.0 and XML 1.1 escape/unescape operations. Whichever the XML version used,
 *   <u>only the five predefined XML character entities are supported</u>: <tt>&amp;lt;</tt>,
 *   <tt>&amp;gt;</tt>, <tt>&amp;amp;</tt>, <tt>&amp;quot</tt> and <tt>&amp;apos;</tt>. This
 *   means there is no support for DTD-defined or user-defined entities.
 * </p>
 * <p>
 *   Each version of XML establishes a series of characters that are considered <em>not-valid</em>, even
 *   when escaped &mdash;for example, the <tt>&#92;u0000</tt> (null byte)&mdash;. Escape operations will
 *   automatically remove these chars.
 * </p>
 * <p>
 *   Also, each version of XML establishes a series of control characters that, even if allowed as
 *   valid characters, should always appear escaped. For example: <tt>&#92;u0001</tt> to
 *   <tt>&#92;u0008</tt> in XML 1.1.
 * </p>
 * <p>
 *   This class supports the whole Unicode character set: <tt>&#92;u0000</tt> to <tt>&#92;u10FFFF</tt>,
 *   including characters not representable by only one <tt>char</tt> in Java (<tt>&gt;&#92;uFFFF</tt>).
 * </p>
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
 *   <dt>ER</dt>
 *     <dd>XML Entity Reference: references to variables used to define shortcuts to standard text or
 *         special characters. Entity references start with <tt>'&amp;'</tt> and end with
 *         <tt>';'</tt>.</dd>
 *   <dt>CER</dt>
 *     <dd>Character Entity Reference: XML Entity Reference used to define a shortcut to a specific
 *         character. XML specifies five <em>predefined</em> CERs: <tt>&amp;lt;</tt> (<tt>&lt;</tt>),
 *         <tt>&amp;gt;</tt> (<tt>&gt;</tt>), <tt>&amp;amp;</tt> (<tt>&amp;</tt>),
 *         <tt>&amp;quot;</tt> (<tt>&quot;</tt>) and <tt>&amp;apos;</tt>
 *         (<tt>&#39;</tt>).</dd>
 *   <dt>DCR</dt>
 *     <dd>Decimal Character Reference: base-10 numerical representation of an Unicode codepoint:
 *         <tt>&amp;#225;</tt></dd>
 *   <dt>HCR</dt>
 *     <dd>Hexadecimal Character Reference: hexadecimal numerical representation of an Unicode codepoint:
 *         <tt>&#xE1;</tt>. Note that XML only allows lower-case <tt>'x'</tt> for defining hexadecimal
 *         character entity references (in contrast with HTML, which allows both <tt>'&amp;#x...;'</tt> and
 *         <tt>'&amp;#x...;'</tt>).</dd>
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
 *   <li><a href="http://www.w3.org/International/questions/qa-escapes" target="_blank">Using character escapes in
 *       markup and CSS</a> [w3.org]</li>
 *   <li><a href="http://www.w3.org/TR/xml" target="_blank">XML 1.0 Specification</a> [w3.org]</li>
 *   <li><a href="http://www.w3.org/TR/xml11" target="_blank">XML 1.1 Specification</a> [w3.org]</li>
 *   <li><a href="http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html"
 *       target="_blank">Supplementary characters in the Java Platform</a> [oracle.com]</li>
 * </ul>
 *
 * @author Daniel Fern&aacute;ndez
 * @since 1.0.0
 */
public final class XmlEscape {


    private XmlEscape() {
        super();
    }

    /*
     * Private escape method called from XML 1.0 and XML 1.1 public methods, once the correct
     * symbol set has been selected.
     */
    private static String escapeXml(final String text, final XmlEscapeSymbols symbols,
                                    final XmlEscapeType type, final XmlEscapeLevel level) {

        if (type == null) {
            throw new IllegalArgumentException("The 'type' argument cannot be null");
        }

        if (level == null) {
            throw new IllegalArgumentException("The 'level' argument cannot be null");
        }

        return XmlEscapeUtil.escape(text, symbols, type, level);

    }

    /*
     * Private escape method called from XML 1.0 and XML 1.1 public methods, once the correct
     * symbol set has been selected.
     */
    private static void escapeXml(final char[] text, final int offset, final int len, final Writer writer,
                                  final XmlEscapeSymbols symbols, final XmlEscapeType type, final XmlEscapeLevel level)
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

        XmlEscapeUtil.escape(text, offset, len, writer, symbols, type, level);

    }


}

