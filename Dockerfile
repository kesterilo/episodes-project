FROM ubuntu:20.04 AS BUILD_ARTIFACT
RUN apt update
RUN apt install openjdk-17-jdk-headless -y
RUN apt-get install git -y
RUN apt install wget -y
# Install maven
# RUN mkdir -p /opt/maven
# RUN cd /opt/maven
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
RUN tar xzf apache-maven-3.9.6-bin.tar.gz
# RUN ln /opt/maven/apache-maven-3.9.6/bin/mvn /usr/bin/mvn
# ENV MAVEN_HOME /opt/maven
ENV MAVEN_CONFIG /.m2
# ENV JAVA_HOME /usr/lib/jvm/default-jvm/
# RUN apt install maven -y
RUN git clone https://github.com/kesterilo/episodes-project.git
WORKDIR /usr/src/episodes-project/
# RUN cd episodes-project
RUN /apache-maven-3.9.6/bin/mvn clean package -DskipTests



FROM openjdk:17

WORKDIR /usr/src/app/
COPY --from=BUILD_ARTIFACT /usr/src/app/episodes-project/target/episodes-project-0.0.1-SNAPSHOT.jar ./episodes-project-0.0.1.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "episodes-project-0.0.1.jar.jar" ]










# # FROM openjdk:17-slim-buster AS BUILD_ARTIFACT
# # WORKDIR /usr/src/app/
# # RUN apt update && apt install maven -y
# # RUN git clone https://github.com/kesterilo/episodes-project.git
# # RUN cd episodes-project
# # RUN mvn install -DskipTests



# FROM openjdk:17
# ADD episodes-project-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT ["java", "-jar", "app.jar"]
# EXPOSE 8080