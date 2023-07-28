package com.stc.stc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String type;
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Item parent;





    @ManyToOne
    private PermissionGroup permissionGroup;

}
