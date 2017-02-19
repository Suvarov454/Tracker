package com.yoyodyne.tracker.db.h2jdbi;

import com.yoyodyne.tracker.db.h2jdbi.dao.TitleDAO;
import com.yoyodyne.tracker.db.jdbi.LazyHandle;
import com.yoyodyne.tracker.db.DbFacade;
import com.yoyodyne.tracker.db.TitleFacade;
import com.yoyodyne.tracker.domain.Title;
import com.yoyodyne.tracker.TrackerConfiguration;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import java.sql.SQLException;
import java.util.List;

/**
 * The concrete instantiation of <code>DbFacade</code> for H2 databases
 * accessed via JDBI.
 */
public class H2Db extends DbFacade {

    private final DBI dbi;
    private final TitleDAO titleDao;
    
    /**
     * Use the given <code>DBI</code> to access Title entities.
     *
     * @param dbi the <code>DBI</code> to be used.
     * @param dao the <code>TitleDBI</code> to be used.
     */
    public H2Db (TrackerConfiguration config, Environment env) {
	super( config, env );
	this.dbi = new DBIFactory().build( env, config.getDataSourceFactory(), "db" );
	this.titleDao = dbi.onDemand( TitleDAO.class );
    }

    @Override
    public AutoCloseable open () {
	// Provide a lazy-opening JDBI handle.
	return new LazyHandle( dbi );
    }

    @Override
    public TitleFacade getTitleFacade () {
	return new H2TitleDb( this.dbi, this.titleDao );
    }

}
