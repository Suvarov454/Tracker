package com.yoyodyne.tracker.db.h2jdbi;

import com.yoyodyne.tracker.domain.Payment;
import com.yoyodyne.tracker.domain.Subscription;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;
import org.skife.jdbi.v2.Handle;

public class PaymentService {

    private final H2PaymentAndSubscriptionDb database;

    /**
     * The service only serves one database master.
     *
     * @param database the <code>H2PaymentAndSubscriptionDb</code> instance
     *   the service should use for its database interactions.
     */
    public PaymentService (H2PaymentAndSubscriptionDb database) {
	this.database = database;
    }

    public static Date addDays (Date startDate, long duration) {
	// TODO : implement me
	return null;
    }

    public static Date getToday () {
	// TODO : implement me
	return new Date();
    }

    protected Date getExpirationBasis (Handle handle, UUID playerId, UUID titleId ) {
	// TODO : implement me
	return null;
    }

    public void processPayment (Handle handle, Payment payment) {
	// TODO : implement me
    }

}
