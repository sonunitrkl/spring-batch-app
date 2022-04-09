package com.ninjas.world.springbatchapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sonu Kumar
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobParameterRequest {

    private String paramKey;

    private String paramValue;

}
