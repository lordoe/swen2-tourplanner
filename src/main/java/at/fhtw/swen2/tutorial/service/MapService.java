package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.utils.MapData;

public interface MapService {
    MapData getMap(String from, String to, String  transportType);
}
