#!/bin/sh

if [ ! -d  "$HOME/jpm" ]; then
  echo "Installing jpm and codacy-coverage-reporter..."

  curl -sL http://bit.ly/jpm4j > jpm4j.jar

  java -jar jpm4j.jar -u init

  $HOME/jpm/bin/jpm install "com.codacy:codacy-coverage-reporter:assembly"
fi
