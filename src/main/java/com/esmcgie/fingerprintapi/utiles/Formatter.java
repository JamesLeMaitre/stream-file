package com.esmcgie.fingerprintapi.utiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  Formatter<variant> {
    private boolean status;
    private variant data;
    private String message;
    private int total=0;
}
