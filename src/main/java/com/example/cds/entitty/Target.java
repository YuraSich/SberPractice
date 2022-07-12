package com.example.cds.entitty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Target")
@NoArgsConstructor
@Getter
@Setter
public class Target {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String guid;


    @OneToOne
    @JoinColumn(name = "user_guid", referencedColumnName = "guid")
    @NotNull
    private Users userGuid;

    @ManyToOne
    @JoinColumn(name = "content_id")
    @NotNull
    private Content contentGuid;


    private LocalDate startDate;
    private LocalDate endDate;
    private Integer priority;


}
