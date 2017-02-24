package com.yoyodyne.tracker.db.h2jdbi;

import com.yoyodyne.tracker.db.h2jdbi.dao.AchievementDAO;
import com.yoyodyne.tracker.db.AchievementFacade;
import com.yoyodyne.tracker.domain.Achievement;
import org.skife.jdbi.v2.DBI;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * The concrete instantiation of <code>AchievementFacade</code> for H2 databases
 * accessed via JDBI.
 */
public class H2AchievementDb implements AchievementFacade {

    private final DBI dbi;
    private final AchievementDAO dao;
    
    /**
     * Use the given <code>DBI</code> to access Achievement entities.
     *
     * @param dbi the <code>DBI</code> to be used.
     * @param dao the <code>AchievementDAO</code> to be used.
     */
    public H2AchievementDb (DBI dbi, AchievementDAO dao) {
        this.dbi = dbi;
        this.dao = dao;
    }

    @Override
    public Achievement addAchievement (AutoCloseable resources, Achievement input) throws SQLException {
        // // We need a DBI handle, which we *should* be available in resources.
        // Handle handle = LazyHandle.open( resources ); //killme

        // Use a random UUID instead of the input one.
        Achievement newAchievement = new Achievement( UUID.randomUUID(),
                                                      input.getTitleId(),
                                                      input.getName(),
                                                      input.getDescription(),
                                                      input.getSort() );
        this.dao.addAchievement( newAchievement );
        return newAchievement;
    }

    @Override
    public Achievement getAchievement (AutoCloseable resources, UUID achievementId) throws SQLException {
        return this.dao.getAchievement( achievementId.toString() );
    }

    @Override
    public List<Achievement> getAchievementsForTitle (AutoCloseable resources, UUID titleId) throws SQLException {
        return this.dao.getAchievementsForTitle( titleId.toString() );
    }

}
