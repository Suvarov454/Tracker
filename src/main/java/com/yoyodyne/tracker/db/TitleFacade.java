package com.yoyodyne.tracker.db;

import com.yoyodyne.tracker.domain.Title;
import java.sql.SQLException;
import java.util.List;

/**
 * Separate the database-specific interactions for title entities from the
 * accessors that the system's resources will use.
 */
public interface TitleFacade {

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
