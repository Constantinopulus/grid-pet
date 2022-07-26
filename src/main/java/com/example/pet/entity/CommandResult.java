package com.example.pet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CommandResult {
    private List<String> stdOut;
    private int exitCode;
    private List<String> stdErr;
}
