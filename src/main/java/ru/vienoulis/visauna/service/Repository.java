package ru.vienoulis.visauna.service;

import org.jvnet.hk2.annotations.Service;
import ru.vienoulis.visauna.dto.Hall;

import java.util.HashMap;
import java.util.Map;

@Service
public class Repository {
    private final Map<String, Hall> repo = new HashMap<>();


    public void saveHall(String kay, Hall val) {
        repo.merge(kay, val, (v1, v2) -> v2);
    }

    public Hall getHall(String kay) {
        return repo.get(kay);
    }
}
