package com.blogspot.kakakikikeke.sensortest.beans;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Ranking {
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private int rank;
    @Setter
    @Getter
    private int clearCount;
    @Setter
    @Getter
    private Date registedDate;
}
