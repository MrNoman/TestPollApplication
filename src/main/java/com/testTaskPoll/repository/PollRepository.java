package com.testTaskPoll.repository;

import com.testTaskPoll.entity.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.testTaskPoll.entity.Poll;

@Repository
public interface PollRepository extends CrudRepository<Poll, Integer> {

    @Query(value="SELECT p.id, p.title, p.hash, p.created_at, p.closed " +
            "FROM polls AS p " +
            "WHERE p.hash = :hash",
            nativeQuery	= true)
    Poll findPoolByHash(@Param("hash") String pollHash);

}
