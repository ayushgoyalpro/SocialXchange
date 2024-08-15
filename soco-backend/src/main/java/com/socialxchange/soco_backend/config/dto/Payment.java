package com.socialxchange.soco_backend.config.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {
    private Long businessId;
    private float amount;
}
