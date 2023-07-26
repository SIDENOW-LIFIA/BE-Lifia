package com.sidenow;

import com.sidenow.Repository.AnswerRepository;
import com.sidenow.Repository.QuestionRepository;
import com.sidenow.entity.Answer;
import com.sidenow.entity.Question;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ComplexBoardApplicationTests {

	// DI기능으로 questionRepository 객체를 스프링이 자동 생성
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	// 질문 데이터 저장
	void testSave(){
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("id는 자동으로 생성되나요?");
		q2.setContent("스프링부트 모델 질문입니다.");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	// 질문 데이터 조회
	void testRead(){

		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());

		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q1 = oq.get();
			assertEquals("sbb가 무엇인가요?", q1.getSubject());
		}

		Question q2 = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(2, q2.getId());


		Question q3 = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(2, q3.getId());

		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q4 = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q4.getSubject());
	}

	@Test
	// 질문 데이터 수정
	void testUpdate(){
		Optional<Question> oq = this.questionRepository.findById(3);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	// 질문 데이터 삭제
	void testDelete() {

		assertEquals(1, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
    // 답변 데이터 생성 후 저장
	void testAnswerSave() {
		Optional<Question> oq = this.questionRepository.findById(3);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q); // 어떤 질문의 답변인지 알기 위해 Question 객체가 필요
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
		// 답변 데이터 조회
	void testAnswerRead() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(3, a.getQuestion().getId());
	}

	@Test
		// 답변에 연결된 질문 찾기 & 질문에 달린 답변 찾기
	void testSearch() {
		Optional<Question> oq = this.questionRepository.findById(3);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}
