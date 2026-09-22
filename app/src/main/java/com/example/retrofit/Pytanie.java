package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

public class Pytanie {
    @SerializedName("tresc")
    public String trescPytanie;
    @SerializedName("odp_a")
    public String odpA;
    @SerializedName("odp_b")
    public String odpB;
    @SerializedName("odp_c")
    public String odpC;
    @SerializedName("poprawna")
    public int odpowiedzPoprawna;

}
