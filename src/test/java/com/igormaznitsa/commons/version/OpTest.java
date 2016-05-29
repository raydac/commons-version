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

import com.igormaznitsa.commons.version.Op;
import org.junit.Test;
import static org.junit.Assert.*;

public class OpTest {
  
  @Test
  public void testSomeMethod() {
    assertEquals(Op.EQU, Op.decode("="));
    assertEquals(Op.LESS, Op.decode("<"));
    assertEquals(Op.GREAT, Op.decode(">"));
    assertEquals(Op.GREAT_OR_EQU, Op.decode(">="));
    assertEquals(Op.LESS_OR_EQU, Op.decode("<="));
    assertEquals(Op.EQU, Op.decode(""));
    assertEquals(Op.EQU, Op.decode(null));
    assertEquals(Op.EQU, Op.decode(">>>>"));
  }
  
}
