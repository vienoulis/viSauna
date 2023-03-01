package ru.vienoulis.visauna.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.google.gson.JsonElement;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vienoulis.visauna.dto.Hall;
import ru.vienoulis.visauna.model.BookingSlot;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class Repository {
    private final Map<String, Hall> repo = new HashMap<>();
    private final Map<String, JsonElement> gsonRepo = new HashMap<>();
    private final Map<LocalDate, List<BookingSlot>> slotRepo = new HashMap<>();

    public void saveHall(String kay, Hall val) {
        repo.merge(kay, val, (v1, v2) -> v2);
    }

    public Hall getHall(String kay) {
        return repo.get(kay);
    }

    public void saveGson(String key, JsonElement json) {
        gsonRepo.merge(key, json, (v1, v2) -> v2);
    }

    public void removeJson(String key) {
        gsonRepo.remove(key);
    }

    public JsonElement getJson(String key) {
        return gsonRepo.get(key);
    }

    public List<BookingSlot> getBookingSlotAt(LocalDate localDate) {
        if (!slotRepo.containsKey(localDate)) return List.of();

        return slotRepo.get(localDate);
    }

    public void addBookingSlot(LocalDate localDate, BookingSlot slot) {
        slotRepo.merge(localDate, List.of(slot), (o, v) -> {
            o.add(slot);
            return o;
        });
    }

    public void removeSlotAt (LocalDate localDate, BookingSlot slot) {
        var currentSlots = slotRepo.get(localDate);
        currentSlots.remove(slot);
        slotRepo.put(localDate, currentSlots);

    }
}
