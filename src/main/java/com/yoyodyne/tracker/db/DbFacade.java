package com.yoyodyne.tracker.db;

import com.yoyodyne.tracker.TrackerConfiguration;
import io.dropwizard.setup.Environment;
import java.util.List;

/**
 * Separate the database-specific interactions from the accessors that the
 * system's resources will use.
 */
public abstract class DbFacade {

    private final TrackerConfiguration config;
    private final Environment env;
    
    /**
     * No default constructors are allowed.
     */
    private DbFacade () {
	throw new UnsupportedOperationException( "All DbFacade instances require a DataSourceFactory." );
    }
    
    /**
     * All subclasses must take a <code>TrackerConfiguration</code> and an
     * <code>Environment</code> in their constructors.
     *
     * @param config the <code>TrackerConfiguration</code> that the facade's
     *   implementation should use during construction.
     * @param env the <code>Environment</code> that the facade's implementation
     *   should use during construction.
     */
    public DbFacade (TrackerConfiguration config, Environment env) {
	this.config = config;
	this.env = env;
    }

    /**
     * Open all resources that are used for database interactions.
     *
     * @return an <code>AutoCloseable</code> object that contains all resources
     *   used during database interactions.
     */
    public abstract AutoCloseable open ();

    /**
     * Get the accessors for interacting with achievement entities.
     *
     * @return a <code>AchievementFacade</code> instance.
     */
    public abstract AchievementFacade getAchievementFacade ();

    /**
     * Get the accessors for interacting with title entities.
     *
     * @return a <code>TitleFacade</code> instance.
     */
    public abstract TitleFacade getTitleFacade ();
    
}
