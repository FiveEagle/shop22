package com.example.pmproject;

import com.example.pmproject.Service.GlobalService;
import com.example.pmproject.Service.MemberService;
import com.example.pmproject.Service.PmService;
import com.example.pmproject.Service.PmUseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PmProjectApplicationTests {
    @Autowired
    GlobalService globalService;
    @Autowired
    MemberService memberService;
    @Autowired
    PmService pmService;
    @Autowired
    PmUseService pmUseService;
    @Autowired



    @Test
    void memberRegisterTest() {

    }

}
