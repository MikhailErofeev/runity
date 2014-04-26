package com.github.mikhailerofeev.runity.domain.entities;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

/**
 * Created by Максим on 27.04.2014.
 */
public class DataPassport {
    @Id
    private String id;

    private String author;
    private String url;
    private String text;
    private DateTime date;

    public DataPassport(String login, String url, String text, DateTime date){
        this.author = login;
        this.url = url;
        this.text = text;
        this.date = date;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getUrl(){
        return this.url;
    }

    public String getText() {
        return text;
    }

    public DateTime getDate() {
        return date;
    }

    public String getId() {
        return id;
    }
}
