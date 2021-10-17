[![License Apache 2.0](https://img.shields.io/badge/license-Apache%20License%202.0-green.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.igormaznitsa/commons-version/badge.svg)](http://search.maven.org/#artifactdetails|com.igormaznitsa|commons-version|1.0.0|jar)
[![Java 8.0+](https://img.shields.io/badge/java-8.0%2b-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![PayPal donation](https://img.shields.io/badge/donation-PayPal-cyan.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=AHWJHJFBAWGL2)
[![YooMoney donation](https://img.shields.io/badge/donation-Yoo.money-blue.svg)](https://yoomoney.ru/to/41001158080699)

# Changes

- __1.0.1 (SNAPSHOT)__
  - updated maven plugin dependencies
  - minimal version of JDK lifted up to 1.8


- __1.0.0 (31-mar-2016)__
  - initial version

# What is it?

It is a small auxiliary framework to parse, validate and compare versions defined through string
format `XXX-NNN.NNN.NN-ZZZZ`.

# How it works?

It has two main classes

- __[com.igormaznitsa.commons.version.Version](https://github.com/raydac/commons-version/blob/master/src/main/java/com/igormaznitsa/commons/version/Version.java)__
  to parse string representation of version, usual format is XXX-NNN.NNN.NN-ZZZZ
- __[com.igormaznitsa.commons.version.VersionValidator](https://github.com/raydac/commons-version/blob/master/src/main/java/com/igormaznitsa/commons/version/VersionValidator.java)__
  to validate version for some logical condition

# Usage of version

```java
Version parsed=new Version("idea-1.04.0015-alpha");
        Version fullyFromScratch=new Version("idea",new long[]{1,4,15},"alpha");

        Version onlyNumber=new Version(1,4,15);
        Version changed=onlyNumber.changePrefix("idea").changePostfix("alpha").changeNumeric(0,1,2);
```

# Usage of version validator

Validator supports __AND (,)__ and __OR (;)__ logical operators, where AND has higher priority.  
Allowed conditions:

- __=__ equals
- __<__ less
- __>__ great
- __>=__ great or equals
- __<=__ less or equals
- if there is no any operator then it will be recognized as __=__

```java
VersionValidator validator=new VersionValidator(">idea-1.1.0,<idea-3.0.2;1.1.0,3.0.2;!=0.0.1-dev");
        if(validator.isValid(someVersion)){
        System.out.println("Version valid");
}
```
Also it is possible to implement own expression parser to parse expressions.   
__NB! Wrong written conditional operator will be recognized as part of the version prefix! Be careful for typo like `=>`!__