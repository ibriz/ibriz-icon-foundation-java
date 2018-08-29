package ibriz.iconfoundation

import foundation.icon.icx.KeyWallet

class IconmainController {
    public final String URL = "http://192.168.1.9:9000/api/v3";

    def iconmainService

//    String scoreAddress = "cx02b13428a8aef265fbaeeb37394d3ae8727f7a19";
//    String tokenSymbol = "MNT"
//    String toAddStr = "hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31";

    Map<String, String> scoreMap = new HashMap<String, String>() {
        {
            put("MNT", "cxb8f2c9ba48856df2e889d1ee30ff6d2e002651cf")
        }
    }

    def index() {}

    def myWallet() {
        def currentAddress = params.address
        String scoreAddress = scoreMap.getOrDefault(params.tokenType, "")
        KeyWallet currentWallet = IconConfiguration.getWalletByAddress(currentAddress)

        def icxbalance = iconmainService.balanceOfICX(currentAddress)
        def tokenBalance = iconmainService.getTokenBalance(currentWallet, scoreAddress)
        [
                address: currentAddress, tokenType: params.tokenType, tokenBalance: tokenBalance, ICXbalance: icxbalance, scoreAddress: scoreAddress, scoreMap: scoreMap
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

    def transfer(params) {
        def currentAddress = params.fromAddress
        def scoreAddress = scoreMap.getOrDefault(params.tokenType, "")
        def toAddress = params.toAddress
        String transferAmount = params.tokenAmount

        KeyWallet currentWallet = IconConfiguration.getWalletByAddress(currentAddress)
        def tokenBalance = iconmainService.getTokenBalance(currentWallet, scoreAddress) // initial balance
        def transactionHash = iconmainService.transfer(currentWallet, scoreAddress, currentWallet.getAddress(), toAddress, transferAmount)
        def remainingBalance = iconmainService.getTokenBalance(currentWallet, scoreAddress)
        def icxbalance = iconmainService.balanceOfICX(currentAddress)

        [
                address       : currentWallet.getAddress(), toAddress: toAddress, scoreAddress: scoreAddress,
                transferAmount: transferAmount, transactionHash: transactionHash, remainingBalance: remainingBalance,
                tokenBalance  : tokenBalance, ICXbalance: icxbalance, tokenType: params.tokenType
        ]
    }
}
