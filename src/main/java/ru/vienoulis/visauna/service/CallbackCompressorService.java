package ru.vienoulis.visauna.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vienoulis.visauna.model.ShortCallbackData;
import ru.vienoulis.visauna.model.callback.Action;
import ru.vienoulis.visauna.model.callback.CallbackQueryData;

import java.util.UUID;

@Component
public class CallbackCompressorService {
    private final Gson gson;
    private final Repository repository;

    @Autowired
    public CallbackCompressorService(Gson gson, Repository repository) {
        this.gson = gson;
        this.repository = repository;
    }

    public String compressData(Action action, CallbackQueryData data) {
        var jsonElement = gson.toJsonTree(data, action.getDataClass());
        var key = generateKey();
        repository.saveGson(key, jsonElement);
        return gson.toJson(ShortCallbackData.builder()
                .action(action)
                .key(key)
                .build());
    }

    private String generateKey() {
        return NanoIdUtils.randomNanoId();
    }

    public CallbackQueryData deCompressData(String data) {
        var shortData = gson.fromJson(data, ShortCallbackData.class);
        var jsonElement = repository.getJson(shortData.getKey());
        repository.removeJson(shortData.getKey());
        return gson.fromJson(jsonElement, shortData.getAction().getDataClass());
    }

    public Action getActionFrom(String data) {
        return gson.fromJson(data, ShortCallbackData.class).getAction();
    }
}
