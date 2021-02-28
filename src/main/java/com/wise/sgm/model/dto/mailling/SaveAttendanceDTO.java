package com.wise.sgm.model.dto.mailling;

import lombok.Data;

@Data
public class SaveAttendanceDTO {

    private Long idMaillingStatus;

    private Long idMailling;


    @Override
    public String toString() {
        return "SaveAttendanceDTO{" +
                "idMaillingStatus=" + idMaillingStatus +
                ", idMailling=" + idMailling +
                '}';
    }
}
