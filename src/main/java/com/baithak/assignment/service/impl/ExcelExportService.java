package com.baithak.assignment.service.impl;

import com.baithak.assignment.model.PersonAssignment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelExportService {

    public ByteArrayInputStream exportAssignmentsToExcel(List<PersonAssignment> assignments) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("History");

            // 🔹 Title Row
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("श्रीबैठक समिती - खालापूर");
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 14);
            titleStyle.setFont(titleFont);
            titleCell.setCellStyle(titleStyle);

            // 🔹 Header Row
            Row headerRow = sheet.createRow(1);
            String[] headers = {"सदस्याचे नाव", "तारीख", "गाव"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle headerStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);
                cell.setCellStyle(headerStyle);
            }

            // 🔹 Group by Person
            Map<String, List<PersonAssignment>> groupedByPerson =
                    assignments.stream().collect(Collectors.groupingBy(pa -> pa.getPerson().getName()));

            int rowIdx = 2;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            for (Map.Entry<String, List<PersonAssignment>> entry : groupedByPerson.entrySet()) {
                String personName = entry.getKey();
                for (PersonAssignment pa : entry.getValue()) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(personName);
                    row.createCell(1).setCellValue(pa.getMeetingDate().format(dateFormatter));
                    row.createCell(2).setCellValue(pa.getPlace().getName());
                }
            }

            // 🔹 Autosize columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}