package assessment.server.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import assessment.server.models.Review;
import assessment.server.repositories.MovieRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Converters {

    @Autowired
    MovieRepository mRepo;

    public static List<Review> toReview(String json) {

		List<Review> reviews = new ArrayList<>();

		JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject j = reader.readObject();
        JsonArray jj = j.getJsonArray("results").asJsonArray();

		jj.stream().forEach(
			v -> {
				JsonObject obj = v.asJsonObject();
				// System.out.println(obj);

				Review review = new Review();
				review.setTitle(obj.getString("display_title"));
				review.setRating(obj.getString("mpaa_rating"));
				review.setByline(obj.getString("byline"));
				review.setHeadline(obj.getJsonString("headline").toString());
				review.setSummary(obj.getString("summary_short"));
				review.setReviewURL(obj.getJsonObject("link").getString("url"));

                try {

                    JsonObject multimedia = obj.getJsonObject("multimedia");
                    review.setImage(multimedia.getString("src"));

                    
                } catch (Exception e) {
                    review.setImage("");
                }

				reviews.add(review);

			}
		);

		System.out.println(reviews);

		return reviews;


	}

    public static JsonObject toJson(List<Review> reviews) {
        JsonObjectBuilder json = Json.createObjectBuilder();
        JsonArrayBuilder arr = Json.createArrayBuilder();

        reviews.stream().forEach(
            v -> {
                JsonObjectBuilder j = Json.createObjectBuilder();
                j.add("title", v.getTitle())
                .add("rating", v.getRating())
                .add("byline", v.getByline())
                .add("headline", v.getHeadline())
                .add("summary", v.getSummary())
                .add("reviewUrl", v.getReviewURL())
                .add("image", v.getImage())
                .add("commentCount", v.getCommentCount());

                arr.add(j);
            }
        );

        json.add("results", arr);

        return json.build();


    }
    
}
