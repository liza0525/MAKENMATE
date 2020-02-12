package com.cocktail.model;
import java.util.Date;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
import org.hibernate.annotations.CreationTimestamp;
 
@Entity
@Table(name="files")
public class UploadFile {
 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column
    private String fileName;
    
    @Column
    private long size;
    
    @Column
    private String mimeType;
    
    @CreationTimestamp    // 입력시 시간 정보를 자동으로 입력해는 어노테이션.
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;
 
    public UploadFile() {
    }
    
 
    public UploadFile(String fileName, long size, String mimeType) {
        this.fileName = fileName;
        this.size = size;
        this.mimeType = mimeType;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    public long getSize() {
        return size;
    }
 
    public void setSize(long size) {
        this.size = size;
    }
 
    public String getMimeType() {
        return mimeType;
    }
 
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
 
    public Date getInsertDate() {
        return insertDate;
    }
 
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
 
    @Override
    public String toString() {
        return "UploadFile [id=" + id + ", fileName=" + fileName + ", size=" + size + ", mimeType=" + mimeType
                + ", insertDate=" + insertDate + "]";
    }
}