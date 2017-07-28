package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 *文件上传service
 */
public interface IFileService {
    /**
     * 上传文件
     * @param file 需要上传的文件
     * @param path 上传的目的目录
     * @return 新文件名
     */
    String upload(MultipartFile file, String path);
}
