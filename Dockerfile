# 1단계: Multi-Stage 빌드를 사용하여 최적화된 Docker 이미지를 생성합니다.

# 빌드 단계
FROM gradle:8.3-jdk17 AS builder
WORKDIR /app

# Gradle wrapper, 설정 파일 및 빌드 파일 복사
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
RUN chmod +x gradlew  # Gradle Wrapper 실행 권한 부여

# 애플리케이션 소스 코드 복사
COPY src src

# 프로젝트를 빌드하여 JAR 파일 생성
RUN ./gradlew clean bootJar

# 런타임 단계
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일을 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 컨테이너에서 사용할 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
