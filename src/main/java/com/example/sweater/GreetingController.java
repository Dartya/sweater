package com.example.sweater;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    /*Аннотация @RequestMapping гарантирует, что HTTP запросы к
    /greeting приведут к выполнению метода greeting(). */
    public String greeting (@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        /*@RequestParam связывает значение строкового параметра name запроса с name параметром метода greeting().
        Этот параметр не required; если он отсутствует в запросе, то будет использовано defaultValue значение "World".
        Значение параметра name добавлено в объект Model, что делает его доступным в шаблоне представления.*/
        model.addAttribute("name", name);
        return "greeting";
    }
    /*В примере выше не определено GET, PUT, POST и так далее, потому что
    @RequestMapping соответствует всем HTTP операциям по умолчанию.
    @RequestMapping(method=GET) уточняет это соответствие.*/
}