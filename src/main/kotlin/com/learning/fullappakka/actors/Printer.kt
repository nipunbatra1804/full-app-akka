package com.learning.fullappakka.actors

import akka.actor.AbstractActor
import akka.actor.Props
import com.learning.fullappakka.model.CoinBaseResponse
import reactor.core.publisher.Mono
import java.time.LocalDateTime

class Printer: AbstractActor() {
    override fun createReceive(): Receive? {
        return receiveBuilder().match(CryptoPrice::class.java) { msg: CryptoPrice ->
            msg.message.subscribe { coinBaseResponse: CoinBaseResponse ->
                println("[" + LocalDateTime.now() + "]"
                    + coinBaseResponse.data!!.base
                    + "Buy Price: $" + coinBaseResponse.data!!.amount
                    + " " + coinBaseResponse.data!!.currency)
            }
        }.build()
    }
    class CryptoPrice(val message: Mono<CoinBaseResponse>)


    companion object {
        fun props(): Props {
            return Props.create(Printer::class.java) {Printer()}
        }
    }
}
