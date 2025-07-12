1. Run command -> mongod --port 2717 --dbpath "C:\Program Files\MongoDB\Server\8.0\data\db1" --replSet rs
  on Command Prompt (Run as Admin for proper permissions).
2. Open Mongo shell and run rs.initiate().
3. run rs.status() to check status if replica set is running.
