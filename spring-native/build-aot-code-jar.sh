#!/bin/bash
gradle clean processAot build

#java -DspringAot=true -jar spring-native-0.0.1-SNAPSHOT.jar