## FinTx Common Project

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fintx/fintx-common/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/org.fintx/fintx-common/)
[![GitHub release](https://img.shields.io/github/release/fintx/fintx-common.svg)](https://github.com/fintx/fintx-common/releases)
![Apache 2](http://img.shields.io/badge/license-Apache%202-red.svg)
[![Join the chat at https://gitter.im/fintx/fintx-common](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/fintx/fintx-common?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/fintx/fintx-common.svg?branch=master)](https://travis-ci.org/fintx/fintx-common)
[![codecov.io](https://codecov.io/github/fintx/fintx-common/coverage.svg?branch=master)](https://codecov.io/github/fintx/fintx-common?branch=master)

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

[1] FinTx https://www.fintx.org/    
[2] Maven https://maven.apache.org/
