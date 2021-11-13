package com.oop.project.repository;
import java.util.List;

import com.oop.project.model.NewZealandData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewZealandDataRepository extends JpaRepository<NewZealandData, Integer> {
	List<NewZealandData> findByType(String type);
	List<NewZealandData> findByProductGroup(String productGroup);
	List<NewZealandData> findByTypeAndProductGroup(String type, String productGroup);
	List<NewZealandData> findByTypeAndProductGroupAndYear(String type, String productGroup, int year);
}
