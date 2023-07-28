package com.stc.stc.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter

public class PermissionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String groupName;

    @JsonIgnore
    @OneToMany
    private List<Item> items;

    @JsonIgnore
    @OneToMany
    private List<Permission> permissions;


}
