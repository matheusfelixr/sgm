package com.matheusfelixr.sgm.model.dto.mailing;

import lombok.Data;

@Data
public class SaveAttendanceDTO {

    private Long idMailingStatus;

    private Long idMailing;


    @Override
    public String toString() {
        return "SaveAttendanceDTO{" +
                "idMailingStatus=" + idMailingStatus +
                ", idMailing=" + idMailing +
                '}';
    }
}
