package com.oop.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oop.project.model.ChinaData;

public interface ChinaDataRepository extends JpaRepository<ChinaData, Long> {
	List<ChinaData> findByCategory(String category);
	List<ChinaData> findByType(String type);
	List<ChinaData> findByTypeAndCategory(String type, String category);
	List<ChinaData> findByTypeAndCategoryAndYear(String type, String category, int year);

}
