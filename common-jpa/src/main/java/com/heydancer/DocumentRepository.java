package com.heydancer;

import com.heydancer.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT doc " +
            "FROM Document AS doc " +
            "WHERE (cast(:start as date) is null OR doc.created >= cast(:start as date)) " +
            "AND (cast(:end as date) is null OR doc.created <= cast(:end as date))")
    List<Document> findAll(LocalDate start, LocalDate end);


}
