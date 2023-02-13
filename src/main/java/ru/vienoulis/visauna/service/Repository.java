package ru.vienoulis.visauna.service;

import com.google.gson.JsonElement;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vienoulis.visauna.dto.Hall;

import java.util.HashMap;
import java.util.Map;

@Service
public class Repository {
    private final Map<String, Hall> repo = new HashMap<>();
    private final Map<Long, JsonElement> gsonRepo = new HashMap<>();

    public void saveHall(String kay, Hall val) {
        repo.merge(kay, val, (v1, v2) -> v2);
    }

    public Hall getHall(String kay) {
        return repo.get(kay);
    }

    public void saveGson(long key, JsonElement json) {
        gsonRepo.merge(key, json, (v1, v2) -> v2);
    }

    public void removeJson(Long key) {
        gsonRepo.remove(key);
    }

    public JsonElement getJson(Long key) {
        return gsonRepo.get(key);
    }
}
