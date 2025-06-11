package com.brendanbuchanan.pt_website_backend.controller;
import com.brendanbuchanan.pt_website_backend.model.HeroSectionModel;
import com.brendanbuchanan.pt_website_backend.repository.HeroSectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestController
@RequestMapping("api/hero-section")
public class HeroSectionController {

   private final HeroSectionRepository heroSectionRepository;


    // Constructor for constructor injection
    public HeroSectionController(HeroSectionRepository heroSectionRepository) {
        this.heroSectionRepository = heroSectionRepository;
    }


    // POST save new heading text
    @PostMapping
    public HeroSectionModel saveHeroSection(@RequestBody HeroSectionModel newHeroSection) {
        return heroSectionRepository.save(newHeroSection);
    }

    // update the hero section
    @PatchMapping
    public HeroSectionModel updateHeroSection(@RequestBody Map<String, String> updates) {
        HeroSectionModel existing = heroSectionRepository.findTopByOrderByIdDesc();

        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero section not found");
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "heading" -> existing.setHeading(value);
                case "subText1" -> existing.setSubText1(value);
                case "subText2" -> existing.setSubText2(value);
            }
        });
        return heroSectionRepository.save(existing);
    }

    @GetMapping
    public HeroSectionModel getHeroSection() {
        HeroSectionModel latest = heroSectionRepository.findTopByOrderByIdDesc();
        if (latest == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero section not found");
        }
        return latest;
    }


}
