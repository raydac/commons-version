/*
 * Copyright 2016 Igor Maznitsa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.igormaznitsa.commons.version;

import com.igormaznitsa.commons.version.operators.OperatorLeaf;
import com.igormaznitsa.commons.version.operators.OperatorOr;
import com.igormaznitsa.commons.version.operators.Operator;
import com.igormaznitsa.commons.version.operators.Condition;
import com.igormaznitsa.commons.version.operators.OperatorAnd;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class allows to define rules to validate versions.
 * It supports logical AND(,) and OR(;) operators. OR has less priority(!)
 * 
 * @since 1.0.0
 */
public final class VersionValidator implements Serializable {
  private static final long serialVersionUID = 641987018021820537L;

  private static final Pattern PATTERN_LEAF = Pattern.compile("(!=|>=|<=|>|<|=)\\s*(.*)");
  private static final Pattern PATTERN_OR = Pattern.compile("(.+)\\;(.+)");
  private static final Pattern PATTERN_AND = Pattern.compile("(.+)\\,(.+)");

  private final Operator expressionRoot;
  
  /**
   * Make validator based on parsed expression.
   * @param expressionRoot operator to be used as the root of expression tree, it can be null
   * @since 1.0.0
   */
  public VersionValidator(final Operator expressionRoot) {
    this.expressionRoot = expressionRoot;
  }
  
  /**
   * Make validator from string.
   * @param str text with rule for validator, it can be null but in the case the result will be false every time
   * @since 1.0.0
   */
  public VersionValidator(final String str) {
    this(str == null ? null : parseExpressionTree(str));
  }
  
  /**
   * Get the parsed expression tree root.
   * @return the expression tree root, it can be null
   * @since 1.0.0
   */
  public Operator getExpressionTreeRoot(){
    return this.expressionRoot;
  }
  
  private static Operator parseExpressionTree(final String text){
    final Matcher orMatcher = PATTERN_OR.matcher(text);
    if (orMatcher.matches()){
      return new OperatorOr(parseExpressionTree(orMatcher.group(1)), parseExpressionTree(orMatcher.group(2)));
    }
    final Matcher andMatcher = PATTERN_AND.matcher(text);
    if (andMatcher.matches()) {
      return new OperatorAnd(parseExpressionTree(andMatcher.group(1)), parseExpressionTree(andMatcher.group(2)));
    }
    final Matcher leaf = PATTERN_LEAF.matcher(text.trim());
    if (leaf.matches()){
      return new OperatorLeaf(Condition.decode(leaf.group(1)), new Version(leaf.group(2)));
    } else {
      return new OperatorLeaf(Condition.EQU, new Version(text));
    }
  }
  
  /**
   * Validate version for the rule.
   * @param version the version to be checked, it can be null
   * @return true if the version is valid or false if the version is null or not valid from point of view the rule.
   * 
   * @since 1.0.0
   */
  public boolean isValid(final Version version){
    return this.expressionRoot == null || version == null ? false : this.expressionRoot.isValid(version);
  }
  
  @Override
  public String toString(){
    return VersionValidator.class.getSimpleName()+(this.expressionRoot == null ? "[]" : '['+this.expressionRoot.toString()+']');
  }
  
}
