package com.example.sweater.repos;

import com.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
//репозиторий (сообщений, я так понимаю)
//позволяет получить полный список полей или получить их по идентификатору
public interface MessageRepo extends CrudRepository<Message, Integer> {
    List<Message> findByTag(String tag);    //метод для поиска в БД по тэгу. Составлен метод по руководству Spring JPA
}