package com.udacity.jdnd.course3.critter.Data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;

    @Nationalized
    private String phoneNumber;

    @Nationalized
    private String notes;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List <Pet> petIds;

    public void addPet(Pet pet){
        if(petIds == null){
            petIds = new ArrayList<>();
        }
        if (!petIds.contains(pet)) {
            petIds.add(pet);
        }

    }

}
