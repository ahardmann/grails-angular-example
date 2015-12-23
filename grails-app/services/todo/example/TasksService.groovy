package todo.example

import grails.transaction.Transactional

@Transactional
class TasksService {

    @Transactional(readOnly = true)
    def findAllByFilter(params = [:]) {
        if (!params.nolimit) {
            params.max = params.max ?: 20
            params.offset = params.offset ? params.int('offset') : 0
        }
        params.sort = params.sort ?: 'date'
        params.order = params.order ?: 'desc'

        def criteria = Tasks.createCriteria()
        def results = criteria.list() {
            if (params.max) {
                maxResults(params.max)
            }
            if (params.offset) {
                firstResult(params.offset)
            }
            if (params.sort && params.order) {
                order(params.sort, params.order)
            }
           if (params.search) {
                def filter = mountSearchFilter(params)
                filter.delegate = delegate
                filter()
            } else {
                def filter = mountFindFilter(params)
                filter.delegate = delegate
                filter()
            }
        }

        return results
    }

    @Transactional(readOnly = true)
    def countByFilter(params = [:]) {
        return Tasks.createCriteria().count() {
           if (params.search) {
                def filter = mountSearchFilter(params)
                filter.delegate = delegate
                filter()
            } else {
				def filter = mountFindFilter(params)
                filter.delegate = delegate
                filter()
            }
        }
    }
	
	private mountFindFilter(params) {
		return {
			 if (params.name) {
                ilike('name', "%$params.name%")
            }
		}
	}

	private mountSearchFilter(params) {
		return {
			or {
				 ilike('name', "%$params.search%")
			}
		}
	}
}
