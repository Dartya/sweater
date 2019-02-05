package com.example.sweater;

import com.example.sweater.domain.Message;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepo messageRepo;    //репозиторий сообщений, применяется для возврата сущностей для списка (см.метод main)

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping //если не указывается раздел, то маппируется корневой каталог сайта
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll(); //ищутся все сущности в репозитории сообщений, закладываются в коллекцию итератор<сущность>

        model.put("messages", messages);    //закладываем в модель созданный "список сущностей"
        return "main";                      //возвращаем вьюху
    }

    @PostMapping    //мапируется пост-запрос, параметры - перечисленные в форме поля
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);   //создали новый объект с принятыми параметрами
        messageRepo.save(message);                  //сохранили в репозитории

        Iterable<Message> messages = messageRepo.findAll(); //опять ищем все сущности в репозитории, закладываем в итератор<сущность>
        model.put("messages", messages);                    //закладываем в модель
        return "main";                                      //возвращаем вьюху
    }

    @PostMapping("filter")  //это метод второй формы, маппируется по тэгу action="filter"; по переданному тэгу нужно найти все сообщения
    public String filter(@RequestParam String filter, Map<String, Object> model) {  //параметр из формы и модель
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {  //если фильтр задан и не пуст
            messages = messageRepo.findByTag(filter);
        } else {    //иначе вернуть все сообщения
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);
        return "main";
    }
}