package com.f_log.flog.csvdata.csvprocessing;

import com.f_log.flog.csvdata.service.CsvImportService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

// 데이터 삽입 원하지 않으면 @Component 주석처리로 CsvImportRunner 로드하지 않으므로 실행되지 않음.
@Component
public class CsvImportRunner implements ApplicationRunner {
    private final CsvImportService csvImportService;

    public CsvImportRunner(CsvImportService csvImportService) {
        this.csvImportService = csvImportService;
    }

    @Override
    @Async
    public void run(ApplicationArguments args) throws Exception {
        String filePath = "/app/nutritiondata.csv";
        MultipartFile multipartFile = createMultipartFile(filePath);
        csvImportService.importCsv(multipartFile);
    }

    private MultipartFile createMultipartFile(String filePath) throws IOException {
        FileSystemResource resource = new FileSystemResource(filePath);
        byte[] fileBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        String filename = resource.getFilename();
        return new InMemoryMultipartFile(fileBytes, filename);
    }

    private static class InMemoryMultipartFile implements MultipartFile {
        private final byte[] content;
        private final String filename;

        public InMemoryMultipartFile(byte[] content, String filename) {
            this.content = content;
            this.filename = filename;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return this.filename;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return this.content.length;
        }

        @Override
        public byte[] getBytes() {
            return this.content;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(this.content);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            Files.write(dest.toPath(), this.content);
        }
    }
}
