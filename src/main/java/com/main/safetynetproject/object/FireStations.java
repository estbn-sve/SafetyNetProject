package com.main.safetynetproject.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FireStations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    int station;
    @ElementCollection
    List<String> address;
}
