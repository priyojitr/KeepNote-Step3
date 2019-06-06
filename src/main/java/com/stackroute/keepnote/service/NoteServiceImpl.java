package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.dao.ReminderDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class NoteServiceImpl implements NoteService {

	/*
	 * Autowiring should be implemented for the NoteDAO,CategoryDAO,ReminderDAO.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	private final NoteDAO noteDAO;
	private final CategoryDAO categoryDAO;
	private final ReminderDAO reminderDAO;

	@Autowired
	public NoteServiceImpl(final NoteDAO noteDAO, final CategoryDAO categoryDAO, final ReminderDAO reminderDAO) {
		this.noteDAO = noteDAO;
		this.categoryDAO = categoryDAO;
		this.reminderDAO = reminderDAO;
	}

	/*
	 * This method should be used to save a new note.
	 */

	public boolean createNote(final Note note) throws ReminderNotFoundException, CategoryNotFoundException {
		final Reminder reminder = note.getReminder();
		final Category category = note.getCategory();
		boolean flag = Boolean.FALSE;
		
		try {
			if (null != reminder) {
				reminderDAO.getReminderById(reminder.getReminderId());
			}
			if (null != category) {
				categoryDAO.getCategoryById(category.getCategoryId());
			}
			flag = noteDAO.createNote(note);
		} catch (ReminderNotFoundException | CategoryNotFoundException ex) {
			if(ex instanceof ReminderNotFoundException) {
				throw new ReminderNotFoundException("reminder not found exception");
			}
			if(ex instanceof CategoryNotFoundException) {
				throw new CategoryNotFoundException("category not found exception");
			}
		}
		return flag;

	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(final int noteId) {
		return noteDAO.deleteNote(noteId);

	}
	/*
	 * This method should be used to get a note by userId.
	 */

	public List<Note> getAllNotesByUserId(final String userId) {
		return noteDAO.getAllNotesByUserId(userId);

	}

	/*
	 * This method should be used to get a note by noteId.
	 */
	public Note getNoteById(final int noteId) throws NoteNotFoundException {
		final Note note = noteDAO.getNoteById(noteId);
		if (null == note) {
			throw new NoteNotFoundException("note not found exception");
		}
		return note;

	}

	/*
	 * This method should be used to update a existing note.
	 */

	public Note updateNote(final Note note, final int id)
			throws ReminderNotFoundException, NoteNotFoundException, CategoryNotFoundException {
		// get existing note
		final Note oldNote = noteDAO.getNoteById(id);
		final Reminder reminder = note.getReminder();
		final Category category = note.getCategory();
		if (null == oldNote) {
			throw new NoteNotFoundException("note not found exception");
		} else {
			if (null != reminder && reminder.getReminderId() > 0) {
				try {
					this.reminderDAO.getReminderById(reminder.getReminderId());
				} catch (final Exception e) {
					throw new ReminderNotFoundException("reminder not found exception");
				}
			}
			if (null != category && category.getCategoryId() > 0) {
				try {
					this.categoryDAO.getCategoryById(category.getCategoryId());
				} catch (final Exception e) {
					throw new CategoryNotFoundException("category not found exception");
				}
			}
			this.noteDAO.UpdateNote(note);
		}
		return note;
	}

}
