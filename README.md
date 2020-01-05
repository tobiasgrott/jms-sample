# Build
mvn clean package && docker build -t com.grott/jms-sample .

# RUN

docker rm -f jms-sample || true && docker run -d -p 8080:8080 -p 4848:4848 --name jms-sample com.grott/jms-sample 