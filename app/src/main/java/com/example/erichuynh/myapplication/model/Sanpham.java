package com.example.erichuynh.myapplication.model;

import java.io.Serializable;

public class Sanpham implements Serializable{
    public int ID;
    public String tensanpham;
    public Integer Giasanpham;
    public String Hinhanhsanpham;
    public String Motasanpham;
    public int IDSanpham;

    public Sanpham(int ID, String tensanpham, Integer giasanpham, String hinhanhsanpham, String motasanpham, int IDSanpham) {
        this.ID = ID;
        this.tensanpham = tensanpham;
        Giasanpham = giasanpham;
        Hinhanhsanpham = hinhanhsanpham;
        Motasanpham = motasanpham;
        this.IDSanpham = IDSanpham;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public Integer getGiasanpham() {
        return Giasanpham;
    }

    public void setGiasanpham(Integer giasanpham) {
        Giasanpham = giasanpham;
    }

    public String getHinhanhsanpham() {
        return Hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        Hinhanhsanpham = hinhanhsanpham;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        Motasanpham = motasanpham;
    }

    public int getIDSanpham() {
        return IDSanpham;
    }

    public void setIDSanpham(int IDSanpham) {
        this.IDSanpham = IDSanpham;
    }

    @Override
    public String toString() {
        return "Sanpham{" +
                "ID=" + ID +
                ", tensanpham='" + tensanpham + '\'' +
                ", Giasanpham=" + Giasanpham +
                ", Hinhanhsanpham='" + Hinhanhsanpham + '\'' +
                ", Motasanpham='" + Motasanpham + '\'' +
                ", IDSanpham=" + IDSanpham +
                '}';
    }
}
