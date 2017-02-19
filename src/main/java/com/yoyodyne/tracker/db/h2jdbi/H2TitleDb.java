package com.yoyodyne.tracker.db.h2jdbi;

import com.yoyodyne.tracker.db.h2jdbi.dao.TitleDAO;
import com.yoyodyne.tracker.db.TitleFacade;
import com.yoyodyne.tracker.domain.Title;
import org.skife.jdbi.v2.DBI;
// import org.skife.jdbi.v2.Handle; //killme
// import java.sql.ResultSet; //killme
import java.sql.SQLException;
import java.util.List;

/**
 * The concrete instantiation of <code>TitleFacade</code> for H2 databases
 * accessed via JDBI.
 */
public class H2TitleDb implements TitleFacade {

    private final DBI dbi;
    private final TitleDAO dao;

    /**
     * Never allow default construction.
     */
    private H2TitleDb () {
	throw new UnsupportedOperationException( "All H2TitleDb instances require a DBI." );
    }
    
    /**
     * Use the given <code>DBI</code> to access Title entities.
     *
     * @param dbi the <code>DBI</code> to be used.
     * @param dao the <code>TitleDBI</code> to be used.
     */
    public H2TitleDb (DBI dbi, TitleDAO dao) {
	this.dbi = dbi;
	this.dao = dao;
    }

    @Override
    public List<Title> getAllTitles (AutoCloseable resources) throws SQLException {
	return this.dao.getAllTitles();
    }

}
