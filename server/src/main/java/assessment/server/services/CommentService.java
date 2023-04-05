package assessment.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import assessment.server.models.Comment;
import assessment.server.repositories.MovieRepository;

@Service
public class CommentService {

    @Autowired
    MovieRepository mRepo;

    public void saveComment(Comment comment) {

        mRepo.postComment(comment);
        
    }
    
}
