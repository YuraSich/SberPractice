package com.example.cds.entitty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Content")
@NoArgsConstructor
@Getter
@Setter
public class Content {
    @Id
    private String guid;

    @NotNull
    private String data;


    @OneToMany(targetEntity = Target.class, mappedBy = "contentGuid",fetch =  FetchType.LAZY)
    private List<Target> targets;

}
