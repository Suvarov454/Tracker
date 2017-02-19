package com.yoyodyne.tracker.db;

import com.yoyodyne.tracker.domain.Achievement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Separate the database-specific interactions for achievement entities from the
 * accessors that the system's resources will use.
 */
public interface AchievementFacade {

    /**
     * Add an achievement to a game title.  Please note, the ID of the passed
     * in achievement will be ignored and a new value will be generated.
     *
     * @param resources the <code>AutoCloseable</code> object that contains all
     *   resources used during database interactions.
     * @param input the new <code>Achievement</code> to be added
     * @return the added </code>Achievement</code> with the new ID; this value will
     *   never be <code>null</code>.
     */
    public Achievement addAchievement (AutoCloseable resources, Achievement input) throws SQLException;

    /**
     * Get the achievement details for the given ID.
     *
     * @param resources the <code>AutoCloseable</code> object that contains all
     *   resources used during database interactions.
     * @param achievementId the <code>UUID</code> ID of the achievements whose
     *   details are needed.
     * @return the </code>Achievement</code>; this value will never be
     *   <code>null</code>; and exception will the thrown instead.
     */
    public Achievement getAchievement (AutoCloseable resources, UUID achievementId) throws SQLException;

    /**
     * Get all achievements for the given game title.
     *
     * @param resources the <code>AutoCloseable</code> object that contains all
     *   resources used during database interactions.
     * @param titleId the <code>UUID</code> ID of the title whose achievements are needed.
     * @return a <code>List</code> of </code>Achievement</code>s; this value will
     *   never be <code>null</code> but may be empty.
     */
    public List<Achievement> getAchievementsForTitle (AutoCloseable resources, UUID titleId) throws SQLException;
    
}
