package com.theekshana.onlineshop.Model;

public class Customers {

   private int idCustomer;
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String user_image;
    private String user_token;
    private String phone_no;
    private int card_status;
    private int role;
    private int is_active;
    private String google_id;
    private String FCMID;
    private int address_status;


    public Customers(int idCustomer, String fname, String lname, String email, String password, String user_image, String user_token, String phone_no, int card_status, int role, int is_active, String google_id, String FCMID, int address_status) {
        this.idCustomer = idCustomer;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.user_image = user_image;
        this.user_token = user_token;
        this.phone_no = phone_no;
        this.card_status = card_status;
        this.role = role;
        this.is_active = is_active;
        this.google_id = google_id;
        this.FCMID = FCMID;
        this.address_status = address_status;
    }

    public Customers() {

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public int getCard_status() {
        return card_status;
    }

    public void setCard_status(int card_status) {
        this.card_status = card_status;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getFCMID() {
        return FCMID;
    }

    public void setFCMID(String FCMID) {
        this.FCMID = FCMID;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getAddress_status() {
        return address_status;
    }

    public void setAddress_status(int address_status) {
        this.address_status = address_status;
    }
}
