# ─── Stage 1: Build ────────────────────────────────────────────────────────────
FROM gradle:8.12-jdk21 AS builder

WORKDIR /app

# Copy Gradle wrapper and config files first for layer caching
COPY gradlew gradlew.bat gradle.properties settings.gradle.kts build.gradle.kts ./
COPY gradle/ gradle/

# Copy source modules
COPY shared/ shared/
COPY webApp/ webApp/

# Build the production JS bundle
RUN ./gradlew :webApp:jsBrowserProductionWebpack --no-daemon --stacktrace

# ─── Stage 2: Serve ────────────────────────────────────────────────────────────
FROM nginx:1.27-alpine AS runtime

# Remove default Nginx static assets
RUN rm -rf /usr/share/nginx/html/*

# Copy index.html from processed resources
COPY --from=builder /app/webApp/build/processedResources/js/main/ /usr/share/nginx/html/
# Copy the JS bundle on top
COPY --from=builder /app/webApp/build/kotlin-webpack/js/productionExecutable/ /usr/share/nginx/html/

# Copy custom Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
