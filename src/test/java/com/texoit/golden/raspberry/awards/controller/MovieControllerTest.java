package com.texoit.golden.raspberry.awards.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.texoit.golden.raspberry.awards.vo.IntervalWinMinMaxVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class MovieControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate; 

	@Test
	void getIntervalTest() {
		ResponseEntity<IntervalWinMinMaxVO> responseEntity = testRestTemplate. getForEntity("/movies/interval/winner", IntervalWinMinMaxVO.class);
		Assertions.assertNotNull(responseEntity);
		
		IntervalWinMinMaxVO intervalWinMinMaxVO = responseEntity.getBody();
		Assertions.assertNotNull(intervalWinMinMaxVO);
		
		Assertions.assertEquals(intervalWinMinMaxVO.getMin().get(0).getProducer(), "Joel Silver");
		Assertions.assertEquals(intervalWinMinMaxVO.getMax().get(0).getProducer(), "Matthew Vaughn");
	}

}
