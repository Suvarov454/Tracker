package com.yoyodyne.tracker.resource;

import com.codahale.metrics.annotation.Timed;
import com.yoyodyne.tracker.domain.Title;
import com.yoyodyne.tracker.db.DbFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/title")
@Api(value = "/title", description = "Get information about game titles")
@Produces(MediaType.APPLICATION_JSON)
public class TitleResource {

    private static final Logger LOGGER = LoggerFactory.getLogger( TitleResource.class );

    private DbFacade database;

    public TitleResource (DbFacade database) {
	this.database = database;
    }
    
    @GET
    @Timed
    @Path("/")
    @ApiOperation(
        value = "Get the list of titles for this studio.",
	response = Title.class,
	responseContainer = "List"
    )
    @ApiResponses(value = {
        @ApiResponse(code = 500, message = "Some data access error.")
    })
    public List<Title> getAllTitles () throws Exception {
	List<Title> titles = Collections.emptyList();
	try (AutoCloseable resources = database.open()) {
	    titles = database.getTitleFacade().getAllTitles( resources );
	    LOGGER.info( "Found {} titles", titles.size() );
	}
	return titles;
    }
}
