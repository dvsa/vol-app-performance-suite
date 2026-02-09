#!/bin/bash

set -e

echo "=== VOL Performance Test Runner ==="
echo "Platform Environment: ${PLATFORM_ENV:-unknown}"
echo "Simulation Class: ${SIMULATION_CLASS:-unknown}"
echo "Users: ${USERS:-10}"
echo "Duration: ${DURATION:-5m}"
echo "Ramp Time: ${RAMP_TIME:-30s}"
echo "Build ID: ${BUILD_ID:-unknown}"

# Validate required parameters
if [ -z "${SIMULATION_CLASS}" ]; then
    echo "ERROR: SIMULATION_CLASS is required"
    exit 1
fi

# Set Gatling system properties
GATLING_OPTS="-Dusers=${USERS:-10} -Dduration=${DURATION:-5m} -DrampTime=${RAMP_TIME:-30s}"

# Add Maven options if provided
if [ -n "${MAVEN_OPTIONS}" ]; then
    GATLING_OPTS="${GATLING_OPTS} ${MAVEN_OPTIONS}"
fi

echo "Gatling Options: ${GATLING_OPTS}"

# Run the Gatling test
echo "Starting Gatling test..."
mvn gatling:test \
    -P github \
    -Dgatling.simulationClass="${SIMULATION_CLASS}" \
    ${GATLING_OPTS}

# Check if test completed successfully
if [ $? -eq 0 ]; then
    echo "Gatling test completed successfully"
else
    echo "Gatling test failed"
    exit 1
fi

# Prepare results directory
mkdir -p gatling-results
if [ -d "target/gatling" ]; then
    echo "Copying Gatling results..."
    cp -r target/gatling/* gatling-results/ || echo "Warning: Could not copy all Gatling results"
fi

# Upload results to S3 if configured
if [ -n "${RESULTS_TARGET_BUCKET}" ] && [ -n "${BUILD_ID}" ]; then
    echo "Uploading results to S3..."

    # Create results archive
    if [ -d "gatling-results" ] && [ "$(ls -A gatling-results)" ]; then
        zip -r "gatling_results_${RESULTS_BUILD_NUMBER:-1}.zip" gatling-results/

        # Upload to S3
        aws s3 cp "gatling_results_${RESULTS_BUILD_NUMBER:-1}.zip" \
            "s3://${RESULTS_TARGET_BUCKET}/${RESULTS_TARGET_BUCKET_PATH}/${BUILD_ID}/gatling_results_${RESULTS_BUILD_NUMBER:-1}.zip"

        echo "Results uploaded successfully"
    else
        echo "Warning: No results found to upload"
    fi
else
    echo "No S3 configuration found, skipping upload"
fi

echo "=== Performance Test Completed ==="