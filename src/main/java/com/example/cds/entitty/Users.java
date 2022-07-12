package com.example.cds.entitty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Getter
@Setter
public class Users {

    @Id
    private String guid;

    private String systemName;

    private String email;

    private String phone;
}
