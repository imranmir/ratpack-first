import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {

        get("foo"){
            render "bar"
        }
        get("persons/:id") {
            render "I am person with id $pathTokens.id and your name is " + request.queryParams.name
        }


        path("fooMethods") {
            byMethod {
                get {
                    render "Hello, Foo Get!"
                }
                post {
                    render "Hello, Foo Post!"
                }
            }
        }

        prefix("products") {
            get("list") {
                render "Product List"
            }
        }

    }
}
