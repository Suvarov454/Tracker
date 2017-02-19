package com.yoyodyne.tracker.db;

import com.yoyodyne.tracker.domain.Title;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Separate the database-specific interactions for title entities from the
 * accessors that the system's resources will use.
 */
public interface TitleFacade {

    /**
     * Add a game title for this studio.  Please note, the ID of the passed
     * in title will be ignored and a new value will be generated.
     *
     * @param resources the <code>AutoCloseable</code> object that contains all
     *   resources used during database interactions.
     * @param input the new <code>Title</code> to be added
     * @return the added </code>Title</code> with the new ID; this value will
     *   never be <code>null</code>.
     */
    public Title addTitle (AutoCloseable resources, Title input) throws SQLException;

    /**
     * Get all game titles for this studio.
     *
     * @param resources the <code>AutoCloseable</code> object that contains all
     *   resources used during database interactions.
     * @return a <code>List</code> of </code>Title</code>s; this value will
     *   never be <code>null</code> but may be empty.
     */
    public List<Title> getAllTitles (AutoCloseable resources) throws SQLException;
    
}
