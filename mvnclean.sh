#!/bin/sh
for file in ./*
  do
      if test -d "$file"
      then
          # shellcheck disable=SC2164
          cd "$file"
          # shellcheck disable=SC1073
          # shellcheck disable=SC1019
          # shellcheck disable=SC1009
          if test -f "pom.xml"
          then
              #echo "mvn -s ~/software/java/maven/chenfeng-maven-3.6.3/conf/settings.xml clean"
              mvn -s /Users/magicLuoMacBook/software/java/maven/mavenreposity/settings.xml clean
          else
              echo "this is not maven project"
          fi
          # shellcheck disable=SC2103
          cd ..
          echo ""
      fi
  done



