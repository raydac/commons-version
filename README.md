[![License Apache 2.0](https://img.shields.io/badge/license-Apache%20License%202.0-green.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Java 6.0+](https://img.shields.io/badge/java-6.0%2b-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.igormaznitsa/commons-version/badge.svg)](http://search.maven.org/#artifactdetails|com.igormaznitsa|commons-version|1.0.0|jar)
[![PayPal donation](https://img.shields.io/badge/donation-PayPal-red.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=AHWJHJFBAWGL2)
[![Yandex.Money donation](https://img.shields.io/badge/donation-Я.деньги-yellow.svg)](https://money.yandex.ru/embed/small.xml?account=41001158080699&quickpay=small&yamoney-payment-type=on&button-text=01&button-size=l&button-color=orange&targets=%D0%9F%D0%BE%D0%B6%D0%B5%D1%80%D1%82%D0%B2%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5+%D0%BD%D0%B0+%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D1%8B+%D1%81+%D0%BE%D1%82%D0%BA%D1%80%D1%8B%D1%82%D1%8B%D0%BC+%D0%B8%D1%81%D1%85%D0%BE%D0%B4%D0%BD%D1%8B%D0%BC+%D0%BA%D0%BE%D0%B4%D0%BE%D0%BC&default-sum=100&successURL=)


# What is it?
it is an auxiliary framework to parse and validate some version information.

# How it works?
It has two main classes
- __[com.igormaznitsa.commons.version.Version](https://github.com/raydac/commons-version/blob/master/src/main/java/com/igormaznitsa/commons/version/Version.java)__ to parse string representation of version, usual format is XXX-NNN.NNN.NN-ZZZZ
- __[com.igormaznitsa.commons.version.VersionValidator](https://github.com/raydac/commons-version/blob/master/src/main/java/com/igormaznitsa/commons/version/VersionValidator.java)__ to validate version for some logical condition

# Usage of version
```
Version parsed = new Version("idea-1.04.0015-alpha");
Version fullyFromScratch = new Version("idea",new long[]{1,4,15},"alpha");

Version onlyNumber = new Version(1,4,15);
Version changed = onlyNumber.changePrefix("idea").changePostfix("alpha").changeNumeric(0,1,2);
```
# Usage of version validator
```
VersionValidator validator = new VersionValidator(">idea-1.1.0,<idea-3.0.2;1.1.0,3.0.2;!=0.0.1-dev");
if (validator.isValid(someVersion)){
  System.out.println("Version valid");
}
```