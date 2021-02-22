package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="importfile")
public class ImportFile {

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
}
