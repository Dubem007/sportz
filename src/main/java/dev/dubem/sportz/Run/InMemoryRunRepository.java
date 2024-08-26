package dev.dubem.sportz.Run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryRunRepository implements RunRepository  {

    private static final Logger log = LoggerFactory.getLogger(InMemoryRunRepository.class);
    private final JdbcClient jdbcclient;
    private List<Run> runs= new ArrayList<>();

    public InMemoryRunRepository(JdbcClient jdbcclient)
    {
       this.jdbcclient = jdbcclient;
    }

    public List<Run> findAll()
    {
        return jdbcclient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer Id)
    {
        return jdbcclient.sql("select id, title,started_on,completed_on,miles,location from Run WHERE id = :id")
                .param("id", Id)
                .query(Run.class)
                .optional();
    }

    public void Create (Run run)
    {
        var updated =  jdbcclient.sql("insert into Run (id, title,started_on,completed_on,miles,location) values (?,?,?,?,?,?)")
                .params(List.of(run.id(),run.title(), run.startedOn(), run.completedOn(),run.miles(),run.location().toString()))
                .update();
        Assert.state(updated == 1, "Failed to crete Run" + run.title());
        //runs.add(run);
    }

    public void Update(Run run, Integer id) {

        var updated =  jdbcclient.sql("update Run set title = ?,started_on =?,completed_on = ?,miles = ?,location = ? WHERE id = ?")
                .params(List.of(run.id(),run.title(), run.startedOn(), run.completedOn(),run.miles(),run.location().toString(), id))
                .update();
        Assert.state(updated == 1, "Failed to udate Run" + run.title());
        // Optional<Run> existingRun = findById(id);

//        if (existingRun.isEmpty()) {
//            return; // No run found with the given ID
//        }
//
//        // Find the index of the existing run and replace it with the new run
//        int index = runs.indexOf(existingRun.get());
//        runs.set(index, run);  // Replace the existing run with the new one
    }

    public void Delete (Integer Id)
    {
        var updated =  jdbcclient.sql("delete from Run WHERE id = ?")
                .param("id",Id)
                .update();
        Assert.state(updated == 1, "Failed to delete Run" );
        //runs.removeIf(runs -> runs.id().equals(Id));
    }

    public int count() {

        return jdbcclient.sql("select * from run").query().listOfRows().size();//runs.size();
    }

    public void saveAll(List<Run> runs) {
        runs.stream().forEach(this::Create);
    }

    public List<Run> findByLocation(String location) {
        return jdbcclient.sql("select * from run where location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
//        return runs.stream()
//                .filter(run -> Objects.equals(run.location(), location))
//                .toList();
    }

    @PostConstruct
    private void init()
    {
        runs.add( new Run (1,"Monday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusHours(1),10,Location.INDOOR));
        runs.add( new Run (2,"Wednesday Evening Run", LocalDateTime.now(), LocalDateTime.now().plusHours(1),10,Location.INDOOR));
    }
}
