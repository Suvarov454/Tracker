package com.yoyodyne.tracker.db.jdbi.mapper;

import com.yoyodyne.tracker.domain.Title;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Used by JDBI to map <code>Title</code> entities to queries.
 */
public class TitleMapper implements ResultSetMapper<Title> {

    public Title map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Title( UUID.fromString( r.getString("title_id") ),
                          r.getString( "name" ) );
    }

}
