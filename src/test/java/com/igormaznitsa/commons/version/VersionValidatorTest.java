package com.igormaznitsa.commons.version;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class VersionValidatorTest {
  
  @Test
  public void getExpressionTreeRoot() {
    assertNotNull(new VersionValidator("=1.2.3").getExpressionTreeRoot());
    assertNull(new VersionValidator(null).getExpressionTreeRoot());
  }
  
  @Test
  public void testCreateFromNull() {
    assertFalse(new VersionValidator(null).isValid(null));
    assertFalse(new VersionValidator(null).isValid(new Version("1.2.3.4")));
  }

  @Test
  public void testToString(){
    assertEquals("VersionValidator[]",new VersionValidator(null).toString());
    assertEquals("VersionValidator[=1.2.3,<4.5.6;>=7.8.1,<=1-SNAPSHOT;!=4.4.5]",new VersionValidator("1.2.3,<4.5.6;>=7.8.1,<=1-SNAPSHOT;!=4.4.5").toString());
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
    final List<Long> result = new ArrayList<Long>();
    for(long i=0;i<50;i++){
      final Version version = new Version(null, new long[]{i}, null);
      if (validator.isValid(version)){
        result.add(i);
      }
    }
    assertEquals(4,result.size());
    assertEquals(2L,result.get(0).longValue());
    assertEquals(3L,result.get(1).longValue());
    assertEquals(8L,result.get(2).longValue());
    assertEquals(11L,result.get(3).longValue());
    
  }

  @Test
  public void testOr() {
    assertTrue(new VersionValidator("some-1.2.3;<some-1.2.5").isValid(new Version("some-1.2.4")));
    assertTrue(new VersionValidator(">some-1.2.3;<some-1.2.5").isValid(new Version("some-1.2.1")));
    assertTrue(new VersionValidator(">some-1.2.3;<some-1.2.5").isValid(new Version("some-1.2.8")));
    assertTrue(new VersionValidator(">some-1.2.3;<=some-1.2.5;some-1.2.10").isValid(new Version("some-1.2.10")));
  }

}
