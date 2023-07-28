package com.stc.stc.models;

import com.stc.stc.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Space {
    private String name;
    private String permissionGroup;
    private List<Permission> permissions;

}
