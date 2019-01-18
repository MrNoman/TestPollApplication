package com.testTaskPoll.dto;

import com.testTaskPoll.entity.Vote;

import java.util.List;

public class VotesWrapper {
    private List<Vote> votes;

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
