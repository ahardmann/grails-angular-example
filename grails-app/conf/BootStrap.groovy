import todo.example.Tasks

class BootStrap {

    def init = { servletContext ->
    }
	
    def destroy = {
    }
	
	def registerMarshallers() {
		JSON.registerObjectMarshaller(Tasks){
			def returnMap = [:]
			returnMap.name = it.name
			returnMap.description = it.description
			returnMap.date =  it.date
			
			return returnMap
		}
		
	}
}
