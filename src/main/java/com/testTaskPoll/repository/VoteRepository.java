package com.testTaskPoll.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.testTaskPoll.entity.Vote;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Integer> {

    @Query(value="SELECT v.id, v.option_id, v.created_at " +
            "FROM votes AS v, options AS o " +
            "WHERE o.poll_id = (SELECT p.id FROM polls AS p WHERE p.hash = :hash) " +
            "AND v.option_id = o.id",
            nativeQuery	= true)
    Iterable<Vote> findByPoll(@Param("hash") String pollHash);

    @Query(value="SELECT o.poll_id FROM options AS o " +
            "WHERE o.id = :option_id " +
            "AND o.poll_id = (SELECT p.id FROM polls AS p WHERE p.hash = :hash )",nativeQuery = true)
    Object optionMatchesPoll (@Param("option_id") int id, @Param("hash") String hash);


}
