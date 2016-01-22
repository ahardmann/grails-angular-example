package todo.example

class Tasks {
	static belongsTo = [tasks: Tasks]
	
	String name
	String description
	Boolean priority = false
//	Boolean wasRead = false
	Date date
	
    static constraints = {
		tasks(nullable: false)
		name(nullable: false, maxSize: 50)
		priority(nullable: false)
//		wasRead(nullable: false)
    }
	
	static mapping = {
		table 'TB_TASKS'
		tasks column: 'TKS_id'
		name column: 'TKS_name', sqlType: 'VARCHAR(50)'
		description column: 'TKS_description'
		priority column: 'TKS_priority'
//		wasRead column: 'TKS_wasRead'
		date column: 'TKS_dt'
	}
}