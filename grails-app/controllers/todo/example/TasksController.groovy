package todo.example

import java.util.List;
import java.util.Map;

import grails.rest.RestfulController;

class TasksController extends RestfulController<Tasks>{
	def tasksService
	
	TasksController (){
		super(Tasks)
	}
	
	@Override
	protected List<Tasks> listAllResources(Map params) {
		return tasksService.findAllByFilter(params)
	}

	@Override
	protected Integer countResources() {
		return tasksService.countByFilter(params)
	}

}