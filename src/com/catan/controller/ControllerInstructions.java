package com.catan.controller;

import com.catan.modal.Instruction;
import com.catan.modal.Settings;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Set;

public class ControllerInstructions extends ControllerBase {
    @FXML
    ImageView currentRulesPage, leftRulesPage, rightRulesPage;

    PerspectiveTransform rightPerspectiveTransform = new PerspectiveTransform();
    PerspectiveTransform leftPerspectiveTransform = new PerspectiveTransform();

    int currentPageNumber = 1;
    @FXML
    public void initialize() {

        leftRulesPage.setVisible(false);
        currentRulesPage.setImage(new Image("./com/catan/assets/catan_rules_"+ Settings.languauge +"/catan_rules_page_"+currentPageNumber+".jpg"));
        rightRulesPage.setImage(new Image("./com/catan/assets/catan_rules_"+ Settings.languauge +"/catan_rules_page_"+(currentPageNumber+1)+".jpg"));
        // the adjustment of perspective of right page
        rightPerspectiveTransform.setUlx(0.0); //10
        rightPerspectiveTransform.setUly(0.0); //10
        rightPerspectiveTransform.setUrx(375); //310
        rightPerspectiveTransform.setUry(80); //40
        rightPerspectiveTransform.setLrx(375); //310
        rightPerspectiveTransform.setLry(700); //60
        rightPerspectiveTransform.setLlx(0.0); //10
        rightPerspectiveTransform.setLly(1000); //90


        // the adjustment of perspective of left page
        leftPerspectiveTransform.setUlx(375); //10
        leftPerspectiveTransform.setUly(80); //10
        leftPerspectiveTransform.setUrx(750); //310
        leftPerspectiveTransform.setUry(0); //40
        leftPerspectiveTransform.setLrx(750); //310
        leftPerspectiveTransform.setLry(1000); //60
        leftPerspectiveTransform.setLlx(375); //10
        leftPerspectiveTransform.setLly(700); //90

        leftRulesPage.setEffect(leftPerspectiveTransform);
        rightRulesPage.setEffect(rightPerspectiveTransform);
    }

    @FXML
    public void goLeftPage(MouseEvent event){
        if(currentPageNumber == 1)
            return;

        // if current page is 2, and user wants to go first page, there will no pages in left, so we make it unvisible
        if(currentPageNumber == 2)
            leftRulesPage.setVisible(false);
        else
            leftRulesPage.setImage(new Image("./com/catan/assets/catan_rules_"+ Settings.languauge +"/catan_rules_page_"+(currentPageNumber-2)+".jpg"));

        // if current page is 16, and user wants to 15'th page, there will be 16'th page on right
        // but it was unvisible since we make it unvisible when we reach 16'th page. So we need to make it visible again.
        if((Settings.languauge == "en" && currentPageNumber == 16) || (Settings.languauge == "tr" && currentPageNumber == 11))
            rightRulesPage.setVisible(true);
        currentRulesPage.setImage(new Image("./com/catan/assets/catan_rules_"+ Settings.languauge +"/catan_rules_page_"+(currentPageNumber-1)+".jpg"));
        rightRulesPage.setImage(new Image("./com/catan/assets/catan_rules_"+ Settings.languauge +"/catan_rules_page_"+currentPageNumber+".jpg"));
        currentPageNumber--;

    }

    @FXML
    public void goRightPage(MouseEvent event){
        if((Settings.languauge == "en" &&  currentPageNumber == 16) || (Settings.languauge == "tr" && currentPageNumber == 11))
            return;
        // if current page is 15'th page, and user wants to go 16'th page which is last page
        // the right imageview must be empty, so we make it unvisible
        if((Settings.languauge == "en" && currentPageNumber == 15) || (Settings.languauge == "tr" && currentPageNumber == 10))
            rightRulesPage.setVisible(false);
        else{
            rightRulesPage.setImage(new Image("./com/catan/assets/catan_rules_"+Settings.languauge+"/catan_rules_page_"+(currentPageNumber+2)+".jpg"));
        }
        // current page number is not incremented yet, if it is 1 now, user wants to go 2'nd page,
        // so there will be another page in left image view, so we need to make it visible
        if(currentPageNumber == 1)
            leftRulesPage.setVisible(true);

        leftRulesPage.setImage(new Image("./com/catan/assets/catan_rules_"+Settings.languauge+"/catan_rules_page_"+currentPageNumber+".jpg"));
        currentRulesPage.setImage(new Image("./com/catan/assets/catan_rules_"+Settings.languauge+"/catan_rules_page_"+(currentPageNumber+1)+".jpg"));
        currentPageNumber++;
    }
}