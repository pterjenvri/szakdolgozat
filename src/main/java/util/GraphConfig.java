package util;

import oracle.pgx.common.types.PropertyType;
import oracle.pgx.config.GraphConfigBuilder;
import oracle.pgx.config.PgNosqlGraphConfig;

import java.util.Collections;

public class GraphConfig {
    private static final String KV_HOST_PORT = "localhost:5000";
    private static final String KV_STORE_NAME = "kvstore";
    private static final String KV_GRAPH_NAME = "users_graph";
    public static final PgNosqlGraphConfig cfg = GraphConfigBuilder.forPropertyGraphNosql()
            .setName(KV_GRAPH_NAME)
            .setHosts(Collections.singletonList(KV_HOST_PORT))
            .setStoreName(KV_STORE_NAME)
            .addEdgeProperty("olvasojegySzam", PropertyType.STRING, "asd")
            .addEdgeProperty("ISBN", PropertyType.STRING, "asd")
            .addVertexProperty("ISBN", PropertyType.STRING, "asd")
            .addVertexProperty("title", PropertyType.STRING, "asd")
            .addVertexProperty("keresztnev", PropertyType.STRING, "asd")
            .addVertexProperty("vezeteknev", PropertyType.STRING, "asd")
            .addVertexProperty("szuletesiIdo", PropertyType.STRING, "asd")
            .addVertexProperty("olvasojegySzam", PropertyType.STRING, "asd")
            .addVertexProperty("tipus", PropertyType.STRING, "asd")
            .setUseVertexPropertyValueAsLabel("tipus")
            .setPropertyValueDelimiter(",")
            .setLoadVertexLabels(true)
            .setLoadEdgeLabel(true)
            .setCreateEdgeIdMapping(true)
            .build();
}
