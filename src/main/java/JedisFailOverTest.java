import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.JedisClusterCRC16;
import redis.clients.util.JedisClusterHashTagUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by uva on 8/4/17.
 */
public class JedisFailOverTest {

	public static void main(String[] args) {
		GenericObjectPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(10);
		jedisPoolConfig.setMaxTotal(100);

		Set<HostAndPort> clusterNodes = new HashSet<>(6);
		clusterNodes.add(new HostAndPort("127.0.0.1", 30001));
		clusterNodes.add(new HostAndPort("127.0.0.1", 30002));
		clusterNodes.add(new HostAndPort("127.0.0.1", 30003));
		clusterNodes.add(new HostAndPort("127.0.0.1", 30004));
		clusterNodes.add(new HostAndPort("127.0.0.1", 30005));
		clusterNodes.add(new HostAndPort("127.0.0.1", 30006));
		JedisCluster jc = new JedisCluster(clusterNodes);
//		JedisCluster internally maintains connection pool. So when an failover happens the requests are routed to new master automatically.
		for (long i = 0; i < 10000000000L; i++) {
			try {

				try {
//                    System.out.println(jedis.info("Replication"));
					jc.incr("foo");
//                    throw ConnectException();
				} catch (Exception e) {
					System.out.println("failed during increment");
					System.out.println(e.getMessage());
				}


			} catch (JedisConnectionException e) {
				System.out.println("Master Down!!");
				System.out.println(e.getMessage());
			}
		}

	}
}
