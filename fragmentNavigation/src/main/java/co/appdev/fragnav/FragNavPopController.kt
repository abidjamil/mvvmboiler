package co.appdev.fragnav

interface FragNavPopController {
    @Throws(UnsupportedOperationException::class)
    fun tryPopFragments(popDepth: Int, transactionOptions: FragNavTransactionOptions?): Int
}
