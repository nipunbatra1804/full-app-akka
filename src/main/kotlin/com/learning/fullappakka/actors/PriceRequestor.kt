package com.learning.fullappakka.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props
import com.learning.fullappakka.service.CoinBaseService

class PriceRequestor(private val printerActor: ActorRef, coinbaseService: CoinBaseService) : AbstractActor() {
    private val coinbaseService: CoinBaseService
    override fun createReceive(): Receive {
        return receiveBuilder().match(GetThisCryptoPrice::class.java) { what: GetThisCryptoPrice ->
            printerActor
                .tell(Printer.CryptoPrice(coinbaseService
                    .getCryptoPrice(what.whatPrice)), self)
        }.build()
    }

    class GetThisCryptoPrice(val whatPrice: String)
    companion object {
        fun props(printerActor: ActorRef, coinbaseService: CoinBaseService): Props {
            return Props.create(PriceRequestor::class.java
            ) { PriceRequestor(printerActor, coinbaseService) }
        }
    }

    init {
        this.coinbaseService = coinbaseService
    }
}


//class PriceRequestor(val printerActor: ActorRef, val coinBaseService: CoinBaseService): AbstractActor() {
//    override fun createReceive(): Receive {
//        return receiveBuilder().match(GetCryptoPrice::class.java) { what ->
//            printerActor.tell(Printer.CryptoPrice(coinBaseService.getCryptoPrice(what.whatPrice)), self)
//        }.build()
//    }
//    companion object {
//        fun props(printerActor: ActorRef?, coinBaseService: CoinBaseService): Props {
//            return Props.create(PriceRequestor::class.java) { PriceRequestor(printerActor!!, coinBaseService) }
//        }
//        class GetCryptoPrice(val whatPrice: String)
//    }
//
//
//}
