/*
 * PolispeakParser.java
 *
 * THIS FILE HAS BEEN GENERATED AUTOMATICALLY. DO NOT EDIT!
 */

package jg.ps.parser;

import java.io.Reader;

import net.percederberg.grammatica.parser.ParserCreationException;
import net.percederberg.grammatica.parser.ProductionPattern;
import net.percederberg.grammatica.parser.ProductionPatternAlternative;
import net.percederberg.grammatica.parser.RecursiveDescentParser;
import net.percederberg.grammatica.parser.Tokenizer;

/**
 * A token stream parser.
 *
 *
 */
public class PolispeakParser extends RecursiveDescentParser {

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_1 = 3001;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_2 = 3002;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_3 = 3003;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_4 = 3004;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_5 = 3005;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_6 = 3006;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_7 = 3007;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_8 = 3008;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_9 = 3009;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_10 = 3010;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_11 = 3011;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_12 = 3012;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_13 = 3013;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_14 = 3014;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_15 = 3015;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_16 = 3016;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_17 = 3017;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_18 = 3018;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_19 = 3019;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_20 = 3020;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_21 = 3021;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_22 = 3022;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_23 = 3023;

    /**
     * A generated production node identity constant.
     */
    private static final int SUBPRODUCTION_24 = 3024;

    /**
     * Creates a new parser with a default analyzer.
     *
     * @param in             the input stream to read from
     *
     * @throws ParserCreationException if the parser couldn't be
     *             initialized correctly
     */
    public PolispeakParser(Reader in) throws ParserCreationException {
        super(in);
        createPatterns();
    }

    /**
     * Creates a new parser.
     *
     * @param in             the input stream to read from
     * @param analyzer       the analyzer to use while parsing
     *
     * @throws ParserCreationException if the parser couldn't be
     *             initialized correctly
     */
    public PolispeakParser(Reader in, PolispeakAnalyzer analyzer)
        throws ParserCreationException {

        super(in, analyzer);
        createPatterns();
    }

    /**
     * Creates a new tokenizer for this parser. Can be overridden by a
     * subclass to provide a custom implementation.
     *
     * @param in             the input stream to read from
     *
     * @return the tokenizer created
     *
     * @throws ParserCreationException if the tokenizer couldn't be
     *             initialized correctly
     */
    protected Tokenizer newTokenizer(Reader in)
        throws ParserCreationException {

        return new PolispeakTokenizer(in);
    }

