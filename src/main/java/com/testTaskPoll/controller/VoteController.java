package com.testTaskPoll.controller;

import com.testTaskPoll.PollApplication;
import com.testTaskPoll.entity.VotesWrapper;
import com.testTaskPoll.repository.OptionRepository;
import com.testTaskPoll.repository.PollRepository;
import com.testTaskPoll.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.testTaskPoll.entity.Poll;
import com.testTaskPoll.entity.Vote;
import com.testTaskPoll.util.Ajax;
import com.testTaskPoll.util.ExceptionHandlerController;
import com.testTaskPoll.util.RestException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@RestController
public class VoteController extends ExceptionHandlerController {
    private static final Logger LOG = LoggerFactory.getLogger(PollApplication.class);


    @Autowired
    @Qualifier("optionRepository")
    private OptionRepository optionRepository;

    @Autowired
    @Qualifier("voteRepository")
    private VoteRepository voteRepository;
    @Autowired
    @Qualifier("pollRepository")
    private PollRepository pollRepository;


    @RequestMapping(value = "/polls/{pollHash}/votes", method = RequestMethod.PUT)
    public Map<String,Object> createMutipleVotes(@PathVariable String pollHash, @Valid @RequestBody VotesWrapper wrapper,
                                                 HttpServletRequest request) throws RestException{

        try {
            Poll poll = pollRepository.findPoolByHash(pollHash);
            if (poll == null){
                return Ajax.errorResponse("You can't vote. Poll not found.");
            }

            if (poll.isClosed()){
                return Ajax.errorResponse("You can't vote. The poll was closed.");
            }

            System.out.println("!");

            for (Vote v:wrapper.getVotes()) {
                if (voteRepository.optionMatchesPoll(v.getOption().getId(),pollHash) == null){
                    System.out.println( v.getOption().getValue() + " vote doesn't matches poll.");
                } else voteRepository.save(v);
            }

            return Ajax.emptyResponse();
        }catch (Exception e){
            throw new RestException(e);
        }
    }


    @RequestMapping(value="/polls/{pollHash}/votes", method = RequestMethod.POST)
    public Map<String, Object> createVote(@PathVariable String pollHash, @Valid @RequestBody Vote vote,
                                          HttpServletRequest request) throws RestException{

        try {
           Poll poll = pollRepository.findPoolByHash(pollHash);
           if (poll == null){
               return Ajax.errorResponse("You can't vote. Poll not found.");
           }

           if (poll.isClosed()){
               return Ajax.errorResponse("You can't vote. The poll was closed.");
           }

           if (voteRepository.optionMatchesPoll(vote.getOption().getId(),pollHash) == null){
                return Ajax.errorResponse("You can't vote. This vote doesn't matches poll.");
           }

           vote = voteRepository.save(vote);

           LOG.info("Success vote");

           return Ajax.successResponse(optionRepository.findByVoteId(vote.getId().toString()));
       }catch (Exception e){
           throw new RestException(e);
       }
    }

    @RequestMapping(value="/polls/{pollHash}/votes", method=RequestMethod.GET)
    public	Map<String, Object>	getAllVotes(@PathVariable String pollHash){
        Iterable<Vote> votes = voteRepository.findByPoll(pollHash);
        Poll poll = pollRepository.findPoolByHash(pollHash);
        if (poll == null){
            return Ajax.errorResponse("No such poll found.");
        }
        if (!votes.iterator().hasNext()){
            return Ajax.errorResponse("Votes pull are empty.");
        }
        return Ajax.successResponse(votes);
    }
}
