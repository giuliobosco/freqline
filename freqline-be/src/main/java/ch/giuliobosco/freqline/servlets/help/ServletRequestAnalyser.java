/*
 * The MIT License
 *
 * Copyright 2019 giuliobosco.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.giuliobosco.freqline.servlets.help;

import ch.giuliobosco.freqline.help.StringArrayHelper;
import ch.giuliobosco.freqline.help.StringHelper;
import ch.giuliobosco.freqline.help.validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Analysis of the http request.
 * Check if the request attribute are right.
 * From: https://github.com/giuliobosco/progetto1/blob/master/src/analyzer/RequestAnalysis.java
 *
 * @author giuliobosco
 * @version 2.0.1 (2018-10-30 - 2019-10-22)
 */
public class ServletRequestAnalyser {
    // -------------------------------------------------------------------------------------------------------- Costants

    /**
     * OK Status of the Request Analyser.
     */
    public static final int OK = 0;

    /**
     * Missing some required parameters.
     * If attribute missingParameters length equals 0.
     */
    public static final int MISSING_PARAMETERS = 1;

    /**
     * Some parameters are not valid (required or optional parameters).
     * If attribute notValidParameters length equals 0.
     */
    public static final int NOT_VALID_PARAMETERS = 2;

    // ------------------------------------------------------------------------------------------------------ Attributes

    /**
     * Map of the parameters.
     */
    private Map<String, String[]> parameters;

    /**
     * Required parameters.
     */
    private String[] requiredParameters;

    /**
     * Optional parameters.
     */
    private String[] optionalParameters;

    /**
     * Missing parameters.
     */
    private String[] missingParameters;

    /**
     * Not valid parameters (required or optional parameters).
     */
    private String[] notValidParameters;

    /**
     * Present optional parameters.
     */
    private String[] presentOptionalParameters;

    /**
     * Validator for values validation.
     */
    private Validator validator;

    // ----------------------------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the missing parameters.
     *
     * @return Missing parameters.
     */
    public String[] getMissingParameters() {
        return this.missingParameters;
    }

    /**
     * Get the not valid parameters.
     *
     * @return Not valid parameters.
     */
    public String[] getNotValidParameters() {
        return this.notValidParameters;
    }

    /**
     * Get the present optional parameters.
     *
     * @return Present optional parameters.
     */
    public String[] getPresentOptionalParameters() {
        return this.presentOptionalParameters;
    }

    /**
     * Get the status of the analysis.
     *
     * @return Status of the analysis.
     */
    public int getStatus() {
        if (getMissingParameters().length > 0) {
            return MISSING_PARAMETERS;
        }

        if (getNotValidParameters().length > 0) {
            return NOT_VALID_PARAMETERS;
        }

        return OK;
    }

    /**
     * Get the parameters.
     *
     * @return Parameters.
     */
    public Map<String, String[]> getParameters() {
        return this.parameters;
    }

    // ---------------------------------------------------------------------------------------------------- Constructors

    /**
     * Create the request analyser with required attributes, optional attributes, parameters and the validator.
     *
     * @param requiredParameters Required attributes.
     * @param optionalParameters Optional attributes.
     * @param parameters         Parameters.
     * @param validator          Validator.
     */
    public ServletRequestAnalyser(String[] requiredParameters, String[] optionalParameters, Map<String, String[]> parameters, Validator validator) {
        this.validator = validator;
        this.parameters = parameters;

        this.requiredParameters = requiredParameters;
        setMissingParameters();
        this.optionalParameters = optionalParameters;
        setPresentOptionalParameters();

        String[] attributes = StringArrayHelper.concatenateArray(this.requiredParameters, getPresentOptionalParameters());
        setNotValidParameters(attributes);
    }

    /**
     * Create the request analyser with required parameters, optional parameters and parameters.
     *
     * @param requiredParameters Required parameters.
     * @param optionalParameters Optional parameters.
     * @param parameters         Parameters.
     */
    public ServletRequestAnalyser(String[] requiredParameters, String[] optionalParameters, Map<String, String[]> parameters) {
        this(requiredParameters, optionalParameters, parameters, null);
    }

    /**
     * Create the request analyser with required parameters and parameters.
     *
     * @param requiredParameters Required parameters.
     * @param parameters         Parameters.
     */
    public ServletRequestAnalyser(String[] requiredParameters, Map<String, String[]> parameters) {
        this(requiredParameters, null, parameters);
    }

    /**
     * Create the request analyser with required parameters, parameters and validator.
     *
     * @param requiredParameters Required parameters.
     * @param parameters         Parameters.
     * @param validator          Validator.
     */
    public ServletRequestAnalyser(String[] requiredParameters, Map<String, String[]> parameters, Validator validator) {
        this(requiredParameters, null, parameters, validator);
    }

    // ---------------------------------------------------------------------------------------------------- Help Methods

    /**
     * Set missing parameters.
     * Check that the parameters map contains all required parameters.
     */
    private void setMissingParameters() {
        List<String> missingParameters = new ArrayList<>();

        for (String parameter : requiredParameters) {
            String[] parameterValues = this.parameters.get(parameter);
            if (StringArrayHelper.arrayEmpty(parameterValues) || !StringHelper.is(parameterValues[0])) {
                missingParameters.add(parameter);
            }
        }

        this.missingParameters = StringArrayHelper.toStringArray(missingParameters);
    }

    /**
     * Set present optional parameters.
     * Insert in the present optional parameters all present optional parameters.
     */
    private void setPresentOptionalParameters() {
        List<String> presentOptionalParameters = new ArrayList<>();

        if (this.optionalParameters != null) {
            for (String parameter : this.optionalParameters) {
                String[] parameterValues = this.parameters.get(parameter);
                System.out.println(StringArrayHelper.arrayNotEmpty(parameterValues));
                if (StringArrayHelper.arrayNotEmpty(parameterValues) && StringHelper.is(parameterValues[0])) {
                    presentOptionalParameters.add(parameter);
                }
            }
        }

        this.presentOptionalParameters = StringArrayHelper.toStringArray(presentOptionalParameters);
    }

    /**
     * Check all not valid parameters.
     *
     * @param parameters Parameters to check.
     */
    private void setNotValidParameters(String[] parameters) {
        List<String> notValidParameters = new ArrayList<>();

        if (this.validator != null) {
            for (String parameter : parameters) {
                String[] parameterValues = this.parameters.get(parameter);
                if (StringArrayHelper.arrayEmpty(parameterValues) || !this.validator.isValid(parameterValues[0])) {
                    notValidParameters.add(parameter);
                }
            }
        }

        this.notValidParameters = StringArrayHelper.toStringArray(notValidParameters);
    }
}