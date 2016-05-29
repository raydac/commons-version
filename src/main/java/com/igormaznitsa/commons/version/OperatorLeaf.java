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

/**
 * It is a pseudo-operator, it does nothing and represents a leaf in the operator tree.
 * 
 * @since 1.0.0
 */
class OperatorLeaf implements Operator {

  private final Op op;
  private final Version base;
  
  OperatorLeaf(final Op op, final Version base) {
    this.op = op;
    this.base = base;
  }

  @Override
  public boolean isValid(final Version version) {
    final int result = this.base.compareTo(version);
    switch (this.op) {
      case EQU:
        return result == 0;
      case LESS:
        return result > 0;
      case GREAT:
        return result < 0;
      case LESS_OR_EQU:
        return result >= 0;
      case GREAT_OR_EQU:
        return result <= 0;
      default:
        throw new Error("Detected unexpected operation : "+this.op);
    }
  }
}
