package com.example.storeback.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseData extends BaseResponse{
    private Object data;

}
