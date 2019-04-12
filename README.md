# Just the "foo"

An attempt to figure out what needs to be done to build gradle project via
maven and have resulting artifacts deployed into maven repo.

Requires:

```bash
$ gradle --version

------------------------------------------------------------
Gradle 4.10.3
------------------------------------------------------------

Build time:   2018-12-05 00:50:54 UTC
Revision:     e76905e3a1034e6f724566aeb985621347ff43bc

Kotlin DSL:   1.0-rc-6
Kotlin:       1.2.61
Groovy:       2.4.15
Ant:          Apache Ant(TM) version 1.9.11 compiled on March 23 2018
JVM:          1.8.0_202-ea (Oracle Corporation 25.202-b03)
OS:           Mac OS X 10.14.3 x86_64


$ java -version
java version "1.8.0_202-ea"
Java(TM) SE Runtime Environment (build 1.8.0_202-ea-b03)
Java HotSpot(TM) 64-Bit Server VM (build 25.202-b03, mixed mode)
```

Run gradle build with IT tests:

```bash
$ gradle clean check
``` 