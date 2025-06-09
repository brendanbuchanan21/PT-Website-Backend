package com.brendanbuchanan.pt_website_backend.repository;
import com.brendanbuchanan.pt_website_backend.model.HeroSectionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroSectionRepository extends JpaRepository<HeroSectionModel, Long> {
    HeroSectionModel findTopByOrderByIdDesc();
}
