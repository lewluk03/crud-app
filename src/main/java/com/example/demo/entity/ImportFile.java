package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name="importfile")
public class ImportFile implements Serializable {

    private static final long serialVersionUID = -2661405911591267384L;

    @Id
    @Column(name="import_file_id")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "importfile_seq")
    @SequenceGenerator(name = "importfile_seq", sequenceName = "SEQ_IMPORTFILE", allocationSize = 1)
    private Long importFileId;

    @Column(name="file_name")
    private String fileName;

    @Column(name="file_type")
    private String fileType;

    @Column(name="file_data")
    private byte[] fileData;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportFile that = (ImportFile) o;

        if (!Objects.equals(importFileId, that.importFileId)) return false;
        if (!Objects.equals(fileName, that.fileName)) return false;
        if (!Objects.equals(fileType, that.fileType)) return false;
        return Arrays.equals(fileData, that.fileData);
    }

    @Override
    public int hashCode() {
        int result = importFileId != null ? importFileId.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(fileData);
        return result;
    }
}
