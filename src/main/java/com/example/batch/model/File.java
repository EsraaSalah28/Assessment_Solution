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

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;


}
