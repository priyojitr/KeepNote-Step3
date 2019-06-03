package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private final SessionFactory sessionFactory;

	public CategoryDAOImpl(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(final Category category) {
		final Session session = this.sessionFactory.getCurrentSession();
		session.save(category);
		session.flush();
		return Boolean.TRUE;

	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(final int categoryId) {
		final Session session = this.sessionFactory.getCurrentSession();
		boolean flag = Boolean.TRUE;
		try {
			session.delete(this.getCategoryById(categoryId));
			session.flush();
		} catch (final CategoryNotFoundException ex) {
			flag = Boolean.FALSE;
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return flag;

	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(final Category category) {
		final Session session=this.sessionFactory.getCurrentSession();
		boolean flag=Boolean.TRUE;
		try {
			this.getCategoryById(category.getCategoryId());
			session.clear();
			session.update(category);
			session.flush();
		}catch (final CategoryNotFoundException ex) {
			flag=Boolean.FALSE;
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return flag;

	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(final int categoryId) throws CategoryNotFoundException {
		final Session session=this.sessionFactory.getCurrentSession();
		final Category category=session.get(Category.class, categoryId);
		session.flush();
		if(null==category) {
			throw new CategoryNotFoundException("category does not exist with specified id");
		}
		return category;

	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(final String userId) {
		final String hql = "From Category category where categoryCreatedBy = :userId";
		final Session session = this.sessionFactory.getCurrentSession();
		final Query<Category> qry = session.createQuery(hql).setParameter("userId", userId);
		return qry.getResultList();

	}

}
