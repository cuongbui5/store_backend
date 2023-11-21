package com.example.storeback.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData extends BaseResponse{
    private Object data;
    public ResponseData(String status, String message, Object data) {
        super(status, message);
        this.data = data;
    }



}
