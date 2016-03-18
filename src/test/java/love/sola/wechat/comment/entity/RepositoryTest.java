package love.sola.wechat.comment.entity;

import love.sola.wechat.comment.AbstractIntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * ***********************************************
 * Created by Sola on 2016/3/17.
 * Don't modify this source without my agreement
 * ***********************************************
 */
public class RepositoryTest extends AbstractIntegrationTest {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CommentRepository commentRepository;

	@Before
	public void setup() {
		userRepository.save(new User("user1", "1", "tom", "foo"));
		userRepository.save(new User("user2", "2", "cat", "bar"));
		//Should Be 1-4
		commentRepository.save(new Comment(null, null, userRepository.findOne("user1"), null, "Test Comment"));
		commentRepository.save(new Comment(null, null, userRepository.findOne("user1"), null, "Test Comment"));
		commentRepository.save(new Comment(null, null, userRepository.findOne("user1"), null, "Test Comment"));
		commentRepository.save(new Comment(null, null, userRepository.findOne("user1"), null, "Test Comment"));
		//Should Be 5-7
		commentRepository.save(new Comment(null, commentRepository.findOne(1), userRepository.findOne("user1"), null, "Test Reply"));
		commentRepository.save(new Comment(null, commentRepository.findOne(1), userRepository.findOne("user1"), null, "Test Reply"));
		commentRepository.save(new Comment(null, commentRepository.findOne(2), userRepository.findOne("user2"), null, "Test Reply"));
	}

	@After
	public void clean() {
		commentRepository.deleteAll(); //delete comment repo first cause foreign keys
		userRepository.deleteAll();
	}

	@Test
	public void testDelete() {
		List<Comment> delList = commentRepository.deleteComment(1);
		assertTrue(delList.containsAll(
				Arrays.asList(
						new Comment(1, null, null, null, null),
						new Comment(5, null, null, null, null),
						new Comment(6, null, null, null, null)
				)
		));

		delList = commentRepository.deleteComment(2);
		assertTrue(delList.containsAll(
				Arrays.asList(
						new Comment(2, null, null, null, null),
						new Comment(7, null, null, null, null)
				)
		));
	}

	@Test
	public void testFetch() {
		List<Comment> fetchRoot = commentRepository.findRootComments(new PageRequest(0, 20)).getContent();
		assertTrue(fetchRoot.containsAll(
				Arrays.asList(
						new Comment(1, null, null, null, null),
						new Comment(2, null, null, null, null),
						new Comment(3, null, null, null, null),
						new Comment(4, null, null, null, null)
				)
		));

		List<Comment> fetchSub = commentRepository.findSubComments(
				Arrays.asList(
						commentRepository.findOne(1),
						commentRepository.findOne(2)
				)
		);
		assertTrue(fetchSub.containsAll(
				Arrays.asList(
						new Comment(5, null, null, null, null),
						new Comment(6, null, null, null, null),
						new Comment(7, null, null, null, null)
				)
		));
	}

}
