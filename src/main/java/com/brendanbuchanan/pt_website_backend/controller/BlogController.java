package com.brendanbuchanan.pt_website_backend.controller;

import com.brendanbuchanan.pt_website_backend.gcs.GCSFileService;
import com.brendanbuchanan.pt_website_backend.model.BlogModel;
import com.brendanbuchanan.pt_website_backend.repository.BlogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogRepository blogRepository;
    private final GCSFileService gcsFileService;

    public BlogController(BlogRepository blogRepository, GCSFileService gcsFileService) {
        this.blogRepository = blogRepository;
        this.gcsFileService = gcsFileService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBlogPost(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String imageUrl = gcsFileService.uploadFile(file);

            BlogModel blog = new BlogModel(title, author, date, description, imageUrl);
            blogRepository.save(blog);

            return ResponseEntity.ok("Blog created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + "failed to create blog post");
        }
    }



}
