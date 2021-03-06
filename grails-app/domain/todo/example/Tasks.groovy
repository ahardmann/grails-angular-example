package todo.example

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode
import grails.rest.*

@Resource(uri='/tasks', formats=['json', 'xml'])
@EqualsAndHashCode
class Tasks {
	Long    id
	Date    dateCreated
	
	String name
	String description
	Boolean priority = false
	
    static constraints = {
		name(nullable: false, maxSize: 50)
		priority(nullable: false)
    }
	
	static mapping = {
		table 'TB_TASKS'
		name column: 'TKS_name', sqlType: 'VARCHAR(100)'
		description column: 'TKS_description'
		priority column: 'TKS_priority'
		dateCreated column: 'TKS_dt_created'
	}
}