package dev.dubem.sportz.Run;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;


public interface RunRepository {

    List<Run> findAll();

    Optional<Run> findById(Integer id);

    void Create(Run run);

    void Update(Run run, Integer id);

    void Delete(Integer id);

    int count();

    void saveAll(List<Run> runs);

    List<Run> findByLocation(String location);
}
