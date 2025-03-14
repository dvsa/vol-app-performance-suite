FROM openjdk:11-jdk-slim

WORKDIR /app

ENV GRADLE_VERSION=7.5.1

RUN apt-get update && apt-get install -y \
    curl \
    wget \
    git \
    unzip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
    && unzip /tmp/gradle-${GRADLE_VERSION}-bin.zip -d /opt \
    && rm /tmp/gradle-${GRADLE_VERSION}-bin.zip \
    && ln -s /opt/gradle-${GRADLE_VERSION}/bin/gradle /usr/bin/gradle

ENV PATH=$PATH:/opt/gradle/bin

RUN mkdir -p gatling-results

COPY . /app

COPY run-gatling.sh /app/run-gatling.sh
RUN chmod +x /app/run-gatling.sh