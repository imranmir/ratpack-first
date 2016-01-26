import anaimal.Animal
import anaimal.AnimalModule
import anaimal.Zoo
import handlers.RouterHandler
import handlers.TestHandler
import ratpack.exec.Blocking
import ratpack.exec.Promise
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

        get("persons/:id") {
            render "I am person with id $pathTokens.id and your name is " + request.queryParams.name
        }

        prefix("channel") {
            get {
                Blocking.get {
                    new File("/Users/imran/projects/Ratpack/ratpack-first/build.gradle").text
                } then {
                    render it
                }
            }
        }

        get('write-usage') {
            Blocking.get {
                new File("/Users/imran/projects/Ratpack/ratpack-first/build.gradle").text.toUpperCase()
            } then {
                render it
            }
        }

        get('right-usage') {
            Blocking.get {
                new File("/Users/imran/projects/Ratpack/ratpack-first/build.gradle").text
            } then {
                render it.toUpperCase()
            }
        }

        get('second-example-reuse') {
            getReusablePromise().then {
                render it
            }
        }
        get('second-example-reuse-2') {
            getCombinedFilesPromise().then {
                render it.toUpperCase()
            }
        }
        get('second-example-reuse-3') {
            getCombinedFilesPromise2().then {
                render it.toUpperCase()
            }
            println "m herere"
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

def getReusablePromise() {
    Blocking.get {
        new File("/Users/imran/projects/Ratpack/ratpack-first/build.gradle").text
    }.map {
        it.toUpperCase()
    }
}

Promise<String> getCombinedFilesPromise() {
    Blocking.get {
        new File("/Users/imran/projects/Ratpack/ratpack-first/first.txt").text
    } flatMap { s1 ->
        Blocking.get {
            new File("/Users/imran/projects/Ratpack/ratpack-first/second.txt").text
        } map { s2 ->
            s1 + s2
        }
    }
}

Promise<String> getCombinedFilesPromise2() {
    Blocking.get {
        new File("/Users/imran/projects/Ratpack/ratpack-first/first.txt").text
    } flatMap { s1 ->

        Blocking.get {
            new File("/Users/imran/projects/Ratpack/ratpack-first/second.txt").text
        } flatMap { s2 ->

            Blocking.get {
                println "6"
                new File("/Users/imran/projects/Ratpack/ratpack-first/third.txt").text
            } map { s3 ->

                s1 + s2 + s3
            }
        }
    }
}
