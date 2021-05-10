package net.huadong.tech.ship.service.impl;

import java.util.List;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.ship.service.FileUploadService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import org.springframework.stereotype.Component;

@Component
public class FileUploadServiceImpl
  implements FileUploadService
{
  public List<ComFileUpload> find(String entityName, String entityId)
  {
    String jpql = "select a from ComFileUpload a where a.entityName=:entityName and a.entityId=:entityId";
    QueryParamLs paramLs = new QueryParamLs();
    paramLs.addParam("entityName", entityName);
    paramLs.addParam("entityId", entityId);
    return JpaUtils.findAll(jpql, paramLs);
  }

  public ComFileUpload findById(String uploadId)
  {
    return (ComFileUpload)JpaUtils.findById(ComFileUpload.class, uploadId);
  }

  public void remove(String uploadId)
  {
    JpaUtils.remove(ComFileUpload.class, uploadId);
  }

  public void save(ComFileUpload upload)
  {
    JpaUtils.save(upload);
  }
}