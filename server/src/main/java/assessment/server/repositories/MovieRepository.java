package assessment.server.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import assessment.server.models.Comment;

@Repository
public class MovieRepository {

    private static final String DB_COMMENTS = "comments";

    @Autowired
    MongoTemplate mongoTemplate;

    // TODO: Task 5
	// You may modify the parameter but not the return type
	// Write the native mongo database query in the comment below
	//

    /*
    db.comments.find({
    "title": "Avengers: Endgame"
    }).count()
     */

	public int countComments(String title) {
        Query query = Query.query(Criteria.where("title").is(title));
        int result =  (int)mongoTemplate.count(query, DB_COMMENTS);
        System.out.println(result);
        return result;

	}

    // TODO: Task 8
	// Write a method to insert movie comments comments collection
	// Write the native mongo database query in the comment below
	//

    /*
    db.comments.insertOne( {
    name: "Wanda Maximoff",
    title: "Avengers: Endgame",
    rating: 5,
    comment: "Slay of the century"
    */

    public void postComment(Comment comment) {

        Document doc = Comment.toDocument(comment);
        mongoTemplate.insert(doc, DB_COMMENTS);

    }


}
