plugins {
    id("br.dev.pedrolamarao.metal.base2")
    id("br.dev.pedrolamarao.metal.c")
}

metal {
    cpp {
        sources {
            create("main")
        }
    }
    c {
        sources {
            create("main")
        }
    }
    applications {
        create("main") {
            source( c.sources.named("main").map { it.outputs } )
        }
    }
}