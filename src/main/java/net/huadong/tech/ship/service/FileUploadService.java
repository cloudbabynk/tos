package net.huadong.tech.ship.service;

import java.util.List;
import net.huadong.tech.com.entity.ComFileUpload;

public  interface FileUploadService
{
    List<ComFileUpload> find(String paramString1, String paramString2);

    ComFileUpload findById(String paramString);

    void remove(String paramString);

    void save(ComFileUpload paramComFileUpload);
}