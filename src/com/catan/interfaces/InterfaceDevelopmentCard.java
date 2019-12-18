package com.catan.interfaces;

import com.catan.modal.DevelopmentCard;
import com.catan.modal.Trade;
import javafx.event.ActionEvent;

public interface InterfaceDevelopmentCard {
    void openDialog(String viewPath, String title, DevelopmentCard developmentCard, Trade trade);
    void setDevelopmentCardInvention(DevelopmentCard developmentCardInvention);
    void playDevelopmentCard(DevelopmentCard developmentCard);
    void displayWarning(String warningType);
    void buyDevelopmentCard(ActionEvent event);
}
