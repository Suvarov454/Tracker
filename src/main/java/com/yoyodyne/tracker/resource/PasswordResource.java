package com.yoyodyne.tracker.resource;

import com.codahale.metrics.annotation.Timed;
import com.yoyodyne.tracker.domain.Passcode;
import com.yoyodyne.tracker.util.PasscodeUtil;
import com.yoyodyne.tracker.TrackerConfiguration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/password")
@Api(value = "/password", description = "Convert a password into a passcode")
@Produces(MediaType.APPLICATION_JSON)
public class PasswordResource {

    private static final Logger LOGGER = LoggerFactory.getLogger( PasswordResource.class );

    private final PasscodeUtil hasher;

    public PasswordResource (TrackerConfiguration config) throws Exception {
        this.hasher = new PasscodeUtil( config.getIterations(),
                                        config.getKeyLength(),
                                        config.getAlgorithm() );
    }
    
    @POST
    @Timed
    @Path("/hash")
    @ApiOperation(
        value = "Convert the plain text password to an encrypted passcode." +
        " A seed will be randomly generated, if one is not supplied.",
        response = Passcode.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Some parsing error."),
        @ApiResponse(code = 500, message = "Some cryptoengine error.")
    })
    public Passcode hashPassword (Passcode plainText) throws Exception {
        // If no seed was passed, generate one.
        UUID seed = plainText.getSeed();
        if (seed == null) {
            seed = UUID.randomUUID();
        }

        String cryptoText = hasher.hash( plainText.getPasscode(), seed );
        return new Passcode( cryptoText, seed );
    }

}
