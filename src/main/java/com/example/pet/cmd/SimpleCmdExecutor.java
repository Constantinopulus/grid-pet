package com.example.pet.cmd;

import com.example.pet.entity.CommandResult;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SimpleCmdExecutor implements CmdExecutor {

    @Override
    public CommandResult execute(final String... arguments) {
        final ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(arguments);
        Process process = null;
        try {
            process = processBuilder.start();

            final int exitCode = process.waitFor();
            List<String> stdOut = readFromProcess(process.getInputStream());
            List<String> stdErr = readFromProcess(process.getErrorStream());

            return new CommandResult(stdOut, exitCode, stdErr);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (process != null && process.isAlive()) {
                process.destroy();
            }
        }
    }

    private List<String> readFromProcess(InputStream inputStream) throws IOException {
        try (BufferedReader stdReader =
                     new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return stdReader.lines()
                    .collect(Collectors.toList());
        }
    }
}
