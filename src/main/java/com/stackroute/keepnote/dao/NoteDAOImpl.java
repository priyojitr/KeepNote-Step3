package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */

@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private final SessionFactory sessionFactory;

	@Autowired
	public NoteDAOImpl(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new note
	 */

	public boolean createNote(final Note note) {
		final Session session=this.sessionFactory.getCurrentSession();
		session.save(note);
		session.flush();
		return Boolean.TRUE;

	}

	/*
	 * Remove an existing note
	 */

	public boolean deleteNote(final int noteId) {
		final Session session=this.sessionFactory.getCurrentSession();
		boolean flag = Boolean.TRUE;
		try {
			session.delete(this.getNoteById(noteId));
			session.flush();
		}catch (final Exception ex) {
			flag=Boolean.FALSE;
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return flag;
	}

	/*
	 * Retrieve details of all notes by userId
	 */

	public List<Note> getAllNotesByUserId(final String userId) {
		final Session session = this.sessionFactory.getCurrentSession();
		final String hql = "FROM Note note where createdBy =  :userId";
		final Query<Note> qry = session.createQuery(hql).setParameter("userId", userId);
		return qry.getResultList();

	}

	/*
	 * Retrieve details of a specific note
	 */

	public Note getNoteById(final int noteId) throws NoteNotFoundException {
		final Session session = this.sessionFactory.getCurrentSession();
		final Note note = session.get(Note.class, noteId);
		if(null==note) {
			throw new NoteNotFoundException("Note does not exist with specified id.");
		}
		session.flush();
		return note;

	}

	/*
	 * Update an existing note
	 */

	public boolean UpdateNote(final Note note) {
		final Session session = this.sessionFactory.getCurrentSession();
		boolean flag = Boolean.TRUE;
		try {
			this.getNoteById(note.getNoteId());
			session.clear();
			session.update(note);
			session.flush();
		} catch (final Exception ex) {
			flag=Boolean.FALSE;
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return flag;

	}

}