    /**
     * Initializes the parser by creating all the production patterns.
     *
     * @throws ParserCreationException if the parser couldn't be
     *             initialized correctly
     */
    private void createPatterns() throws ParserCreationException {
        ProductionPattern             pattern;
        ProductionPatternAlternative  alt;

        pattern = new ProductionPattern(PolispeakConstants.BILL,
                                        "bill");
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.INTRO, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.INTRO,
                                        "intro");
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.RESOL_INTRO, 1, 1);
        alt.addProduction(PolispeakConstants.BILL_DESC, 1, 1);
        alt.addProduction(SUBPRODUCTION_1, 0, -1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.RESOL_INTRO,
                                        "resol_intro");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.RESOL_PHRASE, 1, 1);
        alt.addToken(PolispeakConstants.INTEGER, 1, 1);
        alt.addToken(PolispeakConstants.COLON, 1, 1);
        alt.addToken(PolispeakConstants.STRING, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.BILL_DESC,
                                        "bill_desc");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.TO_PHRASE, 1, 1);
        alt.addToken(PolispeakConstants.STRING, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.DEFINITIONS,
                                        "definitions");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.DEF_PHRASE, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.NAME, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.COLON, 1, 1);
        alt.addProduction(PolispeakConstants.DEFINITION_PARAM, 0, -1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.DEFINITION_PARAM,
                                        "definition_param");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.HYPHEN, 1, 1);
        alt.addProduction(PolispeakConstants.PARAM, 1, 1);
        alt.addToken(PolispeakConstants.PERIOD, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.SECTION,
                                        "section");
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.SECTION_INTRO, 1, 1);
        alt.addProduction(PolispeakConstants.SECTION_PROV, 0, 1);
        alt.addProduction(PolispeakConstants.SECTION_RETURN, 0, 1);
        alt.addProduction(PolispeakConstants.STEP, 0, -1);
        alt.addProduction(PolispeakConstants.SECTION_CONC, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.SECTION_CONC,
                                        "section_conc");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.SECTION_CONC_PHRASE, 1, 1);
        alt.addToken(PolispeakConstants.INTEGER, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.SECTION_INTRO,
                                        "section_intro");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.SECTION_PHRASE, 1, 1);
        alt.addToken(PolispeakConstants.INTEGER, 1, 1);
        alt.addProduction(SUBPRODUCTION_2, 0, 1);
        alt.addToken(PolispeakConstants.COLON, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.SECTION_PROV,
                                        "section_prov");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.PROV_PHRASE, 1, 1);
        alt.addProduction(PolispeakConstants.PARAM, 1, 1);
        alt.addProduction(SUBPRODUCTION_3, 0, -1);
        alt.addProduction(SUBPRODUCTION_4, 0, 1);
        alt.addToken(PolispeakConstants.PERIOD, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.SECTION_RETURN,
                                        "section_return");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.RESULT_PHRASE, 1, 1);
        alt.addProduction(PolispeakConstants.TYPE_DESCRIPTION, 1, 1);
        alt.addToken(PolispeakConstants.PERIOD, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.PARAM,
                                        "param");
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.TYPE_DESCRIPTION, 1, 1);
        alt.addToken(PolispeakConstants.DESIG_PHRASE, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.NAME, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.TYPE_DESCRIPTION,
                                        "type_description");
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.EXTERNAL_BILL, 0, 1);
        alt.addProduction(SUBPRODUCTION_5, 1, 1);
        alt.addProduction(PolispeakConstants.TYPE, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.TYPE,
                                        "type");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.INT_PHRASE, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.STR_PHRASE, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.FLOAT_PHRASE, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.NAME, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.UNKNOWN, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.EXTERNAL_BILL,
                                        "external_bill");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.REGARDS, 1, 1);
        alt.addToken(PolispeakConstants.STRING, 1, 1);
        alt.addToken(PolispeakConstants.COMMA, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.STEP,
                                        "step");
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_6, 1, 1);
        alt.addProduction(SUBPRODUCTION_7, 0, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.VAR_STEP,
                                        "var_step");
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_10, 1, 1);
        alt.addToken(PolispeakConstants.COLON, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, -1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.TYPE_INSTANT_STEP,
                                        "type_instant_step");
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_11, 1, 1);
        alt.addProduction(SUBPRODUCTION_14, 0, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.CONDITIONAL_STEP,
                                        "conditional_step");
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.CASE, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        alt.addToken(PolispeakConstants.BEING, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        alt.addToken(PolispeakConstants.FULFILLED, 1, 1);
        alt.addToken(PolispeakConstants.HOWEVER, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.FUNC_INVOC_STEP,
                                        "func_invoc_step");
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.EXTERNAL_BILL, 0, 1);
        alt.addProduction(SUBPRODUCTION_15, 1, 1);
        alt.addToken(PolispeakConstants.INTEGER, 1, 1);
        alt.addProduction(SUBPRODUCTION_18, 0, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.VAR_REF_STEP,
                                        "var_ref_step");
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_19, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_20, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.INSTANCE_MEM_STEP,
                                        "instance_mem_step");
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_21, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_22, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(PolispeakConstants.ATOMS,
                                        "atoms");
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_24, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.NULL, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_1,
                                        "Subproduction1");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.SECTION, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.DEFINITIONS, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_2,
                                        "Subproduction2");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.HYPHEN, 1, 1);
        alt.addToken(PolispeakConstants.STRING, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_3,
                                        "Subproduction3");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.COMMA, 1, 1);
        alt.addProduction(PolispeakConstants.PARAM, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_4,
                                        "Subproduction4");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.COMMA, 1, 1);
        alt.addToken(PolispeakConstants.AND, 1, 1);
        alt.addProduction(PolispeakConstants.PARAM, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_5,
                                        "Subproduction5");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.AN_PHRASE, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.A_PHRASE, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_6,
                                        "Subproduction6");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.VAR_STEP, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.TYPE_INSTANT_STEP, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.CONDITIONAL_STEP, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.FUNC_INVOC_STEP, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.VAR_REF_STEP, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.INSTANCE_MEM_STEP, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(PolispeakConstants.ATOMS, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_7,
                                        "Subproduction7");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.PERIOD, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.COMMA, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_8,
                                        "Subproduction8");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.WITHIN, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.NAME, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.TO_BE, 1, 1);
        alt.addProduction(PolispeakConstants.TYPE_DESCRIPTION, 1, 1);
        alt.addToken(PolispeakConstants.WHOSE, 1, 1);
        alt.addToken(PolispeakConstants.COLON, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_9,
                                        "Subproduction9");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.NAME, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.SHALL, 1, 1);
        alt.addProduction(PolispeakConstants.TYPE_DESCRIPTION, 1, 1);
        alt.addToken(PolispeakConstants.HOLD, 1, 1);
        alt.addToken(PolispeakConstants.COLON, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        alt.addToken(PolispeakConstants.RESPECT, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_10,
                                        "Subproduction10");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_8, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addProduction(SUBPRODUCTION_9, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_11,
                                        "Subproduction11");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.APPROP, 1, 1);
        alt.addProduction(PolispeakConstants.TYPE_DESCRIPTION, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.THE, 1, 1);
        alt.addProduction(PolispeakConstants.TYPE_DESCRIPTION, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_12,
                                        "Subproduction12");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.SEMICOLON, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_13,
                                        "Subproduction13");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.SEMICOLON, 1, 1);
        alt.addToken(PolispeakConstants.AND, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_14,
                                        "Subproduction14");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.WITH, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        alt.addProduction(SUBPRODUCTION_12, 0, -1);
        alt.addProduction(SUBPRODUCTION_13, 0, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_15,
                                        "Subproduction15");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.INVOKE, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.INVOKING, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.THE_INVOCATION, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_16,
                                        "Subproduction16");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.SEMICOLON, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_17,
                                        "Subproduction17");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.SEMICOLON, 1, 1);
        alt.addToken(PolispeakConstants.AND, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_18,
                                        "Subproduction18");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.WITH, 1, 1);
        alt.addToken(PolispeakConstants.COLON, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        alt.addProduction(SUBPRODUCTION_16, 0, -1);
        alt.addProduction(SUBPRODUCTION_17, 0, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_19,
                                        "Subproduction19");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.REFERRING, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.NAME, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.CUR_CONTEXT, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_20,
                                        "Subproduction20");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.WHATEVER, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.NAME, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_21,
                                        "Subproduction21");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.HELD, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.NAME, 1, 1);
        alt.addToken(PolispeakConstants.S_QUOTE, 1, 1);
        alt.addToken(PolispeakConstants.FROM, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_22,
                                        "Subproduction22");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.AUDIT, 1, 1);
        alt.addProduction(PolispeakConstants.STEP, 1, 1);
        alt.addToken(PolispeakConstants.ASSET, 1, 1);
        alt.addToken(PolispeakConstants.NAME, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_23,
                                        "Subproduction23");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.INT_PHRASE, 1, 1);
        alt.addToken(PolispeakConstants.INTEGER, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.FLOAT_PHRASE, 1, 1);
        alt.addToken(PolispeakConstants.DOUBLE, 1, 1);
        pattern.addAlternative(alt);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.STR_PHRASE, 1, 1);
        alt.addToken(PolispeakConstants.STRING, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);

        pattern = new ProductionPattern(SUBPRODUCTION_24,
                                        "Subproduction24");
        pattern.setSynthetic(true);
        alt = new ProductionPatternAlternative();
        alt.addToken(PolispeakConstants.THE_PHRASE, 1, 1);
        alt.addProduction(SUBPRODUCTION_23, 1, 1);
        pattern.addAlternative(alt);
        addPattern(pattern);
    }
}
