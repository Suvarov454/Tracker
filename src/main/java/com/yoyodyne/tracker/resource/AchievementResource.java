package com.yoyodyne.tracker.resource;

import com.codahale.metrics.annotation.Timed;
import com.yoyodyne.tracker.domain.Achievement;
import com.yoyodyne.tracker.db.DbFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/achievement")
@Api(value = "/achievement", description = "Get information about game achievements")
@Produces(MediaType.APPLICATION_JSON)
public class AchievementResource {

    private static final Logger LOGGER = LoggerFactory.getLogger( AchievementResource.class );

    private DbFacade database;

    public AchievementResource (DbFacade database) {
	this.database = database;
    }
    
    @GET
    @Timed
    @Path("/{achievementId}")
    @ApiOperation(
        value = "Get the detaild of the given achievement.",
	response = Achievement.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Some parsing error."),
        @ApiResponse(code = 404, message = "No achievement has the given ID."),
	@ApiResponse(code = 500, message = "Some data access error.")
    })
    public Achievement getAchievement (
        @ApiParam(value = "ID of the achievement", required = true)
	@PathParam("achievementId") String achievementIdStr) throws Exception {
	UUID achievementId = UUID.fromString( achievementIdStr );
	Achievement achievement = null;
	try (AutoCloseable resources = database.open()) {
	    achievement = database.getAchievementFacade().getAchievement( resources, achievementId );
	    LOGGER.info( "Found achievement with ID {}", achievement.getAchievementId() );
	}
	return achievement;
    }

}
