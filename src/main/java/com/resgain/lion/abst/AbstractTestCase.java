package com.resgain.lion.abst;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 统一的测试类父类
 * @author memphis.guo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
//@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class AbstractTestCase {

}
