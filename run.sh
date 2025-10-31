#!/bin/bash
# ======================================================
# run.sh — Starts the Spring Boot app with auto port detection
# ======================================================

set -e

APP_DIR="$(dirname "$0")/Validation_Portal"
PORT=8080

echo "🚀 Starting Spring Boot Email Checker..."
echo "-------------------------------------------"

while lsof -i:$PORT -t >/dev/null 2>&1; do
  echo "⚠️  Port $PORT is in use, trying next..."
  PORT=$((PORT + 1))
done

echo "✅ Using port $PORT"
echo

cd "$APP_DIR"

# Start Spring Boot app on the chosen port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=$PORT" &

sleep 6

echo " "
echo "-------------------------------------------"
if [ ! -z "$CODESPACE_NAME" ]; then
  URL="https://$CODESPACE_NAME-$PORT.app.github.dev"
  echo "🌐 Running in GitHub Codespaces!"
else
  URL="http://localhost:$PORT"
  echo "🌐 Running locally!"
fi

echo "🔗 Click to open: $URL"
echo "-------------------------------------------"
echo "🛑 Press Ctrl+C to stop the server."
wait
