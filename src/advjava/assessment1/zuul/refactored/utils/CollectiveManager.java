/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advjava.assessment1.zuul.refactored.utils;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author dja33
 */
public abstract class CollectiveManager<T> {

    private final Map<String, T> resources;

    public CollectiveManager() {
        resources = new TreeMap<>();
    }

    /**
     * Check whether the RoomManager has a cerain room
     *
     * @param name Name of room
     * @return true if manager has room
     */
    public boolean has(String name) {
        return resources.containsKey(name);
    }

    /**
     * Return room instance from manager if present
     *
     * @param name Name of room
     * @return room instance
     */
    public T get(String name) {
        return resources.get(name);
    }

    /**
     * Add a room to the room manager, if the room already exists then it won't
     * be added
     *
     * @param key
     * @param t
     * @return true if room was added
     */
    public boolean add(String key, T t) {
        if (resources.containsKey(key)) {
            return false;
        } else {
            resources.put(key, t);
        }
        return true;
    }

    /**
     * Clear all rooms stored in map
     */
    public void clearRooms() {
        resources.clear();
    }

    /**
     * Remove a room from the room manager.
     *
     * @param name Name of room
     * @return true if removed
     */
    public boolean removeRoom(String name) {
        if (has(name)) {
            resources.remove(name);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Get all rooms
     *
     * @return Collection<T> objects
     */
    public Collection<T> values() {
        return resources.values();
    }
    
}
