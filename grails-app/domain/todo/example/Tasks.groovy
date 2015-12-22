package todo.example

class Tasks {
	static belongsTo = [tasks: Tasks]
	
	String name
	String description
	Date date
	
    static constraints = {
		tasks(nullable: false)
		name(nullable: false, maxSize: 50)
    }
	
	static mapping = {
		table 'TB_TASKS'
		tasks column: 'TKS_id'
		name column: 'TKS_name', sqlType: 'VARCHAR(50)'
		description colmn: 'TKS_description'
		date column: 'TKS_dt'
	}
}