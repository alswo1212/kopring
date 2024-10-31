package com.kopring

import com.kopring.domain.entity.Post
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class KopringApplicationTests {

	@Test
	fun contextLoads() {

	}

	@Test
	fun postOfTest(){
		val post = Post.of(1L, "title", "content", "1", "1234")
		print(post.toString())
	}

}
