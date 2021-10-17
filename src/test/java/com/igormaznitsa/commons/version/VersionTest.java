package com.igormaznitsa.commons.version;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class VersionTest {

  private static String[] toStrArray(final Version[] versions) {
    final String[] result = new String[versions.length];
    for (int i = 0; i < versions.length; i++) {
      result[i] = versions[i].toString();
    }
    return result;
  }

  @Test
  public void testConstructor_Str() {
    assertEquals("idea-1.4.15-alpha", new Version("  idea-1.04.0015-alpha  ").toString());
    assertEquals("idea-alpha", new Version("  idea-alpha  ").toString());
    assertEquals("1.2.3.4.5", new Version(" 0001.2.3.004.00005").toString());
    assertEquals("", new Version(" ").toString());
    assertEquals("world", new Version("world ").toString());
    assertEquals("world11", new Version("world11 ").toString());
  }

  @Test
  public void testEqualsAndHash() {
    final Version a = new Version("idea-1.2.3-dev");
    final Version b = new Version("idea-1.2.3-dev");
    assertEquals(a.hashCode(), b.hashCode());
    assertEquals(a, b);
    assertEquals(a, a);
    assertNotEquals(a, b.changePrefix("idal"));
    assertNotEquals(a, new Version((String) null));
  }

  @Test
  public void testChange() {
    assertEquals("netbeans-6.2.1-dev", new Version("idea-1.2.3-prod").changeNumeric(6, 2, 1).changePrefix("netbeans").changePostfix("dev").toString());
  }

  @Test
  public void testCompare_Nums() {
    final Version[] versions = {new Version(1, 2, 3), new Version(2), new Version(8), new Version(1, 1, 1), new Version(1, 2)};
    Arrays.sort(versions);
    assertArrayEquals(new String[]{"1.1.1", "1.2", "1.2.3", "2", "8"}, toStrArray(versions));
  }

  @Test
  public void testCompare_NumsAndPrefix() {
    final Version[] versions = {new Version("zz-1.2.3"), new Version("d-1.2.3"), new Version("c-1.2.3"), new Version("a-1.2.3"), new Version("y-1.2.3")};
    Arrays.sort(versions);
    assertArrayEquals(new String[]{"a-1.2.3", "c-1.2.3", "d-1.2.3", "y-1.2.3", "zz-1.2.3"}, toStrArray(versions));
  }

  @Test
  public void testParts_forNull() {
    final Version version = new Version((String) null);
    assertEquals("", version.getPrefix());
    assertEquals("", version.getPostfix());
    assertEquals(0L, version.getMajor());
    assertEquals("", version.toString());
    assertFalse(version.isNumericPartPresented());
  }

  @Test
  public void testNumericParts() {
    final Version version = new Version("hell-1234.5678.9101112-end");
    assertEquals(1234L, version.getMajor());
    assertEquals(5678L, version.getMinor());
    assertEquals(9101112L, version.getMicro());
    assertTrue(version.isNumericPartPresented());
  }

  @Test
  public void testNetbeansversionParsing() {
    final Version version = new Version("NetBeans IDE 7.4 (Build 201310111528) (#623423d2342)".replace(' ', '-'));
    assertEquals("NetBeans IDE", version.getPrefix().replace('-', ' '));
    assertEquals(7L, version.getMajor());
    assertEquals(4L, version.getMinor());
    assertEquals(0L, version.getMicro());
    assertEquals("(Build 201310111528) (#623423d2342)", version.getPostfix().replace('-', ' '));
  }
}
