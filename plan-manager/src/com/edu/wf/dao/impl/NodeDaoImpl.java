package com.edu.wf.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.NodeDao;
import com.edu.wf.domin.ProcessNode;

/**
 *@author wangfei
 */
@Repository
public class NodeDaoImpl extends BaseDaoImpl<ProcessNode> implements NodeDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public List<ProcessNode> getAll(String nodeType) {
		List<ProcessNode> list = hibernateTemplate.find("from ProcessNode where nodeType = '"+nodeType+"' order by nodeNo asc");
		return list;
	}

}
