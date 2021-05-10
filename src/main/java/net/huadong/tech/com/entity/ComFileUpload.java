package net.huadong.tech.com.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="COM_FILE_UPLOAD")
public class ComFileUpload
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name="UPLOAD_ID")
  private String uploadId;

  @Column(name="ENTITY_ID")
  private String entityId;

  @Column(name="ENTITY_NAME")
  private String entityName;

  @Column(name="FILE_NAME")
  private String fileName;

  @Column(name="FILE_SIZE")
  private String fileSize;

  @Column(name="FILE_PATH")
  private String filePath;

  @Column(name="FILE_UUID")
  private String fileUuid;

  public String getUploadId()
  {
    return this.uploadId;
  }

  public void setUploadId(String uploadId) {
    this.uploadId = uploadId;
  }

  public String getEntityId() {
    return this.entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getEntityName() {
    return this.entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFilePath() {
    return this.filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileUuid() {
    return this.fileUuid;
  }

  public void setFileUuid(String fileUuid) {
    this.fileUuid = fileUuid;
  }

  public String getFileSize() {
    return this.fileSize;
  }

  public void setFileSize(String fileSize) {
    this.fileSize = fileSize;
  }
}