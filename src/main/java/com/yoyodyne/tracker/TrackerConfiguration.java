package com.yoyodyne.tracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import java.util.Collections;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class TrackerConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration;

    public DataSourceFactory getDataSourceFactory () {
        return database;
    }

    public void setDataSourceFactory (DataSourceFactory config) {
        this.database = config;
    }

    public SwaggerBundleConfiguration getSwaggerBundleConfiguration () {
        return swaggerBundleConfiguration;
    }

    public void setSwaggerBundleConfiguration (SwaggerBundleConfiguration config) {
        this.swaggerBundleConfiguration = config;
    }

}
