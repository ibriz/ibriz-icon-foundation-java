package ibriz.iconfoundation

import foundation.icon.icx.KeyWallet

class BootStrap {

    def iconmainService
    def init = { servletContext ->
        def walletMap = WalletOperations.loadWalletFromFile("C:\\Users\\morga\\Desktop\\Temp\\ibriz-icon-foundation-java\\UTC--2018-08-24T13-35-41.671000000Z--hx65f6e18d378b57612a28f72acb97021eaa82aa5a.json")
        println "walletMap = $walletMap"
        String walletAddress = walletMap['address'] as String
        KeyWallet currentWallet = walletMap['wallet'] as KeyWallet
        IconConfiguration.putAddress(walletAddress, currentWallet)
        iconmainService.load()
    }
    def destroy = {
    }
}
