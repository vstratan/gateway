#FROM kaazing/development-enterprise-gateway:5.1.2-RC001
FROM kaazing/enterprise-gateway:latest

# Generate Keystore entry for gateway.kaazing.webrtc.demo

RUN keytool -genkeypair -keystore conf/keystore.db -storetype JCEKS -keypass ab987c -storepass ab987c -alias gateway.kaazing.webrtc.demo -keyalg RSA -dname "CN=gateway.kaazing.webrtc.demo, OU=Example, O=Example, L=Mountain View, ST=California, C=US"

ADD http://central.maven.org/maven2/org/apache/activemq/activemq-client/5.10.0/activemq-client-5.10.0.jar lib/ext/
ADD http://central.maven.org/maven2/org/apache/geronimo/specs/geronimo-jms_1.1_spec/1.1/geronimo-jms_1.1_spec-1.1.jar lib/ext/
ADD http://central.maven.org/maven2/org/apache/geronimo/specs/geronimo-j2ee-management_1.1_spec/1.0.1/geronimo-j2ee-management_1.1_spec-1.0.1.jar lib/ext/
ADD http://central.maven.org/maven2/org/fusesource/hawtbuf/hawtbuf/1.11/hawtbuf-1.11.jar lib/ext/
ADD http://central.maven.org/maven2/org/apache/geronimo/specs/geronimo-jta_1.1_spec/1.1/geronimo-jta_1.1_spec-1.1.jar lib/ext/

# Add gateway config
COPY conf/gateway-config.xml conf/gateway-config.xml
COPY web/webrtc web/webrtc
