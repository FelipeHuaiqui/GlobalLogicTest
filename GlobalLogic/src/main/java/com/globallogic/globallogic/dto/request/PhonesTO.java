package com.globallogic.globallogic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhonesTO {
    private long number;
    private int citycode;
    private String countrycode;
}
