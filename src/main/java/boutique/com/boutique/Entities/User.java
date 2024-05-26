package boutique.com.boutique.Entities;

import boutique.com.boutique.Enum.Roles;
import boutique.com.boutique.view.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonView({ Views.Public.class })
    private Long id;

    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss.SSSX")
    @JsonView(Views.Internal.class)
    private Date creationDate=new Date();
    @Column(unique = true)
    @JsonView(Views.Public.class)
    private String phone;
    @Column(unique = true)
    @JsonView(Views.Public.class)
    private String email;

    @JsonView(Views.Public.class)
    private String password;


    @JsonView(Views.Internal.class)
    private boolean deleted = false;

    @Enumerated(EnumType.ORDINAL)
    @JsonView(value = { Views.Public.class })
    private Roles role;

    @OneToOne
    @JsonView(value = Views.Public.class)
    private Media image;

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Media getImage() {
        return image;
    }

    public void setImage(Media image) {
        this.image = image;
    }
}
