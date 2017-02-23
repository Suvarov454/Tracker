package com.yoyodyne.tracker.db.h2jdbi;

import com.yoyodyne.tracker.db.h2jdbi.dao.PaymentDAO;
import com.yoyodyne.tracker.db.h2jdbi.dao.SubscriptionDAO;
import com.yoyodyne.tracker.db.PaymentFacade;
import com.yoyodyne.tracker.db.SubscriptionFacade;
import com.yoyodyne.tracker.domain.Payment;
import com.yoyodyne.tracker.domain.Subscription;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
// import java.sql.ResultSet; //killme
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The concrete instantiation of <em>both</em> the <code>PaymentFacade</code>
 * and the <code>SubscriptionFacade</code> for H2 databases accessed via JDBI.
 * Adding a payment to the system has several different stages that should all
 * share the same open handle.
 */
public class H2PaymentAndSubscriptionDb implements PaymentFacade, SubscriptionFacade {

    private final DBI dbi;
    private final PaymentDAO payDao;
    private final SubscriptionDAO subDao;

    /**
     * Never allow default construction.
     */
    private H2PaymentAndSubscriptionDb () {
	throw new UnsupportedOperationException( "All H2PaymentAndSubscriptionDb instances require a DAO." );
    }
    
    /**
     * Use the given <code>DBI</code> to access Subscription entities.
     *
     * @param dbi the <code>DBI</code> to be used.
     * @param payDao the <code>PaymentDAO</code> to be used.
     * @param subDao the <code>SubscriptionDAO</code> to be used.
     */
    public H2PaymentAndSubscriptionDb (DBI dbi, PaymentDAO payDao, SubscriptionDAO subDao) {
	this.dbi = dbi;
	this.payDao = payDao;
	this.subDao = subDao;
    }

    // @Override
    // public Subscription addSubscription (AutoCloseable resources, Subscription input) throws SQLException {
    // 	// // We need a DBI handle, which we *should* be available in resources.
    // 	// Handle handle = LazyHandle.open( resources ); //killme

    // 	// Use a random UUID instead of the input one.
    // 	Subscription newSubscription = new Subscription( UUID.randomUUID(), input.getName() );
    // 	this.subDao.addSubscription( newSubscription );
    // 	return newSubscription;
    // }

    @Override
    public List<Payment> getPaymentsForPlayerForTitle (AutoCloseable resources, UUID playerId, UUID titleId) throws SQLException {
	return this.payDao.getPaymentsForPlayerForTitle( playerId.toString(), titleId.toString() );
    }

    @Override
    public List<Subscription> getSubscriptionsForTitle (AutoCloseable resources, UUID titleId) throws SQLException {
	return this.subDao.getSubscriptionsForTitle( titleId.toString() );
    }

    @Override
    public List<Subscription> getSubscriptionsForPlayer (AutoCloseable resources, UUID playerId) throws SQLException {
	return this.subDao.getSubscriptionsForPlayer( playerId.toString() );
    }

    
    protected void addPayment (Handle handle, Payment payment ) {
	// TODO : implement me
    }
    
    protected void addSubscription (Handle handle, Subscription subscription ) {
	// TODO : implement me
    }
    
    protected Date getExpirationDate (Handle handle, UUID playerId, UUID titleId ) {
	// TODO : implement me
	return null;
    }
    
    protected void setExpirationDate (Handle handle, UUID playerId, UUID titleId, Date newDate ) {
	// TODO : implement me
    }

}
