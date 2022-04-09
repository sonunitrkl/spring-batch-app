package com.ninjas.world.springbatchapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sonu Kumar
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StudentCSV {
    private long id;
    private String firstName;
    private String lastName;
    private String email;


}
