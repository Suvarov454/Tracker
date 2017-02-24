package com.yoyodyne.tracker.db.jdbi.mapper;

import com.yoyodyne.tracker.domain.Payment;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Used by JDBI to map <code>Payment</code> entities to queries.
 */
public class PaymentMapper implements ResultSetMapper<Payment> {

    public Payment map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Payment( UUID.fromString( r.getString("payment_id") ),
                            UUID.fromString( r.getString("player_id") ),
                            UUID.fromString( r.getString("title_id") ),
                            r.getTimestamp( "timestamp" ),
                            r.getLong( "extension_duration" ),
                            r.getDate( "expiration_basis" ),
                            r.getDate( "expiration_date" ),
                            r.getBigDecimal( "amount" ),
                            r.getString( "currency" ),
                            r.getString( "type" ),
                            r.getString( "token" ) );
    }

}
