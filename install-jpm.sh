#!/bin/bash

set -ex

if [[ ! -e  "$HOME/jpm/bin/codacy-coverage-reporter" ]]; then
  curl -sL http://bit.ly/jpm4j > jpm4j.jar

  java -jar jpm4j.jar -u init

  $HOME/jpm/bin/jpm install "com.codacy:codacy-coverage-reporter:assembly"
fi
