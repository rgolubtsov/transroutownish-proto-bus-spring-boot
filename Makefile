#
# Makefile
# =============================================================================
# Urban bus routing microservice prototype. Version 0.11.0
# =============================================================================
# A Spring Boot-based application, designed and intended to be run
# as a microservice, implementing a simple urban bus routing prototype.
# =============================================================================
# Written by Radislav (Radicchio) Golubtsov, 2021
#
# (See the LICENSE file at the top of the source tree.)
#

SERV = target
TEST = test
JAR  = jar

# Specify flags and other vars here.
MAVEN_W = ./mvnw
ECHO    = @echo

# Making the first target (the microservice itself).
$(SERV):
	$(MAVEN_W) compile
	$(ECHO)

# Making the second target (tests).
$(TEST):
	$(MAVEN_W) $(TEST)

# Making the third target (runnable JAR file).
$(JAR):
	$(MAVEN_W) package
	$(ECHO)

.PHONY: all clean

all: $(SERV) $(JAR)

clean:
	$(MAVEN_W) clean

# vim:set nu ts=4 sw=4:
