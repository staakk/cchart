#!/bin/bash

ACTION=$1
TEST_CLASS=$2

set -- ./gradlew executeScreenshotTests

case $ACTION in

  record)
    set -- "$@" -Precord
    ;;

  execute)
    # No op.
    ;;

  *)
    echo "No action passed, expected 'record' or 'execute'."
    exit 1
  ;;

esac

if [ -n "$TEST_CLASS" ]
then
  set -- "$@" -Pandroid.testInstrumentationRunnerArguments.class="io.github.staakk.cchart.viewtest.$TEST_CLASS"
fi

"$@"