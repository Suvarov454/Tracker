package com.yoyodyne.tracker.db.jdbi;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

/**
 * Provide an <code>AutoCloseable</code> object that won't open a JDBI
 * <code>Handle</code> until it is actually needed.
 */
public final class LazyHandle implements AutoCloseable {

    private final DBI dbi;
    private Handle handle;

    public LazyHandle (DBI dbi) {
	this.dbi = dbi;
	this.handle = null;
    }

    @Override
    public synchronized void close () {
	if (handle != null) {
	    handle.close();
	}
    }

    private synchronized Handle getOpenHandle () {
	if (handle == null) {
	    handle = dbi.open();
	}
	return handle;
    }

    /**
     * If the given <code>AutoCloseable</code> is a <code>LazyHandle</code>,
     * open it.  Otherwise throw an <code>IllegalArgumentException</code>.
     *
     * @param target the <code>AutoCloseable</code> that <em>should</em> be
     *   a <code>LazyHandle</code> instance.
     * @return an open <code>Handle</code>
     */
    public static Handle open (AutoCloseable target) {
	if (!(target instanceof LazyHandle)) {
	    throw new IllegalArgumentException( "Expected target to be a lazy-open JDBI handle." );
	}
	return ((LazyHandle) target).getOpenHandle();
    }

}
	    
