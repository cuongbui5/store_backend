package com.example.storeback.dto.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageData {
    private int num;
    private int size;
    public PageData(){
        this.num=0;
        this.size=3;
    }
}
