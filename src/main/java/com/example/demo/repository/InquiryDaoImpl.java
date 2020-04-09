package com.example.demo.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Inquiry;

/*
 * Add an annotation here
 */
@Repository
public class InquiryDaoImpl implements InquiryDao{
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public InquiryDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertInquiry(Inquiry inquiry) {
		//hands-on
		jdbcTemplate.update("INSERT INTO inquiry(name, email, contents, created) VALUES(?, ?, ?, ?)",
				inquiry.getName(), inquiry.getEmail(), inquiry.getContents(), inquiry.getCreated());
	}
	
//  This method is used in the latter chapter
//	@Override
//	public int updateInquiry(Inquiry inquiry) {
//		return jdbcTemplate.update("UPDATE inquiry SET name = ?, email = ?,contents = ? WHERE id = ?",
//				 inquiry.getName(), inquiry.getEmail(), inquiry.getContents(), inquiry.getId() );	
//	}

	@Override
	public List<Inquiry> getAll() {
		
		//make SQL
		String sql = "SELECT id , name ,email, contents, created FROM inquiry";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		
		List<Inquiry> list = new ArrayList<Inquiry>();

		//Set the data form database into Inquiry instance
		for(Map<String, Object>result: resultList) {
			Inquiry inquiry = new Inquiry();
			inquiry.setId((int)result.get("id"));
			inquiry.setName((String)result.get("name"));
			inquiry.setEmail((String)result.get("Email"));
			inquiry.setContents((String)result.get("contents"));
			inquiry.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			list.add(inquiry);
		}
		return list;
	}
}
