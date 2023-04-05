package assessment.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import assessment.server.models.Review;
import assessment.server.repositories.MovieRepository;
import assessment.server.utils.Converters;

@Service
public class MovieService {

	@Value("${nyt.api.key}")
	private String apiKey;

	@Autowired
	MovieRepository mRepo;

	private static final String NYT_API = "https://api.nytimes.com/svc/movies/v2/reviews/search.json";

	// TODO: Task 4
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	public List<Review> searchReviews(String query) {

		String nq = query.replace(" ", "%20");

		String url = UriComponentsBuilder.fromUriString(NYT_API)
		.queryParam("query", query)
		.queryParam("api-key", apiKey)
		.toUriString();

		RequestEntity<Void> req = RequestEntity.get(url)
        .accept(MediaType.APPLICATION_JSON).build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

		try {
			resp = template.exchange(req, String.class);
			System.out.println(mRepo.countComments("Avengers: Endgame"));

			List<Review> reviews = Converters.toReview(resp.getBody());
			reviews.stream().forEach(
				r -> {
					String title = r.getTitle();
					System.out.println(title);
					int count = mRepo.countComments(title);
					r.setCommentCount(count);
				}
			);

			return reviews;

		} catch (Exception e) {
			return null;
		}
		// return null;
	}

}
