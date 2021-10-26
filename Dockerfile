FROM adoptopenjdk/openjdk8:alpine
RUN mkdir /opt/app
RUN mkdir /opt/nosql
RUN mkdir -p /opt/pgx/tmp
COPY out/artifacts/Szakdolgozat_Maven_jar /opt/app
COPY databases /opt/nosql
ENV PGX_TMP_DIR=/opt/pgx/tmp