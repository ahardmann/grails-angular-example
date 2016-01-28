import todo.example.Tasks
import grails.converters.JSON
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
		def result = '################## running in UNCLEAR mode.'
		println "Application starting ... "
		switch (Environment.current) {
			case Environment.DEVELOPMENT:
				result = 'now running in DEV mode.'
				seedTestData()
				break;
			case Environment.TEST:
				result = 'now running in TEST mode.'
				break;
			case Environment.PRODUCTION:
				result = 'now running in PROD mode.'
				break;
		}
		println "current environment: $Environment.current"
		println "$result"
		registerMarshallers()
    }
	
    def destroy = {
    }
	
	private void seedTestData() {
		def tasksController
		
		def task = null
		println "Start loading tasks into database"
		task = new Tasks(name: 'TESTE', description: "DESC", priority: 'true')
		assert task.save(flush:true)
 
		task = new Tasks(name: 'TESTE 2', description: "DESC2", priority: 'true')
		assert task.save(flush:true)
		
		def tasks = task.list(flush:true)
		
		println "Tasks " + tasks
		println "Finished loading $Tasks.count tasks into database"
	}
	
	def registerMarshallers() {
		JSON.registerObjectMarshaller(Tasks){
			def returnMap = [:]
			returnMap.id = it.id
			returnMap.name = it.name
			returnMap.description = it.description
			returnMap.priority = it.priority
			returnMap.dateCreated =  it.dateCreated
			
			return returnMap
		}
	}
}
