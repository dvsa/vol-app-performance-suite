#!/bin/sh
set -e

SIMULATION_TASK=${SIMULATION_TASK:-"createApplication"}

case $SIMULATION_TASK in
  "registerUser")
    gradle registerUser
    ;;
  "createApplication")
    gradle createApplication
    ;;
  "searchOperator")
    gradle searchOperator
    ;;
  "internalSearchLicence")
    gradle internalSearchLicence
    ;;
  *)
esac