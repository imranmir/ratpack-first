import anaimal.AnimalModule
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.form.Form
import ratpack.groovy.template.MarkupTemplateModule

import static groovy.json.JsonOutput.prettyPrint
import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

class SimpleConfig {
    String message
}

ratpack {
    bindings {
        module MarkupTemplateModule
        module AnimalModule

        final ConfigData configData = ConfigData.of { ConfigDataBuilder builder ->
            builder.args(args)
        }
        bindInstance(SimpleConfig, configData.get('/simple', SimpleConfig))
    }

    handlers {
        get('config') { SimpleConfig config ->
            render(prettyPrint(toJson(config)))
        }

        get('renderGtpl') {
            render groovyMarkupTemplate("profile.gtpl",
                    name: request.queryParams.name,
                    title: "PROFILE",
                    hobbies: ['flying', 'skieing', 'swimming', 'reading'])
        }

        get('renderjson') {
            render json([name: 'imran'])
        }

        post("persons") {
            String s = ""
            parse(Form).map { Form f ->
                s += f.name
            }.then {
                render s
            }
        }


    }
}
