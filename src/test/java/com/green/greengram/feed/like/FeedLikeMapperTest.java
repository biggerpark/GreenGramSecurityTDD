package com.green.greengram.feed.like;

import com.green.greengram.feed.like.model.FeedLikeReq;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test") // yaml 적용되는 파일 선택(application-test.yml)
@MybatisTest // Mybatis Mapper Test 이기 때문에 작성 >> Mapper 들이 전부 객체화(@Mapper 랑 같은 원리인듯?) >> DI 를 할 수 있다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//테스트는 기본적으로 메모리 데이터베이스(H2) 를 사용하는데 메모리 데이터베이스로 교체하지 않겠다.
// 즉, 우리가 원래 쓰는 데이터베이스로 테스트를 진행하겠다.
@TestInstance(TestInstance.Lifecycle.PER_METHOD) // 메소드마다 객체를 만들겠다. 기본적으로 PER_METHOD 이므로 static 을 붙여서 전역변수에 설정된것도 쓸 의도로 static 을 사용

class FeedLikeMapperTest {
    @Autowired // DI를 받을 수 있도록 해주는 에노테이션
    private FeedLikeMapper feedLikeMapper; //필드 주입 방식의 DI 가 된다.
    static final long FEED_ID_1=1L;
    static final long FEED_ID_5=5L;
    static final long USER_ID_2=2L;

    static final FeedLikeReq existedData=new FeedLikeReq();
    static final FeedLikeReq notExistedData=new FeedLikeReq();

    /*


    @BeforeAll - 모든 테스트 실행 전에 최초 한번 실행
    ---
    @BeforeEach - 각 테스트 실행 전에 한번 실행
    @Test
    @AfterEach - 각 테스트 실행 후에 실행
    ---
    @AfterAll - 모든 테스트 실행 후에 최초 한번 실행


     */



    // @BeforeAll- 테스트 메소드 실행되기 최초 딱 한번 실행이 되는 메소드
    // 테스트 메소드 마다 테스트 객체가 만들어지면, BeforeAll 메소드는 static 메소드여야 한다.
    // 한 테스트 객체가 만들어지면 non-static 메소드여야한다.

    @BeforeAll
    static void initData(){ // static 이므로 모든 메소드에서 따로 객체 생성 안하고 적용 가능 메소드
        existedData.setFeedId(FEED_ID_1);
        existedData.setUserId(USER_ID_2);

        notExistedData.setFeedId(FEED_ID_5);
        notExistedData.setUserId(USER_ID_2);
    }


    // @BeforeEach- 테스트 메소드 마다 테스트 메소드 실행 전에 실행되는 before 메소드
    @Test
    void insFeedLikeDuplicateDataThrowDuplicateKeyException() {
        //중복된 데이터 입력시 DuplicateKeyException 발생 체크
        //given(준비)
//        FeedLikeReq givenParam = new FeedLikeReq(); // mapper 에 insFeedLike 메소드의 매개변수 객체화
//        givenParam.setFeedId(FEED_ID_1);
//        givenParam.setUserId(USER_ID_2);



        // when(실행)
        //then(단언,체크)
        //에러가 나면 정상적으로 작동
        // 에러가 안나면 에러 메시지가 뜰거임
        assertThrows(DuplicateKeyException.class, () -> {
            feedLikeMapper.insFeedLike(existedData);
        });



//
//        int actualAffectedRows=feedLikeMapper.insFeedLike(givenParam);
//
//        assertEquals(1, actualAffectedRows); // 1이 나오는지 확인, 1 이 나오면 test 통과
    }


    @Test
    void insFeedLikeNormal(){
        //given(준비)
//        FeedLikeReq givenParam = new FeedLikeReq(); // mapper 에 insFeedLike 메소드의 매개변수 객체화
//        givenParam.setFeedId(FEED_ID_5);
//        givenParam.setUserId(USER_ID_2);

        //when
        int actualAffectedRows=feedLikeMapper.insFeedLike(notExistedData);

        // then
        assertEquals(1, actualAffectedRows,"insert 문제 발생"); // 1이 나오는지 확인, 1 이 나오면 test 통과
    }

    @Test
    void delFeedLikeNoData(){
//        FeedLikeReq givenParam = new FeedLikeReq();
//        givenParam.setFeedId(FEED_ID_5);
//        givenParam.setUserId(USER_ID_2);

        int actualAffectedRows=feedLikeMapper.delFeedLike(notExistedData);
        assertEquals(0, actualAffectedRows);
    }

    @Test
    void delFeedLikeNormal(){ // 실제로는 db 는 안지워진다. 왜냐하면 메소드 실행하고 rollback 된다.
        int actualAffectedRows=feedLikeMapper.delFeedLike(existedData);
        assertEquals(1, actualAffectedRows);
    }


}