package com.learning.fullappakka.actors

import akka.actor.AbstractActorWithTimers
import akka.actor.ActorRef
import akka.actor.Props
import java.time.Duration

class Poller(private val requestorActor: ActorRef, private val cryptoName: String?) : AbstractActorWithTimers() {
    override fun createReceive(): Receive {
        return receiveBuilder().match(
            FirstTick::class.java) { message: FirstTick? ->
            timers
                .startPeriodicTimer(TICK_KEY,
                    Tick(), Duration.ofSeconds(3))
        }.match(
            Tick::class.java
        ) { message: Tick? -> requestorActor.tell(PriceRequestor.GetThisCryptoPrice(cryptoName!!), self) }.build()
    }

    private class FirstTick
    private class Tick
    companion object {
        private val TICK_KEY: Any = "TickKey"
        fun props(cryptoName: String?, requestorActor: ActorRef): Props {
            return Props.create(Poller::class.java) { Poller(requestorActor, cryptoName) }
        }
    }

    init {
        timers.startSingleTimer(TICK_KEY, FirstTick(), Duration.ofMillis(3000))
    }
}


//class Poller(val requestorActor: ActorRef, val cryptoName: String): AbstractActorWithTimers() {
//
//    init {
//        timers.startSingleTimer(TICK_KEY, FirstTick(), Duration.ofMillis(3000))
//    }
//
//    override fun createReceive(): Receive {
//        return receiveBuilder().match(FirstTick::class.java) {
//            message -> timers.startTimerAtFixedRate(TICK_KEY, FirstTick(),Duration.ofSeconds(3) )
//        }.match(Tick::class.java) {
//            message -> requestorActor.tell(PriceRequestor.Companion.GetCryptoPrice(cryptoName),self)
//        }.build()
//    }
//
//    class FirstTick {
//
//    }
//     class Tick {
//
//     }
//
//    companion object {
//        val TICK_KEY = "TickKey"
//        fun props(cryptoName: String?, requestorActor: ActorRef?): Props {
//            return Props.create(Poller::class.java) { Poller(requestorActor!!, cryptoName!!) }
//        }
//    }
//
//}
