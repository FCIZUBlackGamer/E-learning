package com.example.rootyasmeen.FCI_Learn;

/**
 * Created by rootyasmeen on 27/01/18.
 */

public class ListItem_TF {
    private String id;
    private String question;
    private String true11;
    private String false11;

    public String getQuestion() {
        return question;
    }

    public String getTrue11() {
        return true11;
    }

    public String getFalse11() {
        return false11;
    }

    public String getId() {
        return id;
    }

    public ListItem_TF(String id, String question, String true11, String false11) {
        this.id = id;
        this.question = question;
        this.true11 = true11;
        this.false11 = false11;
    }


}