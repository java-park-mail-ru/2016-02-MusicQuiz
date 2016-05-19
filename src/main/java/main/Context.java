package main;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seven-teen on 30.03.16.
 */
public class Context {
    @NotNull
    private final Map<Class, Object> contextMap = new HashMap<>();

    public void put(@NotNull Class clazz, @NotNull Object object){
        if(contextMap.containsKey(clazz)){
            System.out.println("ERROR: This class is already added to the context!");
            return;
        }
        contextMap.put(clazz, object);
    }

    @NotNull
    public <T> T get(@NotNull Class<T> clazz){
        //noinspection unchecked
        return (T) contextMap.get(clazz);
    }
}
