FROM eclipse-temurin:11-jdk-jammy

# Install Chrome for headless UI testing
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    gnupg2 \
    unzip \
    && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update && apt-get install -y google-chrome-stable \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy Gradle wrapper and build files first (for layer caching)
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle/ gradle/
RUN chmod +x gradlew && ./gradlew --no-daemon dependencies

# Copy source code
COPY src/ src/

# Default: run API tests in headless mode
ENTRYPOINT ["./gradlew", "--no-daemon"]
CMD ["test_API", "-Dheadless=true"]
