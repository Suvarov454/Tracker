package com.yoyodyne.tracker.db.h2jdbi.dao;

import com.yoyodyne.tracker.db.jdbi.mapper.PaymentMapper;
import com.yoyodyne.tracker.domain.Payment;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import java.util.List;
import java.util.UUID;

/**
 * SQL queries Used by JDBI to query <code>Payment</code> entities.
 */
@RegisterMapper(PaymentMapper.class)
public interface PaymentDAO {

    @SqlQuery("select * from payment where title_id = :titleId")
    List<Payment> getPaymentsForTitle (@Bind("titleId") String titleIdStr);

    @SqlQuery("select * from payment where player_id = :playerId and title_id = :titleId")
    List<Payment> getPaymentsForPlayerForTitle (@Bind("playerId") String playerIdStr,
						@Bind("titleId") String titleIdStr);
    
}
