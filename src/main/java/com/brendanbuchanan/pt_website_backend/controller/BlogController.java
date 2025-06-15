package com.brendanbuchanan.pt_website_backend.controller;

import com.brendanbuchanan.pt_website_backend.gcs.GCSFileService;
import com.brendanbuchanan.pt_website_backend.model.BlogModel;
import com.brendanbuchanan.pt_website_backend.repository.BlogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogRepository blogRepository;
    private final GCSFileService gcsFileService;

    public BlogController(BlogRepository blogRepository, GCSFileService gcsFileService) {
        this.blogRepository = blogRepository;
        this.gcsFileService = gcsFileService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<BlogModel>> getPosts() {
        List<BlogModel> posts = blogRepository.findAll();
        return ResponseEntity.ok(posts);
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
