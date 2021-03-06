package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryServiceImpl implements CategoryService {
	/*
	 * Autowiring should be implemented for the CategoryDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */

	private final CategoryDAO categoryDAO;

	@Autowired
	public CategoryServiceImpl(final CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	/*
	 * This method should be used to save a new category.
	 */
	public boolean createCategory(final Category category) {
		boolean flag = Boolean.FALSE;
		if (this.categoryDAO.createCategory(category)) {
			flag = Boolean.TRUE;
		}
		return flag;

	}

	/* This method should be used to delete an existing category. */
	public boolean deleteCategory(final int categoryId) {
		boolean flag = Boolean.FALSE;
		if (this.categoryDAO.deleteCategory(categoryId)) {
			flag = Boolean.TRUE;
		}
		return flag;

	}

	/*
	 * This method should be used to update a existing category.
	 */

	public Category updateCategory(final Category category, final int id) throws CategoryNotFoundException {
		this.categoryDAO.updateCategory(category);
		final Category updCategory = this.getCategoryById(id);
		if (null == updCategory) {
			throw new CategoryNotFoundException("category does not exist with specified id");
		}
		return category;

	}

	/*
	 * This method should be used to get a category by categoryId.
	 */
	public Category getCategoryById(final int categoryId) throws CategoryNotFoundException {
		final Category category = this.categoryDAO.getCategoryById(categoryId);
		if (null == category) {
			throw new CategoryNotFoundException("category does not exist with specified id");
		}
		return category;

	}

	/*
	 * This method should be used to get a category by userId.
	 */

	public List<Category> getAllCategoryByUserId(final String userId) {
		return this.categoryDAO.getAllCategoryByUserId(userId);
	}

}
