package star.programmers.annaabi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import star.programmers.annaabi.database.Category;
import star.programmers.annaabi.database.CategoryRepository;
import star.programmers.annaabi.database.Vote;
import star.programmers.annaabi.database.VoteRepository;

import java.util.List;

@RestController
public class VoteController
{
    @Autowired
    VoteRepository voteRepository;

    @CrossOrigin
    @RequestMapping("/api/vote")
    public String vote(@RequestParam(value = "fileId") Long fileId, @RequestParam(value = "score") Integer score)
    {
        if (!isValidScore(score))
            return "Invalid score.";

        List<Vote> existingVoteList = voteRepository.findByFileIdAndAccountId(fileId, 58L);

        if (existingVoteList.size() == 1)
        {
            Vote existingVote = existingVoteList.get(0);
            existingVote.setScore(score);
            voteRepository.save(existingVote);
            return "Vote updated!";
        }

        // save new vote to database
        Vote vote = new Vote();
        vote.setAccountId(58L);
        vote.setFileId(fileId);
        vote.setScore(score);
        voteRepository.save(vote);

        return "Vote successful!";
    }

    public static boolean isValidScore(Integer score)
    {
        return score == -1 || score == 0 || score == 1;
    }
}
