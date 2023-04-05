package assessment.server.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import assessment.server.models.Comment;
import assessment.server.models.Review;
import assessment.server.services.CommentService;
import assessment.server.services.MovieService;
import assessment.server.utils.Converters;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    MovieService mService;

    @Autowired
    CommentService cService;

    @GetMapping("/search")
    public ResponseEntity<String> getMovies(@RequestParam String query) {
        List<Review> reviews = mService.searchReviews(query);
        JsonObject json = Converters.toJson(reviews);

        return ResponseEntity.ok(json.toString());
    }

    @PostMapping(path = "/comment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> postComment(@RequestParam Map<String, String> comment) {

        Comment cObj = new Comment();
        cObj.setName(comment.get("name"));
        cObj.setTitle(comment.get("title"));
        cObj.setRating(Integer.parseInt(comment.get("rating")));
        cObj.setComment(comment.get("comment"));

        System.out.println(cObj);

        cService.saveComment(cObj);
        JsonObject response = Json.createObjectBuilder().add("response", "comment saved").build();
        return ResponseEntity.ok(response.toString());

    }

}
