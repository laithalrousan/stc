package com.stc.stc.models;

import com.stc.stc.entities.Files;
import com.stc.stc.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileModel {

    Files file;

    private byte[] data;

}
