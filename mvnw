#!/bin/sh

set -e

BASEDIR=$(cd "$(dirname "$0")"; pwd)
WRAPPER_JAR="$BASEDIR/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

if [ -n "$JAVA_HOME" ]; then
  JAVA_EXE="$JAVA_HOME/bin/java"
else
  JAVA_EXE="java"
fi

exec "$JAVA_EXE" \
  -Dmaven.multiModuleProjectDirectory="$BASEDIR" \
  -cp "$WRAPPER_JAR" \
  $WRAPPER_LAUNCHER "$@"