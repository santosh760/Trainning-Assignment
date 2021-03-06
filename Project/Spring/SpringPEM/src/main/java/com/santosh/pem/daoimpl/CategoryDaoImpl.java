package com.santosh.pem.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.santosh.pem.dao.CategoryDAO;
import com.santosh.pem.domain.Category;
import com.santosh.pem.domain.User;
import com.santosh.pem.rowmapper.CustomCategoryRowMapper;
import com.santosh.pem.rowmapper.CustomUserRowMapper;

@Repository("CategoryDao")
public class CategoryDaoImpl implements CategoryDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int addCategory(Category category) {
		String sql = "INSERT INTO category(categoryName,userId) VALUES(?,?)";
		
		
		int result=jdbcTemplate.update(sql, new Object[] {category.getName(),category.getUserId()});
		
		return result;
	}

	public List<Category> categoryList(Integer userId) {
		String sql = "select * from category where userId = ? ";
		List<Category> listCategory=new ArrayList<Category>();
		listCategory=jdbcTemplate.query(sql, new CustomCategoryRowMapper(),userId);
	
	return listCategory;
	}

}
