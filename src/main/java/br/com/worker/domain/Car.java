package br.com.worker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("model")
    private String model;

    @JsonProperty("mark")
    private String mark;

    @JsonProperty("color")
    private String color;

    @JsonProperty("yearModel")
    private Integer yearModel;

    @JsonProperty("odometerKm")
    private Long odometerKm;

    @JsonProperty("chassi")
    private String chassi;

    @JsonProperty("passengerNumbers")
    private Integer passengerNumbers;

}
