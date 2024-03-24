package com.f_log.flog.s3bucket.repository;

import com.f_log.flog.s3bucket.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
