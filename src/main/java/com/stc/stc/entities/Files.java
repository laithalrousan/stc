package com.stc.stc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Lob
    private byte[] data;

    @OneToOne
    private Item item;


    public Files(Item item, byte[] bytes) {
        this.item=item;
        this.data=bytes;
    }
}
