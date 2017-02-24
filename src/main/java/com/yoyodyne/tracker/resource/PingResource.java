package com.yoyodyne.tracker.resource;

import com.codahale.metrics.annotation.Timed;
import com.yoyodyne.tracker.TrackerApplication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/ping")
@Api(value = "/ping", description = "Ping")
@Produces(MediaType.TEXT_PLAIN)
public class PingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger( PingResource.class );

    @GET
    @Timed
    @Path("/")
    @ApiOperation(
        value = "Prove that the application is responding to HTTP requests.",
        response = String.class
    )
    public String getPing () {
        LOGGER.info( "{} pinged", TrackerApplication.APP_NAME );
        return "pong";
    }
}
