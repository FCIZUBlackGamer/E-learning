package com.example.rootyasmeen.FCI_Learn;

/**
 * Created by fci on 20/02/18.
 */

public class ListFile {
    String time, name, url;

    public ListFile(String time, String name, String url) {
        this.time = time;
        this.name = name;
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
