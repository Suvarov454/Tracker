package com.yoyodyne.tracker.db.h2jdbi.dao;

import com.yoyodyne.tracker.db.jdbi.mapper.TitleMapper;
import com.yoyodyne.tracker.domain.Title;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
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

    @SqlUpdate("insert into title (title_id, name) values (:titleId, :name)")
    void addTitle (@BindBean Title entity);

    @SqlQuery("select * from title")
    List<Title> getAllTitles ();
    
}
