package com.yoyodyne.tracker.db.jdbi.mapper;

import com.yoyodyne.tracker.domain.Subscription;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Used by JDBI to map <code>Subscription</code> entities to queries.
 */
public class SubscriptionMapper implements ResultSetMapper<Subscription> {

    public Subscription map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Subscription( UUID.fromString( r.getString("subscription_id") ),
				 UUID.fromString( r.getString("player_id") ),
				 UUID.fromString( r.getString("title_id") ),
				 r.getDate( "expiration_date" ),
				 r.getLong( "level" ),
				 r.getString( "name" ) );
    }

}
