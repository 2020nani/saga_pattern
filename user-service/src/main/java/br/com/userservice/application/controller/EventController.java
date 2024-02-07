package br.com.userservice.application.controller;

import br.com.userservice.application.dto.EventFilters;
import br.com.userservice.core.Event.EventService;
import br.com.userservice.core.document.Event;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/event")
public class EventController {

    private final EventService service;

    @GetMapping
    public Event findByFilters(EventFilters filters) {
        return service.findByFilters(filters);
    }

    @GetMapping("all")
    public List<Event> findAll() {
        return service.findAll();
    }
}
