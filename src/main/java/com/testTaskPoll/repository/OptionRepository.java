package com.testTaskPoll.repository;

import com.testTaskPoll.entity.Option;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends CrudRepository<Option, Integer> {

    @Query(value="SELECT o.id, o.poll_id, o.content_id, o.value, o.created_at " +
            "FROM options AS o " +
            "WHERE o.id = :voteId",
            nativeQuery	= true)
    Option findByVoteId(@Param("voteId") String id);
}
