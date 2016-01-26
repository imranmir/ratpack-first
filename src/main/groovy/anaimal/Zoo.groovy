package anaimal

import com.google.inject.Inject

class Zoo {

    @Inject
    Animal animal


    String run(){
        return "${animal.run()}"
    }
}
