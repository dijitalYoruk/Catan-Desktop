package com.catan.modal;

import com.catan.Util.Constants;

public class City extends Settlement {
    public City(String imagePath, Vertex vertex, Player player) {
        super(Constants.CITY, imagePath, vertex, 2, player);
    }
}
