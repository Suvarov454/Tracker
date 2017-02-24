package com.yoyodyne.tracker.db.h2jdbi;

import com.yoyodyne.tracker.domain.Payment;
import com.yoyodyne.tracker.domain.Subscription;
import java.time.LocalDate;
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

    /**
     * Return the date that is the given duration (in days) after the given
     * start date.
     *
     * @param startDate the <code>Date</code> basis of the action
     * @param duration the <code>long</code> number of days to add to the basis
     *   date.
     * @return the <code>Date</code> that is <code>duration</code> days after
     *   the start date.
     */
    public static Date addDays (Date startDate, long duration) {
	LocalDate basis = LocalDate.of( startDate.getYear() + 1900,
					startDate.getMonth() + 1,
					startDate.getDate() );
	basis = basis.plusDays( duration );
	return new Date( basis.getYear() - 1900,
			 basis.getMonthValue() - 1,
			 basis.getDayOfMonth() );
    }

    /**
     * Get the beginning of the current day.
     *
     * @return the <code>Date</code> instance for today.
     */
    public static Date getToday () {
	// Return the begining of the day today
	Date today = new Date();
	today.setHours( 0 );
	today.setMinutes( 0 );
	today.setSeconds( 0 );
	return today;
    }

    /**
     * Get the expiration date basis for extensions for the given player an
     * game title.  This date will be the expiration date for the player's
     * current subscription, or today if there is no subscription or if the
     * subscription has lapsed.
     *
     * @param expirationDate the <code>Date</code> the current subscription
     *   expires on.
     * @return the <code>Date</code> basis of the expiration of the subscription.
     *   This value will never be <code>null</code>.
     */
    protected Date getExpirationBasis (Date expirationDate) {
	// If the date is null or in the past, today is the basis.
	Date expirationBasis = this.getToday();

	// If the expiration date is after today, it becomes the new basis.
	if (expirationDate != null && expirationDate.after( expirationBasis )) {
	    expirationBasis = expirationDate;
	}

	return expirationBasis;
    }

    /**
     * Process the payment, update the player's subcription (create it if
     * necessary), and return the updated subscription.
     *
     * @param handle an open <code>Handle</code> to use when accessing tables.
     * @param payment the <code>Payment</code> to be processed
     * @return the new expiration <code>Date</code> of the subscription of the
     *   payment's player for the given game title.
     */
    public Date processPayment (Handle handle, Payment payment) {
	// We'll use the player ID and title ID several times.
	final UUID playerId = payment.getPlayerId();
	final UUID titleId = payment.getTitleId();

	// Get the expiration date for the player's current subscription.
	// N.B. this will be null if the payment should open a new subscription.
	Date currentExpiration = this.database.getExpirationDate( handle,
								  playerId,
								  titleId );

	// Get the basis for the updated expiration date.
	Date expirationBasis = this.getExpirationBasis( currentExpiration );

	// Calculate the new expiration date for the player's subscription.
	Date expirationDate = this.addDays( expirationBasis,
					    payment.getExtensionDuration() );

	// Set the payment's values from the calculated values and store the
	// payment record before the subscription.
	payment.setExpirationBasis( expirationBasis );
	payment.setExpirationDate( expirationDate );
	this.database.addPayment( handle, payment );

	// Is the player subscribed to the game? Expired subscriptions count.
	if (currentExpiration == null) {
	    // Nope. Add a subscription to the game.
	    // Start the player at level 1.
	    Subscription subscription = new Subscription( UUID.randomUUID(),
							  playerId,
							  titleId,
							  expirationDate,
							  Long.valueOf( 1 ),
							  null );
	    this.database.addSubscription( handle,
					   subscription );
	}
	else {
	    // Yup. Update the subscription's expiration date.
	    this.database.setExpirationDate( handle,
					     playerId,
					     titleId,
					     expirationDate );
	}
	return expirationDate;
    }

}
