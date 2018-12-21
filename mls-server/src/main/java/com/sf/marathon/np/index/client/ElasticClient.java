package com.sf.marathon.np.index.client;

import com.sf.marathon.index.domain.EsConfig;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.netty.NettyTransport;

/**
 * Created by 204062 on 2015/9/18.
 */
public enum ElasticClient {
    instance;
    public static final int PINT_TIMEOUT = 30000;
    public static final int NODE_SAMPLER_INTERVAL = 10000;
    private Client client;

    ElasticClient() {
        //配置你的es,现在这里只配置了集群的名,默认是elasticsearch,跟服务器的相同
        client = createClient();
    }

    private TransportClient createClient() {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", EsConfig.getInstance().getClusterName())
                .put("client.transport.ping_timeout", PINT_TIMEOUT)//Default 5s
                .put("client.transport.nodes_sampler_interval", NODE_SAMPLER_INTERVAL)//Default 5s
                .put(NettyTransport.WORKER_COUNT, EsConfig.getInstance().getWorkerCount()).build();
        //这里可以同时连接集群的服务器,可以多个,并且连接服务是可访问的
        TransportClient transportClient = new TransportClient(settings);
        String esUrl = EsConfig.getInstance().getEsUrl();
        String[] elasticAddr = esUrl.split(",");
        for (String addr : elasticAddr) {
            String[] split = addr.split(":");
            transportClient.addTransportAddress(new InetSocketTransportAddress(split[0], Integer.parseInt(split[1])));
        }
        return transportClient;
    }

    public Client getClient() {
        return client;
    }
}
