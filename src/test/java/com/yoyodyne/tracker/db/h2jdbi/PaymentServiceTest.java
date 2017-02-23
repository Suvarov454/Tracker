package com.yoyodyne.tracker.db.h2jdbi;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import com.yoyodyne.tracker.domain.Payment;
import com.yoyodyne.tracker.domain.Subscription;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;
import org.skife.jdbi.v2.Handle;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PaymentServiceTest {

    public static final Long EXTENSION_DURATION = Long.valueOf( 90 );
    private H2PaymentAndSubscriptionDb database;
    private Handle handle;
    private UUID playerId;
    private UUID titleId;
    private Date expiredDate;
    private Date basisDate;
    private Date expectedDate;

    /**
     * Get our test subject.
     * <p/>
     * In case we ever extend the PaymentService class or the behavior of our
     * subject during testing, allow sub-classes to call (and override) this
     * method.
     *
     * @param database the <code>H2PaymentAndSubscriptionDb</code> instance
     *   the subject should use for its database interactions.
     * @return the <code>PaymentService</code> instance that will be subjected
     *   to testing.
     */
    protected PaymentService getSubject (H2PaymentAndSubscriptionDb database) {
	return new PaymentService( database );
    }

    /**
     * Provide a mock payment instance. The given values will be returned for:
     * <ul>
     * <li>getPlayerId() : playerId</li>
     * <li>getTitleId() : titleId</li>
     * <li>getExtensionDuration() : EXTENSION_DURATION</li>
     * </ul>
     * <p/>
     * In case we ever extend the PaymentService class or the behavior of our
     * subject during testing, allow sub-classes to call (and override) this
     * method.
     *
     * @return the mock <code>Payment</code> object
     */
    protected Payment getMockPayment () {
	Payment payment = mock( Payment.class );
	doReturn( playerId )
	    .when( payment )
	    .getPlayerId();
	doReturn( titleId )
	    .when( payment )
	    .getTitleId();
	doReturn( EXTENSION_DURATION )
	    .when( payment )
	    .getExtensionDuration();
	return payment;
    }
    
    @BeforeTest
    public void setCommonObjects () {
	handle = mock( Handle.class );
	database = mock( H2PaymentAndSubscriptionDb.class );
	playerId = UUID.randomUUID();
	titleId = UUID.randomUUID();

	// The expired date is in the past
	// N.B. years are since 1900 (117 = 2017-1900) and months start
	//   at zero (April = 3)
	expiredDate = new Date( 117, 0, 1 );

	// The basis should be a point in the future.
	LocalDateTime basisTimestamp = LocalDateTime.now().plusMonths( 3 );
	basisDate = new Date( basisTimestamp.getYear() - 1900,
			      basisTimestamp.getMonthValue() - 1,
			      basisTimestamp.getDayOfMonth() );
	LocalDateTime expectedTimestamp =
	    basisTimestamp.plusDays( EXTENSION_DURATION.longValue() );
	expectedDate = new Date( expectedTimestamp.getYear() - 1900,
				 expectedTimestamp.getMonthValue() - 1,
				 expectedTimestamp.getDayOfMonth() );
    }

    @Test
    public void getTodayTest () {
	// Today is the current Date with hours, minutes and seconds set to zero.
	Date expected = new Date();
	expected.setHours( 0 );
	expected.setMinutes( 0 );
	expected.setSeconds( 0 );
	assertEquals( PaymentService.getToday(), expected );
    }

    @Test
    public void addDaysTest () {
	// Adding days to a date should produce expected results.
	// 90 days after 2017-01-01 is 2017-04-01
	// N.B. years are since 1900 (117 = 2017-1900) and months start
	//   at zero (April = 3)
	Date startDate = new Date( 117, 0, 1 ); 
	Date expectedDate = new Date( 117, 3, 1 );
	Date actualDate = PaymentService.addDays( startDate,
						  EXTENSION_DURATION );
	assertEquals( actualDate, expectedDate );
    }
    
    @Test
    public void noSubscriptionTest () {
	// When the player has no subscription for the given game title,
	// the basis for expiration basis is the current date.
	doReturn( null )
	    .when( database )
	    .getExpirationDate( same( handle ),
				same( playerId ),
				same( titleId ) );
	Date expected = PaymentService.getToday();
	PaymentService subject = getSubject( database );

	assertEquals( subject.getExpirationBasis( handle, playerId, titleId ),
		      expected );
    }

    @Test
    public void foundOldSubscriptionTest () {
	// When the player has an expired subscription for the given game title,
	// the basis for expiration basis is the current date.
	doReturn( expiredDate )
	    .when( database )
	    .getExpirationDate( same( handle ),
				same( playerId ),
				same( titleId ) );
	Date expected = PaymentService.getToday();
	PaymentService subject = getSubject( database );

	assertEquals( subject.getExpirationBasis( handle, playerId, titleId ),
		      expected );
    }

    @Test
    public void foundActiveSubscriptionTest () {
	// When the player has an active subscription for the given game title,
	// the basis for expiration basis is the expiration date.
	doReturn( basisDate )
	    .when( database )
	    .getExpirationDate( same( handle ),
				same( playerId ),
				same( titleId ) );
	Date expected = basisDate;
	PaymentService subject = getSubject( database );

	assertEquals( subject.getExpirationBasis( handle, playerId, titleId ),
		      expected );
    }

    @Test
    public void methodOrderActiveSubscriptionTest () {
	// Test the order methods are called during payment processing.
	// The service has to first check for an active subscription.
	// It must insert a payment record next (with the right values).
	// The first payment should start the subscription.
	Payment payment = getMockPayment();
	doReturn( basisDate )
	    .when( database )
	    .getExpirationDate( same( handle ),
				same( playerId ),
				same( titleId ) );
	PaymentService subject = getSubject( database );

	subject.processPayment( handle, payment );

	// Confirm that the database methods were called in the correct order.
	InOrder inOrder = inOrder( database );
	inOrder.verify( database )
	    .getExpirationDate( same( handle ),
				same( playerId ),
				same( titleId ) );
	inOrder.verify( database )
	    .addPayment( same( handle ),
			 same( payment ) );
	inOrder.verify( database )
	    .setExpirationDate( same( handle ),
				same( playerId ),
				same( titleId ),
				same( expectedDate ) );

	// Verify that the correct values were assigned to the payment
	verify( payment ).setExpirationBasis( same( basisDate ) );
	verify( payment ).setExpirationDate( eq( expectedDate ) );
    }

    @Test
    public void methodOrderNoSubscriptionTest () {
	// Test the order methods are called during payment processing.
	// The service has to first check for an active subscription.
	// It must insert a payment record next (with the right values).
	// The first payment should start the subscription.
	Payment payment = getMockPayment();
	doReturn( null )
	    .when( database )
	    .getExpirationDate( same( handle ),
				same( playerId ),
				same( titleId ) );
	PaymentService subject = getSubject( database );

	subject.processPayment( handle, payment );
	final UUID fplayerId = playerId;
	final UUID ftitleId = titleId;
	final Date expected = PaymentService.addDays( PaymentService.getToday(),
						      EXTENSION_DURATION );

	// Confirm that the database methods were called in the correct order.
	InOrder inOrder = inOrder( database );
	inOrder.verify( database )
	    .getExpirationDate( same( handle ),
				same( playerId ),
				same( titleId ) );
	inOrder.verify( database )
	    .addPayment( same( handle ),
			 same( payment ) );
	// PLEASE NOTE : I expect reference equality in the tests below.
	// ArgumentMatcher<Subscription> matches =
	//     new ArgumentMatcher<Subscription>() {
	// 	public boolean matches (Subscription sub) {
	// 	    return (playerId == sub.getPlayerId() &&
	// 		    titleId == sub.getTitleId() &&
	// 		    expected == sub.getExpirationDate() &&
	// 		    sub.getLevel() != null &&
	// 		    sub.getLevel().longValue() == 0);
	// 	}
	// 	public String toString () {
	// 	    return "New subcription with valid values";
	// 	}
	//     };
	// inOrder.verify( database )
	//     .addSubscription( same( handle ),
	// 		      argThat( matches ) );
	inOrder.verify( database )
	    .addSubscription( same( handle ),
			      argThat( sub -> // Java 8 lambda FTW!
				       (playerId == sub.getPlayerId() &&
					titleId == sub.getTitleId() &&
					expected == sub.getExpirationDate() &&
					sub.getLevel() != null &&
					sub.getLevel().longValue() == 0) ) );

	// Verify that the correct values were assigned to the payment
	verify( payment ).setExpirationBasis( eq( PaymentService.getToday() ) );
	verify( payment ).setExpirationDate( eq( expected ) );
    }

}
