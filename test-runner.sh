#!/bin/bash
set -e

exec mvn test-compile exec:java \
  -Dexec.mainClass="test.TestSetup" \
  -Dexec.classpathScope=test \
  -Denv="${ENV:-qa}" \
  -Dusers="${USERS:-10}" \
  -DtypeOfTest="${TEST_TYPE:-load}" \
  -DrampUp="${RAMP_UP:-0}" \
  -Dduration="${DURATION:-0}"