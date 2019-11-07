package com.catan.modal;

import com.catan.Util.Constants;

public class Village extends Settlement {
    public Village(String imagePath, Vertex vertex, Player player) {
        super(Constants.VILLAGE, imagePath, vertex, 1, player);
    }
}
