# Trans-RoutE-Townish (transroutownish) :small_blue_diamond: Urban bus routing microservice prototype

**A Spring Boot-based application, designed and intended to be run as a microservice,
<br />implementing a simple urban bus routing prototype**

Consider there is an IoT system that aimed at planning and forming a specific bus route for a hypothetical passenger. One crucial part of such system is a **module**, that is responsible for filtering bus routes between two arbitrary bus stops where a direct route is actually present and it can be easily found. Imagine there is a fictional urban public transportation agency that provides a wide series of bus routes which are covering large city areas, such that they are consisting of many bus stop points in each route.

A **module** that is developing here, is dedicated to find out quickly, whether there is a direct route in a list of given bus routes between two specified bus stops. It should immediately report back to the IoT system with the result `true` if such a route is found, i.e. it exists in the bus routes list, or `false` otherwise, by outputting a simple JSON structure, like the following:

```
{
    "from"   : <starting_bus_stop_point>,
    "to"     : <ending_bus_stop_point>,
    "direct" : true
}
```

`<starting_bus_stop_point>` and `<ending_bus_stop_point>` above are bus stop IDs: unique positive integers, taken right from inputs.

A bus routes list is a plain text file where each route has its own unique ID (positive integer) and a sequence of its bus stop IDs. Each route occupies only one line in this file, so that they are all representing something similar to a list &mdash; the list of routes. The first number in a route is always its own ID. Other consequent numbers after it are simply IDs of bus stops in this route, up to the end of line. All IDs in each route are separated by whitespace, usually by single spaces or tabs, but not newline.

There are some constraints:
1. Routes are considered not to be a round trip journey, that is they are operated in the forward direction only.
2. All IDs (of routes and bus stops) must be represented by positive integer values, in the range `1 .. 2,147,483,647`.
3. Any bus stop ID may occure in the current route only once, but it might be presented in any other route too.

**TODO:** Provide a brief definition of this **module** as a microservice.

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

(**Note:** the `package` target above includes `test`.)

Or **build** the microservice using **GNU Make** (optional, but for convenience &mdash; it covers the same **Maven Wrapper** build workflow under the hood):

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

(**Note:** the `jar` target above includes `test`.)

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
$ java -jar target/bus-0.12.0.jar; echo $?
$ #                              ^   ^   ^
$ #                              |   |   |
$ # -----------------------------+---+---+
$ # Whilst this is not necessary, it's beneficial knowing the exit code.
...
```

### Running a Docker image

**Run** a Docker image of the microservice:

```
$ sudo PORT=8080 docker run -p${PORT}:${PORT} --name bus transroutownish/bus; echo $?
...
```

If Docker will complain that the container name is already in use (after subsequent creation of a new container), like the following:

```
docker: Error response from daemon: Conflict.
        The container name "/bus" is already in use by container "<container_id>".
        You have to remove (or rename) that container to be able to reuse that name.
...
```

...it's safe to remove that container: `$ sudo docker rm bus`, and run the image once again.

Yet even better, a Docker image might be run by issuing a compound command, beginning with deleting all stopped containers:

```
$ sudo docker rm `sudo docker ps -aq` && \
  sudo PORT=8080 docker run -dp${PORT}:${PORT} --name bus transroutownish/bus; echo $?
...
```

### Exploring a Docker image payload

The following is not necessary but might be considered interesting &mdash; to look up into the running container, and check out that the microservice's JAR layers, log, and routes data store are at their expected places and in effect:

```
$ sudo docker ps -a
CONTAINER ID   IMAGE                 COMMAND                    CREATED             STATUS             PORTS                                       NAMES
<container_id> transroutownish/bus   "java org.springfram..."   About an hour ago   Up About an hour   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   bus
$
$ sudo docker exec -ti bus sh; echo $?
/var/tmp #
/var/tmp # java --version
openjdk 11.0.13 2021-10-19 LTS
OpenJDK Runtime Environment Zulu11.52+13-CA (build 11.0.13+8-LTS)
OpenJDK 64-Bit Server VM Zulu11.52+13-CA (build 11.0.13+8-LTS, mixed mode)
/var/tmp #
/var/tmp # ls -al
total 32
drwxrwxrwt    1 root     root          4096 Oct 30 00:00 .
drwxr-xr-x    1 root     root          4096 Aug 27 00:00 ..
drwxr-xr-x    1 root     root          4096 Oct 30 00:00 BOOT-INF
drwxr-xr-x    3 root     root          4096 Oct 30 00:00 META-INF
drwxr-xr-x    2 root     root          4096 Oct 30 00:00 data
drwxr-xr-x    2 root     root          4096 Oct 30 00:00 log
drwxr-xr-x    3 root     root          4096 Oct 30 00:00 org
/var/tmp #
/var/tmp # ls -al BOOT-INF/ data/ log/
BOOT-INF/:
total 24
drwxr-xr-x    1 root     root          4096 Oct 30 00:00 .
drwxrwxrwt    1 root     root          4096 Oct 30 00:00 ..
drwxr-xr-x    3 root     root          4096 Oct 30 00:00 classes
-rw-r--r--    1 root     root          1674 Oct 30 00:00 classpath.idx
-rw-r--r--    1 root     root           212 Oct 30 00:00 layers.idx
drwxr-xr-x    2 root     root          4096 Oct 30 00:00 lib

data/:
total 56
drwxr-xr-x    2 root     root          4096 Oct 30 00:00 .
drwxrwxrwt    1 root     root          4096 Oct 30 00:00 ..
-rw-rw-r--    1 root     root         46218 Oct 30 00:00 routes.txt

log/:
total 12
drwxr-xr-x    2 root     root          4096 Oct 30 00:00 .
drwxrwxrwt    1 root     root          4096 Oct 30 00:00 ..
-rw-r--r--    1 root     root           418 Oct 30 00:00 bus.log
/var/tmp #
/var/tmp # exit # Or simply <Ctrl-D>.
0
```

## Operating

All the routes are contained in a so called **routes data store**. It is located in the `data/` directory. The default filename for it is `routes.txt`, but it can be specified explicitly (if intended to use another one) in the `src/main/resources/application.properties` file.

**Identify**, whether there is a direct route between two bus stops with IDs given in the **HTTP GET** request, searching for them against the underlying **routes data store**:

HTTP request param | Sample value | Another sample value | Yet another sample value
------------------ | ------------ | -------------------- | ------------------------
`from`             | `4838`       | `82`                 | `2147483647`
`to`               | `524987`     | `35390`              | `1`

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
