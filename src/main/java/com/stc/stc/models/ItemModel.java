package com.stc.stc.models;

import com.stc.stc.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemModel {

    Item item;
    String email;

    private byte[] data;

}
