package com.learning.fullappakka.model

class CoinBaseResponse {
    var data: Data? = null

    inner class Data {
        var base: String? = null
        var currency: String? = null
        var amount: String? = null
    }
}
