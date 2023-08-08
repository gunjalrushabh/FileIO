package com.app.enitity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filetbl2")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class FileEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long file_id;

    @Column
    private String file_name;

    @Column
    private String file_type;
    
    @Column
    private String filepath;
    
    @Column
    private LocalDate created_at;
    
    @Column
    private LocalDate modified_at;
    
    @Column
    private String status;

  // which will not to be saved at database
    @Transient
    private byte []data;
    
    
    @Column
    private String fileUuid;
    
    public FileEntity(){
    	this.fileUuid= UUID.randomUUID().toString();
    }
    
	@Override
	public String toString() {
		return "FileEntity [file_id=" + file_id +
				",\n file_uuid=" + fileUuid+
				",\n file_name=" + file_name + 
				",\n file_type=" + file_type+ 
				",\n file_path=" + filepath + 
				",\n created_at=" + created_at + 
				",\n modified_at=" + modified_at
				+ ",\n status=" + status +" data: "+(data!= null ? data.length:0)+ "]";
	}


    
      
}
