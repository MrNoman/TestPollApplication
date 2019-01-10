package com.testTaskPoll.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.testTaskPoll.PollApplication;
import com.testTaskPoll.repository.PollRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.testTaskPoll.entity.Poll;
import com.testTaskPoll.util.Ajax;
import com.testTaskPoll.util.ExceptionHandlerController;
import com.testTaskPoll.util.RestException;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class PollController extends ExceptionHandlerController {

    private static final Logger LOG = LoggerFactory.getLogger(PollApplication.class);

    @Autowired
    @Qualifier("pollRepository")
    private PollRepository pollRepository;

    @RequestMapping(value = "/polls", method = RequestMethod.GET)
    public Map<String, Object> getAllPolls() throws RestException{
        try{
            Iterable<Poll> allPolls = pollRepository.findAll();
            for(Poll poll : allPolls){
                updateLinks(poll);
            }
            if (!allPolls.iterator().hasNext()){
                return Ajax.errorResponse("No pools found.");
            }
            return Ajax.successResponse(allPolls);
        }catch (Exception e){
            throw new RestException(e);
        }
    }

    @RequestMapping(value="/polls",	method=RequestMethod.POST)
    public Map<String, Object> createPoll(@Valid @RequestBody Poll poll) throws RestException{
        try {
            System.out.println(poll.getTitle() + poll.getOptions() + poll.getContent());

            poll = pollRepository.save(poll);
            String hash = getHashValue(poll.getPollId(),poll.getTitle(), poll.getCreatedAt().toString());
            poll.setHash(hash);
            pollRepository.save(poll);
            updateLinks(poll);

            LOG.warn(poll + " poll was created.");
            return Ajax.successResponse(poll);
        } catch (Exception e){
            throw new RestException(e);
        }
    }

    @RequestMapping(value="/polls/{pollHash}", method=RequestMethod.GET)
    public Map<String, Object> getPoll(@PathVariable String pollHash) throws RestException{
        try {

            if (pollHash == null){
                return Ajax.errorResponse("Incorrect data");
            }
            Poll poll = pollRepository.findByHash(pollHash);

            if (poll == null){
                return Ajax.errorResponse("No such poll found");
            }
            updateLinks(poll);
            return Ajax.successResponse(poll);
        } catch (Exception e){
            throw new RestException(e);
        }
    }


    @RequestMapping(value = "/close/{pollHash}", method = RequestMethod.PUT)
    public Map<String, Object> closePoll(@PathVariable String pollHash) throws RestException{
        try {
            if (pollHash == null){
                return Ajax.errorResponse("Incorrect data");
            }
            Poll poll = pollRepository.findByHash(pollHash);
            if (poll == null){
                return Ajax.errorResponse("No such poll found");
            }
            poll.setClosed(true);
            pollRepository.save(poll);
            LOG.warn(poll + " poll was closed.");
            return Ajax.emptyResponse();
        } catch (Exception e){
            throw new RestException(e);
        }
    }

    private void updateLinks(Poll poll) throws RestException{
        poll.add(linkTo(methodOn(PollController.class).getAllPolls()).slash(poll.getHash()).withSelfRel());
        poll.add(linkTo(methodOn(VoteController.class).getAllVotes(poll.getHash())).withRel("vote"));
        poll.add(linkTo(methodOn(ResultsController.class).getResults(poll.getHash())).withRel("results"));
        poll.add(linkTo(methodOn(PollController.class).closePoll(poll.getHash())).withRel("close-poll"));
    }

    private String getHashValue(Integer pollId, String title, String timestamp) throws RestException{
        StringBuilder hashedValue = null;
        String allValues = pollId + title + timestamp;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(allValues.getBytes("UTF-8"));
            hashedValue = new StringBuilder();
            for (int i: hash) {
                hashedValue.append(Integer.toHexString(0XFF & i));
            }
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
            throw new RestException(e);
        }
        return hashedValue.toString();
    }
}
