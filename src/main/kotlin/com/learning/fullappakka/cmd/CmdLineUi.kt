package com.learning.fullappakka.cmd

import akka.actor.ActorRef
import akka.actor.ActorSystem
import com.learning.fullappakka.actors.Poller
import com.learning.fullappakka.actors.PriceRequestor
import com.learning.fullappakka.actors.Printer
import com.learning.fullappakka.service.CoinBaseService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class CmdLineUi : CommandLineRunner {

    @Autowired
    private lateinit var coinBaseService: CoinBaseService

    @Throws(Exception::class)
    override fun run(vararg args: String?) {

        val actorSystem: ActorSystem = ActorSystem.create("hellooakka")
        println("Linkedin learning reactive programming with java 8")

        val printerActor  = actorSystem.actorOf(Printer.props(), "printerActor")
        val priceRequestor =  actorSystem.actorOf(PriceRequestor.props(printerActor, coinBaseService), "requestor")
        val poller: ActorRef = actorSystem.actorOf(Poller.props("BTC-USD", priceRequestor), "poller")

    }
}
