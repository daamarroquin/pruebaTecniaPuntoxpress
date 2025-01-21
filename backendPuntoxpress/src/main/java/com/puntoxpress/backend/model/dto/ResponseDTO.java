package com.puntoxpress.backend.model.dto;

import lombok.*;

import java.beans.ConstructorProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO<T> {
    private Integer code;
    private String message;
    private T data;

}
