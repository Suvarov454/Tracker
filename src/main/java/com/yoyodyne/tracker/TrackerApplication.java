package com.yoyodyne.tracker;

import com.yoyodyne.tracker.db.h2jdbi.H2Db;
import com.yoyodyne.tracker.resource.AchievementResource;
import com.yoyodyne.tracker.resource.PasswordResource;
import com.yoyodyne.tracker.resource.PlayerResource;
import com.yoyodyne.tracker.resource.PingResource;
import com.yoyodyne.tracker.resource.TitleResource;
import com.yoyodyne.tracker.db.DbFacade;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrackerApplication extends Application<TrackerConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger( TrackerApplication.class );
    public static final String APP_NAME = "Tracker";

    public static void main(String[] args) throws Exception {
	// We need at least one argument
	if (args.length == 0) {
	    LOGGER.warn( "{} invoked without arguments", APP_NAME );
	    usage();
	    System.exit(1);
	}
	LOGGER.info( "{} with {} arguments (first: {})", APP_NAME, args.length, args[0] );
	new TrackerApplication().run(args);
    }

    @Override
    public String getName() {
        return APP_NAME;
    }

    @Override
    public void initialize(Bootstrap<TrackerConfiguration> bootstrap) {

        bootstrap.addBundle(new SwaggerBundle<TrackerConfiguration>() {
            @Override
	    protected SwaggerBundleConfiguration getSwaggerBundleConfiguration (TrackerConfiguration config) {
		return config.getSwaggerBundleConfiguration();
	    }
        });
    }

    @Override
    public void run(TrackerConfiguration configuration,
                    Environment environment) throws Exception {
	// Instantiate the database facade implementation.
	DbFacade database = new H2Db( configuration, environment );

        environment.jersey().register(new AchievementResource( database ));
        environment.jersey().register(new PasswordResource( configuration ));
        environment.jersey().register(new PlayerResource( database ));
        environment.jersey().register(new PingResource());
        environment.jersey().register(new TitleResource( database ));
    }

    public static void usage() {
	System.err.print( APP_NAME );
	System.err.println(" must be supplied with a configuration YAML filename.");
    }
    
}
