package at.fhtw.swen2.tutorial.presentation.utils;

public enum TransportType {
    FASTEST("fastest"),
    SHORTEST("shortest"),
    BICYCLE("bicycle"),
    PEDESTRIAN("pedestrian");

    TransportType(String transportType) {
        this.transportType = transportType;
    }
    public final String transportType;
}
