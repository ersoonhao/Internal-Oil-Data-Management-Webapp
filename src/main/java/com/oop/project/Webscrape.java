package com.oop.project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;
import java.time.*;

import com.oop.project.model.ChinaData;

@Component
public class Webscrape {

    public static void start() {
        int currYear = Year.now().getValue();
        String browser = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
        String[] types = { "Import", "Export" };
        String search = null;
        String response = Database.deleteAll();
        System.out.println(response);
        // array of all the relevant search words
        HashMap<String, Double> rel = new HashMap<String, Double>() {
            {
                put("Crude petroleum oils", 0.1364);
                put("Aviation kerosene", 7.880);
                put("Gasoline", 8.35);
                put("Natural gases", 1.23);
                put("Naphtha", 5.65);
                put("Diesel oil", 7.46);
            }
        };

        for (String type : types) {
            search = "Major " + type + " Commodities in Quantity and Value";
            for (int year = 2018; year <= currYear; year++) {
                System.out.println(year);
                getURLs(browser, rel, search, year, type);
            }
        }

        System.out.println("Scrape complete");
    }

    public static void getURLs(String browser, HashMap<String, Double> rel, String search, int year, String type) {
        int currYear = Year.now().getValue();
        String url;
        // previous years have the year as a suffix to the html string
        if (year == currYear) {
            url = "http://english.customs.gov.cn/statics/report/monthly.html";
        } else {
            url = "http://english.customs.gov.cn/statics/report/monthly" + year + ".html";
        }

        try {

            Document document = Jsoup.connect(url).userAgent(browser).get();

            Elements links = null;
            Element title = null;
            String titleStr;

            // array of all the urls found
            List<String> urls = new ArrayList<>();

            Elements rows = document.select("tr");
            for (Element row : rows) {
                title = row.select("td").first();
                if (title != null) {
                    titleStr = title.ownText().substring(4, title.ownText().length());
                    if (titleStr.equals(search)) {
                        links = row.select("td > a");
                        for (Element link : links) {
                            urls.add(link.attr("href"));
                        }

                    }
                }

            }

            for (String u : urls) {
                scrape(u, rel, browser, type);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void scrape(String url, HashMap<String, Double> rel, String browser, String type) {
        try {
            Document document = Jsoup.connect(url).userAgent(browser).get();

            Elements elems = null;
            String category = null;
            int qty_unit;
            double final_qty;
            int qty;
            int value;
            double price;
            double conversion;
            Boolean found;
            int count = 0;

            System.out.println("Category | Type | Year | Month | Value | Qty_unit | Qty | FinalQty | Price");

            Elements rows = document.getElementsByTag("tr");

            // get year and month from first row of table

            String date = rows.get(0).text().split(",")[1];
            String mth = date.split("\\.")[0];
            if (mth.contains("-")) {
                mth = mth.split("-")[1];
            }
            int month = Integer.parseInt(mth.trim());
            int year = Integer.parseInt(date.split("\\.")[1].trim());

            System.out.println("Year: " + year + " month: " + month);

            for (Element row : rows) {
                category = row.select("td").first().text();
                found = false;
                count = 0;
                while (!found && count < rel.size()) {
                    if (rel.containsKey(category)) {
                        elems = row.getElementsByTag("td");
                        if (elems.get(1).text().equals("T")) {
                            qty_unit = 1;
                        } else {
                            qty_unit = Integer.parseInt(elems.get(1).text().replaceAll("T", ""));
                        }

                        if (elems.get(2).text().equals("-")) {
                            qty = 0;
                        } else {
                            qty = Integer.parseInt(elems.get(2).text().replaceAll(",", ""));
                        }

                        qty *= qty_unit;

                        if (qty == 0) {
                            value = 0;
                        } else {
                            value = Integer.parseInt(elems.get(3).text().replaceAll(",", ""));
                        }
                        conversion = rel.get(category);
                        final_qty = (qty * conversion) / 1000;

                        if (final_qty == 0) {
                            price = 0;
                        } else {
                            price = value / final_qty;
                        }
                        // category = Gasoline, Diesel, etc...
                        // type = Import/Export

                        ChinaData chinaData = new ChinaData(category, type, year, month, qty_unit, qty, final_qty,
                                value, price);

                        System.out.println(String.format("%s | %s | %s | %s | %s | %s | %s | %s | %s", category, type,
                                year, month, value, qty_unit, qty, final_qty, price));

                        String response = Database.insert(chinaData);
                        found = true;
                        System.out.println(response);
                    }
                    count++;

                }

            }
            System.out.println();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Scheduled(cron = "${webscrape.cron.monthly}")
    public static void monthlyTask() {
        start();
    }

}