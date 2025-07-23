package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Like_InfoRepository extends JpaRepository<Like_Info, Integer> {
	
	@Query("SELECT l FROM Like_Info l WHERE l.reg_user_id = :user_id AND l.posts_id = :posts_id")
	List<Like_Info> findBylikerecord(@Param("user_id") String userid, @Param("posts_id") int postsid);
	
	@Query("SELECT COUNT(l) FROM Like_Info l WHERE l.posts_id = :posts_id")
	Integer countLikesByPostId(@Param("posts_id") int postsid);
}
