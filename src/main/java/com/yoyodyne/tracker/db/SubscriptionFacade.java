package com.yoyodyne.tracker.db;

import com.yoyodyne.tracker.domain.Subscription;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Separate the database-specific interactions for subscription entities from
 * the accessors that the system's resources will use.
 */
public interface SubscriptionFacade {

    /**
     * Get all game subscriptions for a title of this studio.
     *
     * @param resources the <code>AutoCloseable</code> object that contains all
     *   resources used during database interactions.
     * @param titleId the <code>UUID</code> of the title whose subscriptions are
     *   needed
     * @return a <code>List</code> of </code>Subscription</code>s; this value
     *   will never be <code>null</code> but may be empty.
     */
    public List<Subscription> getSubscriptionsForTitle (AutoCloseable resources, UUID titleId) throws SQLException;

    /**
     * Get all game subscriptions for a player of this studio.
     *
     * @param resources the <code>AutoCloseable</code> object that contains all
     *   resources used during database interactions.
     * @param playerId the <code>UUID</code> of the player whose subscriptions
     *   are needed
     * @return a <code>List</code> of </code>Subscription</code>s; this value
     *   will never be <code>null</code> but may be empty.
     */
    public List<Subscription> getSubscriptionsForPlayer (AutoCloseable resources, UUID playerId) throws SQLException;
    
}
