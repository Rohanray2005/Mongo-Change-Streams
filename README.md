1. Run command -> mongod --port 2717 --dbpath "C:\Program Files\MongoDB\Server\8.0\data\db1" --replSet rs
  on Command Prompt (Run as Admin for proper permissions).
2. Open Mongo shell and run rs.initiate().
3. run rs.status() to check status if replica set is running.

Notes : 
## **What are Change Streams?**

A change stream is a real-time stream of database changes that flows from your database to your application. With change streams, your applications can react—in real time—to data changes in a single collection, a database, or even an entire deployment. For apps that rely on notifications of changing data, change streams are critical.

A few use cases where you might find change streams include:

- **Analytics Dashboards** **-** Change streams can provide an audit trail for applications.
- **IoT Event Tracking** - Change streams can be used to detect and adjust a system to events that internet-enabled devices are tracking - for example, tracking when a device moves outside of a geo-fencing area. A change stream can be filtered to detect only those events that fall outside of this range and trigger an alarm when it happens.
- **Real-Time Trading Applications** **-** Change streams can be used to track changes to financial data and react to them in real time.

## Availability
Change streams are available for [replica sets](https://www.mongodb.com/docs/manual/replication/#std-label-replication) and [sharded clusters:](https://www.mongodb.com/docs/manual/sharding/#std-label-sharding-background)

If you’re using MongoDB 3.6 or later, change streams are already built in, and taking advantage of them is easy.

MongoDB pushes change events to a persistent cursor
MongoDB's internal **oplog** (operation log)
Data-change-based: MongoDB emits events when data changes
Push (passive listener, event-driven)
Real-time

A _replica set_ in MongoDB is a group of [`mongod`](https://www.mongodb.com/docs/manual/reference/program/mongod/#mongodb-binary-bin.mongod) processes that maintain the same data set. Replica sets provide redundancy and [high availability](https://www.mongodb.com/docs/manual/reference/glossary/#std-term-high-availability), and are the basis for all production deployments.
`mongod` is the primary daemon process for the MongoDB system. It handles data requests, manages data access, and performs background management operations.

## Change Stream Performance Considerations

If the amount of active change streams opened against a database exceeds the [connection pool size](https://www.mongodb.com/docs/manual/administration/connection-pool-overview/#std-label-connection-pool-overview), you may experience notification latency. Each change stream uses a connection and a [getMore](https://www.mongodb.com/docs/manual/reference/command/getMore/#std-label-manual-reference-commands-getMore) operation on the change stream for the period of time that it waits for the next event. To avoid any latency issues, you should ensure that the pool size is greater than the number of opened change streams.

## What is a Capped Collection?

A **capped collection** in MongoDB is a **fixed-size**, high-performance collection that:

|Feature|Description|
|---|---|
|🔄 **Fixed size**|You specify a maximum size in bytes (and optionally, max number of documents)|
|🧹 **FIFO behavior**|When full, **oldest documents are automatically overwritten (like a log)**|
|🧲 **Insertion-ordered**|Documents are stored and returned in **insertion order**|
|🚀 **High-throughput**|Very fast for **append-only** use cases (e.g., logging)|
|🔁 **Tailable cursors supported**|Allows clients to **tail the collection** like `tail -f` on logs|
OpLog is a capped collection.

### Change Stream Failure Handling - Use of Resume tokens

Resume Tokens are kind of watermarks that helps us pin point the exact position till we consumed in a sequence of change events.
This can be implemented using a capped collection in mongodb which just stores the latest processed resume token. Resume tokens are provided by mongodb change stream itself, we just have to store them and in case application restarts or crashes we can start consuming events from the latest resume token we consumed last.

