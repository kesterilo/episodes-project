FROM openjdk:17 AS BUILD_ARTIFACT
WORKDIR /usr/src/app/
RUN apt-get update && apt-get install maven -y
RUN git clone https://github.com/kesterilo/episodes-project.git
RUN cd episodes-project
RUN mvn install -DskipTests


FROM openjdk:17

WORKDIR /usr/src/app/
COPY --from=BUILD_ARTIFACT /usr/src/app/episodes-project/target/episodes-project-0.0.1.jar ./episodes-project-0.0.1.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]














# FROM openjdk:17
# ADD target/episodes-project-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT ["java", "-jar", "app.jar"]
# EXPOSE 8080