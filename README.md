# jedis-cluster-failover
jedis cluster failover recovery
* Simulate Redis Timeout using `redis-cli DEBUG SLEEP 30`
* Simulate Redis Process Crash using `redis-cli DEBUG SEGFAULT`

Compared to JedisPool and JedisSentinelPool where you need to do `conn.close()`  but in jedisCluster you don't need to 
do that because the redis commands which has state like `pubsub` `pipeline` are invalid in redis cluster. 