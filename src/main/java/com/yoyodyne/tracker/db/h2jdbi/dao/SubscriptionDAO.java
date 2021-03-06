package com.yoyodyne.tracker.db.h2jdbi.dao;

import com.yoyodyne.tracker.db.jdbi.mapper.SubscriptionMapper;
import com.yoyodyne.tracker.domain.Subscription;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import java.util.List;
import java.util.UUID;

/**
 * SQL queries Used by JDBI to query <code>Subscription</code> entities.
 */
@RegisterMapper(SubscriptionMapper.class)
public interface SubscriptionDAO {

    @SqlQuery("select sub.*, tit.name " +
              "from subscription sub " +
              "inner join title tit " +
              "on sub.title_id = tit.title_id " +
              "where sub.title_id = :titleId")
    List<Subscription> getSubscriptionsForTitle (@Bind("titleId") String titleIdStr);

    @SqlQuery("select sub.*, tit.name " +
              "from subscription sub " +
              "inner join title tit " +
              "on sub.title_id = tit.title_id " +
              "where player_id = :playerId")
    List<Subscription> getSubscriptionsForPlayer (@Bind("playerId") String playerId);
    
}
