package com.jeremyli.jeremygenerator.service;

import com.jeremyli.jeremygenerator.vo.TableVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileUploadService {
    List<TableVo> processFile(MultipartFile file);
}
