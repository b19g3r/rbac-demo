# 构建阶段
FROM gradle:jdk21-jammy AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon --info

# 运行阶段
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 