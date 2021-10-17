package com.igormaznitsa.commons.version;

import com.igormaznitsa.commons.version.operators.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VersionValidatorTest {

  @Test
  public void testConstructor_Str() {
    assertNull(new VersionValidator((String) null).getExpressionRoot());
    assertNotNull(new VersionValidator(">=1.1.1").getExpressionRoot());
  }

  @Test
  public void testConstructor_StrParser() {
    final ExpressionParser parser = mock(ExpressionParser.class);
    new VersionValidator("hello parser", parser);
    verify(parser, times(1)).parse("hello parser");
  }

  @Test
  public void testConstructor_Operator() {
    assertNull(new VersionValidator((Operator) null).getExpressionRoot());
    final Operator operator = new OperatorAnd(new OperatorLeaf(Condition.EQU, new Version("1.1.1")), new OperatorLeaf(Condition.EQU, new Version("1.2.2")));
    assertSame(operator, new VersionValidator(operator).getExpressionRoot());
  }

  @Test
  public void testGetExpressionRoot() {
    assertNotNull(new VersionValidator("=1.2.3").getExpressionRoot());
    assertNull(new VersionValidator((String) null).getExpressionRoot());
  }

  @Test
  public void testCreateFromNull() {
    assertFalse(new VersionValidator((String) null).isValid(null));
    assertFalse(new VersionValidator((String) null).isValid(new Version("1.2.3.4-aaa")));
  }

  @Test
  public void testToString() {
    assertEquals("VersionValidator[]", new VersionValidator((String) null).toString());
    assertEquals("VersionValidator[=1.2.3,<4.5.6;>=7.8.1,<=1-SNAPSHOT;!=4.4.5]", new VersionValidator("1.2.3,<4.5.6;>=7.8.1,<=1-SNAPSHOT;!=4.4.5").toString());
  }

  @Test
  public void testEqu() {
    assertTrue(new VersionValidator("=idea-1.2.3-a").isValid(new Version("idea-01.002.0003-a")));
    assertFalse(new VersionValidator("=idea-1.2.3-a").isValid(new Version("idea-01.002.0003-b")));
  }

  @Test
  public void testNotEqu() {
    assertFalse(new VersionValidator("!=idea-1.2.3-a").isValid(new Version("idea-01.002.0003-a")));
    assertTrue(new VersionValidator("!=idea-1.2.3-a").isValid(new Version("idea-01.002.0003-b")));
  }

  @Test
  public void testNoOpRecognizedAsEqu() {
    assertTrue(new VersionValidator("idea-1.2.3-a").isValid(new Version("idea-01.002.0003-a")));
    assertFalse(new VersionValidator("idea-1.2.3-a").isValid(new Version("idea-01.002.0003-b")));
  }

  @Test
  public void testLess() {
    assertTrue(new VersionValidator("<idea-1.2.3-b").isValid(new Version("idea-01.002.0003-a")));
    assertFalse(new VersionValidator("<idea-1.2.3-b").isValid(new Version("idea-01.002.0003-c")));
    assertFalse(new VersionValidator("<idea-1.2.3-b").isValid(new Version("idea-01.002.0003-b")));
  }

  @Test
  public void testLessEqu() {
    assertTrue(new VersionValidator("<=idea-1.2.3-b").isValid(new Version("idea-01.002.0003-a")));
    assertFalse(new VersionValidator("<=idea-1.2.3-b").isValid(new Version("idea-01.002.0003-c")));
    assertTrue(new VersionValidator("<=idea-1.2.3-b").isValid(new Version("idea-01.002.0003-b")));
  }

  @Test
  public void testGreat() {
    assertFalse(new VersionValidator(">idea-1.2.3-b").isValid(new Version("idea-01.002.0003-a")));
    assertFalse(new VersionValidator(">idea-1.2.3-b").isValid(new Version("idea-01.002.0003-b")));
    assertTrue(new VersionValidator(">idea-1.2.3-b").isValid(new Version("idea-01.002.0003-c")));
  }

  @Test
  public void testGreatEqu() {
    assertFalse(new VersionValidator(">=idea-1.2.3-b").isValid(new Version("idea-01.002.0003-a")));
    assertTrue(new VersionValidator(">=idea-1.2.3-b").isValid(new Version("idea-01.002.0003-b")));
    assertTrue(new VersionValidator(">=idea-1.2.3-b").isValid(new Version("idea-01.002.0003-c")));
  }

  @Test
  public void testAnd() {
    assertTrue(new VersionValidator(">some-1.2.3,<some-1.2.5").isValid(new Version("some-1.2.4")));
    assertFalse(new VersionValidator(">some-1.2.3,<some-1.2.5").isValid(new Version("some-1.2.5")));
    assertTrue(new VersionValidator(">some-1.2.3,<=some-1.2.5").isValid(new Version("some-1.2.5")));

    final VersionValidator validator = new VersionValidator("2; 3; 8; 11");
    final List<Long> result = new ArrayList<>();
    for (long i = 0; i < 50; i++) {
      final Version version = new Version(null, new long[]{i}, null);
      if (validator.isValid(version)) {
        result.add(i);
      }
    }
    assertEquals(4, result.size());
    assertEquals(2L, result.get(0).longValue());
    assertEquals(3L, result.get(1).longValue());
    assertEquals(8L, result.get(2).longValue());
    assertEquals(11L, result.get(3).longValue());

  }

  @Test
  public void testOr() {
    assertTrue(new VersionValidator("some-1.2.3;<some-1.2.5").isValid(new Version("some-1.2.4")));
    assertTrue(new VersionValidator(">some-1.2.3;<some-1.2.5").isValid(new Version("some-1.2.1")));
    assertTrue(new VersionValidator(">some-1.2.3;<some-1.2.5").isValid(new Version("some-1.2.8")));
    assertTrue(new VersionValidator(">some-1.2.3;<=some-1.2.5;some-1.2.10").isValid(new Version("some-1.2.10")));
  }

}
