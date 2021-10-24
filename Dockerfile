#
# Dockerfile
# =============================================================================
# Urban bus routing microservice prototype. Version 0.9.9
# =============================================================================
# A Spring Boot-based application, designed and intended to be run
# as a microservice, implementing a simple urban bus routing prototype.
# =============================================================================
# Written by Radislav (Radicchio) Golubtsov, 2021
#
# (See the LICENSE file at the top of the source tree.)
#

FROM       azul/zulu-openjdk-alpine:11-jre-headless
VOLUME     /tmp
COPY       target/*.jar bus.jar
ENTRYPOINT ["java", "-jar", "/bus.jar"]

# vim:set nu ts=4 sw=4:
