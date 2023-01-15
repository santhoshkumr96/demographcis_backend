package com.aurum.demographics.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRequest {
    private String familyId;
    private int id;
    private int userId;
}
