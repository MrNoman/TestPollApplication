package com.testTaskPoll.repository;

import com.testTaskPoll.entity.Content;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<Content, Integer> {
}
