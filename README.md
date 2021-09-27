# Trans-RoutE-Townish (transroutownish) :small_blue_diamond:
<br />Urban bus routing microservice prototype

**A Spring Boot-based application, designed and intended to be run as a microservice,
<br />implementing a simple urban bus routing prototype**

---

## Table of Contents

* **[Building](#building)**
* **[Running](#running)**
* **[Operating](#operating)**

## Building

**Build** the microservice using **Maven Wrapper**:

```
$ ./mvnw clean
...
$ ./mvnw compile
...
$ ./mvnw test  # <== Optional. This currently does nothing except emitting a huge (most likely useless) log output.
...
$ ./mvnw package
...
```

(Note: the `package` target above includes `test`.)

**Build** the microservice using **GNU Make**:

```
$ make clean
...
$ make      # <== Compilation phase.
...
$ make test
...
$ make jar
...
$ make all  # <== Or make all the targets at one pass: compile,.. jar.
...
```

(Note: the `jar` target above includes `test`.)

## Running

**Run** the microservice using **Maven Wrapper** (generally for development and debugging purposes):

```
$ ./mvnw spring-boot:run; echo $?
$ #                     ^   ^   ^
$ #                     |   |   |
$ # --------------------+---+---+
$ # Whilst this is not necessary, it's beneficial knowing the exit code.
...
```

**Run** the microservice using its all-in-one JAR file, built previously by the `package` or `jar` targets:

```
$ java -jar target/bus-0.0.1-SNAPSHOT.jar; echo $?
$ #                                      ^   ^   ^
$ #                                      |   |   |
$ # -------------------------------------+---+---+
$ # Whilst this is not necessary, it's beneficial knowing the exit code.
...
```

## Operating

**TBD**
