package com.yoyodyne.tracker.db.h2jdbi.dao;

import com.yoyodyne.tracker.db.jdbi.TitleMapper;
import com.yoyodyne.tracker.domain.Title;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * SQL queries Used by JDBI to query <code>Title</code> entities.
 */
@RegisterMapper(TitleMapper.class)
public interface TitleDAO {

    @SqlQuery("select * from title")
    List<Title> getAllTitles();

}
