# Just the "foo"

An attempt to figure out what needs to be done to build **gradle** project via
**maven** and have resulting artifacts deployed into maven repo.

The code under the `/src` folder is important to the point that it uses
Elasticsearch gradle plugin which takes care of building of some of the resulting
artifacts (AFAIK).

## Build the project using gradle

### Requires

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

Run gradle build with IT tests and check resulting artifacts:

```bash
$ gradle clean check && ls -n build/distributions/
[...]
-rw-r--r--  1 501  20  11553 Apr 12 16:41 foo-1.0.0.jar
-rw-r--r--  1 501  20  16004 Apr 12 16:41 foo-1.0.0.zip
```

## Build the project using maven

### Requires

```bash
$ mvn --version
Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3; 2018-10-24T20:41:47+02:00)
Maven home: /Users/lvlcek/apps/apache-maven-3.6.0
Java version: 1.8.0_202-ea, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre
Default locale: en_GB, platform encoding: UTF-8
OS name: "mac os x", version: "10.14.4", arch: "x86_64", family: "mac"
```

Running maven requires providing path to maven repository that is used both for
dependencies resolution and artifact deploy target. In this case I am using
the default maven repository found in my home folder: `/Users/lvlcek/.m2/repository`. You man need to customize the value.

```bash
$ mvn -Dmaven.repo.url=local::default::file:///Users/lvlcek/.m2/repository \
  clean deploy

[...]

$ ls -n build/distributions/
total 200
-rw-r--r--  1 501  20  50451 Apr 16 15:22 foo-1.0.0-javadoc.jar
-rw-r--r--  1 501  20  12364 Apr 16 15:22 foo-1.0.0-sources.jar
-rw-r--r--  1 501  20  11552 Apr 16 15:22 foo-1.0.0.jar
-rw-r--r--  1 501  20    627 Apr 16 15:22 foo-1.0.0.pom
-rw-r--r--  1 501  20  16012 Apr 16 15:22 foo-1.0.0.zip
```

As we can see the folder `build/distributions/` contains all the artifacts we would like to deploy into target maven repository.

However, we can see that maven deployed only the following:

```bash
[...]
[INFO] gradle execution complete
[INFO] 
[INFO] --- maven-install-plugin:2.4:install (default-install) @ foo ---
[INFO] Installing /Users/lvlcek/projects/lukas-vlcek/mvn-gradle-es-plugin-test/pom.xml to /Users/lvlcek/.m2/repository/org/foo/foo/1.0.0/foo-1.0.0.pom
[INFO] 
[INFO] --- maven-deploy-plugin:2.7:deploy (default-deploy) @ foo ---
[INFO] Using alternate deployment repository local::default::file:///Users/lvlcek/.m2/repository
Uploading to local: file:///Users/lvlcek/.m2/repository/org/foo/foo/1.0.0/foo-1.0.0.pom
Uploaded to local: file:///Users/lvlcek/.m2/repository/org/foo/foo/1.0.0/foo-1.0.0.pom (2.4 kB at 239 kB/s)
Downloading from local: file:///Users/lvlcek/.m2/repository/org/foo/foo/maven-metadata.xml
Downloaded from local: file:///Users/lvlcek/.m2/repository/org/foo/foo/maven-metadata.xml (290 B at 32 kB/s)
Uploading to local: file:///Users/lvlcek/.m2/repository/org/foo/foo/maven-metadata.xml
Uploaded to local: file:///Users/lvlcek/.m2/repository/org/foo/foo/maven-metadata.xml (290 B at 290 kB/s)
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

```bash
$ ls -n /Users/lvlcek/.m2/repository/org/foo/foo/1.0.0/
total 32
-rw-r--r--  1 501  20   157 Apr 16 15:40 _remote.repositories
-rw-r--r--  1 501  20  2391 Apr 16 15:07 foo-1.0.0.pom
-rw-r--r--  1 501  20    32 Apr 16 15:39 foo-1.0.0.pom.md5
-rw-r--r--  1 501  20    40 Apr 16 15:39 foo-1.0.0.pom.sha1
```

## Next steps

We can see that only POM file was deployed into target mvn repository.

The mvn log indicates that maven deployment is executed after the gradle tasks are complete so perhaps we should consider deplying required artifact using mvn instead of trying to convince gradle to do it.
