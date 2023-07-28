package com.stc.stc.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter

public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String userEmail;

    private Integer level;

    @ManyToOne
    private  PermissionGroup permissionGroup;


}
