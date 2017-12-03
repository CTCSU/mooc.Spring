--秒杀执行存储过程
DELIMITER $$ --consol; 转换为$$
--定义存储过程
--参数 in 输入参数;out 输出参数
--row_count():返回上一条sql的影响行数
--row_count:0:未修改数据 <0 sql错误/未执行sql
delimiter //
CREATE PROCEDURE execute_seckill(
	in v_seckill_id BIGINT,in v_phone bigint,
	  in v_kill_time timestamp,out r_result int)
  begin
	  declare insert_count int default 0;
	  start Transaction;
	  insert ignore into success_killed
	  	(seckill_id,user_phone,create_time)
	  	values(v_seckill_id,v_phone,0);
	  select row_count() INTO insert_count;
	  IF(insert_count=0) then
	  	ROLLBACK;
	  	set r_result = -1;
	  ELSEIF(insert_count < 0) THEN
	  	ROLLBACK;
	  	SET R_RESULT = -2;
	  ELSE
	  	update seckill
	  	set number = number - 1
	  	where seckill_id = v_seckill_id
	  		and end_time > v_kill_time
	  		and start_time < v_kill_time
	  		and number > 0;
	  	select row_count() into insert_count;
	  	IF (insert_count = 0)THEN
	  		ROLLBACK;
	  		set r_result = 0;
	  	ELSEIF (insert_count<0)THEN
	  		ROLLBACK;
	  		SET r_result = -2;
	  	ELSE
	  		COMMIT;
	  		set r_result =1;
	  	End if;
	  END IF;
  end //
  
  DELIMITER ;
  set @r_result = -3;
  --执行存储过程
  call execute_seckill(9,15122563365,now(),@r_result);
  
  --获取结果
  select @r_result;
  