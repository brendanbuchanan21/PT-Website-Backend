package com.brendanbuchanan.pt_website_backend.repository;

import com.brendanbuchanan.pt_website_backend.model.BlogModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogModel, Long> {
}
