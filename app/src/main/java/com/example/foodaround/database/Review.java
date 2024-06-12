package com.example.foodaround.database;

public class Review {
    private String id;
    private String nama;
    private String alamat;
    private String namaresto;
    private String review;

    public Review() {
    }

    public Review(String id, String nama, String alamat, String namaresto, String review) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.namaresto = namaresto;
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaresto() {
        return namaresto;
    }

    public void setNamaresto(String namaresto) {
        this.namaresto = namaresto;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}



