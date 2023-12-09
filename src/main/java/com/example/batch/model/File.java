package com.example.batch.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "files")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] binaryData;

    @ManyToOne
    @JoinColumn(name = "item_id",nullable = false)
    private Item item;


}
