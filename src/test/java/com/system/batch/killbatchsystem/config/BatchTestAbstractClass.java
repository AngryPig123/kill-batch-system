package com.system.batch.killbatchsystem.config;

import org.springframework.test.context.jdbc.Sql;

/**
 * packageName    : com.system.batch.killbatchsystem.config
 * fileName       : BatchInitSqlClass
 * author         : AngryPig123
 * date           : 26. 1. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 26. 1. 11.        AngryPig123       최초 생성
 */
@Sql(
        scripts = "classpath:batch-cleanup.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public abstract class BatchTestAbstractClass {
}
