/*
 * PolispeakTokenizer.java
 *
 * THIS FILE HAS BEEN GENERATED AUTOMATICALLY. DO NOT EDIT!
 */

package jg.ps.parser;

import java.io.Reader;

import net.percederberg.grammatica.parser.ParserCreationException;
import net.percederberg.grammatica.parser.TokenPattern;
import net.percederberg.grammatica.parser.Tokenizer;

/**
 * A character stream tokenizer.
 *
 *
 */
public class PolispeakTokenizer extends Tokenizer {

    /**
     * Creates a new tokenizer for the specified input stream.
     *
     * @param input          the input stream to read
     *
     * @throws ParserCreationException if the tokenizer couldn't be
     *             initialized correctly
     */
    public PolispeakTokenizer(Reader input)
        throws ParserCreationException {

        super(input, false);
        createPatterns();
    }

    /**
     * Initializes the tokenizer by creating all the token patterns.
     *
     * @throws ParserCreationException if the tokenizer couldn't be
     *             initialized correctly
     */
    private void createPatterns() throws ParserCreationException {
        TokenPattern  pattern;

        pattern = new TokenPattern(PolispeakConstants.WHITESPACE,
                                   "WHITESPACE",
                                   TokenPattern.REGEXP_TYPE,
                                   "[ \\t\\n\\r]+");
        pattern.setIgnore();
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.STRING,
                                   "STRING",
                                   TokenPattern.REGEXP_TYPE,
                                   "\\\".*?\\\"");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.INTEGER,
                                   "INTEGER",
                                   TokenPattern.REGEXP_TYPE,
                                   "(^-?[0-9]+)");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.DOUBLE,
                                   "DOUBLE",
                                   TokenPattern.REGEXP_TYPE,
                                   "(^-?\\d+\\.\\d+)");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.RESOL_PHRASE,
                                   "RESOL_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "RESOLUTION");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.TO_PHRASE,
                                   "TO_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "To");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.SECTION_PHRASE,
                                   "SECTION_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "Section");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.PROV_PHRASE,
                                   "PROV_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "- Provisions to this Section are:");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.SECTION_CONC_PHRASE,
                                   "SECTION_CONC_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "This concludes Section");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.RESULT_PHRASE,
                                   "RESULT_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "- The fulfillment of this Section results in");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.DEF_PHRASE,
                                   "DEF_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "Definition of");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.AN_PHRASE,
                                   "AN_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "An");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.A_PHRASE,
                                   "A_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "A");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.DESIG_PHRASE,
                                   "DESIG_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "designated as");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.REGARDS,
                                   "REGARDS",
                                   TokenPattern.STRING_TYPE,
                                   "In Regards to");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.THE_PHRASE,
                                   "THE_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "The");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.NULL,
                                   "NULL",
                                   TokenPattern.STRING_TYPE,
                                   "Null");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.INT_PHRASE,
                                   "INT_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "Integer");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.STR_PHRASE,
                                   "STR_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "String");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.FLOAT_PHRASE,
                                   "FLOAT_PHRASE",
                                   TokenPattern.STRING_TYPE,
                                   "Float");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.UNKNOWN,
                                   "UNKNOWN",
                                   TokenPattern.STRING_TYPE,
                                   "Unknown");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.WITHIN,
                                   "WITHIN",
                                   TokenPattern.STRING_TYPE,
                                   "Within this new context, set");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.TO_BE,
                                   "TO_BE",
                                   TokenPattern.STRING_TYPE,
                                   "to be a");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.WHOSE,
                                   "WHOSE",
                                   TokenPattern.STRING_TYPE,
                                   "whose value is");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.SHALL,
                                   "SHALL",
                                   TokenPattern.STRING_TYPE,
                                   "shall be");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.HOLD,
                                   "HOLD",
                                   TokenPattern.STRING_TYPE,
                                   "with an initial hold of");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.RESPECT,
                                   "RESPECT",
                                   TokenPattern.STRING_TYPE,
                                   "with respect to the creation of this new context");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.APPROP,
                                   "APPROP",
                                   TokenPattern.STRING_TYPE,
                                   "Appropriate the creation of");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.WITH,
                                   "WITH",
                                   TokenPattern.STRING_TYPE,
                                   "with the given provisions of");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.AND,
                                   "AND",
                                   TokenPattern.STRING_TYPE,
                                   "and");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.THE,
                                   "THE",
                                   TokenPattern.STRING_TYPE,
                                   "The appropriation of");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.CASE,
                                   "CASE",
                                   TokenPattern.STRING_TYPE,
                                   "In the case of");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.BEING,
                                   "BEING",
                                   TokenPattern.STRING_TYPE,
                                   "being true, then this section mandates that");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.FULFILLED,
                                   "FULFILLED",
                                   TokenPattern.STRING_TYPE,
                                   "is fulfilled");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.INVOKE,
                                   "INVOKE",
                                   TokenPattern.STRING_TYPE,
                                   "Invoke Section");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.INVOKING,
                                   "INVOKING",
                                   TokenPattern.STRING_TYPE,
                                   "Invoking Section");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.THE_INVOCATION,
                                   "THE_INVOCATION",
                                   TokenPattern.STRING_TYPE,
                                   "The invocation of Section");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.HOWEVER,
                                   "HOWEVER",
                                   TokenPattern.STRING_TYPE,
                                   "However, if such case isn’t true, then this Section mandates the fulfillment of");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.REFERRING,
                                   "REFERRING",
                                   TokenPattern.STRING_TYPE,
                                   "Referring to");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.CUR_CONTEXT,
                                   "CUR_CONTEXT",
                                   TokenPattern.STRING_TYPE,
                                   "within the current context");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.WHATEVER,
                                   "WHATEVER",
                                   TokenPattern.STRING_TYPE,
                                   "Whatever is held by");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.HELD,
                                   "HELD",
                                   TokenPattern.STRING_TYPE,
                                   "The value held under");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.FROM,
                                   "FROM",
                                   TokenPattern.STRING_TYPE,
                                   "from");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.AUDIT,
                                   "AUDIT",
                                   TokenPattern.STRING_TYPE,
                                   "Audit");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.ASSET,
                                   "ASSET",
                                   TokenPattern.STRING_TYPE,
                                   "for the asset held by its component named");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.COLON,
                                   "COLON",
                                   TokenPattern.STRING_TYPE,
                                   ":");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.S_QUOTE,
                                   "S_QUOTE",
                                   TokenPattern.STRING_TYPE,
                                   "'");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.COMMA,
                                   "COMMA",
                                   TokenPattern.STRING_TYPE,
                                   ",");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.HYPHEN,
                                   "HYPHEN",
                                   TokenPattern.STRING_TYPE,
                                   "-");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.PERIOD,
                                   "PERIOD",
                                   TokenPattern.STRING_TYPE,
                                   ".");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.OP_PAREN,
                                   "OP_PAREN",
                                   TokenPattern.STRING_TYPE,
                                   "(");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.CL_PAREN,
                                   "CL_PAREN",
                                   TokenPattern.STRING_TYPE,
                                   ")");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.SEMICOLON,
                                   "SEMICOLON",
                                   TokenPattern.STRING_TYPE,
                                   ";");
        addPattern(pattern);

        pattern = new TokenPattern(PolispeakConstants.NAME,
                                   "NAME",
                                   TokenPattern.REGEXP_TYPE,
                                   "[a-zA-Z][a-zA-Z0-9_]*");
        addPattern(pattern);
    }
}
