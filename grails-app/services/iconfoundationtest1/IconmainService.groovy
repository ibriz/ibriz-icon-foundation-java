package iconfoundationtest1

import foundation.icon.icx.*
import foundation.icon.icx.data.Address
import foundation.icon.icx.data.Bytes
import foundation.icon.icx.transport.http.HttpProvider
import foundation.icon.icx.transport.jsonrpc.RpcItem
import foundation.icon.icx.transport.jsonrpc.RpcObject
import foundation.icon.icx.transport.jsonrpc.RpcValue
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class IconmainService {
    private IconService iconService;

    def load() {
        final String URL = "http://192.168.1.9:9000/api/v3";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        iconService = new IconService(new HttpProvider(httpClient, URL as String));

    }

    def transfer(KeyWallet wallet, scoreAddress, fromAddress, toAddress, value) {
        long timestamp = System.currentTimeMillis() * 1000L;
        RpcObject params = new RpcObject.Builder()
                .put("_to", new RpcValue(toAddress as String))
                .put("_value", new RpcValue(value as String))
                .build();

        Transaction transaction = TransactionBuilder.of(IconConfiguration.NETWORK_ID)
                .from(fromAddress)
                .to(new Address(scoreAddress))
                .stepLimit(IconConfiguration.STEP_LIMIT)
                .timestamp(new BigInteger(Long.toString(timestamp)))
                .nonce(IconConfiguration.NOUNCE)
                .call("transfer")
                .params(params)
                .build();

        SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
        Bytes hash = iconService.sendTransaction(signedTransaction).execute();
        hash
    }

    def getTokenBalance(KeyWallet currentWallet, scoreAddressStr) {
        Address currentAddress = currentWallet.getAddress();
        Address scoreAddress = new Address(scoreAddressStr);

        RpcObject params = new RpcObject.Builder()
                .put("_owner", new RpcValue(currentAddress))
                .build();
        IcxCall<RpcItem> call1 = new IcxCall.Builder()
                .from(currentAddress)
                .to(scoreAddress)
                .method("balanceOf")
                .params(params)
                .build();
        RpcItem result = iconService.query(call1).execute();
        result.asInteger()
    }

    def balanceOfICX(address) {
        BigInteger balance = iconService.getBalance(new Address(address)).execute();
        balance

    }
}
