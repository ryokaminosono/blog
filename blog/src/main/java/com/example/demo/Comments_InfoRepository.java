package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Comments_InfoRepository extends JpaRepository<Comments_Info, Integer> {
	
	@Query("SELECT c FROM Comments_Info c WHERE c.posts_id = :posts_id")
	List<Comments_Info> findByPostsId(@Param("posts_id") int postsid);
}
