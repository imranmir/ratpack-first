import anaimal.Animal
import anaimal.AnimalModule
import anaimal.Zoo
import handlers.RouterHandler
import handlers.TestHandler
import ratpack.exec.Blocking
import ratpack.exec.ExecController
import ratpack.exec.Promise
import ratpack.form.Form
import ratpack.func.Pair
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.handling.Context

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module MarkupTemplateModule
        module AnimalModule
    }

    handlers {

        get('async1') {
            println "async 1111"
            Blocking.get {
                println "222"
                new File("/Users/imran/projects/Ratpack/ratpack-first/build.gradle").text // returns string
            }then {
                println "44"
                render it // it is the return of getValueFromDb()
                println "555"
            }
            println "666"
        }

        prefix('async2') {
            get {
                Blocking.get {
                    println "11"
                    new File("/Users/imran/projects/Ratpack/ratpack-first/first.txt").text // returns string
                } then {
                    println "222"
                    render it
                }
                sleep 3000
                println "4"
            }
        }

        get('async5') {
            def l = []
            Blocking.get {
                 return Thread.start {
                    sleep 3000
                }
            } then {
                l << it
            }
            Blocking.get {
                return Thread.start {
                    sleep 2000
                }
            } then {
                l << it
            }
            Blocking.get {
                return Thread.start {
                    sleep 1000
                }
            }then {
                l << it
                render l.join(",")
            }
        }

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

        get ('pair') {
            getPromiseWithPair().then {
                render it.left + " " + it.right
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

Promise<String> getPromiseWithPair() {
    Blocking.get {
        new File("/Users/imran/projects/Ratpack/ratpack-first/first.txt").text
    } flatMap { s1 ->
        Blocking.get {
            new File("/Users/imran/projects/Ratpack/ratpack-first/second.txt").text
        } map { s2 ->
            Pair.of(s1, s2)
        }
    }
}
