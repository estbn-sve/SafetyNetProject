package com.main.safetynetproject.object;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FireStations {
    int station;
    List<String> address;
}
