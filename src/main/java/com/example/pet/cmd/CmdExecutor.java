package com.example.pet.cmd;

import com.example.pet.entity.CommandResult;

public interface CmdExecutor {
    CommandResult execute(String... arguments);
}
