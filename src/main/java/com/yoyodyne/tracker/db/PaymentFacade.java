package com.yoyodyne.tracker.db;

import com.yoyodyne.tracker.domain.Payment;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Separate the database-specific interactions for payment entities from
 * the accessors that the system's resources will use.
 */
public interface PaymentFacade {

    /**
     * Add a payment for a player for a game.  If the player or the game
     * specified by the game not exist, an exception will be thrown.
     *
     * @param resources the <code>AutoCloseable</code> object that contains all
     *   resources used during database interactions.
     * @param payment the <code>Payment</code> object to be added.
     * @return the new </code>Date</code> the given player's subscription for
     *   the specified game title expires on. This value will never be 
     *   <code>null</code>.
     */
    public Date addPaymentForPlayerForTitle (AutoCloseable resources, Payment payment) throws SQLException;

    /**
     * Get all payments for a player for a game.
     *
     * @param resources the <code>AutoCloseable</code> object that contains all
     *   resources used during database interactions.
     * @param playerId the <code>UUID</code> of the player whose payments
     *   are needed
     * @param titleId the <code>UUID</code> of the title whose subscriptions are
     *   needed
     * @return a <code>List</code> of </code>Payment</code>s; this value
     *   will never be <code>null</code> but may be empty.
     */
    public List<Payment> getPaymentsForPlayerForTitle (AutoCloseable resources, UUID playerId, UUID titleId) throws SQLException;
    
}
