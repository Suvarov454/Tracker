package com.yoyodyne.tracker.db.h2jdbi;

import com.yoyodyne.tracker.db.h2jdbi.dao.TitleDAO;
import com.yoyodyne.tracker.db.TitleFacade;
import com.yoyodyne.tracker.domain.Title;
import org.skife.jdbi.v2.DBI;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * The concrete instantiation of <code>TitleFacade</code> for H2 databases
 * accessed via JDBI.
 */
public class H2TitleDb implements TitleFacade {

    private final DBI dbi;
    private final TitleDAO dao;
    
    /**
     * Use the given <code>DBI</code> to access Title entities.
     *
     * @param dbi the <code>DBI</code> to be used.
     * @param dao the <code>TitleDAO</code> to be used.
     */
    public H2TitleDb (DBI dbi, TitleDAO dao) {
	this.dbi = dbi;
	this.dao = dao;
    }

    @Override
    public Title addTitle (AutoCloseable resources, Title input) throws SQLException {
	// Use a random UUID instead of the input one.
	Title newTitle = new Title( UUID.randomUUID(), input.getName() );
	this.dao.addTitle( newTitle );
	return newTitle;
    }

    @Override
    public List<Title> getAllTitles (AutoCloseable resources) throws SQLException {
	return this.dao.getAllTitles();
    }

}
