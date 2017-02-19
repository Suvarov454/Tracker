package com.yoyodyne.tracker.resource;

import com.codahale.metrics.annotation.Timed;
import com.yoyodyne.tracker.domain.Achievement;
import com.yoyodyne.tracker.domain.Title;
import com.yoyodyne.tracker.db.DbFacade;
import io.swagger.annotations.Api;
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

@Path("/title")
@Api(value = "/title", description = "Get information about game titles")
@Produces(MediaType.APPLICATION_JSON)
public class TitleResource {

    private static final Logger LOGGER = LoggerFactory.getLogger( TitleResource.class );

    private DbFacade database;

    public TitleResource (DbFacade database) {
	this.database = database;
    }
    
    @PUT
    @Timed
    @Path("/{titleId}/achievement")
    @ApiOperation(
        value = "Add an achievement to a game.",
	response = Achievement.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Some parsing error."),
	@ApiResponse(code = 500, message = "Some data access error.")
    })
    public Achievement addAchievement (@PathParam("titleId") String titleIdStr, Achievement achievement) throws Exception {
	// Make sure the title IDs match
	UUID titleId = UUID.fromString( titleIdStr );
	if (!titleId.equals( achievement.getTitleId() )) {
	    LOGGER.warn( "Tried to add an achievement to the wrong title (title ID: {}, achievement title id: {}",
			 titleIdStr, achievement.getTitleId() );
			 throw new IllegalArgumentException( "Tried to add an achievement to the wrong title." );
	}
	Achievement result = null;
	try (AutoCloseable resources = database.open()) {
	    result = database.getAchievementFacade().addAchievement( resources, achievement );
	    LOGGER.info( "Added achievement with ID {}", result.getAchievementId() );
	}
	return result;
    }
    
    @PUT
    @Timed
    @Path("/")
    @ApiOperation(
        value = "Add a game title for this studio.",
	response = Title.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Some parsing error."),
        @ApiResponse(code = 500, message = "Some data access error.")
    })
    public Title addTitle (Title title) throws Exception {
	Title result = null;
	try (AutoCloseable resources = database.open()) {
	    result = database.getTitleFacade().addTitle( resources, title );
	    LOGGER.info( "Added title with ID {}", result.getTitleId() );
	}
	return result;
    }

    @GET
    @Timed
    @Path("/{titleId}/achievement")
    @ApiOperation(
        value = "Get all the achievements in the a game.",
	response = Achievement.class,
	responseContainer = "List"
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Some parsing error."),
	@ApiResponse(code = 500, message = "Some data access error.")
    })
    public List<Achievement> getAchievementsForTitle (@PathParam("titleId") String titleIdStr) throws Exception {
	UUID titleId = UUID.fromString( titleIdStr );
	List<Achievement> result = Collections.emptyList();
	try (AutoCloseable resources = database.open()) {
	    result = database.getAchievementFacade().getAchievementsForTitle( resources, titleId );
	    LOGGER.info( "Got {} achievements for title with ID {}", result.size(), titleIdStr );
	}
	return result;
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
