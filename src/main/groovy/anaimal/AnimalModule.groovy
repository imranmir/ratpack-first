package anaimal

import com.google.inject.AbstractModule
import com.google.inject.Scopes

class AnimalModule extends AbstractModule{


    void configure(){
        bind(Animal).to(Lion)
        bind(Zoo)
    }
}
