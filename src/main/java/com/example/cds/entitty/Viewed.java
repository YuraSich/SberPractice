package com.example.cds.entitty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Viewed"
//        uniqueConstraints = {
//                @UniqueConstraint(name = "UniqueNumberAndStatus", columnNames = {"user_id", "content_id"}),
//                }
)
@NoArgsConstructor
@Getter
@Setter

public class Viewed {

    @Id
    @GeneratedValue
    private Long guid;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "guid")
    @NotNull
    private Users user;


    @OneToOne
    @JoinColumn(name = "content_id", referencedColumnName = "guid")
    @NotNull
    private Content content;

    private Boolean isSended;
}
