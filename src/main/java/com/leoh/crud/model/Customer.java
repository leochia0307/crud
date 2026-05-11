package com.leoh.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity(name = "Customer")
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "名稱未填寫")
    @Size(min=2, message = "名稱至少1個中文字或1個英文單字")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "姓氏未填寫")
    @Size(min=1, message = "姓氏至少1個中文字或1個英文單字")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "電子信箱未填寫")
    @Email(message = "請填寫正確的電子信箱格式")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "手機號碼未填寫")
    @Column(name = "phone")
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
