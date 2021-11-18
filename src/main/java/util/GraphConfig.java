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
            .addEdgeProperty("olvasojegySzam", PropertyType.STRING)
            .addEdgeProperty("ISBN", PropertyType.STRING)
            .addVertexProperty("ISBN", PropertyType.STRING)
            .addVertexProperty("title", PropertyType.STRING)
            .addVertexProperty("keresztnev", PropertyType.STRING,"Jakab")
            .addVertexProperty("vezeteknev", PropertyType.STRING,"Gipsz")
            .addVertexProperty("szuletesiIdo", PropertyType.STRING)
            .addVertexProperty("olvasojegySzam", PropertyType.STRING)
            .addVertexProperty("tipus", PropertyType.STRING)
            .setUseVertexPropertyValueAsLabel("tipus")
            .setLoadVertexLabels(true)
            .setLoadEdgeLabel(true)
            .setCreateEdgeIdMapping(true)
            .build();
}
