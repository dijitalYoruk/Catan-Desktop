package com.catan.modal;

import com.catan.Util.Constants;

public class Civilisation extends Settlement {
    public Civilisation(String imagePath, Vertex vertex, Player player) {
        super(Constants.CIVILISATION, imagePath, vertex, 3, player);
    }
}
