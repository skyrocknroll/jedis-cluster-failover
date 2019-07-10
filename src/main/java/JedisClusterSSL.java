import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by uva on 8/4/17.
 */
public class JedisClusterSSL {

    public static void main(String[] args) {
        GenericObjectPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(100);

        Set<HostAndPort> clusterNodes = new HashSet<>(4);
//        clusterNodes.add(new HostAndPort("host1", 6379));
//        clusterNodes.add(new HostAndPort("host2", 6379));
//        clusterNodes.add(new HostAndPort("host3", 6379));
//        clusterNodes.add(new HostAndPort("host4", 6379));
//You can use elasticashe cluster cfg url also
        clusterNodes.add(new HostAndPort("clustercfg.xx-xx-5.xx.xx.cache.amazonaws.com", 6379));

        JedisCluster jc = new JedisCluster(clusterNodes, 10000, 10000, 1000, "xxxx", "Lappie", jedisPoolConfig, true);
//		JedisCluster internally maintains connection pool. So when an failover happens the requests are routed to new master automatically.
        for (long i = 0; i < 10000000000L; i++) {
            try {

                try {
//                    System.out.println(jedis.info("Replication"));
                    System.out.println(jc.incr("foo111").toString());
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
