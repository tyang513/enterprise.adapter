package com.td.proxy.service.admin;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.td.proxy.entity.admin.TaskService;
import com.td.proxy.page.admin.TaskServicePage;
import com.td.proxy.service.admin.TaskServiceService;
import com.td.proxy.dao.admin.TaskServiceDao;



/*
 * @TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true) 
 * transactionManager的默认取值是"transactionManager"，
 * defaultRollback的默认取值是true，当然，你也可以改成false。
 * true表示测试不会对数据库造成污染,false的话当然就会改动到数据库中了。
 * 在方法名上添加@Rollback(false)表示这个测试用例不需要回滚。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class TaskServiceServiceTest {
	private static Object id;
	@Autowired
	private TaskServiceService taskServiceService;
	

	@Test
	public void testAdd(){
		try {
			TaskService taskService = new TaskService();
			taskServiceService.insert(taskService);
			id = taskService.getId();
			System.out.println("-testAdd-----id---------"+id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testList(){
		try {
			TaskServicePage taskServicePage = new TaskServicePage();
			List<TaskService> taskServices = taskServiceService.queryByList(taskServicePage);
			for(TaskService e:taskServices){
				System.out.println(e.getId());
			}
			System.out.println("----testList----------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testDel(){
		try {
			taskServiceService.deleteByPrimaryKey(id);
			System.out.println("---testDel-----------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
