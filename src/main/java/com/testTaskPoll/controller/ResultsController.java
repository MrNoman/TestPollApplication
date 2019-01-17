package com.testTaskPoll.controller;

import com.testTaskPoll.entity.Poll;
import com.testTaskPoll.repository.PollRepository;
import com.testTaskPoll.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.testTaskPoll.entity.Vote;
import com.testTaskPoll.dto.OptionCount;
import com.testTaskPoll.dto.VoteResult;
import com.testTaskPoll.util.Ajax;
import com.testTaskPoll.util.ExceptionHandlerController;
import com.testTaskPoll.util.RestException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ResultsController extends ExceptionHandlerController{

    @Autowired
    @Qualifier("voteRepository")
    private VoteRepository voteRepository;

    @Autowired
    @Qualifier("pollRepository")
    private PollRepository pollRepository;

    @RequestMapping(value="/results/{hash}", method= RequestMethod.GET)
    public Map<String, Object> getResults(@PathVariable("hash") String pollHash) throws RestException{

        Poll poll = pollRepository.findPoolByHash(pollHash);
        if (poll == null){
            return Ajax.errorResponse("No such poll found");
        }
        try{
            VoteResult voteResult = new VoteResult();
            Iterable<Vote> allVotes = voteRepository.findByPoll(pollHash);
            int	totalVotes = 0;
            Map<Integer, OptionCount> tempMap = new HashMap<>();
            for(Vote v : allVotes) {
                totalVotes++;
                OptionCount	optionCount = tempMap.get(v.getOption().getId());
                if(optionCount	==	null) {
                    optionCount	= new OptionCount();
                    optionCount.setOptionId(v.getOption().getId());
                    optionCount.setOptionValue(v.getOption().getValue());
                    optionCount.setQuestionId(v.getOption().getContent().getId());
                    optionCount.setQuestionValue(v.getOption().getContent().getQuestion());
                    tempMap.put(v.getOption().getId(), optionCount);
                }
                optionCount.setCounter(optionCount.getCounter()+1);
            }
            voteResult.setTotalVotes(totalVotes);
            voteResult.setResults(tempMap.values());
            return Ajax.successResponse(voteResult);
        }catch (Exception e){
            throw new RestException(e);
        }
    }
}
