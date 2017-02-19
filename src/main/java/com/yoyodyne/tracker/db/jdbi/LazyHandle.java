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

    public synchronized Handle open () {
	if (handle == null) {
	    handle = dbi.open();
	}
	return handle;
    }

}
	    
