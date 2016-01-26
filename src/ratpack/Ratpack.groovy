import anaimal.Animal
import anaimal.AnimalModule
import anaimal.Zoo
import handlers.RouterHandler
import handlers.TestHandler
import ratpack.form.Form
import ratpack.groovy.template.MarkupTemplateModule

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module MarkupTemplateModule
        module AnimalModule
    }

    handlers {
        get('testAnimal') { Animal an ->
            render an.run()
        }

        get("zoo") { Zoo zoo ->
            render zoo.run()
        }

        get("persons/:id") {
            render "I am person with id $pathTokens.id and your name is " + request.queryParams.name
        }

        post("persons") {
            String s = ""
            parse(Form).map { Form f ->
                s += f.name
            }.then {
                render s
            }
        }
        put("zoo/:id") {
            String s = ""
            parse(Form).map { Form f ->
                s += f.name
            }.then {
                render s
            }
        }

        get('first', new TestHandler())

        get('router', new RouterHandler())
    }
}
