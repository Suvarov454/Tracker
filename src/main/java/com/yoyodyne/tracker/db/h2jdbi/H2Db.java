package com.yoyodyne.tracker.db.h2jdbi;

import com.yoyodyne.tracker.db.h2jdbi.dao.AchievementDAO;
import com.yoyodyne.tracker.db.h2jdbi.dao.PaymentDAO;
import com.yoyodyne.tracker.db.h2jdbi.dao.SubscriptionDAO;
import com.yoyodyne.tracker.db.h2jdbi.dao.TitleDAO;
import com.yoyodyne.tracker.db.jdbi.LazyHandle;
import com.yoyodyne.tracker.db.AchievementFacade;
import com.yoyodyne.tracker.db.DbFacade;
import com.yoyodyne.tracker.db.PaymentFacade;
import com.yoyodyne.tracker.db.SubscriptionFacade;
import com.yoyodyne.tracker.db.TitleFacade;
import com.yoyodyne.tracker.domain.Achievement;
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
    private final AchievementDAO achievementDao;
    private final PaymentDAO paymentDao;
    private final SubscriptionDAO subscriptionDao;
    private final TitleDAO titleDao;
    
    /**
     * Implement a facade to query H2 database using JDBI using the given
     * configuration and environment
     *
     * @param config the application's <code>TrackerConfiguration</code>.
     * @param env the Dropwizard <code>Environment</code> to be used.
     */
    public H2Db (TrackerConfiguration config, Environment env) {
	super( config, env );
	this.dbi = new DBIFactory().build( env, config.getDataSourceFactory(), "db" );
	this.achievementDao = dbi.onDemand( AchievementDAO.class );
	this.paymentDao = dbi.onDemand( PaymentDAO.class );
	this.subscriptionDao = dbi.onDemand( SubscriptionDAO.class );
	this.titleDao = dbi.onDemand( TitleDAO.class );
    }

    @Override
    public AutoCloseable open () {
	// Provide a lazy-opening JDBI handle.
	return new LazyHandle( dbi );
    }

    @Override
    public AchievementFacade getAchievementFacade () {
	return new H2AchievementDb( this.dbi, this.achievementDao );
    }

    @Override
    public PaymentFacade getPaymentFacade () {
	// The same class as for subcription entities.
	return new H2PaymentAndSubscriptionDb( this.dbi, this.paymentDao, this.subscriptionDao );
    }

    @Override
    public SubscriptionFacade getSubscriptionFacade () {
	// The same class as for payment entities.
	return new H2PaymentAndSubscriptionDb( this.dbi, this.paymentDao, this.subscriptionDao );
    }

    @Override
    public TitleFacade getTitleFacade () {
	return new H2TitleDb( this.dbi, this.titleDao );
    }

}
