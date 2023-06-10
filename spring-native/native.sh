#!/usr/bin/env bash
#rm -rf target
#mvn -DskipTests -Pnative native:compile && ./target/aot
rm -rf build
gradle nativeCompile && ./build/native/nativeCompile/spring-native
