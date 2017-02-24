package com.yoyodyne.tracker.db.h2jdbi;

import com.yoyodyne.tracker.db.h2jdbi.dao.PaymentDAO;
import com.yoyodyne.tracker.db.h2jdbi.dao.SubscriptionDAO;
import com.yoyodyne.tracker.db.jdbi.LazyHandle;
import com.yoyodyne.tracker.db.PaymentFacade;
import com.yoyodyne.tracker.db.SubscriptionFacade;
import com.yoyodyne.tracker.domain.Payment;
import com.yoyodyne.tracker.domain.Subscription;
import org.skife.jdbi.v2.util.StringMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.ResultIterator;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
    private final PaymentService service;
    private final SimpleDateFormat dateFormat;

    private static final String ADD_PAYMENT = "insert into payment ( "
	+ "payment_id, "
	+ "title_id, "
	+ "player_id, "
	+ "timestamp, "
	+ "extension_duration, "
	+ "expiration_basis, "
	+ "expiration_date, "
	+ "amount, "
	+ "currency, "
	+ "type, "
	+ "token) values ( "
	+ ":paymentId, "
	+ ":titleId, "
	+ ":playerId, "
	+ ":timestamp, "
	+ ":extensionDuration, "
	+ ":expirationBasis, "
	+ ":expirationDate, "
	+ ":amount, "
	+ ":currency, "
	+ ":type, "
	+ ":token) ";

    private static final String ADD_SUBSCRIPTION = "insert into subscription ( "
	+ "subscription_id, "
	+ "title_id, "
	+ "player_id, "
	+ "expiration_date, "
	+ "level) values ( "
	+ ":subscriptionId, "
	+ ":titleId, "
	+ ":playerId, "
	+ ":expirationDate, "
	+ ":level) ";

    private static final String GET_EXPIRATION_DATE = "select expiration_date "
	+ "from subscription where player_id = :playerId and title_id = "
	+ ":titleId";

    private static final String SET_EXPIRATION_DATE = "update subscription "
	+ "set expiration_date = :expirationDate where player_id = :playerId "
	+ "and title_id = :titleId";
    
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
	this.service = new PaymentService( this );
	this.dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
    }

    // @Override
    // public Subscription addSubscription (AutoCloseable resources, Subscription input) throws SQLException {
    // 	// Use a random UUID instead of the input one.
    // 	Subscription newSubscription = new Subscription( UUID.randomUUID(), input.getName() );
    // 	this.subDao.addSubscription( newSubscription );
    // 	return newSubscription;
    // }

    @Override
    public Date addPaymentForPlayerForTitle (AutoCloseable resources, Payment payment) throws SQLException {
	// Open a handle from the resources.
 	Handle handle = LazyHandle.open( resources );

	// Process the payment in the service.
	return this.service.processPayment( handle, payment );
    }

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


    /**
     * Add the given payment to the database.
     *
     * @param handle the open database <code>Handle</code> to use for access.
     * @param payment the <code>Payment</code> to be added.
     */
    protected void addPayment (Handle handle, Payment payment ) {
	handle.createStatement( ADD_PAYMENT )
	    .bind( "paymentId", payment.getPaymentId() )
	    .bind( "playerId", payment.getPlayerId() )
	    .bind( "titleId", payment.getTitleId() )
	    .bind( "timestamp", new Date() )
	    .bind( "extensionDuration", payment.getExtensionDuration() )
	    .bind( "expirationBasis", payment.getExpirationBasis() )
	    .bind( "expirationDate", payment.getExpirationDate() )
	    .bind( "amount", payment.getAmount() )
	    .bind( "currency", payment.getCurrency() )
	    .bind( "type", payment.getType() )
	    .bind( "token", payment.getToken() )
	    .execute();
    }
    
    /**
     * Add the given subscription to the database.
     *
     * @param handle the open database <code>Handle</code> to use for access.
     * @param subscription the <code>Subscription</code> to be added.
     */
    protected void addSubscription (Handle handle, Subscription subscription ) {
	handle.createStatement( ADD_SUBSCRIPTION )
	    .bind( "subscriptionId", subscription.getSubscriptionId() )
	    .bind( "playerId", subscription.getPlayerId() )
	    .bind( "titleId", subscription.getTitleId() )
	    .bind( "expirationDate", subscription.getExpirationDate() )
	    .bind( "level", subscription.getLevel() )
	    .execute();
    }

    /**
     * Get the current expiration date for the given player's subscription to
     * the specified game title.  If the player is not currently subscribed, a
     * <code>null</code> value will be returned.
     *
     * @param handle the open database <code>Handle</code> to use for access.
     * @param playerId the <code>UUID</code> of the player whose subscription
     *   expiration date is needed.
     * @param titleId the <code>UUID</code> of the title whose subscription is
     *   needed.
     * @return the expiration <code>Date</code> of the player's current
     *   subcription to the given game or <code>null</code> if the player is
     *   not currently subscribed.
     */
    protected Date getExpirationDate (Handle handle, UUID playerId, UUID titleId ) {
	ResultIterator<String> rs = handle.createQuery( GET_EXPIRATION_DATE )
	    .bind( "playerId", playerId )
	    .bind( "titleId", titleId )
	    .map( StringMapper.FIRST )
	    .iterator();
	Date expirationDate = null;
	if (rs.hasNext()) {
	    String dateStr = rs.next();
	    try {
		expirationDate = this.dateFormat.parse( dateStr );
	    }
	    catch (Exception err) {
		err.printStackTrace( System.err );
	    }
	}
	rs.close();
	return expirationDate;
    }

    /**
     * Set the expiration date of the subscription for the given player for
     * the specified game title.
     */
    protected void setExpirationDate (Handle handle, UUID playerId, UUID titleId, Date newDate ) {
	handle.createStatement( SET_EXPIRATION_DATE )
	    .bind( "playerId", playerId )
	    .bind( "titleId", titleId )
	    .bind( "expirationDate", newDate )
	    .execute();
    }

}
