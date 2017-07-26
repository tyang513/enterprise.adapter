package com.td.proxy.service.admin;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.td.proxy.entity.admin.Partner;
import com.td.proxy.page.admin.PartnerPage;
import com.td.proxy.service.admin.PartnerService;
import com.td.proxy.dao.admin.PartnerDao;



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
public class PartnerServiceTest {
	private static Object id;
	@Autowired
	private PartnerService partnerService;
	

	@Test
	public void testAdd(){
		try {
			Partner partner = new Partner();
			partnerService.insert(partner);
			id = partner.getId();
			System.out.println("-testAdd-----id---------"+id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testList(){
		try {
			PartnerPage partnerPage = new PartnerPage();
			List<Partner> partners = partnerService.queryByList(partnerPage);
			for(Partner e:partners){
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
			partnerService.deleteByPrimaryKey(id);
			System.out.println("---testDel-----------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
