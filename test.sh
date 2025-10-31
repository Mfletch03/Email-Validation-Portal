#!/bin/bash
# ======================================================
# test.sh — Runs all test cases for the Spring Boot app
# ======================================================

set -e

echo "🧪 Running all test cases for Email Checker project..."
echo "------------------------------------------------------"

# Go into the Spring Boot project directory
cd "$(dirname "$0")/Validation_Portal"

# Clean previous build and run tests
mvn clean test

# Check exit status
if [ $? -eq 0 ]; then
  echo "✅ All tests passed successfully!"
else
  echo "❌ Some tests failed. Check the logs above for details."
  exit 1
fi

echo "------------------------------------------------------"
echo "Test run complete."
