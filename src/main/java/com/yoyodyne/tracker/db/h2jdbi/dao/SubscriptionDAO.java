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

    @SqlQuery("select * from subscription where title_id = :titleId")
    List<Subscription> getSubscriptionsForTitle (@Bind("titleId") String titleIdStr);

    @SqlQuery("select * from subscription where player_id = :playerId")
    List<Subscription> getSubscriptionsForPlayer (@Bind("playerId") String playerId);
    
}
