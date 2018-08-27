package iconfoundationtest1

import foundation.icon.icx.IconService
import foundation.icon.icx.KeyWallet
import foundation.icon.icx.Wallet
import foundation.icon.icx.transport.http.HttpProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.web3j.crypto.CipherException

class BootStrap {

    def iconmainService
    def init = { servletContext ->
        def walletMap = WalletOperations.loadWalletFromFile("C:\\Users\\morga\\Desktop\\Temp\\ICONFoundationTest1\\UTC--2018-08-24T13-35-41.671000000Z--hx65f6e18d378b57612a28f72acb97021eaa82aa5a.json")
        println "walletMap = $walletMap"
        String walletAddress = walletMap['address'] as String
        KeyWallet currentWallet = walletMap['wallet'] as KeyWallet
        IconConfiguration.putAddress(walletAddress, currentWallet)
        iconmainService.load()
    }
    def destroy = {
    }
}
