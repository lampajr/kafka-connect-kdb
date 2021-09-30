# Introduction

The KDB plugin is a collection of connectors that are used to interact with KDB databases through http.

# Connectors

## KDB Sink Connector

The KDB Sink Connector is used to write data from Kafka to a KDB instance.

TODO

### Configuration

#### General


##### `kdb.hosts`

The KDB host to connect to.

*Importance:* High

*Type:* String

*Default Value:* [NO VALUE]

##### `kdb.auth`

The KDB authentication <user>:<pass>.

*Importance:* High

*Type:* String

*Default Value:* [NO VALUE]

##### `kdb.ssl.enabled`

Enable ssl communication.

*Importance:* Medium

*Type:* Boolean

*Default Value:* true

##### `kdb.port.read`

The KDB server port for read operations.

*Importance:* High

*Type:* Integer

*Default Value:* [NO VALUE]

##### `kdb.port.write`

The KDB server port for write operations.

*Importance:* High

*Type:* Integer

*Default Value:* [NO VALUE]

##### `kdb.async.write`

Enable write operation to KDB in asynchronous way.

*Importance:* Low

*Type:* Boolean

*Default Value:* true

##### `kdb.write.mode`

KDB writes mode, this strictly depends on the write.fn structure defined on the kdb q process.

*Importance:* HIGH

*Type:* String

*Default Value:* SIMPLE

*Validator:* Matches: ``SIMPLE``, ``FULL``, ``WITH_OFFSET``, ``WITH_PARTITION``

##### `kdb.write.fn`

The q process function, defined on kdb, that must be used to flush the data to the server, e.g., .u.upd.

*Importance:* High

*Type:* String

*Default Value:* [NO VALUE]

##### `kdb.table.name`

The kdb table name where the connector should flush data taken from the provided topic.

*Importance:* High

*Type:* String

*Default Value:* [NO VALUE]

##### `kdb.parser.class`

User defined parser class that must implement com.lampajr.kafka.connect.kdb.parser.Parser.
This class must be in the same classpath of the compile connector.

*Importance:* High

*Type:* String

*Default Value:* [NO VALUE]

##### `kdb.offset.fn`

The q process function, defined on kdb, that must be used to retrieve the kafka offset 
from kdb if implemented , e.g., .u.getOffset. This must be provided if the chosen 
write mode manages the offsets.

*Importance:* High

*Type:* String

*Default Value:* [NO VALUE]

##### `kdb.skip.offset`

If true it ignore the offset returned by the kdb server.

*Importance:* Low

*Type:* Boolean

*Default Value:* false

#### Examples


##### Standalone Example

This configuration is used typically along with [standalone mode](http://docs.confluent.io/current/connect/concepts.html#standalone-workers).

```properties
name=KDBSinkConnector1
connector.class=com.lampajr.kafka.connect.kdb.KdbSinkConnector
tasks.max=1
topics=< Required Configuration >
kdb.host=< Required Configuration >
kdb.auth=< Required Configuration >
kdb.read.port=< Required Configuration >
kdb.write.port=< Required Configuration >
kdb.write.fn=< Required Configuration >
kdb.table.name=< Required Configuration >
kdb.parser.class=< Required Configuration >
```

##### Distributed Example

This configuration is used typically along with [distributed mode](http://docs.confluent.io/current/connect/concepts.html#distributed-workers).
Write the following json to `connector.json`, configure all the required values, and use the command below to
post the configuration to one the distributed connect worker(s).

```json
{
  "config" : {
    "name" : "KDBSinkConnector1",
    "connector.class" : "com.lampajr.kafka.connect.kdb.KdbSinkConnector",
    "tasks.max" : "1",
    "topics" : "< Required Configuration >",
    "kdb.host": "< Required Configuration >",
    "kdb.auth": "< Required Configuration >",
    "kdb.read.port": "< Required Configuration >",
    "kdb.write.port": "< Required Configuration >",
    "kdb.write.fn": "< Required Configuration >",
    "kdb.table.name": "< Required Configuration >",
    "kdb.parser.class": "< Required Configuration >"
    
  }
}
```