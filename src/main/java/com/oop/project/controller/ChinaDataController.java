package com.oop.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oop.project.model.ChinaData;
import com.oop.project.repository.ChinaDataRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController

@RequestMapping("/api")
public class ChinaDataController {

	@Autowired
	ChinaDataRepository chinaDataRepository;

	@GetMapping("/chinaData")
	public ResponseEntity<List<ChinaData>> getAllChinaData(@RequestParam(required = false) String type, String category,
			String year) {
		try {
			List<ChinaData> chinaData = new ArrayList<ChinaData>();

			if (type == null && category == null && year == null)
				chinaDataRepository.findAll().forEach(chinaData::add);
			else if (category == null && year == null)
				chinaDataRepository.findByType(type).forEach(chinaData::add);
			else if (type == null && year == null)
				chinaDataRepository.findByCategory(category).forEach(chinaData::add);
			else if (year == null)
				chinaDataRepository.findByTypeAndCategory(type, category).forEach(chinaData::add);
			else
				chinaDataRepository.findByTypeAndCategoryAndYear(type, category,Integer.parseInt(year)).forEach(chinaData::add);

			if (chinaData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(chinaData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
	@PostMapping("/chinaData")
	public ResponseEntity<ChinaData> createExport(@RequestBody ChinaData chinaData) {
		try {
			ChinaData _chinaData = chinaDataRepository
					.save(new ChinaData(chinaData.getType(), chinaData.getCategory(), chinaData.getYear(),
							chinaData.getMonth(), chinaData.getQuantity_unit(), chinaData.getRaw_quantity(),
							chinaData.getFinal_quantity(), chinaData.getValue(), chinaData.getPrice()));
			return new ResponseEntity<>(_chinaData, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
