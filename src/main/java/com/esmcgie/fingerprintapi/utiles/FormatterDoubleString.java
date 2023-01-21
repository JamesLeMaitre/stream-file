package com.esmcgie.fingerprintapi.utiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormatterDoubleString <variant> {
    private boolean status;
    private variant data;
    private variant pow;
    private String message;
}