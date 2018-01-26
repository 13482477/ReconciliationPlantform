package com.yonyou.reconciliation.taskinstance.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yonyou.reconciliation.task.entity.DataType;
import com.yonyou.reconciliation.task.entity.TaskAttribute;

@Entity
@Table(name = "sys_task_attribute_instance")
public class TaskAttributeInstance implements Serializable {

	private static final long serialVersionUID = 1079457597764374940L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 属性名称
	 */
	private String attributeName;
	
	/**
	 * 显示名称
	 */
	private String viewName;
	
	/**
	 * 数据类型
	 */
	private DataType dataType;
	
	/**
	 * 属性值
	 */
	private String value;
	
	/**
	 * 任务属性定义
	 */
	@ManyToOne
	@JoinColumn(name = "task_attribute_id")
	private TaskAttribute taskAttribute;
	
	/**
	 * 任务实例
	 */
	@ManyToOne
	@JoinColumn(name = "task_instance_id")
	private TaskInstance taskInstance;
	
	/**
	 * 顺序
	 */
	private Integer sequence;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TaskAttribute getTaskAttribute() {
		return taskAttribute;
	}

	public void setTaskAttribute(TaskAttribute taskAttribute) {
		this.taskAttribute = taskAttribute;
	}

	public TaskInstance getTaskInstance() {
		return taskInstance;
	}

	public void setTaskInstance(TaskInstance taskInstance) {
		this.taskInstance = taskInstance;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
}
