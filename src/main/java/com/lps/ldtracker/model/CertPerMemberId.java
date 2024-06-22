package com.lps.ldtracker.model;

import java.sql.Timestamp;
import java.util.Date;
public class CertPerMemberId {
    private String certName;
    private Timestamp certDate;
    private String certLink;
    private String fileContent2;

    // Getter method for certName
    public String getCertName() {
        return certName;
    }

    // Setter method for certName
    public void setCertName(String certName) {
        this.certName = certName;
    }

    // Getter method for certificationDate
    public Timestamp getCertDate() {
        return certDate;
    }

    // Setter method for certificationDate
    public void setCertDate(Timestamp certDate) {
        this.certDate = certDate;
    }

    // Getter method for certLink
    public String getCertLink() {
        return certLink;
    }

    // Setter method for certLink
    public void setCertLink(String certLink) {
        this.certLink = certLink;
    }

    // Getter method for fileContent2
    public String getFileContent2() {
        return fileContent2;
    }

    // Setter method for fileContent2
    public void setFileContent2(String fileContent2) {
        this.fileContent2 = fileContent2;
    }

}
