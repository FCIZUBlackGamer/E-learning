package com.example.rootyasmeen.FCI_Learn;

/**
 * Created by rootyasmeen on 29/01/18.
 */

public class ListItem_Assay {
    private String quest3;
    private String id;
    private String ans;

    public String getQuest3() {
        return quest3;
    }

    public String getAns() {
        return ans;
    }

    public String getId() {
        return id;
    }

    public ListItem_Assay(String id, String quest3, String ans) {
        this.quest3 = quest3;
        this.id = id;
        this.ans = ans;
    }
}
