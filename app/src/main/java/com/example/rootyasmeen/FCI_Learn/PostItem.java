package com.example.rootyasmeen.FCI_Learn;

/**
 * Created by fci on 18/02/18.
 */

public class PostItem {
    String time;
    String post;

   public PostItem(String time, String post) {
        this.time = time;
        this.post = post;
    }

    public String getPost() {
        return post;
    }

    public String getTime() {
        return time;
    }
}
