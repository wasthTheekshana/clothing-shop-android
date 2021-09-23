package com.theekshana.onlineshop.utils;

public class appConfig {
// 192.168.42.111
    //192.168.137.1
  //public static final String hostUrl = "http://192.168.137.1:8080/DownTown/";
public static final String hostUrl = "http://192.168.1.13:8080/DownTown/";
  //  public static final String hostUrl = "http://192.168.137.179:8080/DownTown/";
  //public static final String hostUrl = "http://10.187.46.253:8080/DownTown/";


    //http://localhost:8080/DownTown/Customerregister
    public static final String LoginAuthUrl = hostUrl+"CustomerLogin?email=";
    public static final String RegisterAuthUrl = hostUrl+"Customerregister?Fname=";
    //getAllProducts
    public static final String getAllProductsUrl = hostUrl+"getAllProducts";
    //saveCard
    public static final String saveUserCardUrl = hostUrl+"saveUserCard?Fname=";
    //http://localhost:8080/DownTown/saveUserCard






}
