package com.green.greengram.feed;

import com.green.greengram.feed.model.FeedPicDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test") // yaml 적용되는 파일 선택(application-test.yml)
@MybatisTest // Mybatis Mapper Test 이기 때문에 작성 >> Mapper 들이 전부 객체화(@Mapper 랑 같은 원리인듯?) >> DI 를 할 수 있다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedPicMapperTest {
    @Autowired
    private FeedPicMapper feedPicMapper;

    @Test
    void insFeedPicNoFeedIdThrowsException() {
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(10L);
        givenParam.setPics(new ArrayList<>(1));
        givenParam.getPics().add("a.jpg");

        assertThrows(DataIntegrityViolationException.class, () -> feedPicMapper.insFeedPic(givenParam));
        //DataIntegrityViolationException.class 는  foreign key 를 잡는 exception
        // 에러가 날 것이므로, 제대로 실행되야 맞는 것이다.
    }

//    @Test
//    void insFeedPicNoPicThrowNotNullException() {
//        FeedPicDto givenParam = new FeedPicDto();
//        givenParam.setFeedId(1L);
//
//        assertThrows(MyBatisSystemException.class, () -> feedPicMapper.insFeedPic(givenParam));
//    }

    @Test
    void insFeedPicNoPicThrowNotNullException() {
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>());

        assertThrows(BadSqlGrammarException.class, () -> feedPicMapper.insFeedPic(givenParam));
    }


    @Test
    void insFeedPicLongPicStringLengthThrowsException() { // 사진 길이가 너무 길면 에러가 터질거고, 에러가 터지면 테스트가 정상 작동될것.
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>(1));
        givenParam.getPics().add("_123456789_123456789_123456789_123456789_123456789_12");

        assertThrows(BadSqlGrammarException.class, () -> feedPicMapper.insFeedPic(givenParam));

    }

    @Test
    void insFeedPic(){
        String[] pics={"a.jpg","b.jpg", "c.jpg"};
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>(pics.length));

        for(String pic:pics){
            givenParam.getPics().add(pic);
        }

        int actualAffectedRows = feedPicMapper.insFeedPic(givenParam);

        assertEquals(givenParam.getPics().size(), actualAffectedRows);

    }
  
}