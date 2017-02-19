package com.yoyodyne.tracker.db.h2jdbi.dao;

import com.yoyodyne.tracker.db.jdbi.AchievementMapper;
import com.yoyodyne.tracker.domain.Achievement;
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
 * SQL queries Used by JDBI to query <code>Achievement</code> entities.
 */
@RegisterMapper(AchievementMapper.class)
public interface AchievementDAO {

    @SqlUpdate("insert into achievement (achievement_id "
	       + ", title_id"
	       + ", name"
	       + ", description"
	       + ", sort"
	       + ") values (:achievementId"
	       + ", :titleId"
	       + ", :name"
	       + ", :description"
	       + ", :sort)")
    void addAchievement (@BindBean Achievement entity);

    @SqlQuery("select * from achievement where achievement_id = :entityId")
    Achievement getAchievement (@Bind("entityId") String entityIdStr);
    
    @SqlQuery("select * from achievement where title_id = :titleId order by sort ASC")
    List<Achievement> getAchievementsForTitle (@Bind("titleId") String titleIdStr);
    
}
