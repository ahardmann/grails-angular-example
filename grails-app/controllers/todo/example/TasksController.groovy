package todo.example

import grails.rest.RestfulController;

class TasksController extends RestfulController{
	static responseFormats = ['json', 'xml']

	TasksController (){
		super(Tasks)
	}
}