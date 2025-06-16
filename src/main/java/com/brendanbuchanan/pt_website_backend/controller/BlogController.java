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

    @GetMapping("posts/{id}")
    public ResponseEntity<BlogModel> getPost(@PathVariable Long id) {
        return blogRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/create")
    public ResponseEntity<String> createBlogPost(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("isPublished") String isPublishedStr
    ) {
        try {
            String imageUrl = gcsFileService.uploadFile(file);
            boolean isPublished = Boolean.parseBoolean(isPublishedStr);

            BlogModel blog = new BlogModel(title, author, date, description, imageUrl, isPublished);
            blogRepository.save(blog);

            return ResponseEntity.ok("Blog created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + "failed to create blog post");
        }
    }


    // patch request for an individual post
    @PatchMapping("patch/{id}")
    public ResponseEntity<String> updateBlogPost(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("isPublished") String isPublishedStr
    ) {
        try {
            // somehow take everything from the params, and update all fields via the blogrepository
            BlogModel blog = blogRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Blog not found"));

            // now updating the fields
            blog.setTitle(title);
            blog.setAuthor(author);
            blog.setDate(date);
            blog.setDescription(description);

            // now take the file and put it into the bucket
            // then store the returned string into the imageUrl and update it...
            String imageUrl = gcsFileService.uploadFile(file);
            blog.setImageUrl(imageUrl);
            // then take the boolean "string" and turn it back into a boolean and save into db as well
            Boolean isPublished = Boolean.parseBoolean(isPublishedStr);
            blog.setPublished(isPublished);
            // then return the response ok
            blogRepository.save(blog);
            return ResponseEntity.ok("Blog updated");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + "failed to update blog post");
        }
    }



}
