# Trans-RoutE-Townish (transroutownish) :small_blue_diamond: Urban bus routing microservice prototype

**A Spring Boot-based application, designed and intended to be run as a microservice,
<br />implementing a simple urban bus routing prototype**

---

## Table of Contents

* **[Building](#building)**
* **[Running](#running)**
* **[Operating](#operating)**
  * **[Error handling](#error-handling)**

## Building

The microservice is known to be built and run successfully under **Ubuntu Server (Ubuntu 20.04.3 LTS x86-64)**. Install the necessary dependencies (`openjdk-11-jdk-headless`, `make`, `docker.io`):

```
$ sudo apt-get update && \
  sudo apt-get install openjdk-11-jdk-headless make docker.io -y
```

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

**Build** the microservice using **GNU Make** (optional):

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

### Creating a Docker image

**Build** a Docker image for the microservice:

```
$ sudo docker build -ttransroutownish/bus .
...
```

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
$ java -jar target/bus-0.9.9.jar; echo $?
$ #                             ^   ^   ^
$ #                             |   |   |
$ # ----------------------------+---+---+
$ # Whilst this is not necessary, it's beneficial knowing the exit code.
...
```

### Running a Docker image

**Run** a Docker image of the microservice:

```
$ sudo PORT=8080 docker run -p${PORT}:${PORT} --name bus transroutownish/bus; echo $?
...
```

### Exploring a Docker image payload

The following is not necessary but might be considered interesting &mdash; to look up into the running container, and check out that the microservice's JAR, log, and routes data store are at their expected places and in effect:

```
$ sudo docker ps
CONTAINER ID   IMAGE                 COMMAND               CREATED             STATUS             PORTS                                       NAMES
<container_id> transroutownish/bus   "java -jar bus.jar"   About an hour ago   Up About an hour   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   bus
$
$ sudo docker exec -ti bus sh; echo $?
/var/tmp #
/var/tmp # ls -al
total 17544
drwxrwxrwt    1 root     root          4096 Oct 25 00:00 .
drwxr-xr-x    1 root     root          4096 Aug 27 00:00 ..
-rw-rw-r--    1 root     root      17941043 Oct 25 00:00 bus.jar
drwxr-xr-x    2 root     root          4096 Oct 25 00:00 data
drwxr-xr-x    2 root     root          4096 Oct 25 00:00 log
/var/tmp #
/var/tmp # ls -al data/ log/
data/:
total 56
drwxr-xr-x    2 root     root          4096 Oct 25 00:00 .
drwxrwxrwt    1 root     root          4096 Oct 25 00:00 ..
-rw-rw-r--    1 root     root         46218 Oct 25 00:00 routes.txt

log/:
total 12
drwxr-xr-x    2 root     root          4096 Oct 25 00:00 .
drwxrwxrwt    1 root     root          4096 Oct 25 00:00 ..
-rw-r--r--    1 root     root           447 Oct 25 00:00 bus.log
/var/tmp #
/var/tmp # exit # Or simply <Ctrl-D>.
0
```

## Operating

All the routes are contained in a so called **routes data store**. It is located in the `data/` directory. The default filename for it is `routes.txt`, but it can be specified explicitly (if intended to use another one) in the `src/main/resources/application.properties` file.

**Identify**, whether there is a direct route between two bus stops with IDs given in the **HTTP GET** request, searching for them against the underlying **routes data store**:

HTTP request param | Sample value | Another sample value
------------------ | ------------ | --------------------
`from`             | `4838`       | `82`
`to`               | `524987`     | `35390`

The direct route is found:

```
$ curl 'http://localhost:8080/api/direct?from=4838&to=524987'
{"from":4838,"to":524987,"direct":true}
```

The direct route is not found:

```
$ curl 'http://localhost:8080/api/direct?from=82&to=35390'
{"from":82,"to":35390,"direct":false}
```

### Error handling

When the query string passed in a request, contains inappropriate input, or the URI endpoint doesn't contain anything else at all after its path, the microservice will respond with the **HTTP 400 Bad Request** status code, including a specific response body in JSON representation, like the following:

```
$ curl 'http://localhost:8080/api/direct?from=qwerty4838&to=-i-.;--089asdf../nj524987'
{"error":"Request parameters must take positive integer values, in the range 1 .. 2,147,483,647. Please check your inputs."}
```

Or even simpler:

```
$ curl http://localhost:8080/api/direct
{"error":"Request parameters must take positive integer values, in the range 1 .. 2,147,483,647. Please check your inputs."}
```
