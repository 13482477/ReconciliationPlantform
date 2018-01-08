package com.yonyou.reconciliation.icon.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yonyou.reconciliation.Application;
import com.yonyou.reconciliation.icon.entity.Icon;
import com.yonyou.reconciliation.icon.entity.IconType;
import com.yonyou.reconciliation.icon.repository.IconRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class IconRepositoryTest {
	
	@Autowired
	private IconRepository iconRepository;
	
	@Test
	public void findByIconTypeTest() {
		List<Icon> icons = this.iconRepository.findByIconType(IconType.FONT_AWESOME);
		
		Assert.assertTrue(icons.size() >= 0);
	}

}
