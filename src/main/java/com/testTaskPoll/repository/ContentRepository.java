package com.testTaskPoll.repository;

import com.testTaskPoll.entity.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends CrudRepository<Content, Integer> {

    @Query(value = "select c.id, c.poll_id, c.question, c.created_at from contents as c, poll as p" +
            "where p.id = c.poll_id and p.hash = :hash ",nativeQuery = true)
    Content findContentByPollHash (@Param("hash") String pollHash);
}
