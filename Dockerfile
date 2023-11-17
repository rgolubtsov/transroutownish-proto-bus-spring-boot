#
# Dockerfile
# =============================================================================
# Urban bus routing microservice prototype. Version 0.30.5
# =============================================================================
# A Spring Boot-based application, designed and intended to be run
# as a microservice, implementing a simple urban bus routing prototype.
# =============================================================================
# Copyright (C) 2021-2023 Radislav (Radicchio) Golubtsov
#
# (See the LICENSE file at the top of the source tree.)
#

# === Stage 1: Extract JAR layers =============================================
FROM       azul/zulu-openjdk-alpine:17-jre-headless-latest AS layers
USER       nobody
WORKDIR    var/tmp
COPY       target/*.jar bus.jar
RUN        ["java", "-Djarmode=layertools", "-jar", "bus.jar", "extract", "--destination", "layers"]

# === Stage 2: Run the microservice ===========================================
FROM       azul/zulu-openjdk-alpine:17-jre-headless-latest
USER       daemon
WORKDIR    var/tmp
ARG        LAYERS=var/tmp/layers
COPY       --from=layers ${LAYERS}/dependencies          ./
COPY       --from=layers ${LAYERS}/spring-boot-loader    ./
COPY       --from=layers ${LAYERS}/snapshot-dependencies ./
COPY       --from=layers ${LAYERS}/application           ./
COPY       data data/
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

# vim:set nu ts=4 sw=4:
