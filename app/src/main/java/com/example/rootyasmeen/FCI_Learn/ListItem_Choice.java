package com.example.rootyasmeen.FCI_Learn;

/**
 * Created by rootyasmeen on 27/01/18.
 */

public class ListItem_Choice {

    public String getQuest() {
        return quest;
    }

    public String getId() {
        return id;
    }

    public String getChoice11() {
        return choice11;
    }

    public String getChoice22() {
        return choice22;
    }

    public String getChoice33() {
        return choice33;
    }

    public String getChoice44() {
        return choice44;
    }

    private String id;
    private String quest;
    private String choice11;
    private String choice22;

    private String choice33;
    private String choice44;

    ListItem_Choice(){

    }
    public ListItem_Choice(String id, String quest, String choice11, String choice22, String choice33, String choice44) {
        this.id = id;
        this.quest = quest;
        this.choice11 = choice11;
        this.choice22 = choice22;
        this.choice33 = choice33;
        this.choice44 = choice44;
    }

    public void setChoice11(String choice11) {
        this.choice11 = choice11;
    }

    public void setChoice22(String choice22) {
        this.choice22 = choice22;
    }

    public void setChoice33(String choice33) {
        this.choice33 = choice33;
    }

    public void setChoice44(String choice44) {
        this.choice44 = choice44;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }
}
