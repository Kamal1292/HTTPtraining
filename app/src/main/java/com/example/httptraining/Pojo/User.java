package com.example.httptraining.Pojo;


public class User {
    private String id;
    private String name;
    private String nickname;
    private String email;
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private String phone;
    private String website;
    private String companyName;
    private String catchPhrase;
    private String bs;

    public User(String id,
                String name,
                String nickname,
                String email,
                String street,
                String suite,
                String city,
                String zipcode,
                String phone,
                String website,
                String companyName,
                String catchPhrase,
                String bs) {

        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.phone = phone;
        this.website = website;
        this.companyName = companyName;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getSuite() {
        return suite;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public String getBs() {
        return bs;
    }
}
