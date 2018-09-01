package ibriz.testmodules;

import foundation.icon.icx.Call;
import foundation.icon.icx.IconService;
import foundation.icon.icx.data.Address;
import foundation.icon.icx.data.Block;
import foundation.icon.icx.transport.http.HttpProvider;
import foundation.icon.icx.transport.jsonrpc.RpcItem;
import foundation.icon.icx.transport.jsonrpc.RpcObject;
import foundation.icon.icx.transport.jsonrpc.RpcValue;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.math.BigInteger;

public class TestClass {
    public static void main(String[] args) {
        String URL = "http://192.168.1.9:9000/api/v3";
        Address scoreAddress = new Address("cx0bce5bfe899c4beec7ea93f2000e16351191017e");
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();
        IconService iconService = new IconService(new HttpProvider(httpClient, URL));
        BigInteger height = BigInteger.ONE;
        Block block = null;
        try {
            block = iconService.getBlock(height).execute();
            System.out.println("block:" + block.toString());


            BigInteger totalSupply = iconService.getTotalSupply().execute();
            System.out.println("totalSupply:" + totalSupply);


            HttpLoggingInterceptor loggning = new HttpLoggingInterceptor();
            loggning.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient1 = new OkHttpClient.Builder()
                    .addInterceptor(loggning)
                    .build();

            Address fromAddress = new Address("hx65f6e18d378b57612a28f72acb97021eaa82aa5a");
            Address toAddress = new Address("hx1000000000000000000000000000000000000000");


            /*
             * BALANCE OF CONTRACT
             * */
            Address firstAddress = new Address("hx65f6e18d378b57612a28f72acb97021eaa82aa5a");

            RpcObject params = new RpcObject.Builder()
                    .put("_owner", new RpcValue(firstAddress))
                    .build();

            Call<RpcItem> call = new Call.Builder()
                    .from(firstAddress)
                    .to(scoreAddress)
                    .method("balanceOf")
                    .params(params)
                    .build();

            RpcItem result = iconService.call(call).execute();
            System.out.println(firstAddress + " :result:" + result.asInteger());


            Address secondAddress = new Address("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
            Call<RpcItem> call1 = new Call.Builder()
                    .from(secondAddress)
                    .to(scoreAddress)
                    .method("balanceOf")
                    .params(new RpcObject.Builder()
                            .put("_owner", new RpcValue(secondAddress))
                            .build())
                    .build();

            RpcItem result1 = iconService.call(call1).execute();
            System.out.println(secondAddress + " :result:" + result1.asInteger());
//            RpcObject params = new RpcObject.Builder()
//                    .put("_owner", new RpcValue(fromAddress))
//                    .build();
//
//            IcxCall<RpcItem> call = new IcxCall.Builder()
//                    .from(fromAddress)
//                    .to(scoreAddress)
//                    .method("transfer")
//                    .params(params)
//                    .build();
//
//            RpcItem result = iconService.query(call).execute();
//            System.out.println("result:"+result.asInteger());


//            List<ScoreApi> apis = iconService.getScoreApi(scoreAddress).execute();
//            System.out.println("apis:" + apis);
//
//
//            RpcObject params1 = new RpcObject.Builder()
//
//                    .put("_to", new RpcValue(toAddress))
//                    .put("_value", new RpcValue(new BigInteger("11")))
//                    .build();
/*

//            IcxCall<RpcItem> call1 = new IcxCall.Builder()
//                    .from(fromAddress)
//                    .to(scoreAddress)
//                    .method("transfer")
//                    .params(params1)
//                    .build();
//
//            RpcItem result1 = iconService.query(call1).execute();
*/


            /*
             *
             * CREATING TRANSACTION - TEST 1 - STILL NOT WORKING
             * */
//            String PRIVATE_KEY_STRING =
//                    "2d42994b2f7735bbc93a3e64381864d06747e574aa94655c516f9ad0a74eed79";
//            Wallet  wallet = KeyWallet.load(PRIVATE_KEY_STRING);
//            BigInteger networkId = new BigInteger("3");
//            Address fromAddress1 = wallet.getAddress();
//            Address toAddress1 = new Address("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
//            BigInteger value = IconAmount.of("1", 18).toLoop();
//            BigInteger stepLimit = new BigInteger("75000");
//            long timestamp = System.currentTimeMillis() * 1000L;
//            BigInteger nonce = new BigInteger("1");
//            String methodName = "transfer";
//
//
//            RpcObject params = new RpcObject.Builder()
//                    .put("_to", new RpcValue(toAddress1))
//                    .put("_value", new RpcValue(value))
//                    .build();
//
//            Transaction transaction = TransactionBuilder.of(networkId)
//                    .from(fromAddress1)
//                    .to(scoreAddress)
//                    .stepLimit(stepLimit)
//                    .timestamp(new BigInteger(Long.toString(timestamp)))
//                    .nonce(nonce)
//                    .call(methodName)
//                    .params(params)
//                    .build();
//
//            SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
//            Bytes hash = iconService.sendTransaction(signedTransaction).execute();
//            System.out.println("txHash:"+hash);


            /*
             * CREATING TRANSACTION - TEST 2
             * */

            // Generates a wallet.
//            try {
//                Wallet wallet = KeyWallet.create();
            // Loads a wallet from the private key.
//                Wallet wallet = KeyWallet.load(new Bytes("0x0000"));

// Loads a wallet from the key store file.
//                File file = new File("./key.keystore");
//                Wallet wallet = KeyWallet.load("password", file);

// Stores the keystore on the file path.
//                File dir = new File(".");
//                KeyStoreWallet.store(wallet, "password", dir); // throw exception if an error exists.
            // sending icx
//                Transaction transaction = TransactionBuilder.of(new BigInteger("3"))
//                        .from(wallet.getAddress())
//                        .to(scoreAddress)
//                        .value(new BigInteger("150000000"))
//                        .timestamp(new BigInteger(Long.toString(System.currentTimeMillis() * 1000L)))
//                        .stepLimit(new BigInteger("1000000"))
//                        .nonce(new BigInteger("1000000"))
//                        .build();
//
//            } catch (InvalidAlgorithmParameterException e) {
//                e.printStackTrace();
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (NoSuchProviderException e) {
//                e.printStackTrace();
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
