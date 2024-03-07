package com.f_log.flog.csvdata.controller;

import com.f_log.flog.csvdata.service.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/csv")
public class CsvController {
    @Autowired
    private CsvImportService csvImportService;

    @PostMapping("/import")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importCsv(file);
            return ResponseEntity.ok("CSV import successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("CSV import failed: " + e.getMessage());
        }
    }
}
