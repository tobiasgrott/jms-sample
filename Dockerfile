FROM airhacks/glassfish
COPY ./target/jms-sample.war ${DEPLOYMENT_DIR}
