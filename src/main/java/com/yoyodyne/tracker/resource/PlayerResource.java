package com.yoyodyne.tracker.resource;

import com.codahale.metrics.annotation.Timed;
import com.yoyodyne.tracker.domain.Subscription;
import com.yoyodyne.tracker.domain.Payment;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/player")
@Api(value = "/player", description = "Get information about players")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger( PlayerResource.class );

    private DbFacade database;

    public PlayerResource (DbFacade database) {
	this.database = database;
    }
    
    // @PUT
    // @Timed
    // @Path("/")
    // @ApiOperation(
    //     value = "Add a player for this studio.",
    // 	response = Player.class
    // )
    // @ApiResponses(value = {
    //     @ApiResponse(code = 400, message = "Some parsing error."),
    //     @ApiResponse(code = 500, message = "Some data access error.")
    // })
    // public Player addPlayer (Player player) throws Exception {
    // 	Player result = null;
    // 	try (AutoCloseable resources = database.open()) {
    // 	    result = database.getPlayerFacade().addPlayer( resources, player );
    // 	    LOGGER.info( "Added player with ID {}", result.getPlayerId() );
    // 	}
    // 	return result;
    // }

    @PUT
    @Timed
    @Path("/{playerId}/subscription/{titleId}/payment")
    @ApiOperation(
        value = "Add a payment for a player to their subscription for a title;"
	+ " Returns the new expiration date of the player's subscription.",
	response = Date.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Some parsing error."),
	@ApiResponse(code = 500, message = "Some data access error.")
    })
    public Date addPaymentsForPlayerForTitle (
        @ApiParam(value = "ID of the player", required = true)
	@PathParam("playerId") String playerIdStr,
        @ApiParam(value = "ID of the title", required = true)
	@PathParam("titleId") String titleIdStr,
        Payment payment) throws Exception {
	UUID playerId = UUID.fromString( playerIdStr );
	UUID titleId = UUID.fromString( titleIdStr );

	// The player and title IDs in the path must match the payment.
	if (!playerId.equals( payment.getPlayerId() )) {
	    String message = String.format(
                "Player ID mismatch; path ID %1$s, payment ID %2$s",
		String.valueOf( playerId ),
		String.valueOf( payment.getPlayerId() ) );
	    LOGGER.warn( message );
	    throw new IllegalArgumentException( message );
	}
	if (!titleId.equals( payment.getTitleId() )) {
	    String message = String.format(
                "Title ID mismatch; path ID %1$s, payment ID %2$s",
		String.valueOf( titleId ),
		String.valueOf( payment.getTitleId() ) );
	    LOGGER.warn( message );
	    throw new IllegalArgumentException( message );
	}
	
	Date result = null;
	try (AutoCloseable resources = database.open()) {
	    // Auto-generate a new payment ID.
	    payment.setPaymentId( UUID.randomUUID().toString() );
	    result = database.getPaymentFacade().addPaymentForPlayerForTitle( resources, payment );
	    LOGGER.info( "Player with ID {} for title ID {} subscribed until {}", playerIdStr, titleIdStr, result );
	}
	return result;
    }
    
    // @GET
    // @Timed
    // @Path("/")
    // @ApiOperation(
    //     value = "Get the list of players for this studio.",
    // 	response = Player.class,
    // 	responseContainer = "List"
    // )
    // @ApiResponses(value = {
    //     @ApiResponse(code = 500, message = "Some data access error.")
    // })
    // public List<Player> getAllPlayers () throws Exception {
    // 	List<Player> players = Collections.emptyList();
    // 	try (AutoCloseable resources = database.open()) {
    // 	    players = database.getPlayerFacade().getAllPlayers( resources );
    // 	    LOGGER.info( "Found {} players", players.size() );
    // 	}
    // 	return players;
    // }

    @GET
    @Timed
    @Path("/{playerId}/subscription/{titleId}/payment")
    @ApiOperation(
        value = "Get all the payments a player make for a subscription for a title.",
	response = Subscription.class,
	responseContainer = "List"
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Some parsing error."),
	@ApiResponse(code = 500, message = "Some data access error.")
    })
    public List<Payment> getPaymentsForPlayerForTitle (
        @ApiParam(value = "ID of the player", required = true)
	@PathParam("playerId") String playerIdStr,
        @ApiParam(value = "ID of the title", required = true)
	@PathParam("titleId") String titleIdStr) throws Exception {
	UUID playerId = UUID.fromString( playerIdStr );
	UUID titleId = UUID.fromString( titleIdStr );
	List<Payment> result = Collections.emptyList();
	try (AutoCloseable resources = database.open()) {
	    result = database.getPaymentFacade().getPaymentsForPlayerForTitle( resources, playerId, titleId );
	    LOGGER.info( "Got {} payments for player with ID {} for title ID {}", result.size(), playerIdStr, titleIdStr );
	}
	return result;
    }

    @GET
    @Timed
    @Path("/{playerId}/subscription")
    @ApiOperation(
        value = "Get all the subscriptions for a game.",
	response = Subscription.class,
	responseContainer = "List"
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Some parsing error."),
	@ApiResponse(code = 500, message = "Some data access error.")
    })
    public List<Subscription> getSubscriptionsForPlayer (
        @ApiParam(value = "ID of the player", required = true)
	@PathParam("playerId") String playerIdStr) throws Exception {
	UUID playerId = UUID.fromString( playerIdStr );
	List<Subscription> result = Collections.emptyList();
	try (AutoCloseable resources = database.open()) {
	    result = database.getSubscriptionFacade().getSubscriptionsForPlayer( resources, playerId );
	    LOGGER.info( "Got {} subscriptions for player with ID {}", result.size(), playerIdStr );
	}
	return result;
    }

}
