package com.example.myspring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 18, scale = 15, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 18, scale = 15, nullable = false)
    private BigDecimal longitude;

    @Column(nullable = false)
    private Double height;


//    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
//    @JoinColumn(name = "route_id", nullable = false)
//    private Cesium route;
}
