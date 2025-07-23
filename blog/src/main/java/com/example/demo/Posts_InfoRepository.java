package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Posts_InfoRepository extends JpaRepository<Posts_Info, Integer> {
	
	@Query("SELECT p FROM Posts_Info p WHERE user_id = :user_id")
	List<Posts_Info> findByUserId(@Param("user_id") String user_id);

}