package com.jeremyli.jeremygenerator.controller;

import com.jeremyli.jeremygenerator.service.impl.FileUploadServiceImpl;
import com.jeremyli.jeremygenerator.vo.Result;
import com.jeremyli.jeremygenerator.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class FileUploadController {

    @Autowired
    FileUploadServiceImpl fileUploadService;

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

    @PostMapping("/upload")
    public Result<List<TableVo>> uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        List<TableVo> sheetsData = fileUploadService.processFile(file);
        model.addAttribute("sheetsData", sheetsData);
        return Result.ok(sheetsData);
    }
}
