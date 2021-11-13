// package com.oop.project;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.text.DateFormat;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Date;
// import java.util.Hashtable;
// import java.util.Iterator;
// import java.util.List;

// import com.oop.project.model.NewZealandData;
// import com.oop.project.repository.NewZealandDataRepository;

// import org.apache.commons.io.FileUtils;
// import org.apache.http.HttpEntity;
// import org.apache.http.client.methods.CloseableHttpResponse;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Workbook;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;


// @Component
// public class ScheduledTask {
//     @Autowired
//     private NewZealandDataRepository NewZealandDataRepository;

//     @Scheduled(cron = "0 0 0 1 1/1 *")
//     @Bean
//     public void extract_data()  {
//         System.out.println("Starting scheduled monthly task: New Zealand Data Extraction");
//         downloadExcel();
//         System.out.println("Erasing old New Zealand data");

//         NewZealandDataRepository.deleteAll();
//         System.out.println("Old New Zealand data has been erased");
//         System.out.println("Extracting New Zealand data to the database. This may take a while!");

//         List<NewZealandData> listData = new ArrayList<NewZealandData>();

//         String FILE_PATH = ".\\src\\main\\java\\com\\oop\\project\\oil-data-monthly.xlsx";
//         try {
//         FileInputStream inputStream = new FileInputStream(new File(FILE_PATH));
//         Workbook workbook = new XSSFWorkbook(inputStream);
//         // Get the third sheet which is monthly data
//         Sheet firstSheet = workbook.getSheetAt(3);
//             // Iterator<Row> iterator = firstSheet.iterator();

//         // Get all time frames
//         Row timeFrame = firstSheet.getRow(8);
//         Iterator<Cell> cellIterator = timeFrame.cellIterator();

//         // Get last column
//         int lastColumnNumber = timeFrame.getLastCellNum();
//         ArrayList<Date> dateList = new ArrayList<Date>();
//         for (int i = 2; i < lastColumnNumber; i++) {
//             Cell cell = timeFrame.getCell(i);
//             Date date = cell.getDateCellValue();
//             dateList.add(date);
//         }

//         List<String> categoryNames = Arrays.asList("Supply","Indigenous Production", "From Other Sources", "Imports", "Exports","Refinery Intake", "Refinery Output");
        
//         Hashtable<String, int[]> CategoryAndRows = new Hashtable<String, int[]>();

//         CategoryAndRows.put("Supply", new int[]{10, 11});
//         CategoryAndRows.put("Imports", new int[]{16,30});
//         CategoryAndRows.put("Exports", new int[]{30, 45});
//         CategoryAndRows.put("Refinery Intake", new int[]{87,91});
//         CategoryAndRows.put("Refinery Output", new int[]{92, 102});

//         for (String categoryKey : CategoryAndRows.keySet()) {
//             System.out.println("Extracting data for " + categoryKey);
//             int start_index = CategoryAndRows.get(categoryKey)[0];
//             int end_index = CategoryAndRows.get(categoryKey)[1];
//             String productGroup = "";
//             String type = categoryKey;
//             for (int i = start_index; i < end_index; i++) {
//                 // Iterate through all the rows
//                 Row row= firstSheet.getRow(i);
//                 Cell categoryCell = row.getCell(0);
//                 // Check if the first cell is a category or sub category
//                 String categoryNameInCell = categoryCell.getStringCellValue();

//                 if (!categoryNameInCell.equals(categoryKey)) {
//                     productGroup = categoryNameInCell;
//                 }
                
//             // System.out.println(category);

//                 for (int x = 0; x < lastColumnNumber - 3; x++) {
//                     Cell cell = row.getCell(x + 2);
//                     double cellValue = cell.getNumericCellValue();

//                     DateFormat dfMonth = new SimpleDateFormat("MM");
//                     DateFormat dfYear = new SimpleDateFormat("yyyy");
//                     String reportMonth = dfMonth.format(dateList.get(x));
//                     String reportYear = dfYear.format(dateList.get(x));
//                     // System.out.println(reportMonth + reportYear + " : " + cellValue);

//                     // Add to database
//                     NewZealandData n = new NewZealandData();
//                     n.setProductGroup(productGroup);
//                     n.setType(type);
//                     n.setQuantity(cellValue);
//                     n.setMonth(Integer.parseInt(reportMonth));
//                     n.setYear(Integer.parseInt(reportYear));
//                     NewZealandDataRepository.save(n);
//                     }
//         }
//         }
//     } catch (Exception e) {
//         e.printStackTrace();
//     }
//         System.out.println("New Zealand Oil Data has been reloaded successfully");
//     }

//     public void downloadExcel() {
//         String FILE_URL = "https://www.mbie.govt.nz/assets/Data-Files/Energy/oil-data-monthly.xlsx";

//         //MacOS 
//         //String FILE_PATH = "src/main/java/com/oop/project/oil-data-monthly.xlsx";
       
//         String FILE_PATH = ".\\src\\main\\java\\com\\oop\\project\\oil-data-monthly.xlsx";
  
//         CloseableHttpClient httpClient = HttpClients.createDefault();
  
//         HttpGet httpGet = new HttpGet(FILE_URL);
//         httpGet.addHeader("User-Agent",
//               "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.11 Safari/537.36");
//         httpGet.addHeader("Referer", "https://www.google.com");
  
//         try {
//             CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
//             HttpEntity fileEntity = httpResponse.getEntity();
  
//             if (fileEntity != null) {
//                 FileUtils.copyInputStreamToFile(fileEntity.getContent(), new File(FILE_PATH));
//                 System.out.println("New Zealand data Downloaded successfully");
//             }

//         } catch (IOException e) {
//            e.printStackTrace();
//         }
//     }

// }
