package com.example.sweater.domain;

import javax.persistence.*;

@Entity //аннотация, которая говорит спрингу, что данный класс - сущность, которую необходимо сохранять в БД
public class Message {
    @Id //аннотация перед идентификатором - id, тут все понятно
    @GeneratedValue(strategy=GenerationType.AUTO)   //этой аннотацией говорим спрингу, чтобы они с БД сами разобрались, как им там этот тип генерировать, хранить и т.п.
    private Integer id;
    //остальные поля сущности (будущие столбы), им задаются геттеры и сеттеры
    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER) //связь в БД. указываем, что одному пользователю соответствует множество сообщений
    @JoinColumn(name = "user_id")       //колонка будет записана так, как в параметре, а не author_id
    private User author;  //автор сообщения, связывать будем с записью таблицы пользователей

    public Message() {  //всегда для сущности нужно генерировать пустой конструктор, чтобы спринг не ругался
    }

    public Message(String text, String tag, User user) {   //а так же потребный конструктор
        this.text = text;
        this.tag = tag;
        this.author = user; //добавили юзера в конструктор
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}