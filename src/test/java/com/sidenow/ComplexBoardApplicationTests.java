package com.sidenow;

import com.sidenow.Repository.QuestionRepository;
import com.sidenow.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ComplexBoardApplicationTests {

	// DI기능으로 questionRepository 객체를 스프링이 자동 생성
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	// 데이터 저장
	void testSave(){
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);
	}

	@Test
	// 데이터 조회
	void testRead(){

		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());

		Optional<Question> oq = this.questionRepository.findById(3);
		if (oq.isPresent()) {
			Question q1 = oq.get();
			assertEquals("sbb가 무엇인가요?", q1.getSubject());
		}

		Question q2 = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(3, q2.getId());


		Question q3 = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(3, q3.getId());

		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q4 = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q4.getSubject());
	}

	@Test
	// 데이터 수정
	void testUpdate(){
		Optional<Question> oq = this.questionRepository.findById(3);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	// 데이터 삭제
	void testDelete() {

/*
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(3);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
*/
	}
}
