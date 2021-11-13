package com.oop.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.oop.project.model.NewZealandData;
import com.oop.project.repository.NewZealandDataRepository;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/NewZealand")
public class NewZealandDataController {
    @Autowired 
    private NewZealandDataRepository NewZealandDataRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewData ( @RequestParam Integer id, @RequestParam String productGroup, @RequestParam String type, @RequestParam Integer quantity,@RequestParam Integer month,
    @RequestParam Integer year) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    
    NewZealandData n = new NewZealandData();
    n.setProductGroup(productGroup);
    n.setType(type);
    n.setQuantity(quantity);
    n.setMonth(month);
    n.setYear(year);

    NewZealandDataRepository.save(n);
    return "Saved";
  }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<NewZealandData> getAllData() {
        return NewZealandDataRepository.findAll();
    }

    @GetMapping("/query")
	public ResponseEntity<List<NewZealandData>> getAllChinaData(@RequestParam(required = false) String type, String productGroup,
			String year) {
		try {
			List<NewZealandData> result = new ArrayList<NewZealandData>();

			if (type == null && productGroup == null && year == null)
                NewZealandDataRepository.findAll().forEach(result::add);
			else if (productGroup == null && year == null)
                NewZealandDataRepository.findByType(type).forEach(result::add);
			else if (type == null && year == null)
                NewZealandDataRepository.findByProductGroup(productGroup).forEach(result::add);
			else if (year == null)
                NewZealandDataRepository.findByTypeAndProductGroup(type, productGroup).forEach(result::add);
			else
            NewZealandDataRepository.findByTypeAndProductGroupAndYear(type, productGroup, Integer.parseInt(year)).forEach(result::add);

			if (result.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


    @GetMapping("/data")
    public ResponseEntity<List<NewZealandData>> getAllNewZealandData() {
        try {
            List<NewZealandData> data = new ArrayList<NewZealandData>();

            NewZealandDataRepository.findAll().forEach(data::add);

            if (data.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(path="/extract_data")
    public @ResponseBody String readDataFromExcelFile() throws IOException {
        System.out.println("Extracting New Zealand Oil Data from Excel File downloaded");
        NewZealandDataRepository.deleteAll();
        System.out.println("Old data deleted first");
        List<NewZealandData> listData = new ArrayList<NewZealandData>();

        //MacOS
        //String FILE_PATH = "src/main/java/com/oop/project/oil-data-monthly.xlsx";
      
     
        String FILE_PATH = ".\\src\\main\\java\\com\\oop\\project\\oil-data-monthly.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(FILE_PATH));
     
        Workbook workbook = new XSSFWorkbook(inputStream);
        // Get the third sheet which is monthly data
        Sheet firstSheet = workbook.getSheetAt(3);
            // Iterator<Row> iterator = firstSheet.iterator();
            
        String to_return = "New Zealand Oil Data has been reloaded successfully";

        // Get all time frames
        Row timeFrame = firstSheet.getRow(8);
        Iterator<Cell> cellIterator = timeFrame.cellIterator();
        
        // Get last column
        int lastColumnNumber = timeFrame.getLastCellNum();
        ArrayList<Date> dateList = new ArrayList<Date>();
        for (int i = 2; i < lastColumnNumber; i++) {
            Cell cell = timeFrame.getCell(i);
            Date date = cell.getDateCellValue();
            dateList.add(date);
            // LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // System.out.println(date.getTime());
        }

        List<String> categoryNames = Arrays.asList("Supply","Indigenous Production", "From Other Sources", "Imports", "Exports");
        String currentCategory = "";
        for (int i = 10; i < 44; i++) {
            // Iterate through all the rows
            Row row= firstSheet.getRow(i);
            Cell categoryCell = row.getCell(0);
            // Check if the first cell is a category or sub category
            String category = categoryCell.getStringCellValue();
            for(String str: categoryNames) {
                if(str.trim().contains(category)) {
                    currentCategory = category;
                }
            }
            // System.out.println(category);

            for (int x = 0; x < lastColumnNumber - 3; x++) {
                Cell cell = row.getCell(x + 2);
                double cellValue = cell.getNumericCellValue();

                DateFormat dfMonth = new SimpleDateFormat("MM");
                DateFormat dfYear = new SimpleDateFormat("yyyy");
                String reportMonth = dfMonth.format(dateList.get(x));
                String reportYear = dfYear.format(dateList.get(x));
                // System.out.println(reportMonth + reportYear + " : " + cellValue);

                // Add to database
                NewZealandData n = new NewZealandData();
                n.setProductGroup(currentCategory);
                n.setType(category);
                n.setQuantity(cellValue);
                n.setMonth(Integer.parseInt(reportMonth));
                n.setYear(Integer.parseInt(reportYear));
                    
                NewZealandDataRepository.save(n);
                    
                }
        
    
        }

        return to_return;


    
    }
}
