## FinTx Common Project

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fintx/fintx-common/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/org.fintx/fintx-common/)
[![GitHub release](https://img.shields.io/github/release/fintx/fintx-common.svg)](https://github.com/fintx/fintx-common/releases)
![Apache 2](http://img.shields.io/badge/license-Apache%202-red.svg)
[![Join the chat at https://gitter.im/fintx/fintx-common](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/fintx/fintx-common?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/fintx/fintx-common.svg?branch=master)](https://travis-ci.org/fintx/fintx-common)
[![Coverage Status](https://coveralls.io/repos/github/fintx/fintx-common/badge.svg)](https://coveralls.io/github/fintx/fintx-common)
[![Dependency Status](https://www.versioneye.com/user/projects/598c0fc3368b08388f34135b/badge.svg?style=flat)](https://www.versioneye.com/user/projects/598c0fc3368b08388f34135b)


# FinTx[1]

## What's is FinTx?

FinTx is an open source group focus on financial technologies.

## What's is fintx-common

fintx-common is common library for all fintx projects.

## Using
This is something that you get for free just by adding the following dependency inside your project:

```xml
<dependency>
    <groupId>org.fintx</groupId>
    <artifactId>fintx-common</artifactId>
    <version>${latest.version></version>
</dependency>
```
## Example
1. Get a 20 characters length unique id.

```java
String id = UniqueId.get().toBase64String();
```
2. Parse id to get timestamp, machine identifier (physical MAC address), process identifier, counter number.

```java
UniqueId uniqueId = UniqueId.fromBase64String(id);    
long timestamp = uniqueId.getTimestamp();    
long machineId = uniqueId.getMachineIdentifier();    
int processId = uniqueId.getProcessIdentifier();    
long counter = uniqueId.getCounter();    
```
3. Deep clone of a object

```java
PoJo pojo = new PoJo();
PoJo pojo2 = Objects.deepClone(pojo);
String[] strs=Objects.deepClone(new String[] {"a","b"});
```
4. Properties copy between different class object.

```java
PoJo pojo = new PoJo();
pojo.setIn(0);
APoJo aPojo = new APoJo();
Objects.copyProperties(pojo, aPojo); 
```

5. Xml and object binding.

```java
//Normal binding
PoJo pojo = new PoJo();
pojo.setIn(0);
ints[0] = 1;
pojo.setIns(ints);
pojo.setObjs(new Object[10]);
pojo.getObjs() [0]="s";
pojo.setStr("aa");
pojo.setStrs(new String[3]);
List<String> list=new ArrayList<String>();
list.add("a");
list.add("b");
pojo.setList(list);
String xml = Objects.Xml.toString(pojo);
PoJo pojo2 = Objects.Xml.toObject(xml, PoJo.class);
//Custom binding. Properties like prefix, format, encoding, fragment and headers could be specified.
PoJo2 pojo3=new PoJo2();
Objects.copyProperties(pojo, pojo3);
Map<String,String> mapper=new HashMap<String,String>();
mapper.put("www.adtec.com.cn", "adt");
ObjectsXml objectsXml = Objects.Xml.custom(mapper, false, Encoding.GB18030, false, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
xml = objectsXml.toString(pojo3);
System.out.println(xml);
pojo3 = objectsXml.toObject(xml, PoJo2.class);
```

6. Text and object binding.

```java
//Normal binding
TextPoJo pojo = new TextPoJo();
pojo.setF1("a");
pojo.setF3("b");
String text = null;
text = Objects.Text.toString(pojo);
//Custom binding. Properties like encoding, seperator, linebreak, withname and associate could be specified.
TextPoJo pojo2 = Objects.Text.toObject(text, TextPoJo.class);
ObjectsText objectsText = Objects.Text.custom(Encoding.GB18030, '|', "\r\n", true, '=');
text = objectsText.toString(pojo);
pojo2 = objectsText.toObject(text, TextPoJo.class);

```

7. Http client. 

```java
//Normal use
HttpClient.get(new URL("http://www.baidu.com"))
//Custom
HttpClientBase httpClient = HttpClient.custom(trustStore, keyStore, keyPass);
httpClient.get(new URL("http://www.baidu.com"));
```

7. Other Arrays and Strings are from ArrayUtil and StringUtil of commons-lang3 project. They are the little and simple version that remove and combine many unusual functions. The rest of utils are very simple. Please refer to the test code and source code.




[1] FinTx https://www.fintx.org/    
[2] Maven https://maven.apache.org/
