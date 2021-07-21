FROM gradle:7.1.1-jdk8 as cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY ./build.gradle.kts /home/gradle/STSEnvironmentSurveyor/
WORKDIR /home/gradle/STSEnvironmentSurveyor
RUN gradle clean build

FROM gradle:7.1.1-jdk8 as builder
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/STSEnvironmentSurveyor/
WORKDIR /usr/src/STSEnvironmentSurveyor
RUN gradle shadowJar copyJar

FROM openjdk:8-jre-slim
EXPOSE 8008
EXPOSE 5900

RUN apt-get update
RUN apt-get install -y fluxbox xvfb x11vnc x11-xserver-utils mesa-utils libxrender1 libxtst6 libxi6 libxcursor1 libxrandr2 libasound2 libasound2-plugins alsa-utils alsa-oss

COPY ./run /usr/src/STSEnvironmentSurveyor/run
WORKDIR /usr/src/STSEnvironmentSurveyor/run
COPY ./run/asound.conf /etc/asound.conf
COPY ./run/mod_lists.json /root/.config/ModTheSpire/mod_lists.json
COPY --from=builder /usr/src/STSEnvironmentSurveyor/build/libs/*.jar ./mods/STSEnvironmentSurveyor.jar
RUN ln -s /usr/local/openjdk-8 /usr/src/STSEnvironmentSurveyor/run/jre
CMD ["sh", "run.sh"]
