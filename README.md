# Introduction

The KDB plugin is a collection of connectors that are used to interact with KDB databases through http.

# Connectors

## KDB Sink Connector

The KDB Sink Connector is used to write data from Kafka to a KDB instance.

TODO

### Configuration

TODO

```
mvn clean package
export CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')"
$CONFLUENT_HOME/bin/connect-standalone $CONFLUENT_HOME/etc/schema-registry/connect-avro-standalone.properties config/MySourceConnector.properties
```
