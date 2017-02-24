package com.yoyodyne.tracker.db.jdbi.mapper;

import com.yoyodyne.tracker.domain.Achievement;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Used by JDBI to map <code>Achievement</code> entities to queries.
 */
public class AchievementMapper implements ResultSetMapper<Achievement> {

    public Achievement map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Achievement( UUID.fromString( r.getString("achievement_id") ),
                                UUID.fromString( r.getString("title_id") ),
                                r.getString( "name" ),
                                r.getString( "description" ),
                                r.getString( "sort" ) );
    }

}
