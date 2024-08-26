package dev.dubem.sportz.Run;

import jakarta.validation.Valid;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/run")
public class RunController {

    private final InMemoryRunRepository runRepository;

    public RunController(InMemoryRunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("/runs")
    public List<Run> findAll()
    {
        return runRepository.findAll();
    }

    @GetMapping("/{Id}")
    public Run findById(@PathVariable Integer Id)
    {
        Optional<Run> run = runRepository.findById(Id);
        if(run.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found");
        }
        return run.get();
    }

    //create
    @PostMapping("/runs")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRun(@Valid @RequestBody Run run)
    {
        runRepository.Create(run);
    }

    //put
    @PutMapping("/{Id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createRun(@Valid @RequestBody Run run, @PathVariable Integer Id)
    {
        runRepository.Update(run,Id);
    }

    //delete
    @DeleteMapping("/{Id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createRun(@PathVariable Integer Id)
    {
        runRepository.Delete(Id);
    }
}
