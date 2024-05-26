package boutique.com.boutique.Entities;


import java.util.Date;

import boutique.com.boutique.view.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;

@Entity
@Table(name = "media")
public class Media {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonView({Views.Public.class})
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSX")
    @JsonView(Views.Internal.class)
    private Date creationDate =new Date() ;

    @JsonView(Views.Public.class)
    private String label;

    @JsonView(Views.Public.class)
    private String path;

    @JsonView(Views.Public.class)
    private String contentType;

    @JsonView(Views.Internal.class)
    private boolean deleted = false;

    public Media() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }



    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.deleted = isDeleted;
    }









}