package handlers


import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler
import ratpack.handling.Handler

class RouterHandler extends GroovyHandler{

    @Override
    protected void handle(GroovyContext context){
        if(context.request.path=='router') {
            context.insert(new TestHandler())
        }else{
            context.response.send("Not routed handler")
        }
    }
}
