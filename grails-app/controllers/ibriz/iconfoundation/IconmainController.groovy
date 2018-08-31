package ibriz.iconfoundation

import com.google.gson.Gson
import foundation.icon.icx.KeyWallet
import org.grails.web.json.JSONObject

class IconmainController {
    public final String URL = "http://192.168.1.9:9000/api/v3";

    def iconmainService

//    String scoreAddress = "cx02b13428a8aef265fbaeeb37394d3ae8727f7a19";
//    String tokenSymbol = "MNT"
//    String toAddStr = "hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31";

    Map<String, String> scoreMap = new HashMap<String, String>() {
        {
            put("MNT", "cxb8f2c9ba48856df2e889d1ee30ff6d2e002651cf")
            put("IDOL", "cx0bce5bfe899c4beec7ea93f2000e16351191017e")
        }
    }

    def index() {}

    def myWallet(params) {
        params.address = "hx65f6e18d378b57612a28f72acb97021eaa82aa5a"
        String scoreAddress = scoreMap.getOrDefault(params.tokenType, "cx0bce5bfe899c4beec7ea93f2000e16351191017e")

        checkAccountPage(params)
        [
                address: params.address, tokenType: 'MNT', scoreMap: scoreMap, scoreAddress: scoreAddress
        ]
    }

    def scoreDetail(String tokenType) {
        return scoreMap.get(tokenType)
    }

    def transferToken() {
        def currentAddress = params.address
        def tokenType = params.tokenType

        [address: currentAddress, scoreMap: scoreMap, tokenType: tokenType]
    }

    def transferIdolToken() {
        def currentAddress = params.address
        def tokenType = params.tokenType

        [address: currentAddress, scoreMap: scoreMap, tokenType: tokenType]
    }

    def transfer(params) {
        def currentAddress = params.fromAddress
        def scoreAddress = scoreMap.getOrDefault(params.tokenType, "cx0bce5bfe899c4beec7ea93f2000e16351191017e")
        def toAddress = params.toAddress
        String transferAmount = params.tokenAmount
        String tokenId = params?.tokenId
        KeyWallet currentWallet = IconConfiguration.getWalletByAddress(currentAddress)
        def tokenBalance = iconmainService.getTokenBalance(currentWallet, scoreAddress) // initial balance
        def transactionHash
        def tokens = []

        if (params.tokenType == 'IDOL') {
            def approvalTransactionHash = iconmainService.approveTransaction(currentWallet, scoreAddress, toAddress, tokenId)
            transactionHash = iconmainService.sendTransaction(currentWallet, scoreAddress, toAddress, tokenId)
            tokens = iconmainService.getAllTokensOf(currentWallet, scoreAddress)
        } else
            transactionHash = iconmainService.transfer(currentWallet, scoreAddress, currentWallet.getAddress(), toAddress, transferAmount)
        def remainingBalance = iconmainService.getTokenBalance(currentWallet, scoreAddress)
        def icxbalance = iconmainService.balanceOfICX(currentAddress)

        [
                address       : currentWallet.getAddress(), toAddress: toAddress, scoreAddress: scoreAddress,
                transferAmount: transferAmount, transactionHash: transactionHash, remainingBalance: remainingBalance,
                tokenBalance  : tokenBalance, ICXbalance: icxbalance, tokenType: params.tokenType,
                tokenList     : tokens
        ]
    }

    def checkAccount() {
        def currentAddress = params.address ? params.address : "hx65f6e18d378b57612a28f72acb97021eaa82aa5a"
        String tokenType = params.tokenType ? params.tokenType : "IDOL"
        String scoreAddress = scoreMap.getOrDefault(tokenType, "cx0bce5bfe899c4beec7ea93f2000e16351191017e")

        [address: currentAddress, scoreMap: scoreMap, tokenType: tokenType, scoreAddress: scoreAddress]
    }

    // TODO disabled the need of keystore right now,
    // changse will require the need of keystore for the entered address to see the account page
    def checkAccountPage(params) {
        def currentAddress = params.address ? params.address : "hx65f6e18d378b57612a28f72acb97021eaa82aa5a"
        String tokenType = params.tokenType ? params.tokenType : "IDOL"
        String scoreAddress = scoreMap.getOrDefault(tokenType, "cx0bce5bfe899c4beec7ea93f2000e16351191017e")
        def icxbalance = null
        def tokenBalance = null
        def tokens = []
        try {
            KeyWallet currentWallet = IconConfiguration.getWalletByAddress(currentAddress)
            icxbalance = iconmainService.balanceOfICX(currentAddress)
            tokenBalance = iconmainService.getTokenBalance(currentWallet, scoreAddress)

            if (params.tokenType == "IDOL") {
                tokens = iconmainService.getAllTokensOf(currentWallet, scoreAddress)
            }
        } catch (Exception ex) {
            println "Invalid Keystore Error:" + ex.toString()
            icxbalance = iconmainService.balanceOfICX(currentAddress)
            tokenBalance = iconmainService.getTokenBalance(currentAddress, scoreAddress)
            if (params.tokenType == "IDOL") {
                tokens = iconmainService.getAllTokensOf(currentAddress, scoreAddress)
            }
        }

        [
                address  : currentAddress, tokenType: tokenType, tokenBalance: tokenBalance, ICXbalance: icxbalance, scoreAddress: scoreAddress, scoreMap: scoreMap,
                tokenList: tokens
        ]
    }

    def checkTokenDetails(params) {
        String currentAddress = params.address ? params.address : "hx65f6e18d378b57612a28f72acb97021eaa82aa5a"
        String tokenId = params.tokenId ? params.tokenId : ""
        String tokenType = params.tokenType ? params.tokenType : "IDOL"
        String scoreAddress = scoreMap.getOrDefault(tokenType, "cx0bce5bfe899c4beec7ea93f2000e16351191017e")
        def tokenInfo = iconmainService.getTokenInfo(currentAddress, scoreAddress, tokenId)
        [
                address     : tokenInfo.owner,
                tokenType   : tokenType,
                scoreAddress: scoreAddress,
                scoreMap    : scoreMap,
                name        : tokenInfo.name,
                age         : tokenInfo.age,
                gender      : tokenInfo.gender,
                ipfs_handle : tokenInfo.ipfs_handle
        ]
    }
}
